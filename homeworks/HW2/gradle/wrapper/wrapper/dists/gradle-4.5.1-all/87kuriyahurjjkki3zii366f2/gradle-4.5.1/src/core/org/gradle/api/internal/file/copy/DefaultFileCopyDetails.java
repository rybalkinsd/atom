/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.api.internal.file.copy;

import groovy.lang.Closure;
import org.gradle.api.Transformer;
import org.gradle.api.file.ContentFilterable;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileVisitDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.internal.file.AbstractFileTreeElement;
import org.gradle.internal.nativeintegration.filesystem.Chmod;

import java.io.*;
import java.util.Map;

public class DefaultFileCopyDetails extends AbstractFileTreeElement implements FileVisitDetails, FileCopyDetailsInternal {
    private final FileVisitDetails fileDetails;
    private final CopySpecResolver specResolver;
    private final FilterChain filterChain;
    private RelativePath relativePath;
    private boolean excluded;
    private Integer mode;
    private DuplicatesStrategy duplicatesStrategy;

    public DefaultFileCopyDetails(FileVisitDetails fileDetails, CopySpecResolver specResolver, Chmod chmod) {
        super(chmod);
        this.filterChain = new FilterChain(specResolver.getFilteringCharset());
        this.fileDetails = fileDetails;
        this.specResolver = specResolver;
        this.duplicatesStrategy = specResolver.getDuplicatesStrategy();
    }

    public boolean isIncludeEmptyDirs() {
        return specResolver.getIncludeEmptyDirs();
    }

    public String getDisplayName() {
        return fileDetails.toString();
    }

    public void stopVisiting() {
        fileDetails.stopVisiting();
    }

    public File getFile() {
        if (filterChain.hasFilters()) {
            throw new UnsupportedOperationException();
        } else {
            return fileDetails.getFile();
        }
    }

    public boolean isDirectory() {
        return fileDetails.isDirectory();
    }

    public long getLastModified() {
        return fileDetails.getLastModified();
    }

    public long getSize() {
        if (filterChain.hasFilters()) {
            ByteCountingOutputStream outputStream = new ByteCountingOutputStream();
            copyTo(outputStream);
            return outputStream.size;
        } else {
            return fileDetails.getSize();
        }
    }

    public InputStream open() {
        if (filterChain.hasFilters()) {
            return filterChain.transform(fileDetails.open());
        } else {
            return fileDetails.open();
        }
    }

    public void copyTo(OutputStream output) {
        if (filterChain.hasFilters()) {
            super.copyTo(output);
        } else {
            fileDetails.copyTo(output);
        }
    }

    public boolean copyTo(File target) {
        if (filterChain.hasFilters()) {
            return super.copyTo(target);
        } else {
            final boolean copied = fileDetails.copyTo(target);
            adaptPermissions(target);
            return copied;
        }
    }

    private void adaptPermissions(File target) {
        int specMode = getMode();
        getChmod().chmod(target, specMode);
    }

    public RelativePath getRelativePath() {
        if (relativePath == null) {
            RelativePath path = fileDetails.getRelativePath();
            relativePath = specResolver.getDestPath().append(path.isFile(), path.getSegments());
        }
        return relativePath;
    }

    public int getMode() {
        if (mode != null) {
            return mode;
        }

        Integer specMode = getSpecMode();
        if (specMode != null) {
            return specMode;
        }

        return fileDetails.getMode();
    }

    private Integer getSpecMode() {
        return fileDetails.isDirectory() ? specResolver.getDirMode() : specResolver.getFileMode();
    }

    public void setRelativePath(RelativePath path) {
        this.relativePath = path;
    }

    public void setName(String name) {
        relativePath = getRelativePath().replaceLastName(name);
    }

    public void setPath(String path) {
        relativePath = RelativePath.parse(getRelativePath().isFile(), path);
    }

    boolean isExcluded() {
        return excluded;
    }

    public void exclude() {
        excluded = true;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public ContentFilterable filter(Closure closure) {
        return filter(new ClosureBackedTransformer(closure));
    }

    @Override
    public ContentFilterable filter(Transformer<String, String> transformer) {
        filterChain.add(transformer);
        return this;
    }

    public ContentFilterable filter(Map<String, ?> properties, Class<? extends FilterReader> filterType) {
        filterChain.add(filterType, properties);
        return this;
    }

    public ContentFilterable filter(Class<? extends FilterReader> filterType) {
        filterChain.add(filterType);
        return this;
    }

    public ContentFilterable expand(Map<String, ?> properties) {
        filterChain.expand(properties);
        return this;
    }

    public void setDuplicatesStrategy(DuplicatesStrategy strategy) {
        this.duplicatesStrategy = strategy;
    }

    public DuplicatesStrategy getDuplicatesStrategy() {
        return this.duplicatesStrategy;
    }

    public String getSourceName() {
        return this.fileDetails.getName();
    }

    public String getSourcePath() {
        return this.fileDetails.getPath();
    }

    public RelativePath getRelativeSourcePath() {
        return this.fileDetails.getRelativePath();
    }

    private static class ByteCountingOutputStream extends OutputStream {
        long size;

        @Override
        public void write(int b) throws IOException {
            size++;
        }

        @Override
        public void write(byte[] b) throws IOException {
            size += b.length;
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            size += len;
        }
    }
}
