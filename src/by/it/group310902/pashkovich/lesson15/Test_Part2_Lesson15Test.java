package by.it.group310902.pashkovich.lesson15;

import by.it.HomeWork;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@SuppressWarnings("NewClassNamingConvention")
public class Test_Part2_Lesson15Test extends HomeWork {

    private static volatile List<String> samples; // кэшированный список файлов
    private static final AtomicBoolean isLazyWalkCompleted = new AtomicBoolean(false);

    @Test(timeout = 10000) // увеличен тайм-аут для большого числа файлов
    public void testSourceScannerA() {
        HomeWork run = run("");
        List<String> sampleFiles = getFilteredSamples();
        for (String sample : sampleFiles) {
            run.include(sample); // проверка вхождения имени файла
        }
    }

    @Test(timeout = 10000)
    public void testSourceScannerB() {
        HomeWork run = run("");
        List<String> sampleFiles = getFilteredSamples();
        for (String sample : sampleFiles) {
            run.include(sample);
        }
    }

    @Test(timeout = 30000)
    public void testSourceScannerC() {
        HomeWork run = run("");

        // Проверяется наличие конкретного файла, например "FiboA.java"
        run.include("FiboA.java");
    }

    /**
     * Получение списка файлов с фильтрацией (кэшируемый метод).
     *
     * @return Список файлов в формате относительных путей
     */
    private static List<String> getFilteredSamples() {
        if (samples == null) {
            synchronized (Test_Part2_Lesson15Test.class) {
                if (samples == null && isLazyWalkCompleted.compareAndSet(false, true)) {
                    samples = lazyWalk();
                }
            }
        }
        return samples;
    }

    /**
     * Метод для ленивой загрузки файлов .java из каталога src (кэшируемый список).
     *
     * @return Список отфильтрованных файлов .java
     */
    private static List<String> lazyWalk() {
        List<String> result = new ArrayList<>();
        Path root = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);
        try (var walk = Files.walk(root).parallel()) { // Параллельный обход
            result = walk.filter(path -> path.toString().endsWith(".java")) // Только .java
                    .filter(path -> {
                        try {
                            String content = Files.readString(path);
                            return !content.contains("@Test") && !content.contains("org.junit.Test");
                        } catch (IOException e) {
                            System.err.println("Ошибка чтения файла: " + path);
                            return false;
                        }
                    })
                    .map(path -> root.relativize(path).toString()) // Преобразование в относительный путь
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка обхода директории: " + root, e);
        }
        return result;
    }
}
