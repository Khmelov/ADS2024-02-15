package by.it.group310902.sverchkov.lesson15;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC {

    private static String stringClean(String str) {

        StringBuilder sb = new StringBuilder(str);

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) < 33) {
                sb.setCharAt(i, ' ');
            }
        }

        return sb.toString();
    }

    static void walkThroughFiles() throws IOException {

        Path path = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        HashMap<String, HashMap<String, String>> filesData = new HashMap<>();

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
                    ;
                    str = stringClean(str).trim();

                    if (!filesData.containsKey(directory.getFileName().toString()))
                        filesData.put(directory.getFileName().toString(), new HashMap<>());
                    filesData.get(directory.getFileName().toString()).put(path.relativize(directory).toString(), str);

                } catch (IOException e) {
                    System.err.println("Wrong charset (Not UTF-8) of the file: " + directory);
                }
            });
        }

        HashMap<String, ArrayList<String>> pathes = new HashMap<>();

        filesData.forEach((lessonName, info) -> {
            TreeSet<String> visited = new TreeSet<>();
            info.forEach((studentsDir, data) -> {
                visited.add(studentsDir);
                info.forEach((otherStudentsDir, otherData) -> {
                    if (visited.contains(otherStudentsDir))
                        return;

                    if (compareByHuf(data, otherData)) {
                        if (!pathes.containsKey(studentsDir))
                            pathes.put(studentsDir, new ArrayList<>());
                        pathes.get(studentsDir).add(otherStudentsDir);
                    }
                });
            });
        });

        pathes.forEach((studentsDir, copies) -> {
            System.out.println(studentsDir);
            System.out.println("Такое же решение представили");

            copies.sort(Comparator.naturalOrder());
            copies.forEach(otherDirectory -> {
                System.out.println("\t" + otherDirectory);
            });
            System.out.println();
        });

    }

    private static int getDistanceEdinting(String one, String two) {

        int[] prev = new int[two.length() + 1];
        int[] cur = new int[two.length() + 1];

        for (int i = 0; i < prev.length; i++) {
            prev[i] = i;
        }

        for (int i = 0; i < one.length(); i++) {
            cur[0] = i + 1;
            for (int j = 1; j < cur.length; j++) {
                int k = 0;
                if (one.charAt(i) != two.charAt(j - 1)) {
                    k = 1;
                }
                cur[j] = Math.min(Math.min(cur[j - 1] + 1, prev[j] + 1), prev[j - 1] + k);
            }
            prev = cur.clone();
        }

        return cur[cur.length - 1];
    }

    private static boolean compareByHuf(String s1, String s2) {
        String[] words1 = s1.split(" ");
        String[] words2 = s2.split(" ");
        int distance = 0;

        for (int i = 0; i < words1.length; i++) {
            if (i >= words2.length) return false;
            distance += getDistanceEdinting(words1[i], words2[i]);
            if (distance >= 10) return false;
        }
        return true;
    }


    public static void main(String[] args) throws IOException {
        walkThroughFiles();
    }
}
