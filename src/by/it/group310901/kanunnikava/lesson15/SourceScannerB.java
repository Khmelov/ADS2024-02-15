package by.it.group310901.kanunnikava.lesson15;
/*Создайте класс SourceScannerB с методом main,
который читает все файлы *.java из каталога src и его подкаталогов.
Файлы, содержащие в тексте @Test или org.junit.Test (тесты)
не участвуют в обработке.
В каждом тексте файла необходимо:
1. Удалить строку package и все импорты за O(n) от длины текста.
2. Удалить все комментарии за O(n) от длины текста.
3. Удалить все символы с кодом <33 в начале и конце текстов.
4. Удалить пустые строки

*/
import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class SourceScannerB {

    public static void main(String[] args) {
        String src = System.getProperty("user.dir")
                + File.separator + "src" + File.separator; // Получает текущий рабочий каталог и добавляет путь к директории src
        List<FileResult> fileResults = new ArrayList<>(); // Создает список для хранения результатов файлов

        try (Stream<Path> paths = Files.walk(Paths.get(src))) { // Открывает поток путей, рекурсивно обходя директорию src
            paths.filter(Files::isRegularFile) // Фильтрует только обычные файлы
                    .filter(p -> p.toString().endsWith(".java")) // Фильтрует только файлы с расширением .java.
                    .forEach(p -> {
                        try {
                            String content = Files.readString(p); // Читает содержимое файла как строку
                            if (!content.contains("@Test") && !content.contains("org.junit.Test")) { // Проверяет, не содержит ли файл аннотаций @Test
                                content = processContent(content); // Обрабатывает содержимое файла (удаляет пакеты и импорты)
                                fileResults.add(new FileResult(content.getBytes().length, p.toString().replace(src, ""), content)); // Добавляет результат в список
                            }
                        } catch (MalformedInputException ignored) {
                        } catch (IOException e) {
                            e.printStackTrace(); // Печатает трассировку стека
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace(); // Печатает трассировку стека
        }

        fileResults.stream() // Создает поток из списка результатов файлов.
                .sorted(Comparator.comparing(FileResult::getSize).thenComparing(FileResult::getPath)) // Сортирует результаты по размеру и пути
                .forEach(fr -> System.out.printf("%d %s%n", fr.getSize(), fr.getPath())); // Выводит размер и путь каждого файла
    }

    private static String processContent(String content) { // Метод для обработки содержимого файла
        StringBuilder processed = new StringBuilder(); // Создает объект для хранения обработанного содержимого
        boolean inBlockComment = false;

        for (String line : content.split("\n")) { // Разделяет содержимое на строки
            line = line.strip(); // Удаляет начальные и конечные пробелы

            // Пропуск строк пакета и импорта
            if (line.startsWith("package ") || line.startsWith("import ")) {
                continue;
            }

            // Пропуск строковых комментариев
            if (line.startsWith("//")) {
                continue;
            }

            // Обработка блоков комментариев
            if (line.startsWith("/*")) {
                inBlockComment = true; // Устанавливает флаг начала блока комментариев.
                continue;
            }
            if (inBlockComment) {
                if (line.endsWith("*/")) {
                    inBlockComment = false; // Сбрасывает флаг окончания блока комментариев.
                }
                continue;
            }

            // Добавление непустых строк
            if (!line.isEmpty()) {
                processed.append(line).append("\n"); // Добавляет строку к обработанному содержимому.
            }
        }

        return processed.toString().strip(); // Возвращает обработанное содержимое с удаленными начальными и конечными пробелами
    }

    private static class FileResult { // Внутренний статический класс для хранения результата файла
        private final int size; // Переменная для хранения размера файла
        private final String path; // Переменная для хранения пути файла
        private final String content; // Переменная для хранения содержимого файла

        public FileResult(int size, String path, String content) { // Конструктор класса FileResult
            this.size = size;
            this.path = path;
            this.content = content;
        }

        public int getSize() { // Метод для получения размера файла
            return size;
        }

        public String getPath() { // Метод для получения пути файла
            return path;
        }

        public String getContent() { // Метод для получения содержимого файла
            return content;
        }
    }
}
