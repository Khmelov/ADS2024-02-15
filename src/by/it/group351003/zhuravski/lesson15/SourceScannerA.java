package by.it.group351003.zhuravski.lesson15;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
        import java.util.*;
        import java.util.stream.Collectors;

public class SourceScannerA {
    private static Path globalPath;
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        globalPath = Path.of(src);
        List<FileInfo> fileInfos = new ArrayList<>();

        try {
            Files.walk(Paths.get(src))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> processFile(path, fileInfos));
        } catch (IOException e) {
            System.err.println("Error walking through the directory: " + e.getMessage());
        }

        // Сортировка по размеру, затем лексикографически
        fileInfos.sort(Comparator.comparingLong(FileInfo::getSize)
                .thenComparing(FileInfo::getRelativePath));

        // Вывод информации о файлах
        for (FileInfo fileInfo : fileInfos) {
            System.out.printf("%d bytes - %s%n", fileInfo.getSize(), fileInfo.getRelativePath());
        }
    }

    private static void processFile(Path path, List<FileInfo> fileInfos) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            StringBuilder content = new StringBuilder();

            for (String line : lines) {
                // Игнорируем строки с @Test или org.junit.Test
                if (line.contains("@Test") || line.contains("org.junit.Test")) {
                    return;
                }

                // Удаляем строку package и все импорты
                if (line.startsWith("package ") || line.startsWith("import ")) {
                    continue;
                }

                // Удаляем символы с кодом <33 в начале и конце
                content.append(line).append(System.lineSeparator());
            }

            String processedContent = content.toString().trim();

            // Удаление символов с кодом <33 в начале и конце
            processedContent = processedContent.replaceAll("^[\\x00-\\x1F]+|[\\x00-\\x1F]+$", "");

            // Получаем размер обработанного текста
            long size = processedContent.getBytes(StandardCharsets.UTF_8).length;

            // Добавляем информацию о файле
            String relativePath = globalPath.relativize(path).toString();
            fileInfos.add(new FileInfo(size, relativePath));

        } catch (MalformedInputException e) {
            // Игнорируем ошибки MalformedInputException
        } catch (IOException e) {
            System.err.println("Error reading file " + path + ": " + e.getMessage());
        }
    }

    private static class FileInfo {
        private final long size;
        private final String relativePath;

        public FileInfo(long size, String relativePath) {
            this.size = size;
            this.relativePath = relativePath;
        }

        public long getSize() {
            return size;
        }

        public String getRelativePath() {
            return relativePath;
        }
    }
}