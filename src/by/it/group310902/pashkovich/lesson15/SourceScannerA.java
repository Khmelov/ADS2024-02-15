package by.it.group310902.pashkovich.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

//Определение пути к каталогу src:
//
//Используется System.getProperty("user.dir") для получения текущей директории и добавляется подкаталог src.
//
//Поиск файлов .java:
//
//Метод Files.walk() рекурсивно перебирает все файлы и папки в каталоге src.
//Проверяется расширение файлов на соответствие .java.
//
//Обработка текста файлов
//
//Проверяется отсутствие строк с @Test и org.junit.Test для исключения тестовых файлов.
//Удаляются строки с package и import с использованием регулярных выражений.
//Удаляются символы с кодом <33 в начале и конце текста с использованием метода move().
//
//Расчет размеров и сортировка
//
//Для каждого обработанного файла вычисляется размер текста в байтах с помощью getBytes().length.
//Результаты сохраняются в список size_directory как строка в формате "размер путь".
//Используется кастомный компаратор для сортировки по размеру и пути.

public class SourceScannerA {

    // Вложенный класс для сравнения строк, содержащих размер и путь к файлу
    protected static class myStringComparator implements Comparator<String> {


        @Override
        public int compare(String s1, String s2) {
            int int_s1, int_s2;

            // Извлекаем размер файла (число) из начала строки
            int_s1 = new Scanner(s1).nextInt(10);
            int_s2 = new Scanner(s2).nextInt(10);

            // Если размеры равны, сравниваем строки лексикографически
            if (int_s1 == int_s2) {
                return s1.compareTo(s2);
            }
            // Сравнение по размеру
            return int_s1 > int_s2 ? 1 : -1;
        }
    }

    // Метод для удаления символов с кодом <33 из начала и конца массива
    protected static char[] move(char[] array) {
        char[] temp;
        int i = 0, size;

        // Убираем символы <33 с начала массива
        while (array[i] == 0)
            i++;

        size = array.length - i;
        temp = new char[size];
        System.arraycopy(array, i, temp, 0, size); // Копируем оставшуюся часть массива
        array = temp;

        // Убираем символы <33 с конца массива
        i = array.length - 1;
        while (array[i] == 0)
            i--;

        size = i + 1;
        temp = new char[size];
        System.arraycopy(array, 0, temp, 0, size); // Копируем оставшуюся часть массива
        return temp;
    }

    // Основной метод для обработки файлов
    private static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>(); // Список для хранения размеров и путей

        // Определение пути к каталогу src
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Рекурсивный обход всех файлов в каталоге src
        try (Stream<Path> fileTrees = Files.walk(src))  {
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
                                    str = str.replaceAll("package.+;", "")
                                            .replaceAll("import.+;", "");

                                    // Удаляем символы с кодом <33 из начала и конца строки
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
                                // Игнорируем ошибки чтения
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
        getInformation(); // Вызываем основной метод обработки
    }
}
