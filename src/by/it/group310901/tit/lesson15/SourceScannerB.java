package by.it.group310901.tit.lesson15;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

public class SourceScannerB {

    public static void main(String[] args) {
        // Получаем путь к директории src в текущем рабочем каталоге
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;

        try {
            // Проходим по всем файлам в директории src и обрабатываем их
            List<FileData> results = Files.walk(Paths.get(src))
                    .filter(Files::isRegularFile) // Оставляем только обычные файлы
                    .filter(path -> path.toString().endsWith(".java")) // Оставляем только файлы с расширением .java
                    .map(Path::toFile) // Преобразуем Path в File
                    .map(SourceScannerB::processFile) // Обрабатываем файл
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
            // Удаляем комментарии из содержимого
            content = removeComments(content);
            // Очищаем текст от пустых строк и управляющих символов
            content = cleanText(content);

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

    // Метод для удаления комментариев из содержимого
    private static String removeComments(String content) {
        StringBuilder result = new StringBuilder(); // Строка для хранения результата
        boolean inBlockComment = false; // Флаг для отслеживания блока комментария

        for (int i = 0; i < content.length(); i++) {
            // Проверяем начало блока комментария
            if (i < content.length() - 1 && content.charAt(i) == '/' && content.charAt(i + 1) == '*') {
                inBlockComment = true; // Входим в блок комментария
                i++; // Пропускаем следующий символ
            }
            // Проверяем конец блока комментария
            else if (inBlockComment && i < content.length() - 1 && content.charAt(i) == '*' && content.charAt(i + 1) == '/') {
                inBlockComment = false; // Выходим из блока комментария
                i++; // Пропускаем следующий символ
            }
            // Если находимся в комментарии, пропускаем символы
            else if (inBlockComment) {
                continue;
            }
            // Если находим однострочный комментарий, пропускаем до конца строки
            else if (i < content.length() - 1 && content.charAt(i) == '/' && content.charAt(i + 1) == '/') {
                while (i < content.length() && content.charAt(i) != '\n') {
                    i++; // Пропускаем символы до конца строки
                }
            } else {
                result.append(content.charAt(i)); // Добавляем символ в результат
            }
        }
        return result.toString(); // Возвращаем очищенное содержимое
    }

    // Метод для очистки текста от пустых строк и управляющих символов
    private static String cleanText(String content) {
        String[] lines = content.split("\n"); // Разбиваем содержимое на строки
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String trimmedLine = line.trim(); // Убираем пробелы в начале и конце
            // Если строка не пустая, добавляем ее в результат
            if (!trimmedLine.isEmpty()) {
                result.append(trimmedLine).append("\n");
            }
        }

        // Обрезаем управляющие символы в начале и конце
        int start = 0, end = result.length();

        while (start < end && result.charAt(start) < 33) {
            start++; // Увеличиваем start, пока не дойдем до первого допустимого символа
        }

        while (end > start && result.charAt(end - 1) < 33) {
            end--; // Уменьшаем end, пока не дойдем до последнего допустимого символа
        }

        return result.substring(start, end); // Возвращаем обрезанное содержимое
    }

    // Класс для хранения информации о файле
    private static class FileData {
        private final int size; // Размер файла
        private final String relativePath; // Относительный путь к файлу

        public FileData(int size, String relativePath) {
            this.size = size; // Конструктор
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