package by.it.group310901.kanunnikava.lesson14;
/*Создайте класс SitesB с методом main.

Пусть у нас есть набор связанных через взаимные гиперссылки сайтов,
и мы хотим разбить их на кластеры в которых можно по ссылкам дойти
до любого сайта этого кластера.

Цель: объединение связанных сайтов в кластеры.

Для кластеризации используем структуру данных DSU,
причем эвристик должно быть две:
1. по рангу или размеру поддерева
2. по сокращению пути поддерева

С консоли вводится в каждой новой строке пара связанных сайтов.
Допускается связь сайта с самим собой. Направление ссылок для простоты не учитывается.
Пара объединяется символом "+", а весь ввод завершается строкой "end"
Нужно вывести на консоль число сайтов в полученных кластерах (в порядке возрастания).
Сложность DSU должна быть не выше, чем N log*(N), где log*(N) - итерированный логарифм.
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SitesB {

    private static class DSU { // Внутренний статический класс для реализации структуры данных "Система непересекающихся множеств"
        int[] parent; // Массив для хранения родительских элементов
        int[] rank; // Массив для хранения рангов элементов

        DSU(int size){ // Конструктор класса DSU, принимающий размер
            parent = new int[size];
            rank = new int[size];
        }
        public void make_set(int v) { // Метод для создания множества, содержащего один элемент
            parent[v] = v; // Устанавливает элемент как родителя самого себя
            rank[v] = 0; // Устанавливает ранг элемента равным 0
        }

        int find_set(int v) { // Метод для поиска корня множества, содержащего элемент
            if (v == parent[v]) // Если элемент является своим собственным родителем
                return v;
            return parent[v] = find_set(parent[v]); // Выполняет путь сжатия и возвращает корень множества
        }

        void union_sets(int a, int b) { // Метод для объединения двух множеств
            a = find_set(a); // Находит корень множества для первого элемента
            b = find_set(b); // Находит корень множества для второго элемента
            if (a != b) {
                if (rank[a] < rank[b]) {
                    int temp = a; // Меняет местами корни множеств
                    a = b;
                    b = temp;
                }
                parent[b] = a; // Устанавливает корень второго множества родителем корня первого множества
                if (rank[a] == rank[b])
                    rank[a]++; // Увеличивает ранг корня первого множества.
            }
        }
    }

    public static void main(String[] args) { // Основной метод, точка входа в программу
        Scanner scanner = new Scanner(System.in);
        List<String> points = new ArrayList<>(); // Создает список строк для хранения точек
        DSU dsu = new DSU(1000); // Создает объект DSU с размером 1000
        String line = scanner.next(); // Считывает следующую строку ввода
        while(line.compareTo("end") != 0) {
            String[] pair = line.split("\\+"); // Разделяет строку на пару по символу "+"
            int x1 = points.indexOf(pair[0]); // Находит индекс первой точки в списке
            if (x1 == -1) { // Если первая точка не найдена
                x1 = points.size(); // Устанавливает индекс как размер списка
                points.add(pair[0]); // Добавляет первую точку в список
                dsu.make_set(x1); // Создает множество для первой точки
            }
            int x2 = points.indexOf(pair[1]); // Находит индекс второй точки в списке
            if (x2 == -1) {
                x2 = points.size();
                points.add(pair[1]);
                dsu.make_set(x2);
            }
            dsu.union_sets(x1, x2); // Объединяет множества, содержащие первую и вторую точки
            line = scanner.next(); // Считывает следующую строку ввода
        }

        int max_size = points.size(); // Устанавливает максимальный размер как размер списка точек
        int[] dsu_size_array = new int[max_size];
        for(int i = 0; i < max_size; i++){
            dsu_size_array[dsu.find_set(i)]++;
        }

        StringBuilder sb = new StringBuilder(); // Создает объект для формирования строки результата
        for(int i = 0; i < max_size; i++){
            int max = i;
            for(int j = i+1; j < max_size; j++)
                if(dsu_size_array[max] < dsu_size_array[j])
                    max = j;
            if (dsu_size_array[max] == 0)
                break;
            int temp = dsu_size_array[max]; // Меняет местами текущий размер и максимальный
            dsu_size_array[max] = dsu_size_array[i];
            dsu_size_array[i] = temp;
            sb.append(dsu_size_array[i]); // Добавляет текущий размер в строку результата
            sb.append(" "); // Добавляет пробел
        }
        sb.deleteCharAt(sb.length()-1); // Удаляет последний пробел
        System.out.println(sb); // Выводит строку результата
    }
}
