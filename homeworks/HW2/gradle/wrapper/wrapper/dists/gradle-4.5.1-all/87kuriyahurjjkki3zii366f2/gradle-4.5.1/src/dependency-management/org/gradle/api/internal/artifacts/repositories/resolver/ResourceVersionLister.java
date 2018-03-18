/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.repositories.resolver;

import com.google.common.collect.Lists;
import org.apache.ivy.core.IvyPatternHelper;
import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.internal.component.model.IvyArtifactName;
import org.gradle.internal.resolve.result.BuildableModuleVersionListingResolveResult;
import org.gradle.internal.resource.ExternalResourceName;
import org.gradle.internal.resource.ExternalResourceRepository;
import org.gradle.internal.resource.ResourceExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceVersionLister implements VersionLister {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceVersionLister.class);
    private static final String REVISION_TOKEN = IvyPatternHelper.getTokenString(IvyPatternHelper.REVISION_KEY);
    public static final int REV_TOKEN_LENGTH = REVISION_TOKEN.length();

    private final ExternalResourceRepository repository;
    private final String fileSeparator = "/";
    private final Set<ExternalResourceName> visitedDirectories = new HashSet<ExternalResourceName>();

    public ResourceVersionLister(ExternalResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void listVersions(ModuleIdentifier module, IvyArtifactName artifact, List<ResourcePattern> patterns, BuildableModuleVersionListingResolveResult result) {
        List<String> collector = Lists.newArrayList();
        for (ResourcePattern pattern : patterns) {
            visit(pattern, artifact, module, collector, result);
        }
        // TODO:DAZ Be a bit smarter about this
        if (!collector.isEmpty()) {
            result.listed(collector);
        }
    }

    private void visit(ResourcePattern pattern, IvyArtifactName artifact, ModuleIdentifier module, List<String> collector, BuildableModuleVersionListingResolveResult result) {
        ExternalResourceName versionListPattern = pattern.toVersionListPattern(module, artifact);
        LOGGER.debug("Listing all in {}", versionListPattern);
        try {
            List<String> versionStrings = listRevisionToken(versionListPattern, result);
            for (String versionString : versionStrings) {
                collector.add(versionString);
            }
        } catch (Exception e) {
            throw ResourceExceptions.failure(versionListPattern.getUri(), String.format("Could not list versions using %s.", pattern), e);
        }
    }

    // lists all the values a revision token listed by a given url lister
    private List<String> listRevisionToken(ExternalResourceName versionListPattern, BuildableModuleVersionListingResolveResult result) {
        String pattern = versionListPattern.getPath();
        if (!pattern.contains(REVISION_TOKEN)) {
            LOGGER.debug("revision token not defined in pattern {}.", pattern);
            return Collections.emptyList();
        }
        String prefix = pattern.substring(0, pattern.indexOf(REVISION_TOKEN));
        if (revisionMatchesDirectoryName(pattern)) {
            ExternalResourceName parent = versionListPattern.getRoot().resolve(prefix);
            return listAll(parent, result);
        } else {
            int parentFolderSlashIndex = prefix.lastIndexOf(fileSeparator);
            String revisionParentFolder = parentFolderSlashIndex == -1 ? "" : prefix.substring(0, parentFolderSlashIndex + 1);
            ExternalResourceName parent = versionListPattern.getRoot().resolve(revisionParentFolder);
            LOGGER.debug("using {} to list all in {} ", repository, revisionParentFolder);
            if (!visitedDirectories.add(parent)) {
                return Collections.emptyList();
            }
            result.attempted(parent);
            List<String> all = repository.resource(parent).list();
            if (all == null) {
                return Collections.emptyList();
            }
            LOGGER.debug("found {} urls", all.size());
            Pattern regexPattern = createRegexPattern(pattern, parentFolderSlashIndex);
            List<String> ret = filterMatchedValues(all, regexPattern);
            LOGGER.debug("{} matched {}", ret.size(), pattern);
            return ret;
        }
    }

    private List<String> filterMatchedValues(List<String> all, final Pattern p) {
        List<String> ret = new ArrayList<String>(all.size());
        for (String path : all) {
            Matcher m = p.matcher(path);
            if (m.matches()) {
                String value = m.group(1);
                ret.add(value);
            }
        }
        return ret;
    }

    private Pattern createRegexPattern(String pattern, int prefixLastSlashIndex) {
        int endNameIndex = pattern.indexOf(fileSeparator, prefixLastSlashIndex + 1);
        String namePattern;
        if (endNameIndex != -1) {
            namePattern = pattern.substring(prefixLastSlashIndex + 1, endNameIndex);
        } else {
            namePattern = pattern.substring(prefixLastSlashIndex + 1);
        }
        namePattern = namePattern.replaceAll("\\.", "\\\\.");

        String acceptNamePattern = namePattern.replaceAll("\\[revision\\]", "(.+)");
        return Pattern.compile(acceptNamePattern);
    }

    private boolean revisionMatchesDirectoryName(String pattern) {
        int startToken = pattern.indexOf(REVISION_TOKEN);
        if (startToken > 0 && !pattern.substring(startToken - 1, startToken).equals(fileSeparator)) {
            // previous character is not a separator
            return false;
        }
        int endToken = startToken + REV_TOKEN_LENGTH;
        if (endToken < pattern.length() && !pattern.substring(endToken, endToken + 1).equals(fileSeparator)) {
            // next character is not a separator
            return false;
        }
        return true;
    }

    private List<String> listAll(ExternalResourceName parent, BuildableModuleVersionListingResolveResult result)  {
        if (!visitedDirectories.add(parent)) {
            return Collections.emptyList();
        }
        LOGGER.debug("using {} to list all in {}", repository, parent);
        result.attempted(parent.toString());
        List<String> paths = repository.resource(parent).list();
        if (paths == null) {
            return Collections.emptyList();
        }
        LOGGER.debug("found {} resources", paths.size());
        return paths;
    }
}
