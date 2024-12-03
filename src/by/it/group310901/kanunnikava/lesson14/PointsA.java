package by.it.group310901.kanunnikava.lesson14;
/*Создайте класс PointsA с методом main.

Пусть у нас есть набор точек в трехмерном пространстве,
и мы хотим разбить их на кластеры на основе расстояний между ними.

Используем структуру данных DSU
с эвристикой по рангу или размеру поддерева
для объединения близких точек в один кластер.

С консоли вводится допустимое расстояние D между точками НЕ ВКЛЮЧИТЕЛЬНО [0,D) и число точек N,
а затем в каждой новой строке вводится точка с координатами X Y Z через пробел.

Точки объединяются в DSU если расстояние между ними допустимо.
Нужно вывести на консоль число точек в полученных кластерах (в порядке возрастания).*/

import java.util.*;
import java.util.stream.Collectors;

public class PointsA {

    public static void main(String[] args) {

        List<Set<Point>> dsu = new ArrayList<>(); // Инициализирует список для хранения наборов точек
        int distance, dotsAmount;

        try (Scanner scanner = new Scanner(System.in)) { // Создает объект Scanner для чтения ввода пользователя

            distance = scanner.nextInt(); // Считывает значение расстояния
            dotsAmount = scanner.nextInt(); // Считывает количество точек

            for (int i = 0; i < dotsAmount; i++) { // Проходит по количеству точек
                Point point = new Point(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()); // Считывает координаты x, y, z для точки
                Set<Point> set = new HashSet<>(); // Создает новый HashSet для хранения точек
                set.add(point); // Добавляет точку в набор
                dsu.add(set); // Добавляет набор в список
            }
        }

        for (int i = 0; i < dsu.size(); i++) { // Итерируется по каждому набору в списке
            for (Set<Point> set : dsu) { // Итерируется по каждому набору снова для сравнения
                boolean union = false; // Флаг для проверки, нужно ли объединять два набора
                ok: // Метка для выхода из вложенных циклов
                if (dsu.get(i) != set) { // Проверяет, что сравниваемые наборы не одинаковы.
                    for (Point p1 : dsu.get(i)) { // Итерируется по точкам в первом наборе
                        for (Point p2 : set) { // Итерируется по точкам во втором наборе
                            if (p1 != p2 && checkDistance(p1, p2, distance)) { // Проверяет, что точки не одинаковы и находятся на заданном расстоянии
                                union = true; // Устанавливает флаг union в true
                                break ok; // Выход из вложенных циклов
                            }
                        }
                    }
                }

                if (union) { // Если наборы нужно объединить
                    dsu.get(i).addAll(set); // Добавляет все точки из второго набора в первый набор
                    set.clear(); // Очищает второй набор
                    i = 0; // Сбрасывает внешний цикл для повторной проверки всех наборов
                }
            }
        }

        dsu.removeIf(Set::isEmpty); // Удаляет пустые наборы из списка
        String output = dsu.stream() // Создает поток из списка наборов
                .map(Set::size) // Преобразует каждый набор в его размер
                .sorted((n, m) -> m - n) // Сортирует размеры в порядке убывания
                .map(String::valueOf) // Преобразует каждый размер в строку
                .collect(Collectors.joining(" ")) // Объединяет размеры в одну строку с пробелами
                .trim(); // Убирает пробелы в начале и конце строки.

        System.out.println(output); // Выводит конечный результат
    }

    private static boolean checkDistance(Point p1, Point p2, int distance) { // Метод для проверки, находятся ли две точки на заданном расстоянии
        return Math.hypot(Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY()), p1.getZ() - p2.getZ()) <= distance;
        // Вычисляет евклидово расстояние между двумя точками в 3D-пространстве и проверяет, меньше ли оно или равно заданному расстоянию
    }

    private static class Point { // Определяет класс Point.

        private final int x; // Координата X.
        private final int y; // Координата Y.
        private final int z; // Координата Z.

        public Point(int x, int y, int z) { // Конструктор для класса Point.
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
