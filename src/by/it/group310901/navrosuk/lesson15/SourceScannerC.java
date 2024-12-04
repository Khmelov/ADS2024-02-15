//Создайте класс SourceScannerC с методом main,
//который читает все файлы *.java из каталога src и его подкаталогов.
//
//Каталог можно получить так:
//        String src = System.getProperty("user.dir")
//                       + File.separator + "src" + File.separator;
//
//Файлы, содержащие в тексте @Test или org.junit.Test (тесты)
//не участвуют в обработке.
//
//В каждом тексте файла необходимо:
//1. Удалить строку package и все импорты.
//2. Удалить все комментарии за O(n) от длины текста.
//3. Заменить все последовательности символов с кодом <33 на 32 (один пробел), т.е привести текст к строке.
//4. Выполнить trim() для полученной строки.
//
//В полученном наборе текстов:
//1. Найти наиболее похожие тексты по метрике "расстояние Левенштейна",
//   и определить копия ли это, считая копиями тексты с числом правок <10.
//2. Если текст имеет копию(и), то вывести путь файла этого текста
//   и в следующих строках путь(и) к копии(ям).
//3. Повторить для всех файлов с копиями,
//   при выводе сортировать файлы лексикографически по их пути.
//
//Найдите способ корректно обрабатывать ошибки MalformedInputException
//
//Оптимизируйте производительность решения (это может интересно).
//
//Все операции не должны ничего менять на дисках (разрешено только чтение).
//Работа не имеет цели найти плагиат, поэтому не нужно менять коды своих программ.
package by.it.group310901.navrosuk.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC extends SourceScannerA{
    static final int NORMAL_DISTANCE = 9;
    private static int areReplacementNumbers(char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    }
    private static int getMinEdit(int... numbers) {
        return Arrays.stream(numbers).min().orElse(
                Integer.MAX_VALUE);
    }
    private static boolean checkDistance(String file1, String file2) {
        int distance = Math.abs(file1.length() - file2.length());
        if (distance > NORMAL_DISTANCE)
            return false;
        String s1, s2;
        String[] array_s1 = file1.split(" "), array_s2 = file2.split(" ");
        for (int index = 0; index < array_s1.length; index++) {
            s1 = array_s1[index];
            s2 = array_s2[index];
            int length = s2.length() + 1;
            int[] currRow = new int[length];
            int[] prevRow;
            for (int i = 0; i <= s1.length(); i++) {
                prevRow = currRow;
                currRow = new int[length];
                for (int j = 0; j <= s2.length(); j++) {
                    currRow[j] = i == 0 ? j : (j == 0 ? i : getMinEdit(prevRow[j - 1]
                                    + areReplacementNumbers(s1.charAt(i - 1), s2.charAt(j - 1)),
                            prevRow[j] + 1,
                            currRow[j - 1] + 1));
                }
            }
            distance += currRow[s2.length()];
            if (distance > NORMAL_DISTANCE)
                return false;
        }
        return true;
    }
    protected static class myArrayComparator implements Comparator<ArrayList<Path>> {
        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            Collections.sort(a1);
            Collections.sort(a2);
            return a1.getFirst().compareTo(a2.getFirst());
        }
    }
    private static ArrayList<ArrayList<Path>> findEqualFiles(HashMap<Path, String> filePaths) {
        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>();
        ArrayList<Path> array, used = new ArrayList<>();
        for(Path filePath1 : filePaths.keySet()) {
            if (!used.contains(filePath1)) {
                array = new ArrayList<>();
                array.add(filePath1);
                for (Path filePath2 : filePaths.keySet())
                    if (filePath1 != filePath2 && checkDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        array.add(filePath2);
                        used.add(filePath2);
                    }
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
            equalFiles = findEqualFiles(classes.get(className));
            Collections.sort(equalFiles, new myArrayComparator());
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
    protected static void getInformation() throws IOException {
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>();
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
    public static void main(String[] args) throws IOException {
        getInformation();
    }
}