/*
 * Программа расширяет функциональность программы SourceScannerA для анализа и обработки
 * всех Java-файлов в директории src, подсчёта их размеров и сортировки информации по определенным критериям.
 *
 * Основные изменения и улучшения по сравнению с SourceScannerA:
 * 1. **Удаление комментариев из исходного кода**:
 *    - Удаляются блоки комментариев типа /* ...  и однострочные комментарии типа //.
 *    - Это позволяет анализировать только чистый код без лишних комментариев.
 *
 * 2. **Очистка пустых и повторяющихся переносов строк**:
 *    - Удаляются повторяющиеся переносы строк ("\r\n\r\n") из Java-файлов.
 *
 * 3. **Обновленный метод `getInformation()`**:
 *    - Осуществляет обход файлов Java в директории `src`.
 *    - Исключает тестовые файлы и извлекает из них пакеты и импорты.
 *    - Подсчитывает размер очищенного текста и записывает в результирующий список.
 *
 * 4. **Сортировка и вывод информации**:
 *    - Анализируемые файлы и их размеры сортируются с использованием кастомного компаратора **myStringComparator**.
 *
 * 5. **Наследование от SourceScannerA**:
 *    - Основной функционал и структуру кода SourceScannerA расширяют и переопределяют в этом классе.
 *
 * В результате программа возвращает отсортированный список размеров очищенных файлов и их путей
 * (относительных путей) после обработки и удаления комментариев.
 */

package by.it.group351001.shimanchuk.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;


public class SourceScannerB extends SourceScannerA{

    protected static void getInformation() throws IOException {
        ArrayList<String> size_directory = new ArrayList<>();

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

                                    size_directory.add(str.getBytes().length + " " + src.relativize(directory));
                                }
                            } catch (IOException e) {
                                if (System.currentTimeMillis() < 0) {
                                    System.err.println(directory);
                                }
                            }
                        }
                    }
            );

            Collections.sort(size_directory, new myStringComparator());

            for (var info : size_directory)
                System.out.println(info);
        }

    }

    public static void main(String[] args) throws IOException {
        getInformation();
    }
}

