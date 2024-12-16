package by.it.group310902.pashkovich.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

// Класс SourceScannerB расширяет функциональность SourceScannerA
public class SourceScannerB extends SourceScannerA {

    // Метод для обработки файлов и вывода информации
    protected static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>(); // Список для хранения размеров и путей

        // Определяем путь к каталогу src
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Рекурсивный обход всех файлов в каталоге src
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        // Проверяем, что файл имеет расширение .java
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                // Читаем содержимое файла в строку
                                String str = Files.readString(directory);

                                // Исключаем файлы, содержащие @Test или org.junit.Test
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем строки package и import
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");

                                    // Удаляем многострочные комментарии
                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                                            // Удаляем однострочные комментарии
                                            .replaceAll("//.*?\r\n\\s*", "");

                                    // Удаляем все подряд идущие пустые строки
                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");

                                    // Удаляем символы с кодом <33 в начале и конце строки
                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Убираем символы с начала
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;

                                        // Убираем символы с конца
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        // Преобразуем очищенный массив символов обратно в строку
                                        str = new String(move(charArr));
                                    }

                                    // Добавляем размер текста и относительный путь в список
                                    size_directory.add(str.getBytes().length + " " + src.relativize(directory));
                                }
                            } catch (IOException e) {
                                // Игнорируем ошибки чтения (MalformedInputException)
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );

            // Сортируем список по размеру и пути с использованием myStringComparator
            Collections.sort(size_directory, new myStringComparator());

            // Выводим результаты в консоль
            for (var info : size_directory)
                System.out.println(info);
        }

    }

    // Точка входа в программу
    public static void main(String[] args) throws IOException {
        getInformation(); // Запускаем обработку файлов
    }
}
