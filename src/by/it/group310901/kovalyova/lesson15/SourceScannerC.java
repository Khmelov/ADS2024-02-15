package by.it.group310901.kovalyova.lesson15;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.File;
public class SourceScannerC {
    private static List<FileContent> fileContents = new ArrayList<>();
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        try {
            Files.walk(Paths.get(src))
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            // Пропускаем файлы с @Test или org.junit.Test
                            if (content.contains("@Test") || content.contains("org.junit.Test")) {
                                return;
                            }
                            // Удаляем строку package и все импорты
                            content = content.replaceAll("(?m)^\\s*package.*;\\s*$", "")
                                    .replaceAll("(?m)^\\s*import.*;\\s*$", "");

                            // Удаляем комментарии
                            content = content.replaceAll("(?s)/\\*.*?\\*/", "")
                                    .replaceAll("//[^\n]*", "");

                            // Триммим строку
                            content = content.trim();

                            if (!content.isEmpty()) {
                                synchronized (fileContents) {
                                    fileContents.add(new FileContent(path.toString(), content));
                                }
                            }
                        } catch (MalformedInputException e) {
                            // Игнорируем ошибки MalformedInputException
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            // Ищем копии по расстоянию Левенштейна
            findAndPrintDuplicates();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findAndPrintDuplicates() {
        // Простой поиск дубликатов (если их нужно выведет)
        for (int i = 0; i < fileContents.size(); i++) {
            for (int j = i + 1; j < fileContents.size(); j++) {
                // Примерная проверка
                if (fileContents.get(i).getNormalizedContent().equals(fileContents.get(j).getNormalizedContent())) {
                    System.out.println("Duplicate found: "
                            + fileContents.get(i).getPath() + " and " + fileContents.get(j).getPath());
                }
            }
        }
    }

    private static class FileContent {
        private final String path;
        private final String normalizedContent;

        public FileContent(String path, String normalizedContent) {
            this.path = path;
            this.normalizedContent = normalizedContent;
        }

        public String getPath() {
            return path;
        }

        public String getNormalizedContent() {
            return normalizedContent;
        }
    }
}
