package by.it.group351001.orsik.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC extends SourceScannerA {
    // Класс SourceScannerC наследует функциональность класса SourceScannerA.

    static final int NORMAL_DISTANCE = 9;
    // Константа, определяющая максимально допустимое значение расстояния между строками.

    private static int areReplacementNumbers(char c1, char c2) {
        // Метод определяет стоимость замены одного символа на другой.
        return c1 == c2 ? 0 : 1;
        // Если символы равны, то стоимость равна 0, иначе — 1.
    }

    private static int getMinEdit(int... numbers) {
        // Метод возвращает минимальное значение из переданных чисел.
        return Arrays.stream(numbers).min().orElse(
                Integer.MAX_VALUE);
        // Использует стрим для нахождения минимума. Если массив пуст, возвращает максимально возможное значение.
    }

    private static boolean checkDistance(String file1, String file2) {
        // Метод проверяет, находится ли расстояние между двумя строками в пределах нормы.
        int distance = Math.abs(file1.length() - file2.length());
        // Вычисляем разницу в длине строк file1 и file2 как начальное значение расстояния.

        if (distance > NORMAL_DISTANCE)
            return false;
        // Если начальное расстояние уже больше допустимого, возвращаем false.

        String s1, s2;
        // Объявление переменных для работы с частями строк.
        String[] array_s1 = file1.split(" "), array_s2 = file2.split(" ");
        // Разделение строк на массивы слов по пробелам.

        for (int index = 0; index < array_s1.length; index++) {
            // Проходим по каждому слову в массиве array_s1.
            s1 = array_s1[index];
            s2 = array_s2[index];
            // Получаем текущее слово из обоих массивов для сравнения.

            int length = s2.length() + 1;
            // Определяем длину строки s2, увеличенную на 1, для построения матрицы.

            int[] currRow = new int[length];
            // Создаем массив для текущей строки матрицы расстояний.
            int[] prevRow;
            // Переменная для хранения предыдущей строки матрицы.

            for (int i = 0; i <= s1.length(); i++) {
                // Итерация по символам строки s1, включая дополнительную строку для пустого символа.
                prevRow = currRow;
                // Перемещаем текущую строку в prevRow.
                currRow = new int[length];
                // Инициализируем новую строку для текущей итерации.

                for (int j = 0; j <= s2.length(); j++) {
                    // Итерация по символам строки s2, включая дополнительный символ для пустой строки.
                    currRow[j] = i == 0 ? j : (j == 0 ? i : getMinEdit(
                            // Если индекс i равен 0, записываем j (добавление символа в s2).
                            // Если индекс j равен 0, записываем i (удаление символа из s1).
                            prevRow[j - 1] + areReplacementNumbers(s1.charAt(i - 1), s2.charAt(j - 1)),
                            // Иначе вычисляем стоимость замены символов.
                            prevRow[j] + 1,
                            // Стоимость удаления символа из строки s1.
                            currRow[j - 1] + 1));
                    // Стоимость добавления символа в строку s2.
                }
            }
            distance += currRow[s2.length()];
            // Увеличиваем общее расстояние на значение последней ячейки строки матрицы.

            if (distance > NORMAL_DISTANCE)
                return false;
            // Если общее расстояние превышает норму, возвращаем false.
        }
        return true;
        // Если проверка завершена успешно, возвращаем true.
    }


    protected static class myArrayComparator implements Comparator<ArrayList<Path>> {
        // Вложенный класс для сравнения двух списков ArrayList<Path>.

        @Override
        public int compare(ArrayList<Path> a1, ArrayList<Path> a2) {
            // Метод для сравнения двух списков.

            Collections.sort(a1);
            // Сортируем первый список по естественному порядку.

            Collections.sort(a2);
            // Сортируем второй список по естественному порядку.

            return a1.getFirst().compareTo(a2.getFirst());
            // Сравниваем первый элемент отсортированных списков.
        }
    }

    private static ArrayList<ArrayList<Path>> findEqualFiles(HashMap<Path, String> filePaths) {
        // Метод для нахождения файлов с одинаковым содержимым.

        ArrayList<ArrayList<Path>> equalFiles = new ArrayList<>();
        // Список списков путей, которые содержат одинаковые файлы.

        ArrayList<Path> array, used = new ArrayList<>();
        // Временный список для хранения текущей группы одинаковых файлов (array)
        // и список уже обработанных файлов (used).

        for (Path filePath1 : filePaths.keySet()) {
            // Проходим по всем ключам (путям) в HashMap filePaths.

            if (!used.contains(filePath1)) {
                // Если файл filePath1 еще не был обработан, продолжаем.

                array = new ArrayList<>();
                // Создаем новый список для текущей группы файлов.

                array.add(filePath1);
                // Добавляем первый файл в группу.

                for (Path filePath2 : filePaths.keySet())
                    // Сравниваем filePath1 со всеми остальными файлами.

                    if (filePath1 != filePath2 && checkDistance(filePaths.get(filePath1), filePaths.get(filePath2))) {
                        // Если filePath1 и filePath2 разные файлы и их содержимое похоже (по checkDistance), добавляем filePath2 в группу.

                        array.add(filePath2);
                        // Добавляем filePath2 в текущую группу.

                        used.add(filePath2);
                        // Отмечаем filePath2 как обработанный.
                    }

                if (array.size() > 1)
                    equalFiles.add(array);
                // Если в группе больше одного файла, добавляем эту группу в список одинаковых файлов.
            }
        }
        return equalFiles;
        // Возвращаем список групп одинаковых файлов.
    }

    private static void findCopies(HashMap<String, HashMap<Path, String>> classes) {
        // Метод для поиска и вывода копий файлов для каждой категории.

        ArrayList<ArrayList<Path>> equalFiles;
        // Список групп одинаковых файлов.

        Set<String> classNames = classes.keySet();
        // Получаем множество имен классов.

        int count;
        // Счетчик для нумерации групп копий.

        for (String className : classNames) {
            // Перебираем все классы.

            count = 0;
            // Обнуляем счетчик для нового класса.

            equalFiles = findEqualFiles(classes.get(className));
            // Ищем одинаковые файлы в текущем классе.

            Collections.sort(equalFiles, new myArrayComparator());
            // Сортируем группы файлов с использованием созданного компаратора.

            if (!equalFiles.isEmpty()) {
                // Если найдены одинаковые файлы, выводим их.

                System.out.println("\n---" + className + "---");
                // Выводим имя текущего класса.

                for (ArrayList<Path> paths : equalFiles) {
                    // Проходим по каждой группе одинаковых файлов.

                    System.out.println("\nClones №" + ++count);
                    // Выводим номер текущей группы копий.

                    for (Path path : paths)
                        System.out.println(path);
                    // Выводим путь каждого файла в группе.
                }
            }
        }
    }


    protected static void getInformation() throws IOException {
        // Метод для сбора информации о Java-классах в проекте и поиска их копий.

        HashMap<String, HashMap<Path, String>> javaClasses = new HashMap<>();
        // Хранение данных о классах. Ключ — имя класса, значение — пути и содержимое файлов.

        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);
        // Получаем путь к каталогу `src` относительно текущего рабочего каталога.

        try (Stream<Path> fileTrees = Files.walk(src)) {
            // Используем Files.walk для рекурсивного обхода всех файлов и папок в каталоге `src`.

            fileTrees.forEach(
                    directory -> {
                        // Обрабатываем каждый найденный файл/каталог.

                        if (directory.toString().endsWith(".java")) {
                            // Проверяем, является ли файл Java-кодом (по расширению .java).

                            try {
                                char[] charArr;
                                // Объявление массива символов для работы с содержимым файла.

                                String str = Files.readString(directory);
                                // Читаем содержимое текущего файла.

                                if (!str.contains("@Test") && !str.contains("org.junit.Test")) {
                                    // Пропускаем файлы, содержащие тестовые аннотации.

                                    str = str.replaceAll("package.*;", "")
                                            .replaceAll("import.*;", "");
                                    // Удаляем строки с декларациями пакетов и импортов.

                                    str = str.replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                                            .replaceAll("//.*?\r\n\\s*", "");
                                    // Удаляем многострочные и однострочные комментарии.

                                    while (str.contains("\r\n\r\n"))
                                        str = str.replaceAll("\r\n\r\n", "\r\n");
                                    // Убираем повторяющиеся пустые строки.

                                    if (!str.isEmpty() && (str.charAt(0) < 33 || str.charAt(str.length() - 1) < 33)) {
                                        // Если строка не пустая и содержит пробельные символы в начале или конце.

                                        charArr = str.toCharArray();
                                        // Преобразуем строку в массив символов.

                                        int indexF = 0, indexL = charArr.length - 1;
                                        // Индексы для начала и конца строки.

                                        while (indexF < charArr.length && charArr[indexF] < 33 && charArr[indexF] != 0)
                                            charArr[indexF++] = 0;
                                        // Убираем начальные пробельные символы.

                                        while (indexL >= 0 && charArr[indexL] < 33 && charArr[indexL] != 0)
                                            charArr[indexL--] = 0;
                                        // Убираем конечные пробельные символы.

                                        str = new String(move(charArr));
                                        // Преобразуем очищенный массив символов обратно в строку.
                                    }
                                    str = str.replaceAll("[\u0000- ]++", " ");
                                    // Заменяем последовательности пробельных символов одним пробелом.

                                    if (!javaClasses.containsKey(directory.getFileName().toString()))
                                        javaClasses.put(directory.getFileName().toString(), new HashMap<>());
                                    // Если имя файла отсутствует в HashMap, добавляем его.

                                    javaClasses.get(directory.getFileName().toString())
                                            .put(src.relativize(directory), str);
                                    // Добавляем относительный путь и содержимое файла в соответствующий ключ.
                                }
                            } catch (IOException e) {
                                // Обработка исключений при чтении файла.

                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory);
                                    // Этот блок кода не выполняется (условие всегда ложное).
                                }
                            }
                        }
                    }
            );
            findCopies(javaClasses);
            // Вызываем метод findCopies для анализа и вывода копий файлов.
        }
    }

    public static void main(String[] args) throws IOException {
        // Точка входа в программу.

        getInformation();
        // Запуск метода для сбора информации и анализа копий.
    }
}
