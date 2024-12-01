//Создайте класс SourceScannerB с методом main,
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
//1. Удалить строку package и все импорты за O(n) от длины текста.
//2. Удалить все комментарии за O(n) от длины текста.
//3. Удалить все символы с кодом <33 в начале и конце текстов.
//4. Удалить пустые строки
//
//В полученном наборе текстов:
//1. Рассчитать размер в байтах для полученных текстов
//   и вывести в консоль
//   размер и относительный от src путь к каждому из файлов (по одному в строке)
//2. При выводе сортировать файлы по размеру,
//   а если размер одинаковый,
//   то лексикографически сортировать пути
//
//Найдите способ корректно обрабатывать ошибки MalformedInputException
//
//Все операции не должны ничего менять на дисках (разрешено только чтение)
//Работа не имеет цели найти плагиат, поэтому не нужно менять коды своих программ.
package by.it.group310901.navrosuk.lesson15;
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