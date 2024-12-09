package by.it.group351001.orsik.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

public class SourceScannerA {
    // Основной класс программы для сканирования Java-файлов и их обработки

    protected static class myStringComparator implements Comparator<String> {
        // Вложенный класс, реализующий интерфейс Comparator для сравнения строк

        @Override
        public int compare(String s1, String s2) {
            // Переопределение метода сравнения для строк

            int int_s1, int_s2;

            // Преобразование первой строки в число
            int_s1 = new Scanner(s1).nextInt(10);

            // Преобразование второй строки в число
            int_s2 = new Scanner(s2).nextInt(10);

            // Если числа равны, строки сравниваются лексикографически
            if (int_s1 == int_s2) {
                return s1.compareTo(s2);
            }
            // Иначе возвращается результат сравнения чисел
            return int_s1 > int_s2 ? 1 : -1;
        }
    }

    protected static char[] move(char[] array) {
        // Метод для удаления лидирующих и конечных нулевых символов в массиве
        char[] temp; // Временный массив
        int i = 0, size; // Индексы и размер нового массива

        // Пропуск лидирующих нулевых символов
        while(array[i] == 0)
            i++;

        // Копирование оставшейся части массива без лидирующих нулей
        size = array.length - i;
        temp = new char[size];
        System.arraycopy(array, i, temp, 0, size);
        array = temp;

        // Пропуск конечных нулевых символов
        i = array.length - 1;
        while (array[i] == 0)
            i--;

        // Создание нового массива без конечных нулей
        size = i + 1;
        temp = new char[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp; // Возвращается обработанный массив
    }

    private static void getInformation() throws IOException {
        // Метод для сбора информации о Java-файлах в директории
        ArrayList<String> size_directory = new ArrayList<>();
        // Список для хранения информации о размерах файлов и их путях

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
                                    str = str.replaceAll("package.+;", "")
                                            .replaceAll("import.+;", "");
                                    // Удаление строк с пакетами и импортами

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
        // Вызов метода для сбора информации
    }
}
