package lesson15;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SourceScannerA  {
    public static void main(String[] args) throws IOException {
        // Путь к корневой папке
        Path rootDirectory = Paths.get("src"); // создаётся объект Path, который указывает на корневую папку

        // Список для хранения информации о файлах
        List<FileInfo> fileInfoList = new ArrayList<>();

        // Метод walkFileTree из класса Files используется для рекурсивного обхода дерева файлов в указанной директории
        Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() { //Мы создаем анонимный класс, унаследованный от SimpleFileVisitor, чтобы переопределить его методы.
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Проверка на расширение файла
                if (file.toString().endsWith(".java")) { // Проверяем, заканчивается ли имя файла на ".java"
                    String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8); // Читаем все байты файла и преобразуем их в строку, используя кодировку UTF-8

                    // Проверка на @Test или org.junit.Test
                    if (!content.contains("@Test") && !content.contains("org.junit.Test")) { // Проверяем, не содержит ли содержимое файла  @Test или org.junit
                        // Удаляем строку package и все импорты
                        content = removePackageAndImports(content);

                        // Удаляем символы с кодом <33
                        content = trimSpecialCharacters(content);

                        // Добавляем информацию о файле в список
                        fileInfoList.add(new FileInfo(file, content.length()));
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        // Сортировка списка по размеру и пути
        fileInfoList.sort(Comparator.comparingInt(FileInfo::getSize)
                .thenComparing(fileInfo -> fileInfo.getPath().toString()));

        // Вывод информации о файлах
        for (FileInfo fileInfo : fileInfoList) {
            System.out.println(fileInfo.getSize() + " " + rootDirectory.relativize(fileInfo.getPath()));
        }
    }

    // Класс для хранения информации о файле
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

    // Метод для удаления строки package и всех импортов
    private static String removePackageAndImports(String content) {
        String[] lines = content.split("n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (!line.startsWith("package ") && !line.startsWith("import ")) {
                result.append(line).append("n");
            }
        }
        return result.toString();
    }

    // Метод для удаления символов с кодом <33 в начале и в конце текста
    private static String trimSpecialCharacters(String content) {
        int startIndex = 0;
        while (startIndex < content.length() && content.charAt(startIndex) < 33) {
            startIndex++;
        }

        int endIndex = content.length() - 1;
        while (endIndex >= 0 && content.charAt(endIndex) < 33) {
            endIndex--;
        }

        return (startIndex <= endIndex) ? content.substring(startIndex, endIndex + 1) : "";
    }


}
