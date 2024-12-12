package by.it.group310901.tit.lesson15;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

public class SourceScannerA {

    public static void main(String[] args) {
        // Получаем путь к директории src в текущем рабочем каталоге
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;

        try {
            // Проходим по всем файлам в директории src и обрабатываем их
            List<FileData> results = Files.walk(Paths.get(src))
                    .filter(Files::isRegularFile) // Оставляем только обычные файлы
                    .filter(path -> path.toString().endsWith(".java")) // Оставляем только файлы с расширением .java
                    .map(Path::toFile) // Преобразуем Path в File
                    .map(SourceScannerA::processFile) // Обрабатываем файл
                    .filter(Objects::nonNull) // Исключаем файлы, для которых processFile вернул null
                    .sorted(Comparator
                            .comparingInt(FileData::getSize) // Сортируем по размеру файла
                            .thenComparing(FileData::getRelativePath)) // Затем по относительному пути
                    .toList(); // Преобразуем Stream в List

            // Выводим результаты в консоль
            results.forEach(result ->
                    System.out.println(result.getSize() + " " + result.getRelativePath()));

        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений ввода-вывода
        }
    }

    // Метод для обработки каждого файла
    private static FileData processFile(File file) {
        try {
            // Читаем содержимое файла
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            // Проверяем, содержит ли файл аннотации тестов, если да, возвращаем null
            if (content.contains("@Test") || content.contains("org.junit.Test")) {
                return null;
            }

            // Удаляем объявления пакетов и импортов
            content = removePackageAndImports(content);
            // Обрезаем управляющие символы
            content = trimControlCharacters(content);

            // Получаем размер содержимого в байтах
            int size = content.getBytes(StandardCharsets.UTF_8).length;

            // Получаем относительный путь к файлу
            String relativePath = file.getPath().replace(System.getProperty("user.dir") + File.separator, "");

            // Возвращаем новый объект FileData с размером и относительным путем
            return new FileData(size, relativePath);

        } catch (IOException e) {
            return null; // В случае ошибки чтения файла возвращаем null
        }
    }

    // Метод для удаления строк с объявлением пакета и импортов
    private static String removePackageAndImports(String content) {
        String[] lines = content.split("\n"); // Разбиваем содержимое на строки
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String trimmedLine = line.trim(); // Убираем пробелы в начале и конце
            // Если строка не начинается с package или import, добавляем ее в результат
            if (!trimmedLine.startsWith("package") && !trimmedLine.startsWith("import")) {
                result.append(line).append("\n");
            }
        }
        return result.toString(); // Возвращаем очищенное содержимое
    }

    // Метод для удаления управляющих символов из начала и конца содержимого
    private static String trimControlCharacters(String content) {
        int start = 0, end = content.length();

        // Увеличиваем start, пока не дойдем до первого допустимого символа
        while (start < end && content.charAt(start) < 33) {
            start++;
        }
        // Уменьшаем end, пока не дойдем до последнего допустимого символа
        while (end > start && content.charAt(end - 1) < 33) {
            end--;
        }

        return content.substring(start, end); // Возвращаем обрезанное содержимое
    }

    // Класс для хранения информации о файле
    private static class FileData {
        private final int size; // Размер файла
        private final String relativePath; // Относительный путь к файлу

        public FileData(int size, String relativePath) {
            this.size = size;
            this.relativePath = relativePath;
        }

        public int getSize() {
            return size; // Метод для получения размера файла
        }

        public String getRelativePath() {
            return relativePath; // Метод для получения относительного пути
        }
    }
}
