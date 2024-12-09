package by.it.group351001.sosnovski.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC extends SourceScannerA {

    /**
     Назначение:
     Класс SourceScannerC предназначен для анализа и поиска дубликатов (копий) Java-файлов
     в проекте на основе их текстового содержимого.

     Основные шаги работы программы:

     Сканирование файлов (src) и фильтрация их данных:
     Удаляются ненужные части из Java-файлов, такие как package, import, комментарии и
     лишние переносы строк.
     Только чистый и обработанный код учитывается для анализа.

     Расчёт схожести между файлами:
     Сравниваются файлы на основе их текстового сходства при помощи функции checkDistance.
     Если два файла достаточно схожи, считаются дубликатами.

     Поиск и группировка схожих файлов:
     Группировка выполняется по схожести их текстового контента.

     Вывод информации о найденных дубликатах:
     Найденные дубликаты выводятся на экран с детализацией.

     Ключевые функции и их роли:
     checkDistance — проверяет расстояние между двумя строками и определяет, насколько их
     тексты схожи.
     findEqualFiles — находит файлы с похожим содержимым.
     findCopies — группирует и печатает информацию о найденных группах схожих файлов.
     getInformation — сканирует файлы в папке src, очищает их от лишних данных и вызывает
     поиск дубликатов.
     */

    private static final int NORMAL_DISTANCE = 9; // Максимальное расстояние схожести между файлами

    /**
     * Проверяет, являются ли два символа заменяемыми.
     */
    private static int areReplacementNumbers(char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    }

    /**
     * Возвращает минимальное значение из переданных чисел.
     */
    private static int getMinEdit(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    /**
     * Проверяет схожесть между двумя файлами на основе расстояния Левенштейна.
     */
    private static boolean checkDistance(String file1, String file2) {
        int distance = Math.abs(file1.length() - file2.length());

        if (distance > NORMAL_DISTANCE) return false;

        String[] array_s1 = file1.split(" ");
        String[] array_s2 = file2.split(" ");

        for (int index = 0; index < array_s1.length; index++) {
            String s1 = array_s1[index];
            String s2 = array_s2[index];

            int[] currRow = new int[s2.length() + 1];
            int[] prevRow;

            for (int i = 0; i <= s1.length(); i++) {
                prevRow = currRow;
                currRow = new int[s2.length() + 1];

                for (int j = 0; j <= s2.length(); j++) {
                    currRow[j] = i == 0 ? j : (j == 0 ? i : getMinEdit(
                            prevRow[j - 1] + areReplacementNumbers(s1.charAt(i - 1), s2.charAt(j - 1)),
                            prevRow[j] + 1,
                            currRow[j - 1] + 1
                    ));
                }
            }

            distance += currRow[s2.length()];
            if (distance > NORMAL_DISTANCE) return false;
        }

        return true;
    }

    /**
     * Кастомный компаратор для сортировки найденных групп схожих файлов.
     */
    protected static class myArrayComparator implements Comparator<ArrayList<Path>> {
        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            Collections.sort(a1);
            Collections.sort(a2);

            return a1.get(0).compareTo(a2.get(0));
        }
    }

    /**
     * Находит равные файлы на основе схожести их текстового содержимого.
     */
    private static ArrayList<ArrayList<Path>> findEqualFiles(HashMap<Path, String> filePaths) {
        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>();
        ArrayList<Path> array, used = new ArrayList<>();

        for (Path filePath1 : filePaths.keySet()) {
            if (!used.contains(filePath1)) {
                array = new ArrayList<>();
                array.add(filePath1);

                for (Path filePath2 : filePaths.keySet()) {
                    if (filePath1 != filePath2 && checkDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        array.add(filePath2);
                        used.add(filePath2);
                    }
                }

                if (array.size() > 1) {
                    equalFiles.add(array);
                }
            }
        }

        return equalFiles;
    }

    /**
     * Основной метод для поиска и группировки схожих файлов.
     */
    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) {
        ArrayList<ArrayList<Path>> equalFiles;
        Set<String> classNames = classes.keySet();

        for (String className : classNames) {
            int count = 0;
            equalFiles = findEqualFiles(classes.get(className));
            Collections.sort(equalFiles, new myArrayComparator());

            if (!equalFiles.isEmpty()) {
                System.out.println("\n---" + className + "---");
                for (ArrayList<Path> paths : equalFiles) {
                    System.out.println("\nClones №" + ++count);
                    for (Path path : paths) {
                        System.out.println(path);
                    }
                }
            }
        }
    }

    /**
     * Сканирует файлы и вызывает анализ.
     */
    protected static void getInformation() throws IOException {
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>();
        Path src = Path.of(System.getProperty("user.dir") + File.separator + "src" + File.separator);

        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(directory -> {
                if (directory.toString().endsWith(".java")) {
                    try {
                        String str = Files.readString(directory);
                        str = str.replaceAll("package.*;", "")
                                .replaceAll("import.*;", "");

                        if (!javaClasses.containsKey(directory.getFileName().toString()))
                            javaClasses.put(directory.getFileName().toString(), new HashMap<>());
                        javaClasses.get(directory.getFileName().toString()).put(src.relativize(directory), str);
                    } catch (IOException e) {
                        System.err.println("Error while processing: " + directory);
                    }
                }
            });

            findCopies(javaClasses);
        }
    }

    public static void main(String[] args) throws IOException {
        getInformation();
    }
}
