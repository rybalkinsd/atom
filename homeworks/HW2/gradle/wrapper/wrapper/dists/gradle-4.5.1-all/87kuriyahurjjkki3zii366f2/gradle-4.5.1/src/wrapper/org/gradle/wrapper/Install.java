/*
 * Copyright 2010 the original author or authors.
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

package org.gradle.wrapper;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.security.MessageDigest;

public class Install {
    public static final String DEFAULT_DISTRIBUTION_PATH = "wrapper/dists";
    private final Logger logger;
    private final IDownload download;
    private final PathAssembler pathAssembler;
    private final ExclusiveFileAccessManager exclusiveFileAccessManager = new ExclusiveFileAccessManager(120000, 200);

    public Install(Logger logger, IDownload download, PathAssembler pathAssembler) {
        this.logger = logger;
        this.download = download;
        this.pathAssembler = pathAssembler;
    }

    public File createDist(final WrapperConfiguration configuration) throws Exception {
        final URI distributionUrl = configuration.getDistribution();
        final String distributionSha256Sum = configuration.getDistributionSha256Sum();

        final PathAssembler.LocalDistribution localDistribution = pathAssembler.getDistribution(configuration);
        final File distDir = localDistribution.getDistributionDir();
        final File localZipFile = localDistribution.getZipFile();

        return exclusiveFileAccessManager.access(localZipFile, new Callable<File>() {
            public File call() throws Exception {
                final File markerFile = new File(localZipFile.getParentFile(), localZipFile.getName() + ".ok");
                if (distDir.isDirectory() && markerFile.isFile()) {
                    return getAndVerifyDistributionRoot(distDir, distDir.getAbsolutePath());
                }

                boolean needsDownload = !localZipFile.isFile();
                URI safeDistributionUrl = Download.safeUri(distributionUrl);

                if (needsDownload) {
                    File tmpZipFile = new File(localZipFile.getParentFile(), localZipFile.getName() + ".part");
                    tmpZipFile.delete();
                    logger.log("Downloading " + safeDistributionUrl);
                    download.download(distributionUrl, tmpZipFile);
                    tmpZipFile.renameTo(localZipFile);
                }

                List<File> topLevelDirs = listDirs(distDir);
                for (File dir : topLevelDirs) {
                    logger.log("Deleting directory " + dir.getAbsolutePath());
                    deleteDir(dir);
                }

                verifyDownloadChecksum(configuration.getDistribution().toString(), localZipFile, distributionSha256Sum);

                logger.log("Unzipping " + localZipFile.getAbsolutePath() + " to " + distDir.getAbsolutePath());
                unzip(localZipFile, distDir);

                File root = getAndVerifyDistributionRoot(distDir, safeDistributionUrl.toString());
                setExecutablePermissions(root);
                markerFile.createNewFile();

                return root;
            }
        });
    }

    private String calculateSha256Sum(File file)
            throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        InputStream fis = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[4096];
        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                md.update(buffer, 0, n);
            }
        }
        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i=0; i < byteData.length; i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private File getAndVerifyDistributionRoot(File distDir, String distributionDescription)
            throws Exception {
        List<File> dirs = listDirs(distDir);
        if (dirs.isEmpty()) {
            throw new RuntimeException(String.format("Gradle distribution '%s' does not contain any directories. Expected to find exactly 1 directory.", distributionDescription));
        }
        if (dirs.size() != 1) {
            throw new RuntimeException(String.format("Gradle distribution '%s' contains too many directories. Expected to find exactly 1 directory.", distributionDescription));
        }
        return dirs.get(0);
    }

    private void verifyDownloadChecksum(String sourceUrl, File localZipFile, String expectedSum) throws Exception {
        if (expectedSum != null) {
            // if a SHA-256 hash sum has been defined in gradle-wrapper.properties, verify it here
            String actualSum = calculateSha256Sum(localZipFile);
            if (!expectedSum.equals(actualSum)) {
                localZipFile.delete();
                String message = String.format("Verification of Gradle distribution failed!%n"
                        + "%n"
                        + "Your Gradle distribution may have been tampered with.%n"
                        + "Confirm that the 'distributionSha256Sum' property in your gradle-wrapper.properties file is correct and you are downloading the wrapper from a trusted source.%n"
                        + "%n"
                        + " Distribution Url: %s%n"
                        + "Download Location: %s%n"
                        + "Expected checksum: '%s'%n"
                        + "  Actual checksum: '%s'%n",
                    sourceUrl, localZipFile.getAbsolutePath(), expectedSum, actualSum
                );
                System.err.println(message);
                System.exit(1);
            }
        }
    }

    private List<File> listDirs(File distDir) {
        List<File> dirs = new ArrayList<File>();
        if (distDir.exists()) {
            for (File file : distDir.listFiles()) {
                if (file.isDirectory()) {
                    dirs.add(file);
                }
            }
        }
        return dirs;
    }

    private void setExecutablePermissions(File gradleHome) {
        if (isWindows()) {
            return;
        }
        File gradleCommand = new File(gradleHome, "bin/gradle");
        String errorMessage = null;
        try {
            ProcessBuilder pb = new ProcessBuilder("chmod", "755", gradleCommand.getCanonicalPath());
            Process p = pb.start();
            if (p.waitFor() == 0) {
                logger.log("Set executable permissions for: " + gradleCommand.getAbsolutePath());
            } else {
                BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
                Formatter stdout = new Formatter();
                String line;
                while ((line = is.readLine()) != null) {
                    stdout.format("%s%n", line);
                }
                errorMessage = stdout.toString();
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
        } catch (InterruptedException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            logger.log("Could not set executable permissions for: " + gradleCommand.getAbsolutePath());
            logger.log("Please do this manually if you want to use the Gradle UI.");
        }
    }

    private boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.US);
        if (osName.indexOf("windows") > -1) {
            return true;
        }
        return false;
    }

    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    private void unzip(File zip, File dest) throws IOException {
        Enumeration entries;
        ZipFile zipFile = new ZipFile(zip);

        try {
            entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (entry.isDirectory()) {
                    (new File(dest, entry.getName())).mkdirs();
                    continue;
                }

                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(dest, entry.getName())));
                try {
                    copyInputStream(zipFile.getInputStream(entry), outputStream);
                } finally {
                    outputStream.close();
                }
            }
        } finally {
            zipFile.close();
        }
    }

    private void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();
    }


}
