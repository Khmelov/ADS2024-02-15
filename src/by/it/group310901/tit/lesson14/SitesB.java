package by.it.group310901.tit.lesson14;

import java.util.*;

public class SitesB {

    // Вложенный класс для структуры объединения-поиск (DSU)
    static class DSU {
        private final Map<String, String> parent; // Хранит родителей каждого сайта
        private final Map<String, Integer> size;   // Хранит размеры кластеров

        // Конструктор DSU
        public DSU() {
            parent = new HashMap<>(); // Инициализация словаря родителей
            size = new HashMap<>();   // Инициализация словаря размеров
        }

        // Метод для нахождения корня сайта с сжатием пути
        public String find(String site) {
            // Если сайт не является своим родителем
            if (!parent.get(site).equals(site)) {
                // Рекурсивно находим корень и сжимаем путь
                parent.put(site, find(parent.get(site)));
            }
            return parent.get(site); // Возвращаем корень
        }

        // Метод для объединения двух сайтов
        public void union(String site1, String site2) {
            String root1 = find(site1); // Находим корень для первого сайта
            String root2 = find(site2); // Находим корень для второго сайта

            // Если корни разные, объединяем их
            if (!root1.equals(root2)) {
                // Объединяем по размеру
                if (size.get(root1) < size.get(root2)) {
                    parent.put(root1, root2); // root2 становится родителем для root1
                    size.put(root2, size.get(root2) + size.get(root1)); // Обновляем размер
                } else {
                    parent.put(root2, root1); // root1 становится родителем для root2
                    size.put(root1, size.get(root1) + size.get(root2)); // Обновляем размер
                }
            }
        }

        // Метод для добавления нового сайта
        public void addSite(String site) {
            if (!parent.containsKey(site)) { // Если сайт еще не добавлен
                parent.put(site, site); // Устанавливаем самого себя как родителя
                size.put(site, 1); // Размер нового кластера равен 1
            }
        }

        // Метод для получения размеров кластеров
        public List<Integer> getClusterSizes() {
            Map<String, Integer> clusterSizeMap = new HashMap<>(); // Словарь для хранения размеров кластеров
            for (String site : parent.keySet()) {
                String root = find(site); // Находим корень для каждого сайта
                clusterSizeMap.put(root, clusterSizeMap.getOrDefault(root, 0) + 1); // Увеличиваем размер кластера
            }
            List<Integer> clusterSizes = new ArrayList<>(clusterSizeMap.values()); // Получаем размеры кластеров
            Collections.sort(clusterSizes); // Сортируем размеры кластеров
            return clusterSizes; // Возвращаем отсортированный список
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем сканер для ввода
        DSU dsu = new DSU(); // Создаем экземпляр DSU

        // Чтение ввода до тех пор, пока не будет введено "end"
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("end")) break; // Выход из цикла при вводе "end"

            String[] sites = input.split("\\+"); // Разделяем ввод по символу "+"
            String site1 = sites[0]; // Первый сайт
            String site2 = sites[1]; // Второй сайт

            dsu.addSite(site1); // Добавляем первый сайт
            dsu.addSite(site2); // Добавляем второй сайт

            dsu.union(site1, site2); // Объединяем сайты
        }

        // Получаем размеры кластеров и выводим их в обратном порядке
        List<Integer> clusterSizes = dsu.getClusterSizes();
        for (int i = clusterSizes.size() - 1; i >= 0; i--) {
            int size = clusterSizes.get(i);
            System.out.print(size + " "); // Печатаем размер кластера
        }
    }
}