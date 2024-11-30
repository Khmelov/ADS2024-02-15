package by.it.group351003.lutai.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceScannerB {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path srcPath = Paths.get(src);

        try (Stream<Path> paths = Files.walk(srcPath)) {

            List<FileData> fileDataList = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(SourceScannerB::doesNotContainTestAnnotation)
                    .map(SourceScannerB::processFile)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingLong(FileData::size)
                            .thenComparing(FileData::path))
                    .toList();

            for (FileData fileData : fileDataList) {
                System.out.println(fileData.size() + " " + fileData.path());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean doesNotContainTestAnnotation(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.contains("@Test") || line.contains("org.junit.Test")) {
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static FileData processFile(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            List<String> filteredLines = lines.stream()
                    .filter(line -> !line.startsWith("package") && !line.startsWith("import"))
                    .collect(Collectors.toList());

            String content = String.join("\n", filteredLines).trim();
            content = removeNonPrintableChars(content);
            content = removeSentence(content);
            long size = content.getBytes(StandardCharsets.UTF_8).length;

            return new FileData(size, path.toString());

        } catch (IOException e) {
            return null;
        }
    }

    private static String removeSentence(String content){
        int index = content.lastIndexOf(';');
        if(index != -1) {
            content = content.substring(0, index);
        }
        return content;
    }

    // Метод для удаления символов с кодом < 33 в начале и конце текста
    private static String removeNonPrintableChars(String content) {
        int start = 0;
        int end = content.length() - 1;

        // Удаляем не печатные символы в начале
        while (start <= end && content.charAt(start) < 33) {
            start++;
        }

        // Удаляем не печатные символы в конце
        while (end >= start && content.charAt(end) < 33) {
            end--;
        }

        return content.substring(start, end + 1);
    }
}

