package by.it.group310901.usov.lesson15;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

public class SourceScannerA {

    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;

        try {
            List<FileData> results = Files.walk(Paths.get(src))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .map(SourceScannerA::processFile)
                    .filter(Objects::nonNull)
                    .sorted(Comparator
                            .comparingInt(FileData::getSize)
                            .thenComparing(FileData::getRelativePath))
                    .toList();

            results.forEach(result ->
                    System.out.println(result.getSize() + " " + result.getRelativePath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileData processFile(File file) {
        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            if (content.contains("@Test") || content.contains("org.junit.Test")) {
                return null;
            }

            content = removePackageAndImports(content);
            content = trimControlCharacters(content);

            int size = content.getBytes(StandardCharsets.UTF_8).length;

            String relativePath = file.getPath().replace(System.getProperty("user.dir") + File.separator, "");

            return new FileData(size, relativePath);

        } catch (IOException e) {
            return null;
        }
    }

    private static String removePackageAndImports(String content) {
        String[] lines = content.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (!trimmedLine.startsWith("package") && !trimmedLine.startsWith("import")) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    private static String trimControlCharacters(String content) {
        int start = 0, end = content.length();

        while (start < end && content.charAt(start) < 33) {
            start++;
        }
        while (end > start && content.charAt(end - 1) < 33) {
            end--;
        }

        return content.substring(start, end);
    }

    private static class FileData {
        private final int size;
        private final String relativePath;

        public FileData(int size, String relativePath) {
            this.size = size;
            this.relativePath = relativePath;
        }

        public int getSize() {
            return size;
        }

        public String getRelativePath() {
            return relativePath;
        }
    }
}
