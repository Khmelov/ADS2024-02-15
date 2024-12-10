package by.it.group351001.sosnovski.lesson15;

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

    /**
     сканируем все Java-файлы в директории проекта и подсчитываем их размеры после удаления
     информации о пакетах и импортируемых классах. Сканирование проводится с целью
     анализа размера и фильтрации файлов, не содержащих специфические аннотации,
     такие как @Test.

     Основные этапы работы программы:

     Сканирование всех .java-файлов в директории и её подпапках.
     Фильтрация и очистка текста Java-файлов от строк пакетов и импортов.
     Подсчёт размера файла после обработки.
     Сортировка файлов по размерам и печать информации.
     */

    // Компаратор для сравнения размеров файлов
    protected static class myStringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int int_s1, int_s2;

            // Сканируем строку и преобразуем её в целое число
            int_s1 = new Scanner(s1).nextInt(10);
            int_s2 = new Scanner(s2).nextInt(10);

            // Если размеры равны, сравниваем строки по лексикографическому порядку
            if (int_s1 == int_s2) {
                return s1.compareTo(s2);
            }

            // Иначе сравниваем по числовому значению
            return int_s1 > int_s2 ? 1 : -1;
        }
    }

    // Метод перемещения значений массива (обрезка начальных и конечных нулей)
    protected static char[] move(char[] array) {
        char[] temp;
        int i = 0, size;

        // Пропускаем начальные символы, равные 0
        while (array[i] == 0) i++;

        size = array.length - i;
        temp = new char[size];
        System.arraycopy(array, i, temp, 0, size);
        array = temp;

        // Пропускаем конечные символы, равные 0
        i = array.length - 1;
        while (array[i] == 0) i--;

        size = i + 1;
        temp = new char[size];
        System.arraycopy(array, 0, temp, 0, size);

        return temp;
    }

    // Метод для сканирования информации о файлах
    private static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>(); // Список для хранения информации о размерах файлов

        // Определяем начальную директорию для сканирования
        Path src = Path.of(System.getProperty("user.dir") + File.separator + "src" + File.separator);

        // Сканируем файлы рекурсивно
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) { // Только Java-файлы
                            try {
                                char[] charArr;
                                String str = Files.readString(directory);

                                // Проверяем, содержит ли файл аннотации типа @Test или Test
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {

                                    // Удаляем package и import из файла
                                    str = str.replaceAll("package.+;", "").replaceAll("import.+;", "");

                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Обрезаем начальные и конечные символы, равные 0
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0) indexF++;
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0) indexL--;

                                        str = new String(move(charArr));
                                    }

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

            // Сортировка информации по размерам с использованием компаратора
            Collections.sort(size_directory, new myStringComparator());

            // Печать информации о размерах файлов
            for (var info : size_directory) {
                System.out.println(info);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        getInformation(); // Вызов метода для получения информации
    }
}
