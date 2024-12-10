/*
 * Программа анализирует все файлы с расширением .java в директории src проекта,
 * подсчитывает их размер в байтах после обработки и сортирует по определенным критериям.
 *
 * Основные компоненты программы:
 * 1. **myStringComparator**:
 *    - Класс-компаратор для сравнения строк по числовым значениям, извлеченным из первых чисел строки.
 *    - Если числа равны, строки сравниваются лексикографически.
 *
 * 2. **move()**:
 *    - Функция для обработки и очистки массивов символов, удаляет символы, не удовлетворяющие условиям ASCII (менее 33).
 *
 * 3. **getInformation()**:
 *    - Основной метод, который обходит директорию `src` и рекурсивно обрабатывает все файлы с расширением .java.
 *    - Исключает тестовые классы с аннотацией @Test.
 *    - Обрезает пакеты и импорты из исходного кода.
 *    - Подсчитывает размер очищенного текстового контента в байтах и сохраняет информацию.
 *    - Сортирует полученную информацию по определенным правилам с помощью **myStringComparator**.
 *
 * 4. **main()**:
 *    - Запускает метод `getInformation()` для анализа файлов и вывода информации.
 *
 * В результате программа возвращает отсортированный список размеров файлов и их путей (относительных путей).
 */


package by.it.group351001.shimanchuk.lesson15;

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
    protected static class myStringComparator implements Comparator<String>{
        @Override
        public int compare(String s1, String s2) {
            int int_s1, int_s2;

            int_s1 = new Scanner(s1).nextInt(10);
            int_s2 = new Scanner(s2).nextInt(10);

            if (int_s1 == int_s2) {
                return s1.compareTo(s2);
            }
            return int_s1 > int_s2 ? 1 : -1;
        }

    }

    protected static char[] move(char[] array) {
        char[] temp;
        int i = 0, size;

        while(array[i] == 0)
            i++;

        size = array.length - i;
        temp = new char[size];
        System.arraycopy(array, i, temp, 0, size);
        array = temp;

        i = array.length - 1;
        while (array[i] == 0)
            i--;

        size = i + 1;
        temp = new char[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp;
    }

    private static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>();

        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                String str = Files.readString(directory);
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    str = str.replaceAll("package.+;", "")
                                            .replaceAll("import.+;", "");

                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

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

            Collections.sort(size_directory, new myStringComparator());

            for (var info : size_directory)
                System.out.println(info);
        }

    }

    public static void main(String[] args) throws IOException {
        getInformation();
    }
}