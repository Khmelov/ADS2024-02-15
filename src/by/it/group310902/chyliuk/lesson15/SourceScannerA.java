package by.it.group310902.chyliuk.lesson15;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SourceScannerA {
    public static void main(String[] args) throws IOException {
        // Указываем директорию, с которой начнётся обход файлов
        Path rootDirectory = Paths.get("src");

        // Создаём список для хранения данных о файлах
        List<FileInfo> fileInfoList = new ArrayList<>();

        // Рекурсивный обход файловой структуры, начиная с rootDirectory
        Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Проверяем, является ли файл Java-исходником по расширению
                if (file.toString().endsWith(".java")) {
                    // Считываем содержимое файла в строку с использованием UTF-8
                    String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);

                    // Проверяем, содержит ли файл аннотацию @Test или строку org.junit.Test
                    if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                        // Убираем строки с директивой package и все строки с импортами
                        content = removePackageAndImports(content);

                        // Удаляем спецсимволы (ASCII-коды меньше 33) с начала и конца текста
                        content = trimSpecialCharacters(content);

                        // Добавляем информацию о текущем файле в список
                        fileInfoList.add(new FileInfo(file, content.length()));
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        // Сортируем файлы: сначала по размеру содержимого, затем по пути
        fileInfoList.sort(Comparator.comparingInt(FileInfo::getSize)
                .thenComparing(fileInfo -> fileInfo.getPath().toString()));

        // Печатаем результаты: размер файла и относительный путь
        for (FileInfo fileInfo : fileInfoList) {
            System.out.println(fileInfo.getSize() + " " + rootDirectory.relativize(fileInfo.getPath()));
        }
    }

    // Вспомогательный класс для хранения данных о файле
    static class FileInfo {
        private final Path path;
        private final int size;

        public FileInfo(Path path, int size) {
            this.path = path;
            this.size = size;
        }

        public Path getPath() {
            return path;
        }

        public int getSize() {
            return size;
        }
    }

    // Удаляет строку package и все строки с директивой import
    private static String removePackageAndImports(String content) {
        String[] lines = content.split("n"); // Разбиваем содержимое файла на строки
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            // Добавляем строки, не начинающиеся с package или import
            if (!line.startsWith("package ") && !line.startsWith("import ")) {
                result.append(line).append("n");
            }
        }
        return result.toString();
    }

    // Удаляет символы с ASCII-кодами < 33 из начала и конца текста
    private static String trimSpecialCharacters(String content) {
        int startIndex = 0;
        // Ищем первый "печатный" символ с начала текста
        while (startIndex < content.length() && content.charAt(startIndex) < 33) {
            startIndex++;
        }

        int endIndex = content.length() - 1;
        // Ищем первый "печатный" символ с конца текста
        while (endIndex >= 0 && content.charAt(endIndex) < 33) {
            endIndex--;
        }

        // Возвращаем обрезанную строку или пустую, если "печатных" символов нет
        return (startIndex <= endIndex) ? content.substring(startIndex, endIndex + 1) : "";
    }
}
