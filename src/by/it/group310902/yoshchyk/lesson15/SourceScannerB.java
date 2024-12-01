package by.it.group310902.yoshchyk.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SourceScannerB  {
    public static void main(String[] args) {
        Path srcDir = Paths.get("src"); // Путь к вашему каталогу
        List<FileInfo> filesToProcess = new ArrayList<>();

        try {   // метод walkFileTree из класса Files для рекурсивного обхода дерева файлов
            Files.walkFileTree(srcDir, new SimpleFileVisitor<Path>() { // класс, который позволяет создавать обработчик для прохода по файлам
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().endsWith(".java")) {
                        processJavaFile(file).ifPresent(filesToProcess::add); // передавая ему путь к файлу.
                        // Метод возвращает объект Optional<FileInfo>, и если он присутствует, добавляем его в список filesToProcess.
                    }
                    return FileVisitResult.CONTINUE; // продолжаем обход файлов
                }
            });
        } catch (IOException e) {
            System.err.println("Ошибка при проходе по дереву файлов: " + e.getMessage());
        }

        // Сортировка файлов по размеру и пути
        filesToProcess.sort(Comparator.comparingInt(FileInfo::getSize).thenComparing(FileInfo::getPath));

        // Вывод результатов
        for (FileInfo fileInfo : filesToProcess) {
            System.out.println(fileInfo.getSize() + " " + fileInfo.getPath());
        }
    }

    private static Optional<FileInfo> processJavaFile(Path file) { // обработать случаи, когда файл не может быть прочитан или имеет неправильный формат.
        StringBuilder content = new StringBuilder(); // Создаем StringBuilder для накопления содержимого файла.
        try {
            // Читаем файл
            content.append(new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
        } catch (MalformedInputException e) { // Обрабатываем исключение MalformedInputException, которое может произойти, если файл имеет неправильный формат
            System.err.println("Ошибка формата файла: " + file.toString() + " - " + e.getMessage());
            return Optional.empty(); // Игнорируем файл с неправильным форматом
        } catch (IOException e) { // обрабытываем общее сключение
            System.err.println("Ошибка чтения файла: " + file.toString() + " - " + e.getMessage());
            return Optional.empty();
        }

        // Преобразуем содержимое
        String processedContent = processContent(content.toString());

        // Получаем размер после обработки
        int size = processedContent.length();
        if (size == 0) {
            return Optional.empty(); // Игнорируем пустой файл
        }

        // Получаем относительный путь
        String relativePath = Paths.get("src").relativize(file).toString();
        return Optional.of(new FileInfo(size, relativePath));
    }

    private static String processContent(String content) {
        // 1. Удаляем строки package и импорты
        content = content.replaceAll("(?m)^(package\\s+.*;|import\\s+.*;\\s*)", "");

        // 2. Удаляем комментарии
        content = content.replaceAll("/\\*.*?\\*/|//.*", "");

        // 3. Удаляем символы с кодом <33 в начале и конце текста
        content = content.replaceAll("(^[\\x00-\\x20]+|[\\x00-\\x20]+$)", "");

        // 4. Удаляем пустые строки
        content = content.replaceAll("(?m)^[\\s]*\\n", "");

        return content;
    }

    private static class FileInfo {
        private final int size;
        private final String path;

        public FileInfo(int size, String path) {
            this.size = size;
            this.path = path;
        }

        public int getSize() {
            return size;
        }

        public String getPath() {return path;
        }
    }
}