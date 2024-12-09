package by.it.group351001.orsik.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class SourceScannerB extends SourceScannerA {
    // Класс SourceScannerB наследует функционал SourceScannerA, переопределяя метод getInformation

    protected static void getInformation() throws IOException {
        // Метод для сбора информации о Java-файлах с более детальной обработкой
        ArrayList<String> size_directory = new ArrayList<>();
        // Список для хранения информации о размере файлов и их путях

        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);
        // Определение пути к папке src в текущем рабочем каталоге

        try (Stream<Path> fileTrees = Files.walk(src)) {
            // Рекурсивный обход всех файлов и директорий в src
            fileTrees.forEach(
                    directory -> {
                        // Обработка каждого пути
                        if (directory.toString().endsWith(".java")) {
                            // Проверка, что это файл с расширением .java
                            try {
                                char[] charArr; // Массив символов для обработки файла
                                String str = Files.readString(directory);
                                // Чтение содержимого файла в строку

                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Пропуск файлов, содержащих аннотации @Test
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");
                                    // Удаление строк с пакетами и импортами

                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                                            .replaceAll("//.*?\r\n\\s*", "");
                                    // Удаление многострочных комментариев (/* ... */) и однострочных комментариев (// ...)

                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");
                                    // Устранение лишних пустых строк

                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        // Проверка на пустую строку и пробелы в начале или конце
                                        charArr = str.toCharArray(); // Преобразование строки в массив символов
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Удаление лидирующих пробелов
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;
                                        // Удаление конечных пробелов
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        str = new String(move(charArr));
                                        // Преобразование обработанного массива символов обратно в строку
                                    }

                                    size_directory.add(str.getBytes().length + " " + src.relativize(directory));
                                    // Добавление информации о размере файла и относительном пути в список
                                }
                            } catch (IOException e) {
                                // Обработка исключений при чтении файла
                                if (System.currentTimeMillis() < 0) {
                                    // Для тестирования или отладки выводится путь к файлу
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );

            Collections.sort(size_directory, new myStringComparator());
            // Сортировка списка по размерам с использованием кастомного компаратора

            for (var info : size_directory)
                // Вывод информации о каждом файле
                System.out.println(info);
        }
    }

    public static void main(String[] args) throws IOException {
        // Главный метод программы
        getInformation();
        // Вызов переопределенного метода для сбора информации
    }
}

