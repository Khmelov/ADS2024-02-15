package by.it.group310902.chyliuk.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC {
    static final int NORMAL_DISTANCE = 9; // Максимально допустимое расстояние редактирования между строками для определения их схожести.

    private static int compareChars(char c1, char c2) {
        // Сравниваем два символа: возвращаем 0, если они равны, и 1, если разные.
        return c1 == c2 ? 0 : 1;
    }

    private static int findMinimumValue(int... numbers) {
        // Находим минимальное значение среди переданных чисел. Если список пуст, возвращаем Integer.MAX_VALUE.
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    private static boolean checkEditDistance(String file1, String file2) {
        // Проверяем схожесть двух файлов по их содержимому, вычисляя расстояние редактирования.
        if (Math.abs(file1.length() - file2.length()) > NORMAL_DISTANCE) {
            // Если разница в длине файлов превышает допустимое значение, возвращаем false.
            return false;
        }

        int distance = 0; // Счётчик расстояния редактирования.
        String[] words1 = file1.split(" "), words2 = file2.split(" "); // Разделяем содержимое файлов на слова.

        for (int index = 0; index < words1.length; index++) {
            String s1 = words1[index];
            String s2 = words2[index];
            int[] currRow = new int[s2.length() + 1]; // Массив для текущей строки.
            int[] prevRow;

            for (int i = 0; i <= s1.length(); i++) {
                prevRow = currRow;
                currRow = new int[s2.length() + 1]; // Обновляем массив текущей строки.

                for (int j = 0; j <= s2.length(); j++) {
                    currRow[j] = (i == 0)
                            ? j
                            : (j == 0
                            ? i
                            : findMinimumValue(
                            prevRow[j - 1] + compareChars(s1.charAt(i - 1), s2.charAt(j - 1)),
                            prevRow[j] + 1,
                            currRow[j - 1] + 1));
                }
            }
            distance += currRow[s2.length()]; // Добавляем вычисленное расстояние для текущей пары слов.
            if (distance > NORMAL_DISTANCE) {
                // Прекращаем проверку, если общее расстояние превысило допустимое значение.
                return false;
            }
        }
        return true;
    }

    protected static class PathListComparator implements Comparator<ArrayList<Path>> {
        // Класс для сортировки списков путей.
        @Override
        public int compare(ArrayList<Path> list1, ArrayList<Path> list2) {
            Collections.sort(list1);
            Collections.sort(list2);
            return list1.get(0).compareTo(list2.get(0)); // Сравниваем списки по первому элементу.
        }
    }

    private static ArrayList<ArrayList<Path>> findSimilarFilesGroups(HashMap<Path, String> filePaths) {
        // Ищем группы файлов с похожим содержимым.
        ArrayList<ArrayList<Path>> similarFiles = new ArrayList<>();
        ArrayList<Path> used = new ArrayList<>(); // Список обработанных файлов.

        for (Path path1 : filePaths.keySet()) {
            if (!used.contains(path1)) {
                ArrayList<Path> group = new ArrayList<>();
                group.add(path1);

                for (Path path2 : filePaths.keySet()) {
                    if (path1 != path2 && checkEditDistance(filePaths.get(path1), filePaths.get(path2))) {
                        // Если файлы различны, но их содержимое похоже, добавляем их в одну группу.
                        group.add(path2);
                        used.add(path2);
                    }
                }
                if (group.size() > 1) {
                    similarFiles.add(group); // Добавляем группу файлов, если она содержит более одного элемента.
                }
            }
        }
        return similarFiles;
    }

    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) {
        // Находим дубликаты среди переданных классов.
        for (String className : classes.keySet()) {
            ArrayList<ArrayList<Path>> similarFiles = findSimilarFilesGroups(classes.get(className));
            similarFiles.sort(new PathListComparator());

            if (!similarFiles.isEmpty()) {
                System.out.println("\n---" + className + "---");
                int count = 0;
                for (ArrayList<Path> paths : similarFiles) {
                    System.out.println("\nГруппа дубликатов №" + ++count);
                    for (Path path : paths) {
                        System.out.println(path);
                    }
                }
            }
        }
    }

    protected static void getInformation() throws IOException {
        // Загружаем информацию о файлах в директории src и ищем группы дубликатов.
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>();
        Path src = Path.of(System.getProperty("user.dir"), "src");

        try (Stream<Path> fileStream = Files.walk(src)) {
            fileStream.forEach(file -> {
                if (file.toString().endsWith(".java")) {
                    try {
                        String content = Files.readString(file);
                        if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                            // Убираем строки package, import и комментарии.
                            content = content.replaceAll("package.*;", "")
                                    .replaceAll("import.*;", "")
                                    .replaceAll("/\\*[\\w\\W]*?\\*/", "")
                                    .replaceAll("//.*", "")
                                    .replaceAll("\\s+", " ").trim();

                            javaClasses.computeIfAbsent(file.getFileName().toString(), k -> new HashMap<>())
                                    .put(src.relativize(file), content);
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка при обработке файла: " + file);
                    }
                }
            });
            findCopies(javaClasses); // Запускаем поиск дубликатов.
        }
    }

    public static void main(String[] args) throws IOException {
        getInformation(); // Запускаем процесс анализа файлов.
    }
}
