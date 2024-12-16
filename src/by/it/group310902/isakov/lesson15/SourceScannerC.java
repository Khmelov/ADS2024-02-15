package by.it.group310902.isakov.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

// Класс SourceScannerC расширяет функционал SourceScannerA
public class SourceScannerC extends SourceScannerA {
    static final int NORMAL_DISTANCE = 9; // Максимально допустимое расстояние для сравнения файлов

    // Метод для сравнения двух символов. Возвращает 0, если символы равны, иначе 1.
    private static int areReplacementNumbers(char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    }

    // Метод для нахождения минимального значения среди переданных чисел
    private static int getMinEdit(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    // Метод для проверки, являются ли два текста на одинаковом расстоянии (по Левенштейну)
    private static boolean checkDistance(String file1, String file2) {
        int distance = Math.abs(file1.length() - file2.length()); // Разница в длинах строк

        if (distance > NORMAL_DISTANCE) // Если разница больше допустимой, возвращаем false
            return false;

        String s1, s2;
        String[] array_s1 = file1.split(" "), array_s2 = file2.split(" ");

        // Проходим по словам в текстах и сравниваем их
        for (int index = 0; index < array_s1.length; index++) {
            s1 = array_s1[index];
            s2 = array_s2[index];
            int length = s2.length() + 1;
            int[] currRow = new int[length]; // Текущая строка для алгоритма Левенштейна
            int[] prevRow;

            // Реализация алгоритма Левенштейна
            for (int i = 0; i <= s1.length(); i++) {
                prevRow = currRow;
                currRow = new int[length];

                for (int j = 0; j <= s2.length(); j++) {
                    currRow[j] = i == 0 ? j : (j == 0 ? i : getMinEdit(
                            prevRow[j - 1] + areReplacementNumbers(s1.charAt(i - 1), s2.charAt(j - 1)), // Замена
                            prevRow[j] + 1, // Удаление
                            currRow[j - 1] + 1 // Вставка
                    ));
                }
            }
            distance += currRow[s2.length()]; // Добавляем расстояние между словами
            if (distance > NORMAL_DISTANCE) // Если превышено допустимое расстояние, возвращаем false
                return false;
        }
        return true; // Если все проверки пройдены, строки считаются схожими
    }

    // Класс для сравнения списков путей. Сортирует и сравнивает по первому элементу.
    protected static class myArrayComparator implements Comparator<ArrayList<Path>> {
        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            Collections.sort(a1); // Сортируем первый список
            Collections.sort(a2); // Сортируем второй список

            return a1.get(0).compareTo(a2.get(0)); // Сравниваем по первому элементу
        }
    }

    // Метод для нахождения схожих файлов
    private static ArrayList<ArrayList<Path>> findEqualFiles(HashMap<Path, String> filePaths) {
        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>();
        ArrayList<Path> array, used = new ArrayList<>(); // Список обработанных файлов

        for (Path filePath1 : filePaths.keySet()) {
            if (!used.contains(filePath1)) { // Если файл еще не обработан
                array = new ArrayList<>();
                array.add(filePath1);

                for (Path filePath2 : filePaths.keySet())
                    if (filePath1 != filePath2 && checkDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        array.add(filePath2); // Добавляем схожий файл в список
                        used.add(filePath2); // Помечаем файл как обработанный
                    }

                if (array.size() > 1) // Если есть схожие файлы, добавляем список в результат
                    equalFiles.add(array);
            }
        }
        return equalFiles;
    }

    // Метод для нахождения и отображения клонов в Java-классах
    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) {
        ArrayList<ArrayList<Path>> equalFiles;
        Set<String> classNames = classes.keySet(); // Список имен классов

        int count;

        for (String className : classNames) {
            count = 0;
            equalFiles = findEqualFiles(classes.get(className));
            Collections.sort(equalFiles, new myArrayComparator()); // Сортируем результаты

            if (!equalFiles.isEmpty()) {
                System.out.println("\n---" + className + "---");
                for (ArrayList<Path> paths : equalFiles) {
                    System.out.println("\nClones №" + ++count);
                    for (Path path : paths)
                        System.out.println(path); // Выводим пути до файлов-клонов
                }
            }
        }
    }

    // Метод для анализа Java-файлов в директории src
    protected static void getInformation() throws IOException {
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>();

        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Рекурсивный обход всех файлов в директории src
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) { // Проверяем, что файл Java
                            try {
                                char[] charArr;
                                String str = Files.readString(directory);
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем package, import, комментарии и лишние пробелы
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "")
                                            .replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                                            .replaceAll("//.*?\r\n\\s*", "");

                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");

                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;

                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        str = new String(move(charArr));
                                    }
                                    str = str.replaceAll("[\u0000- ]++", " ");

                                    // Группируем файлы по имени класса
                                    javaClasses.computeIfAbsent(directory.getFileName().toString(), k -> new HashMap<>())
                                            .put(src.relativize(directory), str);
                                }
                            } catch (IOException e) {
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );
            findCopies(javaClasses); // Ищем клоны файлов
        }
    }

    // Точка входа в программу
    public static void main(String[] args) throws IOException {
        getInformation(); // Запускаем анализ
    }
}
