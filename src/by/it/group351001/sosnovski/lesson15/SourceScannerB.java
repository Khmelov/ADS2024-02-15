package by.it.group351001.sosnovski.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class SourceScannerB extends SourceScannerA {

    /**
     SourceScannerB — это подкласс, который расширяет функциональность класса SourceScannerA.
     Его основная цель — сканирование Java-файлов и анализ их размера после обработки.
     В этом коде добавляется дополнительная фильтрация:

     Удаляются все многострочные комментарии (/* ... * /) и однострочные комментарии (//...).
     Упрощается и очищается текст Java-файлов, удаляются ненужные пустые строки.

            Основные действия программы:
            Сканирование файлов: Перебираются все файлы с расширением .java.
            Фильтрация аннотаций: Исключаются файлы с аннотацией @Test.
            Обрезка ненужных данных: Удаляются пакеты, импорты, комментарии и пустые строки.
            Подсчёт размера файлов: Подсчитывается размер очищенного текста.
            Сортировка данных: Сканированные файлы сортируются по размерам и выводятся на экран.
     */

    // Переопределяем метод для обработки информации и фильтрации
    protected static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>(); // Список для хранения информации о размерах файлов

        // Определяем корневую директорию для поиска Java-файлов
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Сканируем директории и файлы рекурсивно
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        // Проверяем, что файл имеет расширение .java
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                String str = Files.readString(directory);

                                // Исключаем файлы с аннотацией @Test
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {

                                    // Удаляем package и import
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");

                                    // Удаляем многострочные комментарии и однострочные комментарии
                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "") // Удаляем блоки комментариев
                                            .replaceAll("//.*?\r\n\\s*", ""); // Удаляем однострочные комментарии

                                    // Удаляем ненужные пустые строки (двойные переносы строк)
                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");

                                    // Проверяем, содержит ли строка символы, и обрезаем начальные и конечные пустые символы
                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Обрезаем начальные символы меньше 33
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;

                                        // Обрезаем конечные символы меньше 33
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        str = new String(move(charArr));
                                    }

                                    // Добавляем размер файла и путь в массив
                                    size_directory.add(str.getBytes().length + " " + src.relativize(directory));
                                }
                            } catch (IOException e) {
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );

            // Сортируем файлы по размеру
            Collections.sort(size_directory, new myStringComparator());

            // Вывод информации
            for (var info : size_directory)
                System.out.println(info);
        }
    }

    public static void main(String[] args) throws IOException {
        getInformation(); // Запуск метода для обработки информации
    }
}
