package by.it.group310901.fomina.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SourceScannerB {
    public static void main(String[] args) {
        String srcDir = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<FileEntry> results = new ArrayList<>();

        try {
            Files.walk(Path.of(srcDir))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            String content = Files.readString(path);

                            if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                                String processed = processContent(content);
                                int sizeInBytes = processed.getBytes().length;
                                String relativePath = Path.of(srcDir).relativize(path).toString();
                                results.add(new FileEntry(relativePath, sizeInBytes));
                            }
                        } catch (MalformedInputException e) {
                            System.err.println("Malformed input: " + path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            results.sort(Comparator.comparingInt((FileEntry f) -> f.size)
                    .thenComparing(f -> f.path));

            for (FileEntry entry : results) {
                System.out.println(entry.size + " " + entry.path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processContent(String content) {
        StringBuilder sb = new StringBuilder();
        String[] lines = content.split("\n");

        for (String line : lines) {
            line = line.strip();

            if (line.startsWith("package") || line.startsWith("import") || line.isEmpty()) {
                continue;
            }
            if (line.startsWith("//")) {
                continue;
            }
            sb.append(line).append("\n");
        }

        String processed = sb.toString();
        processed = removeNonPrintable(processed);
        return processed.stripTrailing();
    }

    private static String removeNonPrintable(String text) {
        int start = 0, end = text.length();

        while (start < end && text.charAt(start) < 33) {
            start++;
        }
        while (end > start && text.charAt(end - 1) < 33) {
            end--;
        }

        return text.substring(start, end);
    }

    static class FileEntry {
        String path;
        int size;

        public FileEntry(String path, int size) {
            this.path = path;
            this.size = size;
        }
    }
}