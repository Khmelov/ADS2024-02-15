package by.it.group310902.kasperets.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.move;

public class SourceScannerC  {
    static final int NORMAL_DISTANCE = 9; // Константа NORMAL_DISTANCE, которая используется для определения предельного значения расстояния редактирования между строками.

    private static int compareChars (char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    } // Это метод используется для расчета дистанции редактирования между строками.

    private static int findMinimumValue(int... numbers) {
        return Arrays.stream(numbers).min().orElse(
                Integer.MAX_VALUE);
    } // принимает переменное количество целых чисел и использует поток для нахождения минимального значения. Если список пуст, возвращает Integer.MAX_VALUE.

    private static boolean checkEditDistance(String file1, String file2) { // используется для проверки, насколько схожи два файла на основе их содержимого.
        if (Math.abs(file1.length() - file2.length()) > NORMAL_DISTANCE) // Если разница в длине двух файлов превышает NORMAL_DISTANCE, сразу возвращаем false
            return false;

        int distance = 0; // будет хранить общее расстояние редактирования между двумя файлами.
        String s1, s2;
        String[] array_s1 = file1.split(" "), array_s2 = file2.split(" "); // Разделяем содержимое обоих файлов на слова с помощью метода split(" ").

        for (int index = 0; index < array_s1.length; index++) {
            s1 = array_s1[index];
            s2 = array_s2[index];
            int length = s2.length() + 1;
            int[] currRow = new int[length];
            int[] prevRow;
            // Для каждой пары слов s1 и s2, создаем массивы для хранения расстояний редактирования.
            for (int i = 0; i <= s1.length(); i++) { // бежим по первому слову
                prevRow = currRow;
                currRow = new int[length]; // обновляем текущую и предыдущую строки

                for (int j = 0; j <= s2.length(); j++) {
                    currRow[j] = i == 0 ? j : (j == 0 ? i : findMinimumValue(prevRow[j - 1]
                                    + compareChars(s1.charAt(i - 1), s2.charAt(j - 1)),
                            // Вычисляем расстояние редактирования для текущего символа. Если i или j равны нулю (это означает, что мы находимся в первой строке или столбце), то инициализируем с нуля.
                            prevRow[j] + 1,
                            currRow[j - 1] + 1));
                    // В противном случае, находим минимум из трёх возможных операций (замена, добавление, удаление).
                }
            }
            distance += currRow[s2.length()];
            // добавляем вычисленное расстояние к общему расстоянию
            if (distance > NORMAL_DISTANCE)
                return false;
        }
        return true;
    }

    protected static class PathListComparator implements Comparator<ArrayList<Path>> { // класс для сравнения списка путей
        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            Collections.sort(a1);
            Collections.sort(a2);

            return a1.getFirst().compareTo(a2.getFirst());
        }

    }

    private static ArrayList<ArrayList<Path>> findSimilarFilesGroups(HashMap<Path, String> filePaths) { // Метод для нахождения групп похожих файлов
        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>(); // для хранения групп похожих файлов
        ArrayList<Path> array, used = new ArrayList<>(); // для отслеживания файлов, которые уже были обработаны

        for(Path filePath1 : filePaths.keySet()) { // Запускаем цикл по всем ключам (путям) в filePaths
            if (!used.contains(filePath1)) {
                array = new ArrayList<>();
                array.add(filePath1);

                for (Path filePath2 : filePaths.keySet()) // Внутренний цикл для сравнения текущего файла filePath1 с каждым другим файлом в filePaths
                    if (filePath1 != filePath2 && checkEditDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        array.add(filePath2);
                        used.add(filePath2);
                    } // Если файлы различные и расстояние редактирования между ними небольшое, добавляем второй файл в array и добавляем его в список использованных файлов.

                if (array.size() > 1)
                    equalFiles.add(array);
            }
        }
        return equalFiles;
    }

    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) {
        ArrayList<ArrayList<Path>> equalFiles;
        Set<String> classNames = classes.keySet();

        int count;

        for (String className : classNames) {
            count = 0;
            equalFiles = findSimilarFilesGroups(classes.get(className));
            Collections.sort(equalFiles, new PathListComparator());

            if (!equalFiles.isEmpty()) {
                System.out.println("\n---" + className + "---");
                for (ArrayList<Path> paths : equalFiles) {
                    System.out.println("\nClones №" + ++count);
                    for (Path path : paths)
                        System.out.println(path);
                }
            }
        }
    }

    protected static void getInformation() throws IOException { // который загружает и обрабатывает файлы Java в директории src, а затем сохраняет информацию о классах в хэш-таблице
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>(); // ключ имя файла, значение путь к файлу и его содержимое

        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);// Получаем путь к директории src

        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                String str = Files.readString(directory);
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");

                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
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

                                    if (!javaClasses.containsKey(directory.getFileName().toString()))
                                        javaClasses.put(directory.getFileName().toString(), new HashMap<>());
                                    javaClasses.get(directory.getFileName().toString()).put(src.relativize(directory), str);
                                }
                            } catch (IOException e) {
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );
            findCopies(javaClasses);
        }
    }

    private static char[] move(char[] charArr) {

        return charArr;
    }

    public static void main(String[] args) throws IOException {
        getInformation();
    }


}