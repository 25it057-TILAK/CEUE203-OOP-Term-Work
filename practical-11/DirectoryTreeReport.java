import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

public class DirectoryTreeReport {

    public static void main(String[] args) {

        Path rootFolder = Paths.get("SampleFolder");
        Path reportFile = Paths.get("directory_report.txt");

        try {
            // Create sample folder structure
            Files.createDirectories(rootFolder.resolve("Documents"));
            Files.createDirectories(rootFolder.resolve("Images"));

            Files.write(
                rootFolder.resolve("readme.txt"),
                "This is a sample file.".getBytes()
            );

            Files.write(
                rootFolder.resolve("Documents/notes.txt"),
                "Java NIO practical.".getBytes()
            );

            Files.write(
                rootFolder.resolve("Images/photo.txt"),
                "Sample image information.".getBytes()
            );

            StringBuilder report = new StringBuilder();

            report.append("DIRECTORY TREE REPORT\n");
            report.append("=====================\n");
            report.append("Root Folder: ").append(rootFolder).append("\n\n");

            final int[] fileCount = {0};
            final long[] totalSize = {0};

            // Walk through the folder tree
            Files.walkFileTree(rootFolder, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(
                        Path file, BasicFileAttributes attributes) {

                    fileCount[0]++;
                    totalSize[0] += attributes.size();

                    report.append("File Name: ")
                          .append(file.getFileName())
                          .append("\n");

                    report.append("Path: ")
                          .append(file.toAbsolutePath())
                          .append("\n");

                    report.append("Size: ")
                          .append(attributes.size())
                          .append(" bytes\n");

                    report.append("Modified Time: ")
                          .append(Instant.ofEpochMilli(
                                  attributes.lastModifiedTime().toMillis()))
                          .append("\n");

                    report.append("--------------------------\n");

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(
                        Path file, IOException exception) {

                    report.append("Unable to access: ")
                          .append(file)
                          .append("\n");

                    return FileVisitResult.CONTINUE;
                }
            });

            report.append("\nSUMMARY\n");
            report.append("Total Files: ").append(fileCount[0]).append("\n");
            report.append("Total Size: ").append(totalSize[0])
                  .append(" bytes\n");

            // Save report to file
            Files.write(
                reportFile,
                report.toString().getBytes()
            );

            System.out.println(report);
            System.out.println("Report saved to: "
                    + reportFile.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}