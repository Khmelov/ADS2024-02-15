package by.it.group310902.sverchkov.lesson15;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerB {

    static String stringClean(String str) {
        int i = 0;
        int j = str.length() - 1;

        while (i < str.length() && str.charAt(i) < 33)
            i++;
        while (j >= 0 && str.charAt(j) < 33)
            j--;

        if (j < i)
            return "";
        return str.substring(i, j);
    }

    static void walkThroughFiles() throws IOException {

        Path path = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        ArrayList<String> fileSize = new ArrayList<>();

        try (Stream<Path> files = Files.walk(path, Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS)) {
            files.forEach(directory -> {
                if (!directory.toString().endsWith(".java"))
                    return;

                try {
                    String str = Files.readString(directory);

                    if (str.contains("@Test") || str.contains("org.junit.Test"))
                        return;

                    str = str.replaceAll("import.+", "")
                            .replaceAll("package.+", "")
                            .replaceAll("/\\*[\\w\\W\r\n\t]*?\\*/", "")
                            .replaceAll("//.*?\r\n\\s*", "");

                    while (str.contains("\r\n\r\n"))
                        str = str.replaceAll("\r\n\r\n", "\r\n");

                    if (!str.isEmpty()) {
                        str = stringClean(str);
                    }

                    fileSize.add(str.getBytes().length + " " + path.relativize(directory));

                } catch (IOException e) {
                    System.err.println("Wrong charset (Not UTF-8) of the file: " + directory);
                }
            });
        }

        fileSize.sort((s1, s2) -> {
            int int1 = new Scanner(s1).nextInt(10);
            int int2 = new Scanner(s2).nextInt(10);

            if (int1 == int2)
                return s1.compareTo(s2);
            return int1 > int2 ? 1 : -1;
        });

        fileSize.forEach(System.out::println);

    }

    public static void main(String[] args) throws IOException {
        walkThroughFiles();
    }
}
