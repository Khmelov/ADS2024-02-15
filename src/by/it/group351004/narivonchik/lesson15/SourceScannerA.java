package by.it.group351004.narivonchik.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

// Главный класс программы
public class SourceScannerA {

    // Внутренний класс для сравнения строк
    protected static class myStringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int int_s1, int_s2;

            // Извлекаем целые числа из строк
            int_s1 = new Scanner(s1).nextInt(10); //размер файлов
            int_s2 = new Scanner(s2).nextInt(10);

            // Сравниваем числа (размеры файлов), если они равны, сравниваем строки (по алфавиту)
            if (int_s1 == int_s2) {
                return s1.compareTo(s2);
            }
            return int_s1 > int_s2 ? 1 : -1; // Возвращаем результат сравнения
        }
    }



    // Основной метод программы
    public static void main(String[] args) throws IOException {
        getInformation(); // Вызываем метод получения информации
    }
    // Метод для получения информации о Java файлах
    private static void getInformation() throws IOException {
        ArrayList<String> filesList = new ArrayList<>(); // Список для хранения информации о файлах

        // Определяем путь к директории src
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Обходим файлы в директории
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        // Обрабатываем только файлы с расширением .java
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                // Читаем содержимое файла
                                String str = Files.readString(directory);
                                // Проверяем, содержит ли файл тесты
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем строки с package и import
                                    str = str.replaceAll("package.+;", "")
                                            .replaceAll("import.+;", "");

                                    // Если файл не пустой и содержит пробелы в начале или конце
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
                                    filesList.add(str.getBytes().length + " " + src.relativize(directory));
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
            Collections.sort(filesList, new myStringComparator());

            // Выводим информацию о файлах
            for (var info : filesList)
                System.out.println(info);
        }
    }

    // Метод для удаления ведущих и завершающих нулевых символов из массива символов
    protected static char[] move(char[] array) {
        char[] temp;
        int i = 0, size;

        // Находим индекс первого ненулевого символа
        while(array[i] == 0)
            i++;

        // Создаем новый массив без ведущих нулей
        size = array.length - i;
        temp = new char[size];
        System.arraycopy(array, i, temp, 0, size);
        array = temp;

        // Находим индекс последнего ненулевого символа
        i = array.length - 1;
        while (array[i] == 0)
            i--;

        // Создаем новый массив без завершающих нулей
        size = i + 1;
        temp = new char[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp; // Возвращаем массив без нулей
    }
}