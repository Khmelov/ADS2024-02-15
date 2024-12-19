package by.it.group310902.isakov.lesson15;

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

    // Вложенный класс для сравнения строк по числовому значению и алфавитному порядку
    protected static class myStringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int int_s1, int_s2;

            // Извлечение числового значения из строки
            int_s1 = new Scanner(s1).nextInt(10);
            int_s2 = new Scanner(s2).nextInt(10);

            // Сравнение по числовому значению, при равенстве сравниваем строки лексикографически
            if (int_s1 == int_s2) {
                return s1.compareTo(s2);
            }
            return int_s1 > int_s2 ? 1 : -1;
        }
    }

    // Метод для удаления начальных и конечных нулей из массива символов
    protected static char[] move(char[] array) {
        char[] temp;
        int i = 0, size;

        // Находим первое ненулевое значение
        while (array[i] == 0)
            i++;

        // Копируем массив начиная с первого ненулевого значения
        size = array.length - i;
        temp = new char[size];
        System.arraycopy(array, i, temp, 0, size);
        array = temp;

        // Находим последнее ненулевое значение
        i = array.length - 1;
        while (array[i] == 0)
            i--;

        // Копируем массив до последнего ненулевого значения
        size = i + 1;
        temp = new char[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp;
    }

    // Метод для получения информации о файлах и их содержимом
    private static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>();

        // Определяем путь до директории "src"
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Используем Files.walk для рекурсивного обхода файлов
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) { // Проверяем, что файл имеет расширение .java
                            try {
                                char[] charArr;
                                String str = Files.readString(directory); // Читаем содержимое файла
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем строки package и import
                                    str = str.replaceAll("package.+;", "")
                                            .replaceAll("import.+;", "");

                                    // Удаляем пробелы или управляющие символы в начале и конце строки
                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        // Удаляем начальные пробелы и управляющие символы
                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;
                                        // Удаляем конечные пробелы и управляющие символы
                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        // Применяем метод move для удаления ненужных символов
                                        str = new String(move(charArr));
                                    }

                                    // Добавляем информацию о размере файла и относительном пути
                                    size_directory.add(str.getBytes().length + " " + src.relativize(directory));
                                }
                            } catch (IOException e) {
                                // Обработка исключений чтения файла
                                if (System.currentTimeMillis() < 0) { // Фиктивная проверка
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );

            // Сортируем список файлов с помощью кастомного компаратора
            Collections.sort(size_directory, new myStringComparator());

            // Выводим информацию о файлах
            for (var info : size_directory)
                System.out.println(info);
        }
    }

    // Точка входа в программу
    public static void main(String[] args) throws IOException {
        getInformation(); // Запуск метода получения информации
    }
}
