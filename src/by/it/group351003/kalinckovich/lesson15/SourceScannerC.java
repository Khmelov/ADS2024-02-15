package by.it.group351003.kalinckovich.lesson15;

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

import static by.it.group351003.kalinckovich.lesson15.SourceScannerA.getString;


public class SourceScannerC {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path srcPath = Paths.get(src);

        try (Stream<Path> paths = Files.walk(srcPath)) {

            List<FileData> fileDataList = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(SourceScannerC::doesNotContainTestAnnotation)
                    .map(SourceScannerC::processFile)
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

            long size = content.getBytes(StandardCharsets.UTF_8).length;

            return new FileData(size, path.toString());

        } catch (IOException e) {
            return null;
        }
    }

    private static String removeNonPrintableChars(String content) {
        return getString(content);
    }
}
