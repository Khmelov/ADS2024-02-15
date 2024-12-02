package by.it.group310902.chyliuk.lesson15;


import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SourceScannerB {
    public static void main(String[] args) {
        Path srcDir = Paths.get("src"); // Указываем путь к каталогу для сканирования
        List<FileInfo> filesToProcess = new ArrayList<>(); // Список для хранения обработанных файлов

        try {
            // Рекурсивный обход файлов и директорий в указанной папке
            Files.walkFileTree(srcDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    // Проверяем, является ли текущий файл Java-исходником
                    if (file.toString().endsWith(".java")) {
                        // Обрабатываем файл и добавляем информацию о нём в список, если он валиден
                        processJavaFile(file).ifPresent(filesToProcess::add);
                    }
                    // Продолжаем обход остальных файлов
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            // Логируем ошибки при обходе файловой системы
            System.err.println("Ошибка при обходе файловой системы: " + e.getMessage());
        }

        // Сортировка файлов по размеру содержимого и пути
        filesToProcess.sort(Comparator.comparingInt(FileInfo::getSize).thenComparing(FileInfo::getPath));

        // Вывод информации о каждом файле
        for (FileInfo fileInfo : filesToProcess) {
            System.out.println(fileInfo.getSize() + " " + fileInfo.getPath());
        }
    }

    private static Optional<FileInfo> processJavaFile(Path file) {
        // Создаём объект для накопления содержимого файла
        StringBuilder content = new StringBuilder();
        try {
            // Считываем содержимое файла с кодировкой UTF-8
            content.append(new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
        } catch (MalformedInputException e) {
            // Обрабатываем случай, когда файл содержит некорректные символы
            System.err.println("Ошибка чтения файла (неверный формат): " + file.toString() + " - " + e.getMessage());
            return Optional.empty();
        } catch (IOException e) {
            // Обрабатываем общие ошибки при чтении файлов
            System.err.println("Ошибка чтения файла: " + file.toString() + " - " + e.getMessage());
            return Optional.empty();
        }

        // Выполняем обработку содержимого файла
        String processedContent = processContent(content.toString());

        // Определяем размер обработанного содержимого
        int size = processedContent.length();
        if (size == 0) {
            // Пропускаем пустые файлы
            return Optional.empty();
        }

        // Получаем относительный путь к файлу
        String relativePath = Paths.get("src").relativize(file).toString();
        return Optional.of(new FileInfo(size, relativePath));
    }

    private static String processContent(String content) {
        // Убираем директиву package и строки с импортами
        content = content.replaceAll("(?m)^(package\\s+.*;|import\\s+.*;\\s*)", "");

        // Удаляем комментарии (однострочные и многострочные)
        content = content.replaceAll("/\\*.*?\\*/|//.*", "");

        // Удаляем непечатаемые символы (код ASCII < 33) в начале и конце текста
        content = content.replaceAll("(^[\\x00-\\x20]+|[\\x00-\\x20]+$)", "");

        // Убираем пустые строки
        content = content.replaceAll("(?m)^[\\s]*\\n", "");

        return content;
    }

    private static class FileInfo {
        private final int size;
        private final String path;

        public FileInfo(int size, String path) {
            this.size = size; // Размер обработанного содержимого
            this.path = path; // Относительный путь к файлу
        }

        public int getSize() {
            return size;
        }

        public String getPath() {
            return path;
        }
    }
}
