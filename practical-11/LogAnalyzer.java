import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {

    public static void main(String[] args) {

        String[] fileNames = {
            "log1.txt",
            "log2.txt",
            "log3.txt"
        };

        String keyword = "ERROR";

        // Create sample log files
        String[] contents = {
            "Application started\nERROR: Database connection failed\nUser logged in",
            "System running normally\nERROR: File not found\nApplication closed",
            "User registered\nData saved successfully\nERROR: Network problem"
        };

        try {
            for (int i = 0; i < fileNames.length; i++) {
                Files.write(
                    Paths.get(fileNames[i]),
                    contents[i].getBytes()
                );
            }

            System.out.println("Sample log files created.\n");

        } catch (IOException e) {
            System.out.println("Error creating files: " + e.getMessage());
            return;
        }

        int totalLines = 0;
        int totalKeywordLines = 0;

        // Analyze each file
        for (String fileName : fileNames) {

            Path path = Paths.get(fileName);

            try {
                List<String> lines = Files.readAllLines(path);

                int keywordCount = 0;

                for (String line : lines) {
                    totalLines++;

                    if (line.toLowerCase().contains(keyword.toLowerCase())) {
                        keywordCount++;
                        totalKeywordLines++;
                    }
                }

                BasicFileAttributes attributes =
                        Files.readAttributes(path, BasicFileAttributes.class);

                System.out.println("File: " + fileName);
                System.out.println("Total Lines: " + lines.size());
                System.out.println("Keyword Lines: " + keywordCount);
                System.out.println("File Size: " + attributes.size() + " bytes");
                System.out.println("----------------------------");

            } catch (IOException e) {
                System.out.println("Error reading " + fileName + ": "
                        + e.getMessage());
            }
        }

        System.out.println("\nOverall Log Report:");
        System.out.println("Total Lines: " + totalLines);
        System.out.println("Total Lines Containing " + keyword + ": "
                + totalKeywordLines);
    }
}