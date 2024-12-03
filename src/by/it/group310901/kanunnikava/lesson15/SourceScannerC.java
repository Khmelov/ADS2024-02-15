package by.it.group310901.kanunnikava.lesson15;

/*Создайте класс SourceScannerC с методом main,
который читает все файлы *.java из каталога src и его подкаталогов.
Файлы, содержащие в тексте @Test или org.junit.Test (тесты)
не участвуют в обработке.
В каждом тексте файла необходимо:
1. Удалить строку package и все импорты.
2. Удалить все комментарии за O(n) от длины текста.
3. Заменить все последовательности символов с кодом <33 на 32 (один пробел), т.е привести текст к строке.
4. Выполнить trim() для полученной строки.*/

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC {
    static final int NORMAL_DISTANCE = 9; // Объявляет константу для нормального расстояния

    private static int areReplacementNumbers(char c1, char c2) { // Метод для проверки, являются ли символы одинаковыми
        return c1 == c2 ? 0 : 1; // Возвращает 0, если символы одинаковые, и 1, если разные
    }

    private static int getMinEdit(int... numbers) { // Метод для получения минимального значения из набора чисел
        return Arrays.stream(numbers).min().orElse(
                Integer.MAX_VALUE); // Возвращает минимальное значение или Integer.MAX_VALUE, если поток пуст
    }

    protected static char[] move(char[] array) { // Метод для сжатия массива, удаляя нулевые символы в начале и в конце
        char[] temp;
        int i = 0, size;

        while(array[i] == 0) // Пропускает начальные нулевые символы.
            i++;

        size = array.length - i; // Вычисляет новый размер массива.
        temp = new char[size]; // Создает новый временный массив.
        System.arraycopy(array, i, temp, 0, size); // Копирует содержимое исходного массива в новый массив.
        array = temp;

        i = array.length - 1;
        while (array[i] == 0) // Пропускает конечные нулевые символы.
            i--;

        size = i + 1; // Вычисляет окончательный размер массива.
        temp = new char[size]; // Создает новый временный массив.
        System.arraycopy(array, 0, temp, 0, size); // Копирует содержимое в новый массив
        return temp; // Возвращает новый массив.
    }

    private static boolean checkDistance(String file1, String file2) { // Метод для проверки расстояния Левенштейна между двумя строками
        int distance = Math.abs(file1.length() - file2.length()); // Вычисляет начальное расстояние на основе разницы в длине строк

        if (distance > NORMAL_DISTANCE) // Если начальное расстояние больше допустимого
            return false;

        String s1, s2;
        String[] array_s1 = file1.split(" "), array_s2 = file2.split(" "); // Разделяет строки по пробелам

        for (int index = 0; index < array_s1.length; index++) { // Итерирует по каждому слову
            s1 = array_s1[index];
            s2 = array_s2[index];
            int length = s2.length() + 1;
            int[] currRow = new int[length]; // Создает массив для текущей строки матрицы
            int[] prevRow;

            for (int i = 0; i <= s1.length(); i++) { // Итерирует по каждой букве строки
                prevRow = currRow;
                currRow = new int[length];

                for (int j = 0; j <= s2.length(); j++) { // Итерирует по каждой букве второй строки
                    currRow[j] = i == 0 ? j : (j == 0 ? i : getMinEdit(prevRow[j - 1]
                                    + areReplacementNumbers(s1.charAt(i - 1), s2.charAt(j - 1)),
                            prevRow[j] + 1,
                            currRow[j - 1] + 1)); // Вычисляет минимальное количество правок для текущей позиции
                }
            }
            distance += currRow[s2.length()]; // Добавляет расстояние для текущего слова
            if (distance > NORMAL_DISTANCE) // Если расстояние превышает допустимое
                return false;
        }
        return true; // Возвращает true, если расстояние в пределах нормы
    }

    protected static class MyArrayComparator implements Comparator<ArrayList<Path>> { // Внутренний класс для сравнения списков путей
        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            Collections.sort(a1); // Сортирует первый список
            Collections.sort(a2); // Сортирует второй список

            return a1.get(0).compareTo(a2.get(0)); // Сравнивает первые элементы списков
        }
    }

    private static ArrayList<ArrayList<Path>> findEqualFiles(HashMap<Path, String> filePaths) { // Метод для поиска идентичных файлов.
        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>();
        ArrayList<Path> array, used = new ArrayList<>();

        for(Path filePath1 : filePaths.keySet()) { // Итерирует по каждому пути файла
            if (!used.contains(filePath1)) {
                array = new ArrayList<>();
                array.add(filePath1);

                for (Path filePath2 : filePaths.keySet())
                    if (filePath1 != filePath2 && checkDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        array.add(filePath2); // Добавляет файл, если он идентичен
                        used.add(filePath2); // Помечает файл как использованный
                    }

                if (array.size() > 1)
                    equalFiles.add(array); // Добавляет список идентичных файлов
            }
        }
        return equalFiles;
    }

    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) { // Метод для поиска копий классов
        ArrayList<ArrayList<Path>> equalFiles;
        Set<String> classNames = classes.keySet();

        int count;

        for (String className : classNames) { // Итерирует по каждому имени класса
            count = 0;
            equalFiles = findEqualFiles(classes.get(className));
            Collections.sort(equalFiles, new MyArrayComparator());

            if (!equalFiles.isEmpty()) {
                System.out.println("\n" + className + ":");
                for (ArrayList<Path> paths : equalFiles) {
                    System.out.println("\nКлон №" + ++count);
                    for (Path path : paths)
                        System.out.println(path); // Выводит пути идентичных файлов
                }
            }
        }
    }

    protected static void getInformation() throws IOException { // Метод для получения информации о файлах
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>();

        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator); // Устанавливает путь к директории src

        try (Stream<Path> fileTrees = Files.walk(src)) { // Рекурсивно обходит директорию src
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) {
                            try {
                                char[] charArr;
                                String str = Files.readString(directory);
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", ""); // Удаляет строки пакета и импорта

                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "") // Удаляет блочные комментарии.
                                            .replaceAll("//.*?\r\n\\s*", ""); // Удаляет строковые комментарии.

                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n"); // Удаляет пустые строки

                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        charArr = str.toCharArray();
                                        int indexF = 0, indexL = charArr.length - 1;

                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;

                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;

                                        str = new String(move(charArr)); // Сжимает массив и удаляет нулевые символы
                                    }
                                    str = str.replaceAll("[\u0000- ]++", " "); // Заменяет последовательности пробелов на один пробел

                                    if (!javaClasses.containsKey(directory.getFileName().toString()))
                                        javaClasses.put(directory.getFileName().toString(), new HashMap<>());
                                    javaClasses.get(directory.getFileName().toString()).put(src.relativize(directory), str);
                                }
                            } catch (IOException e) {
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory); // Печатает путь
                                    System.err.println(directory); // Печатает путь к файлу с ошибкой.
                                }
                            }
                        }
                    }
            );
            findCopies(javaClasses); // Ищет и выводит копии файлов в различных классах
        }
    }

    public static void main(String[] args) throws IOException {
        getInformation(); // Вызывает метод для получения информации о файлах и поиска копий
    }
}
