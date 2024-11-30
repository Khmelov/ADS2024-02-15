package by.it.group351001.ushakou.lesson14;

import java.util.*;

public class SitesB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем объект Scanner для чтения входных данных.

        DSU<String> disjointSet = new DSU<>(); // Создаем экземпляр DSU для управления множествами строк (сайтов).

        // Бесконечный цикл для обработки входных данных.
        while (true) {
            String input = scanner.nextLine(); // Считываем строку из входных данных.

            if (input.equals("end")) { // Если строка равна "end", завершаем цикл.
                break;
            }

            String[] sites = input.split("\\+"); // Разделяем строку на два сайта по символу "+".

            // Добавляем каждый сайт в DSU, если он еще не был добавлен.
            for (String site : sites) {
                if (!disjointSet.contains(site)) { // Проверяем, содержится ли сайт в DSU.
                    disjointSet.makeSet(site); // Если нет, создаем для него новое множество.
                }
            }

            disjointSet.union(sites[0], sites[1]); // Объединяем два сайта в одно множество.
        }

        scanner.close(); // Закрываем объект Scanner для освобождения ресурсов.

        Map<String, Integer> clusterSizes = new HashMap<>(); // Словарь для хранения размеров кластеров по их корню.
        HashSet<String> set = new HashSet<>(); // Множество для хранения уже обработанных корней.

        // Перебираем все сайты из DSU.
        for (String site : disjointSet) {
            if (set.contains(site)) // Если сайт уже обработан, пропускаем.
                continue;
            set.add(site); // Добавляем сайт в множество обработанных.
            String root = disjointSet.findSet(site); // Находим корень множества для текущего сайта.
            clusterSizes.put(root, disjointSet.getClusterSize(site)); // Записываем размер кластера в словарь.
        }

        ArrayList<Integer> temp = new ArrayList<>(clusterSizes.values()); // Извлекаем размеры кластеров в список.

        Collections.sort(temp); // Сортируем размеры кластеров по возрастанию.
        Collections.reverse(temp); // Переворачиваем список, чтобы получить порядок убывания.

        // Выводим размеры кластеров в отсортированном порядке.
        for (int item : temp) {
            System.out.print(item + " "); // Печатаем каждый размер через пробел.
        }
    }
}
