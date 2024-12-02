package by.it.group310901.sikachenkoliana.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

public class SourceScannerA {
    public static void main(String[] args) {
        var root = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        try {
            Files.walk(Path.of(root)) //рекурсивный обход файловой структуры
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(path -> {
                        try {
                            var content = Files.readString(path);
                            if (content.contains("@Test") || content.contains("org.junit.Test"))
                                return null;
                            // Обрабатываем содержимое файла, удаляя ненужные строки
                            content = processContent(content);
                            //исключаем пустые файлы
                            return content.isEmpty() ? null
                                    : new AbstractMap.SimpleEntry<>(path, content.getBytes().length);
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + path);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(Map.Entry<Path, Integer>::getValue)
                            .thenComparing(Map.Entry::getKey))
                    .forEach(entry -> System.out
                            .println(entry.getValue() + " " + Path.of(root).relativize(entry.getKey())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processContent(String content) {
        StringBuilder sb = new StringBuilder();
        // Разделяем содержимое файла на строки и обрабатываем каждую.
        for (String line : content.split("\n")) {
            // Пропускаем строки, начинающиеся с package или import.
            if (line.startsWith("package") || line.startsWith("import"))
                continue;
            // Удаляем управляющие символы в начале и в конце строки.
            line = line.replaceAll("^[\\x00-\\x1F]+|[\\x00-\\x1F]+$", "");
            sb.append(line).append("\n");
        }
        // Возвращаем обработанное содержимое, обрезая лишние пробелы.
        return sb.toString().trim();
    }
}