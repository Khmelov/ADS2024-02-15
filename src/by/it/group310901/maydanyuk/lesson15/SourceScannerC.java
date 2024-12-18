package by.it.group310901.maydanyuk.lesson15;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
//Сканирует все файлы с расширением .java в директории src.
//2. Исключает строки, содержащие пакеты, импорты, многострочные комментарии, однострочные комментарии и непечатные символы.
//3. Вычисляет расстояние Левенштейна между словами в содержимом файлов для определения схожести файлов.
//4. Группирует файлы по схожести.
//5. Выводит группы схожих файлов, если такие найдены.

// Основной класс для сканирования исходных файлов Java и выявления схожих файлов
public class SourceScannerC {
    // Константа для максимального допустимого расстояния для определения схожести строк
    static final int MAX_DISTANCE = 9;

    // Метод для вычисления расстояния Левенштейна между двумя строками
    private static int calculateDistance(String word1, String word2) {
        // Массив для хранения текущих значений строк
        int[] currRow = new int[word2.length() + 1];
        // Перебираем символы первой строки
        for (int i = 0; i <= word1.length(); i++) {
            int[] prevRow = currRow;
            currRow = new int[word2.length() + 1];
            // Перебираем символы второй строки
            for (int j = 0; j <= word2.length(); j++) {
                // Вычисляем минимальные операции для преобразования строк
                currRow[j] = i == 0 ? j : (j == 0 ? i : Math.min(
                        Math.min(prevRow[j] + 1, currRow[j - 1] + 1),
                        prevRow[j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1)));
            }
        }
        // Возвращаем расстояние Левенштейна для двух строк
        return currRow[word2.length()];
    }

    // Метод для определения схожести содержимого двух файлов
    private static boolean isSimilar(String content1, String content2) {
        // Если разница в длинах строк больше максимального допустимого расстояния, они не схожи
        if (Math.abs(content1.length() - content2.length()) > MAX_DISTANCE)
            return false;

        // Разбиваем строки на слова и считаем расстояние Левенштейна между словами
        String[] words1 = content1.split(" "), words2 = content2.split(" ");
        int distance = 0;

        // Перебираем все слова первой строки
        for (int i = 0; i < words1.length; i++) {
            // Если количество слов не совпадает, файлы не схожи
            if (i >= words2.length) return false;
            // Вычисляем суммарное расстояние между словами
            distance += calculateDistance(words1[i], words2[i]);
            // Если расстояние превышает максимальное, файлы не схожи
            if (distance > MAX_DISTANCE) return false;
        }
        // Файлы схожи
        return true;
    }

    // Метод для группировки схожих файлов
    private static ArrayList<ArrayList<Path>> groupSimilarFiles(HashMap<Path, String> files) {
        ArrayList<ArrayList<Path>> groups = new ArrayList<>();
        // Множество для отслеживания уже обработанных файлов
        Set<Path> processed = new HashSet<>();

        // Перебираем все файлы
        for (Path file1 : files.keySet()) {
            if (!processed.contains(file1)) {
                ArrayList<Path> group = new ArrayList<>();
                group.add(file1);
                // Перебираем все файлы для сравнения
                for (Path file2 : files.keySet()) {
                    // Если файлы разные и схожи, добавляем в группу
                    if (!file1.equals(file2) && isSimilar(files.get(file1), files.get(file2))) {
                        group.add(file2);
                        // Отмечаем файл как обработанный
                        processed.add(file2);
                    }
                }
                // Добавляем только группы с более чем одним файлом
                if (group.size() > 1) groups.add(group);
            }
        }
        // Возвращаем группы схожих файлов
        return groups;
    }

    // Метод для отображения групп схожих файлов
    private static void displayGroups(HashMap<String, HashMap<Path, String>> fileData) {
        fileData.forEach((name, files) -> {
            ArrayList<ArrayList<Path>> groups = groupSimilarFiles(files);
            if (!groups.isEmpty()) {
                System.out.println("\n---" + name + "---");
                int count = 1;
                for (ArrayList<Path> group : groups) {
                    System.out.println("\nClones №" + count++);
                    group.forEach(System.out::println);
                }
            }
        });
    }

    // Метод для анализа файлов и выявления схожих
    protected static void analyzeFiles() throws IOException {
        HashMap<String, HashMap<Path, String>> fileData = new HashMap<>();
        // Определяем корневую директорию для поиска Java файлов
        Path root = Path.of(System.getProperty("user.dir") + File.separator + "src");

        // Используем Stream для обхода всех файлов в директории src
        try (Stream<Path> paths = Files.walk(root)) {
            // Фильтруем только файлы с расширением .java
            paths.filter(p -> p.toString().endsWith(".java")).forEach(path -> {
                try {
                    // Читаем и обрабатываем содержимое файла, удаляя ненужные части
                    String content = Files.readString(path)
                            .replaceAll("package.*;|import.*;|/\\*[\\w\\W]*?\\*/|//.*|[\u0000- ]+", " ").trim();
                    // Если содержимое не пустое и не содержит тестовых аннотаций, добавляем его в данные файлов
                    if (!content.isEmpty() && !content.contains("@Test")) {
                        fileData.computeIfAbsent(path.getFileName().toString(), k -> new HashMap<>())
                                .put(root.relativize(path), content);
                    }
                } catch (IOException ignored) {} // Игнорируем исключения
            });
        }
        // Отображаем группы схожих файлов
        displayGroups(fileData);
    }

    // Главный метод программы
    public static void main(String[] args) throws IOException {
        // Анализируем файлы
        analyzeFiles();
    }
}
