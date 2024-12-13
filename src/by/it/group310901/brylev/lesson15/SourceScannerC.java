package by.it.group310901.brylev.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SourceScannerC {

    public static void main(String[] args) {
        String src = STR."\{System.getProperty("user.dir")}\{File.separator}src\{File.separator}";

        try {
            List<FileData> fileDataList = Files.walk(Paths.get(src))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .map(SourceScannerC::processFile)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(FileData::getRelativePath))
                    .toList();

            Map<String, List<String>> similarFiles = findSimilarFiles(fileDataList);

            similarFiles.forEach((key, value) -> {
                System.out.println(key);
                value.forEach(System.out::println);
            });

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
            content = normalizeContent(content);

            String relativePath = file.getPath().replace(System.getProperty("user.dir") + File.separator, "");

            return new FileData(relativePath, content);

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

    private static String normalizeContent(String content) {
        StringBuilder normalized = new StringBuilder();

        for (char c : content.toCharArray()) {
            if (c < 33) {
                normalized.append(' ');
            } else {
                normalized.append(c);
            }
        }

        return normalized.toString().trim();
    }

    private static Map<String, List<String>> findSimilarFiles(List<FileData> fileDataList) {
        Map<String, List<String>> similarFiles = new TreeMap<>();

        for (int i = 0; i < fileDataList.size(); i++) {
            FileData file1 = fileDataList.get(i);
            for (int j = i + 1; j < fileDataList.size(); j++) {
                FileData file2 = fileDataList.get(j);
                int distance = levenshteinDistance(file1.getContent(), file2.getContent());

                if (distance < 10) {
                    similarFiles
                            .computeIfAbsent(file1.getRelativePath(), k -> new ArrayList<>())
                            .add(file2.getRelativePath());
                }
            }
        }

        return similarFiles;
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[] prev = new int[s2.length() + 1];
        int[] curr = new int[s2.length() + 1];

        for (int j = 0; j <= s2.length(); j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            curr[0] = i;
            for (int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }

            System.arraycopy(curr, 0, prev, 0, curr.length);
        }

        return prev[s2.length()];
    }

    private static class FileData {
        private final String relativePath;
        private final String content;

        public FileData(String relativePath, String content) {
            this.relativePath = relativePath;
            this.content = content;
        }

        public String getRelativePath() {
            return relativePath;
        }

        public String getContent() {
            return content;
        }
    }
}
