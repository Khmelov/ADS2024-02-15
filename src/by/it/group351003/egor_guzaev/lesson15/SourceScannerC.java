package by.it.group351003.egor_guzaev.lesson15;import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SourceScannerC {
    public static void main(String[] args) {
        String srcDir = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<FileEntry> fileEntries = new ArrayList<>();

        try {
            // Чтение всех файлов .java
            Files.walk(Path.of(srcDir))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            String content = Files.readString(path);

                            // Исключаем тесты
                            if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                                String processed = processContent(content);
                                if (!processed.isEmpty()) {
                                    String relativePath = Path.of(srcDir).relativize(path).toString();
                                    fileEntries.add(new FileEntry(relativePath, processed));
                                }
                            }
                        } catch (MalformedInputException e) {
                            System.err.println("Malformed input: " + path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            // Сравнение текстов по метрике Левенштейна
            Map<FileEntry, List<FileEntry>> duplicates = findDuplicates(fileEntries);

            // Вывод результатов
            duplicates.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparing(f -> f.path)))
                    .forEach(entry -> {
                        System.out.println(entry.getKey().path);
                        entry.getValue().stream()
                                .map(f -> "  " + f.path)
                                .sorted()
                                .forEach(System.out::println);
                    });

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
        processed = processed.replaceAll("[\\x00-\\x20]+", " "); // Замена символов <33 на пробел
        return processed.strip();
    }

    private static Map<FileEntry, List<FileEntry>> findDuplicates(List<FileEntry> files) {
        Map<FileEntry, List<FileEntry>> duplicates = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            for (int j = i + 1; j < files.size(); j++) {
                FileEntry file1 = files.get(i);
                FileEntry file2 = files.get(j);

                if (Math.abs(file1.content.length() - file2.content.length()) > 10) {
                    continue; // Быстрое исключение файлов сильно разного размера
                }

                int distance = optimizedLevenshtein(file1.content, file2.content);
                if (distance < 10) {
                    duplicates.computeIfAbsent(file1, k -> new ArrayList<>()).add(file2);
                }
            }
        }
        return duplicates;
    }

    private static int optimizedLevenshtein(String s1, String s2) {
        int[] prev = new int[s2.length() + 1];
        int[] curr = new int[s2.length() + 1];

        for (int j = 0; j <= s2.length(); j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            curr[0] = i;
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            System.arraycopy(curr, 0, prev, 0, curr.length);
        }

        return curr[s2.length()];
    }

    static class FileEntry {
        String path;
        String content;

        public FileEntry(String path, String content) {
            this.path = path;
            this.content = content;
        }
    }
}
