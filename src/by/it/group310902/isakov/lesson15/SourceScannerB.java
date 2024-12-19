package by.it.group310902.isakov.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

// Класс SourceScannerB наследует функциональность от SourceScannerA
public class SourceScannerB extends SourceScannerA {

    // Метод для получения информации о Java-файлах с дополнительной обработкой
    protected static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>();

        // Определяем путь к директории "src"
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Используем Files.walk для рекурсивного обхода файлов в указанной директории
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) { // Проверяем, что файл имеет расширение .java
                            try {
                                char[] charArr;
                                String str = Files.readString(directory); // Читаем содержимое файла
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем строки package и import
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");

                                    // Удаляем многострочные комментарии и однострочные комментарии
                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                                            .replaceAll("//.*?\r\n\\s*", "");

                                    // Удаляем лишние пустые строки
                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");

                                    // Удаляем лишние пробелы и управляющие символы в начале и конце файла
                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Удаляем начальные пробелы и управляющие символы
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;

                                        // Удаляем конечные пробелы и управляющие символы
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        // Применяем метод move для создания массива без лишних символов
                                        str = new String(move(charArr));
                                    }

                                    // Добавляем в список информацию о размере файла и его относительном пути
                                    size_directory.add(str.getBytes().length + " " + src.relativize(directory));
                                }
                            } catch (IOException e) {
                                // Обработка ошибок чтения файла
                                if (System.currentTimeMillis() < 0) { // Фиктивная проверка, никогда не выполняется
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );

            // Сортируем список по размеру файлов с использованием кастомного компаратора
            Collections.sort(size_directory, new myStringComparator());

            // Выводим отсортированную информацию о файлах
            for (var info : size_directory)
                System.out.println(info);
        }
    }

    // Точка входа в программу
    public static void main(String[] args) throws IOException {
        getInformation(); // Запускаем метод анализа и вывода информации
    }
}
