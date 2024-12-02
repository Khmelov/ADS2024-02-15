package by.it.group351004.narivonchik.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

// Класс SourceScannerC наследует функциональность класса SourceScannerA
public class SourceScannerC extends SourceScannerA {
    static final int NORMAL_DISTANCE = 9; // Максимально допустимое число правок между файлами

    // Внутренний класс для сравнения массивов путей
    protected static class myArrayComparator implements Comparator<ArrayList<Path>> {
        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            Collections.sort(a1); // Сортируем первый массив
            Collections.sort(a2); // Сортируем второй массив

            return a1.get(0).compareTo(a2.get(0)); // Сравниваем первый элемент
        }
    }

    // Основной метод программы
    public static void main(String[] args) throws IOException {
        getInformation(); // Вызываем метод получения информации
    }

    // Метод для получения информации о Java файлах
    protected static void getInformation() throws IOException {
        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>(); // Словарь для хранения классов Java

        // Определяем путь к директории src
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        // Обходим файлы в директории
        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(
                    directory -> {
                        if (directory.toString().endsWith(".java")) { // Проверяем, что файл .java
                            try {
                                char[] charArr;
                                // Читаем содержимое файла
                                String str = Files.readString(directory);
                                // Проверяем, содержит ли файл тесты
                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Удаляем строки с package и import
                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");

                                    // Удаляем многострочные и однострочные комментарии
                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                                            .replaceAll("//.*?\r\n\\s*", "");

                                    // Удаляем пустые строки
                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");

                                    // Проверяем, не пустая ли строка
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
                                    // Заменяем нулевые символы на пробелы
                                    str = str.replaceAll("[\u0000- ]++", " ");

                                    // Добавляем информацию о классе в словарь
                                    if (!javaClasses.containsKey(directory.getFileName().toString()))
                                        javaClasses.put(directory.getFileName().toString(), new HashMap<>());
                                    javaClasses.get(directory.getFileName().toString()).put(src.relativize(directory), str);
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
            findCopies(javaClasses); // Ищем копии файлов
        }
    }

    // Метод для определения, равны ли два символа
    private static int areReplacementNumbers(char c1, char c2) {
        return c1 == c2 ? 0 : 1; // Возвращает 0, если символы равны, иначе 1
    }

    // Метод для получения минимального значения из массива целых чисел
    private static int getMinEdit(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE); // Возвращает минимальное значение
    }

    // Метод для вывода копий файлов
    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) {     //fileName -> (path, content)
        ArrayList<ArrayList<Path>> equalFiles; // Список для хранения равных файлов
        Set<String> classNames = classes.keySet(); // Получаем имена классов

        int count;

        // Обходим имена классов
        for (String className : classNames) {
            count = 0;
            equalFiles = findEqualFiles(classes.get(className)); // Находим равные файлы
            Collections.sort(equalFiles, new myArrayComparator()); // Сортируем их

            if (!equalFiles.isEmpty()) {
                System.out.println("\n---" + className + "---"); // Выводим имя класса
                for (ArrayList<Path> paths : equalFiles) {
                    System.out.println("\nClones №" + ++count); // Выводим номер клонов
                    for (Path path : paths)
                        System.out.println(path); // Выводим пути к равным файлам
                }
            }
        }
    }

    // Метод для нахождения равных файлов
    private static ArrayList<ArrayList<Path>> findEqualFiles(HashMap<Path, String> filePaths) {   //path/content
        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>(); // Список для хранения равных файлов
        ArrayList<Path> array, used = new ArrayList<>(); // Массив для хранения использованных файлов

        // Обходим все пути файлов
        for(Path filePath1 : filePaths.keySet()) {
            if (!used.contains(filePath1)) { // Проверяем, не использован ли файл
                array = new ArrayList<>(); // Новый массив для равных файлов
                array.add(filePath1);

                // Сравниваем с другими файлами
                for (Path filePath2 : filePaths.keySet())
                    if (filePath1 != filePath2 && checkDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        array.add(filePath2); // Добавляем равный файл
                        used.add(filePath2); // Помечаем файл как использованный
                    }

                if (array.size() > 1) // Если найдены равные файлы
                    equalFiles.add(array);
            }
        }
        return equalFiles; // Возвращаем список равных файлов
    }

    // Метод для проверки расстояния между двумя строками
    private static boolean checkDistance(String file1, String file2) {
        int distance = Math.abs(file1.length() - file2.length()); // Вычисляем разницу в длине

        // Если разница превышает нормальное расстояние, возвращаем false
        if (distance > NORMAL_DISTANCE)
            return false;

        // Разбиваем строки на массивы слов
        String s1, s2;
        String[] array_s1 = file1.split(" "), array_s2 = file2.split(" ");

        // Для каждого слова в первой строке
        for (int index = 0; index < array_s1.length; index++) {
            s1 = array_s1[index];
            s2 = array_s2[index];
            int length = s2.length() + 1;
            int[] currRow = new int[length]; // Текущая строка для расстояния
            int[] prevRow; // Предыдущая строка

            // Вычисляем расстояние Левенштейна
            for (int i = 0; i <= s1.length(); i++) {
                prevRow = currRow; // Сохраняем предыдущую строку
                currRow = new int[length]; // Обновляем текущую строку

                for (int j = 0; j <= s2.length(); j++) {
                    currRow[j] = i == 0 ? j : (j == 0 ? i :
                            getMinEdit(prevRow[j - 1] + areReplacementNumbers(s1.charAt(i - 1), s2.charAt(j - 1)),
                                    prevRow[j] + 1,
                                    currRow[j - 1] + 1));
                }
            }
            distance += currRow[s2.length()]; // Обновляем общее расстояние
            if (distance > NORMAL_DISTANCE) // Проверяем, не превышает ли оно нормальное
                return false;
        }
        return true; // Возвращаем true, если расстояние допустимо
    }

}