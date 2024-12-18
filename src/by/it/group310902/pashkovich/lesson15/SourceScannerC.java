package by.it.group310902.pashkovich.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

//Для всех обработанных текстов файлов вычисляется расстояние Левенштейна, чтобы определить степень их схожести.
//Если два текста имеют расстояние Левенштейна менее 10, они считаются копиями.
//Расстояние Левенштейна вычисляется с помощью метода checkDistance, который:
//
//Итерируется по словам в текстах.
//Использует алгоритм динамического программирования для оценки минимального числа правок (вставка, удаление, замена символа).
//группировка копий :
//
//        Файлы с текстами, которые являются копиями, группируются вместе.
//        Группы файлов сортируются лексикографически для удобства вывода.
//
//        Вывод результатов :
//
//        Для каждой группы копий выводится:
//        Основной файл (первый в группе).
//        Все файлы-копии (с отступом).

        public class SourceScannerC {
    private static final int NORMAL_DISTANCE = 9;


    private static int areReplacementNumbers(char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    }

    private static int getMinEdit(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    private static boolean checkDistance(String file1, String file2) {
        int distance = Math.abs(file1.length() - file2.length());

        if (distance > NORMAL_DISTANCE)
            return false;

        String[] words1 = file1.split(" "), words2 = file2.split(" ");
        for (int i = 0; i < Math.min(words1.length, words2.length); i++) {
            String s1 = words1[i];
            String s2 = words2[i];
            int[] prevRow = new int[s2.length() + 1];
            int[] currRow = new int[s2.length() + 1];

            for (int j = 0; j <= s1.length(); j++) {
                int[] temp = prevRow;
                prevRow = currRow;
                currRow = temp;

                for (int k = 0; k <= s2.length(); k++) {
                    if (j == 0) {
                        currRow[k] = k;
                    } else if (k == 0) {
                        currRow[k] = j;
                    } else {
                        currRow[k] = getMinEdit(
                                prevRow[k - 1] + areReplacementNumbers(s1.charAt(j - 1), s2.charAt(k - 1)),
                                prevRow[k] + 1,
                                currRow[k - 1] + 1
                        );
                    }
                }
            }
            distance += currRow[s2.length()];
            if (distance > NORMAL_DISTANCE)
                return false;
        }
        return true;
    }

    private static List<List<Path>> findEqualFiles(Map<Path, String> fileContents) {
        List<List<Path>> result = new ArrayList<>();
        Set<Path> processed = new HashSet<>();

        for (Path path1 : fileContents.keySet()) {
            if (!processed.contains(path1)) {
                List<Path> group = new ArrayList<>();
                group.add(path1);

                for (Path path2 : fileContents.keySet()) {
                    if (!path1.equals(path2) && checkDistance(fileContents.get(path1), fileContents.get(path2))) {
                        group.add(path2);
                        processed.add(path2);
                    }
                }
                if (group.size() > 1) {
                    result.add(group);
                }
            }
        }
        return result;
    }

    private static String processFileContent(String content) {
        // Удаляем строки package, import и комментарии
        content = content.replaceAll("package\\s+.*?;", "")
                .replaceAll("import\\s+.*?;", "")
                .replaceAll("/\\*.*?\\*/", "")
                .replaceAll("//.*?(\r\n|\n)", "");
        // Преобразуем символы <33 в пробелы
        content = content.replaceAll("[\\u0000-\\u0020]+", " ").trim();
        return content;
    }

    public static void main(String[] args) {
        String srcDir = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path srcPath = Path.of(srcDir);
        Map<Path, String> fileContents = new HashMap<>();

        try (Stream<Path> paths = Files.walk(srcPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            String content = Files.readString(path);
                            if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                                fileContents.put(path, processFileContent(content));
                            }
                        } catch (MalformedInputException e) {
                            System.err.println("Error reading file (MalformedInput): " + path);
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + path);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error walking directory: " + srcPath);
        }

        List<List<Path>> equalFiles = findEqualFiles(fileContents);
        equalFiles.sort(Comparator.comparing(list -> list.get(0).toString()));

        for (List<Path> group : equalFiles) {
            group.sort(Comparator.naturalOrder());
            System.out.println("\nFile: " + group.get(0));
            group.subList(1, group.size()).forEach(file -> System.out.println("  Copy: " + file));
        }
    }
}
