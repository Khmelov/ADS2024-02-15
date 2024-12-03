package by.it.group310901.kanunnikava.lesson13;

import java.util.*;

/*Создайте класс GraphC в методе main которого считывается строка структуры орграфа вида:
        C->B, C->I, I->A, A->D, D->I, D->B, B->H, H->D, D->E, H->E, E->G, A->F, G->F, F->K, K->G

        Затем в консоль выводятся вершины компонент сильной связности
        каждый компонент с новой строки, первый - исток, последний - сток,
        пробелов и табуляции в выводе нигде нет, пример для введенного выше графа:
        C
        ABDHI
        E
        FGK

 */
public class GraphC {
    private final Map<String, Set<String>> adjacencyList = new HashMap<>(); // Создание карты для представления списка смежности графа
    private final List<List<String>> strongComponents = new ArrayList<>(); // Создание списка для хранения сильных компонент
    public List<List<String>> getStrongComponents() { return strongComponents; } // Метод для получения сильных компонент
    public GraphC(String input) { // Конструктор класса GraphC
        var edges = input.split(", "); // Разделение входных данных на ребра
        for (var edge : edges) { // Цикл по всем ребрам
            var vertices = edge.split("->"); // Разделение ребра на вершины
            var from = vertices[0]; // Первая вершина
            var to = vertices[1]; // Вторая вершина
            adjacencyList.computeIfAbsent(from, _1_ -> new HashSet<>()).add(to); // Добавление ребра в список смежности
        }
        calcStrongComponents();
    }
    private static class ReverseGraph { // Вложенный класс для представления обратного графа
        private final Map<String, Set<String>> adjacencyList = new HashMap<>(); // Создание карты для представления списка смежности обратного графа
        private final List<String> topologicalOrder = new ArrayList<>(); // Создание списка для хранения топологического порядка
        public List<String> getTopologicalOrder() { return topologicalOrder; } // Метод для получения топологического порядка
        public ReverseGraph(Map<String, Set<String>> adjacencyList) { // Конструктор класса ReverseGraph
            for (var entry : adjacencyList.entrySet()) { // Цикл по всем вершинам графа
                var vertex = entry.getKey(); // Вершина
                for (var neighbor : entry.getValue()) // Цикл по всем соседям вершины
                    this.adjacencyList.computeIfAbsent(neighbor, _1_ -> new HashSet<>()).add(vertex); // Добавление ребра в обратный граф
            }
            calcTopologicalSort(); // Вычисление топологического порядка
        }
        private void calcTopologicalSort() { // Метод для вычисления топологического порядка
            var marked = new HashSet<String>(); // Создание множества для отслеживания посещенных вершин
            for (var vertex : adjacencyList.keySet()) // Цикл по всем вершинам обратного графа
                if (!marked.contains(vertex))
                    calcTopologicalSort(vertex, marked); // Вызов рекурсивного метода для вычисления топологического порядка
            Collections.reverse(topologicalOrder); // Обратный порядок для топологической сортировки
        }
        private void calcTopologicalSort(String vertex, HashSet<String> marked) { // Рекурсивный метод для вычисления топологического порядка
            marked.add(vertex); // Добавление вершины в множество посещенных
            var neighbors = adjacencyList.get(vertex); // Получение списка соседей вершины
            if (neighbors != null)
                for (var neighbor : neighbors) // Цикл по всем соседям вершины
                    if (!marked.contains(neighbor))
                        calcTopologicalSort(neighbor, marked); // Рекурсивный вызов метода для соседней вершины
            topologicalOrder.add(vertex); // Добавление вершины в топологический порядок
        }
    }
    private void calcStrongComponents() { // Метод для вычисления сильных компонент
        var reverse = new ReverseGraph(adjacencyList); // Создание обратного графа
        var topologicalOrder = reverse.getTopologicalOrder(); // Получение топологического пордка

        var marked = new HashSet<String>(); // Создание множества для отслеживания посещенных вершин
        for (var vertex : topologicalOrder) {
            if (!marked.contains(vertex)) {
                List<String> component = new ArrayList<>(); // Создание нового списка для сильной компоненты
                dfs(vertex, marked, component); // Вызов DFS для вершины
                strongComponents.add(component); // Добавление сильной компоненты в список сильных компонент }
            }
        }
        Collections.reverse(strongComponents); // Обратный порядок для сильных компонент
        for (var component : strongComponents) // Цикл по всем сильным компонентам
            Collections.sort(component); // Сортировка вершин
    }
    private void dfs(String vertex, Set<String> marked, List<String> component) { // Рекурсивный метод для выполнения DFS
        marked.add(vertex); // Добавление вершины в множество посещенных
        component.add(vertex); // Добавление вершины в текущую сильную компоненту
        var neighbors = adjacencyList.get(vertex); // Получение списка соседей вершины
        if (neighbors != null)
            for (var neighbor : neighbors)
                if (!marked.contains(neighbor))
                    dfs(neighbor, marked, component); // Рекурсивный вызов метода для соседней вершины
    }
    public static void main(String[] args) {
        var scanner = new Scanner(System.in); // Создание сканера для чтения ввода
        var graph = new GraphC(scanner.nextLine()); // Создание графа на основе входных данных

        var strongComponents = graph.getStrongComponents(); // Получение сильных компонент
        for (var strongComponent : strongComponents) {
            for (var vertex : strongComponent)
                System.out.print(vertex); // Вывод вершины
            System.out.print('\n');
        }
    }
}