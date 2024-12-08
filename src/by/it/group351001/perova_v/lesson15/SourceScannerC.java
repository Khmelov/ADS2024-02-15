package by.it.group351001.perova_v.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SourceScannerC {
    private static boolean isSimilar(String content1, String content2) {
        var distance = Math.abs(content1.length() - content2.length());
        if (distance >= 10)
            return false;
        var words1 = content1.split(" ");
        var words2 = content2.split(" ");
        for (var i = 0; i < words1.length; i++) {
            var word1 = words1[i];
            var word2 = i < words2.length ? words2[i] : "";
            distance += computeEditDistance(word1, word2);
            if (distance >= 10)
                return false;
        }
        return true;
    }

    private static int computeEditDistance(String s1, String s2) {
        var prevRow = new int[s2.length() + 1];
        for (var j = 0; j <= s2.length(); j++)
            prevRow[j] = j;
        for (var i = 1; i <= s1.length(); i++) {
            var currRow = new int[s2.length() + 1];
            currRow[0] = i;
            for (var j = 1; j <= s2.length(); j++) {
                currRow[j] = Math.min(
                        Math.min(prevRow[j] + 1, currRow[j - 1] + 1),
                        prevRow[j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1));
            }
            prevRow = currRow;
        }
        return prevRow[s2.length()];
    }

    private static void findDuplicates(Map<String, Map<Path, String>> javaClasses) {
        javaClasses.forEach((className, files) -> {
            var duplicates = new ArrayList<List<Path>>();
            var visited = new HashSet<Path>();
            files.forEach((file1, content1) -> {
                if (visited.contains(file1))
                    return;
                var group = new ArrayList<Path>();
                group.add(file1);
                files.forEach((file2, content2) -> {
                    if (!file1.equals(file2) && !visited.contains(file2) && isSimilar(content1, content2)) {
                        group.add(file2);
                        visited.add(file2);
                    }
                });
                if (group.size() > 1)
                    duplicates.add(group);
            });
            if (!duplicates.isEmpty()) {
                System.out.println("\n" + className + ":");
                for (var i = 0; i < duplicates.size(); i++) {
                    System.out.println("Clone #" + (i + 1));
                    duplicates.get(i).forEach(System.out::println);
                }
            }
        });
    }

    public static void main(String[] args) {
        var javaFiles = new HashMap<String, Map<Path, String>>();
        var src = Path.of(System.getProperty("user.dir") + File.separator + "src");
        try (var files = Files.walk(src)) {
            files.filter(path -> path.toString().endsWith(".java"))
                    .forEach(file -> processFile(javaFiles, src, file));
            findDuplicates(javaFiles);
        } catch (IOException e) {
            System.err.println("Error scanning directory: " + e.getMessage());
        }
    }

    private static void processFile(Map<String, Map<Path, String>> javaClasses, Path src, Path file) {
        try {
            var content = Files.readString(file);
            if (content.contains("@Test") || content.contains("org.junit.Test"))
                return;
            content = content.replaceAll("(?s)/\\*.*?\\*/", "")
                    .replaceAll("//.*", "")
                    .replaceAll("package.*;|import.*;", "")
                    .replaceAll("[\u0000-\\x20]+", " ")
                    .strip();
            if (content.isEmpty())
                return;
            javaClasses.computeIfAbsent(file.getFileName().toString(), k -> new HashMap<>())
                    .put(src.relativize(file), content);
        } catch (IOException e) {
            System.err.println("Error processing file: " + file);
        }
    }
}
