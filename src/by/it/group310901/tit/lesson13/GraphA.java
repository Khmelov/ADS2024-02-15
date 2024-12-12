package by.it.group310901.tit.lesson13;



import java.util.*;


public class GraphA {
    // Граф, представленный в виде списка смежности с использованием HashMap
    private static final HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
    // Счетчики входящих ребер для каждой вершины
    private static final HashMap<String, Integer> inDegree = new HashMap<>();


    // Метод для добавления направленного ребра в граф
    private static void addComponent(String[] pair) {
        // Создаем TreeSet для хранения уникальных узлов
        TreeSet<String> set = new TreeSet<>();


        // Инициализируем структуру графа для обоих узлов, если они еще не присутствуют
        graph.putIfAbsent(pair[0], new PriorityQueue<>());
        graph.putIfAbsent(pair[1], new PriorityQueue<>());
        // Добавляем ребро от первого узла ко второму
        graph.get(pair[0]).add(pair[1]);


        // Обновляем количество входящих ребер для целевого узла
        inDegree.put(pair[1], inDegree.getOrDefault(pair[1], 0) + 1);
        // Обеспечиваем, чтобы у исходного узла было количество входящих ребер 0, если он начальная точка
        inDegree.putIfAbsent(pair[0], 0);
    }


    // Метод для выполнения топологической сортировки на направленном графе
    private static String topologicalSort() {
        Queue<String> queue = new LinkedList<>(); // Очередь для обработки узлов
        List<String> topologyOrder = new ArrayList<>(); // Список для хранения отсортированного порядка


        // Добавляем все узлы с количеством входящих ребер 0 в очередь
        for (String s : graph.keySet()) {
            if (inDegree.get(s) == 0) {
                queue.add(s);
            }
        }


        // Обрабатываем узлы, пока очередь не опустеет
        while (!queue.isEmpty()) {
            String s = queue.poll(); // Получаем следующий узел
            topologyOrder.add(s); // Добавляем его в отсортированный порядок


            // Уменьшаем количество входящих ребер у его соседей
            for (String item : graph.get(s)) {
                inDegree.put(item, inDegree.get(item) - 1);
                // Если количество входящих ребер становится 0, добавляем его в очередь для обработки
                if (inDegree.get(item) == 0) {
                    queue.add(item);
                }
            }
        }


        // Формируем результат в виде строки из отсортированного порядка
        StringBuilder result = new StringBuilder();
        for (String s : topologyOrder) {
            result.append(s).append(" ");
        }
        return result.toString(); // Возвращаем отсортированный порядок в виде строки
    }


    // Основной метод для выполнения программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Сканер для ввода данных
        String n = scanner.nextLine(); // Читаем строку ввода
        String[] parts = n.split(", "); // Разделяем ввод на компоненты
        // Добавляем каждую компоненту как ребро в граф
        for (String s : parts) {
            addComponent(s.split(" -> "));
        }
        scanner.close(); // Закрываем сканер
        System.out.println(topologicalSort()); // Печатаем результат топологической сортировки
        // Очищаем граф и карту входящих ребер для потенциального повторного использования
        graph.clear();
        inDegree.clear();
    }
}


