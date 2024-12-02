package by.it.group351004.narivonchik.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

// Класс SourceScannerB наследует функциональность класса SourceScannerA
public class SourceScannerB extends SourceScannerA {

    // Основной метод программы
    public static void main(String[] args) throws IOException {
        getInformation(); // Вызываем метод получения информации
    }

    // Переопределенный метод для получения информации о Java файлах
    protected static void getInformation() throws IOException {
        ArrayList<String> fileList = new ArrayList<>(); // Список для хранения информации о файлах

        // Определяем путь к директории src
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Обходим файлы в директории
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> { //для каждой директории
                        // Обрабатываем только файлы с расширением .java
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                // Читаем содержимое файла
                                String str = Files.readString(directory);
                                // Проверяем, содержит ли файл тесты
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем строки с package и import
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");

                                    // Удаляем многострочные и однострочные комментарии
                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "") // Удаление многострочных комментариев
                                            .replaceAll("//.*?\r\n\\s*", ""); // Удаление однострочных комментариев

                                    // Удаление пустых строк
                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");

                                    // Проверяем, не пустая ли строка и не содержит ли пробелы в начале и конце
                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Удаляем ведущие пробелы
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;
                                        // Удаляем завершающие пробелы
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        // Применяем метод move для обработки массива символов
                                        str = new String(move(charArr));
                                    }

                                    // Добавляем размер файла и его относительный путь в список
                                    fileList.add(str.getBytes().length + " " + src.relativize(directory));
                                }
                            } catch (IOException e) {
                                // Обработка исключения при чтении файла
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory); // Выводим ошибку
                                }
                            }
                        }
                    }
            );
            // Сортируем список с использованием myStringComparator
            Collections.sort(fileList, new myStringComparator());

            // Выводим информацию о файлах
            for (var info : fileList)
                System.out.println(info);
        }
    }
}