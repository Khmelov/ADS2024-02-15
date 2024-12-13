package by.it.group310901.brylev.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SourceScannerB {

    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;

        try {
            List<FileData> results = Files.walk(Paths.get(src))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .map(SourceScannerB::processFile)
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
            content = removeComments(content);
            content = cleanText(content);

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

    private static String removeComments(String content) {
        StringBuilder result = new StringBuilder();
        boolean inBlockComment = false;

        for (int i = 0; i < content.length(); i++) {
            if (i < content.length() - 1 && content.charAt(i) == '/' && content.charAt(i + 1) == '*') {
                inBlockComment = true;
                i++;
            } else if (inBlockComment && i < content.length() - 1 && content.charAt(i) == '*' && content.charAt(i + 1) == '/') {
                inBlockComment = false;
                i++;
            } else if (inBlockComment) {
                continue;
            }
            else if (i < content.length() - 1 && content.charAt(i) == '/' && content.charAt(i + 1) == '/') {
                while (i < content.length() && content.charAt(i) != '\n') {
                    i++;
                }
            } else {
                result.append(content.charAt(i));
            }
        }
        return result.toString();
    }

    private static String cleanText(String content) {
        String[] lines = content.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) {
                result.append(trimmedLine).append("\n");
            }
        }

        int start = 0, end = result.length();

        while (start < end && result.charAt(start) < 33) {
            start++;
        }

        while (end > start && result.charAt(end - 1) < 33) {
            end--;
        }

        return result.substring(start, end);
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

