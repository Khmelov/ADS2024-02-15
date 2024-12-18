package by.it.group310901.maydanyuk.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
//1. Сканирует все файлы с расширением .java в директории src.
//2. Исключает файлы, содержащие тестовые аннотации (@Test и org.junit.Test).
//3. Обрабатывает содержимое файлов, удаляя строки, начинающиеся с package и import.
//4. Вычисляет размер обработанных файлов в байтах и сохраняет информацию о файлах (путь и размер) в список.
//5. Сортирует файлы по размеру и пути.
//6. Выводит размеры и пути файлов в консоль.

// Основной класс для сканирования исходных файлов Java
public class SourceScannerA {

    // Главный метод программы
    public static void main(String[] args) {
        // Определяем директорию src для поиска Java файлов
        String srcDir = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        // Список для хранения информации о файлах
        List<FileEntry> fileEntries = new ArrayList<>();

        try {
            // Рекурсивно обходим все файлы и подкаталоги в директории src
            Files.walk(Path.of(srcDir))
                    .filter(path -> path.toString().endsWith(".java")) // Фильтруем только файлы с расширением .java
                    .forEach(path -> {
                        try {
                            // Читаем содержимое файла
                            String content = Files.readString(path);

                            // Исключаем тесты
                            if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                                // Обрабатываем содержимое файла
                                String processed = processContent(content);
                                if (!processed.isEmpty()) {
                                    // Вычисляем размер обработанного содержимого в байтах
                                    int sizeInBytes = processed.getBytes().length;
                                    // Определяем относительный путь файла
                                    String relativePath = Path.of(srcDir).relativize(path).toString();
                                    // Добавляем информацию о файле в список
                                    fileEntries.add(new FileEntry(relativePath, sizeInBytes));
                                }
                            }
                        } catch (MalformedInputException e) {
                            // Обрабатываем исключение при ошибке чтения файла
                            System.err.println("Malformed input: " + path);
                        } catch (IOException e) {
                            // Обрабатываем исключение при ошибке ввода/вывода
                            e.printStackTrace();
                        }
                    });

            // Сортировка файлов по размеру и пути
            fileEntries.sort(Comparator.comparingInt((FileEntry f) -> f.size)
                    .thenComparing(f -> f.path));

            // Вывод результатов
            for (FileEntry entry : fileEntries) {
                System.out.println(entry.size + " " + entry.path);
            }
        } catch (IOException e) {
            // Обрабатываем исключение при ошибке ввода/вывода
            e.printStackTrace();
        }
    }

    // Метод для обработки содержимого файла
    private static String processContent(String content) {
        StringBuilder sb = new StringBuilder();
        String[] lines = content.split("\n");

        // Обходим все строки в файле
        for (String line : lines) {
            line = line.strip(); // Удаляем начальные и конечные пробелы

            // Исключаем строки, начинающиеся с package или import
            if (line.startsWith("package") || line.startsWith("import")) {
                continue;
            }
            // Добавляем строку в результат
            sb.append(line).append("\n");
        }

        // Преобразуем содержимое в строку и удаляем непечатные символы
        String processed = sb.toString();
        return removeNonPrintable(processed);
    }

    // Метод для удаления непечатных символов из строки
    private static String removeNonPrintable(String text) {
        int start = 0, end = text.length();

        // Удаляем начальные непечатные символы
        while (start < end && text.charAt(start) < 33) {
            start++;
        }
        // Удаляем конечные непечатные символы
        while (end > start && text.charAt(end - 1) < 33) {
            end--;
        }

        // Возвращаем обработанную строку
        return text.substring(start, end);
    }

    // Внутренний класс для хранения информации о файлах
    static class FileEntry {
        String path; // Относительный путь файла
        int size; // Размер файла в байтах

        // Конструктор класса FileEntry
        public FileEntry(String path, int size) {
            this.path = path;
            this.size = size;
        }
    }
}
