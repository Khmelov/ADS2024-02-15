package by.it.group351004.leshok.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerC {
    static final int MAX_DISTANCE = 9;

    private static int calculateDistance(String word1, String word2) {
        int[] currRow = new int[word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            int[] prevRow = currRow;
            currRow = new int[word2.length() + 1];
            for (int j = 0; j <= word2.length(); j++) {
                currRow[j] = i == 0 ? j : (j == 0 ? i : Math.min(
                        Math.min(prevRow[j] + 1, currRow[j - 1] + 1),
                        prevRow[j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1)));
            }
        }
        return currRow[word2.length()];
    }

    private static boolean isSimilar(String content1, String content2) {
        if (Math.abs(content1.length() - content2.length()) > MAX_DISTANCE)
            return false;

        String[] words1 = content1.split(" "), words2 = content2.split(" ");
        int distance = 0;

        for (int i = 0; i < words1.length; i++) {
            if (i >= words2.length) return false;
            distance += calculateDistance(words1[i], words2[i]);
            if (distance > MAX_DISTANCE) return false;
        }
        return true;
    }

    private static ArrayList<ArrayList<Path>> groupSimilarFiles(HashMap<Path, String> files) {
        ArrayList<ArrayList<Path>> groups = new ArrayList<>();
        Set<Path> processed = new HashSet<>();

        for (Path file1 : files.keySet()) {
            if (!processed.contains(file1)) {
                ArrayList<Path> group = new ArrayList<>();
                group.add(file1);
                for (Path file2 : files.keySet()) {
                    if (!file1.equals(file2) && isSimilar(files.get(file1), files.get(file2))) {
                        group.add(file2);
                        processed.add(file2);
                    }
                }
                if (group.size() > 1) groups.add(group);
            }
        }
        return groups;
    }

    private static void displayGroups(HashMap<String, HashMap<Path, String>> fileData) {
        fileData.forEach((name, files) -> {
            ArrayList<ArrayList<Path>> groups = groupSimilarFiles(files);
            if (!groups.isEmpty()) {
                System.out.println("\n---" + name + "---");
                int count = 1;
                for (ArrayList<Path> group : groups) {
                    System.out.println("\nClones �" + count++);
                    group.forEach(System.out::println);
                }
            }
        });
    }

    protected static void analyzeFiles() throws IOException {
        HashMap<String, HashMap<Path, String>> fileData = new HashMap<>();
        Path root = Path.of(System.getProperty("user.dir") + File.separator + "src");

        try (Stream<Path> paths = Files.walk(root)) {
            paths.filter(p -> p.toString().endsWith(".java")).forEach(path -> {
                try {
                    String content = Files.readString(path)
                            .replaceAll("package.*;|import.*;|/\\*[\\w\\W]*?\\*/|//.*|[\u0000- ]+", " ").trim();
                    if (!content.isEmpty() && !content.contains("@Test")) {
                        fileData.computeIfAbsent(path.getFileName().toString(), k -> new HashMap<>())
                                .put(root.relativize(path), content);
                    }
                } catch (IOException ignored) {}
            });
        }
        displayGroups(fileData);
    }

    public static void main(String[] args) throws IOException {
        analyzeFiles();
    }
}
