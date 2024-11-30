package by.it.group351001.ushakou.lesson14;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

// Класс DSU (Disjoint Set Union) реализует структуру данных "Система непересекающихся множеств".
public class DSU<T> implements Iterable<T> {

    // Вложенный класс, представляющий узел в структуре.
    private class DisJointSetNode {
        public T Data; // Данные, хранящиеся в узле.
        public int Rank; // Ранг узла, используемый для оптимизации объединения.
        public DisJointSetNode Parent; // Ссылка на родительский узел.
        public int Size; // Размер множества, к которому относится данный узел.

        public DisJointSetNode() {
            this.Size = 1; // Изначально множество состоит из одного элемента.
        }
    }

    private final Map<T, DisJointSetNode> set = new HashMap<>(); // Хранение узлов по их данным.
    private int count = 0; // Количество множеств в системе.

    // Возвращает текущее количество множеств.
    public int getCount() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        // Возвращает итератор по данным всех элементов в системе.
        return set.values().stream().map(node -> node.Data).iterator();
    }

    // Создает новое множество, содержащее указанный элемент.
    public void makeSet(T member) {
        // Проверка: элемент уже существует в системе.
        if (set.containsKey(member)) {
            throw new IllegalArgumentException("A set with the given member already exists."); // Исключение, если множество уже существует.
        }

        // Создаем новый узел для элемента.
        DisJointSetNode newSet = new DisJointSetNode();
        newSet.Data = member; // Устанавливаем данные элемента.
        newSet.Rank = 0; // Изначальный ранг равен 0.
        newSet.Parent = newSet; // Узел сам себе родитель.

        set.put(member, newSet); // Добавляем узел в систему.
        count++; // Увеличиваем количество множеств.
    }

    // Находит представителя множества, к которому относится указанный элемент.
    public T findSet(T member) {
        // Проверка: элемент существует в системе.
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member."); // Исключение, если элемента нет.
        }

        // Возвращаем представителя множества.
        return findSet(set.get(member)).Data;
    }

    // Рекурсивная функция для нахождения представителя множества с применением сжатия пути.
    DisJointSetNode findSet(DisJointSetNode node) {
        DisJointSetNode parent = node.Parent; // Получаем родительский узел.

        if (node != parent) { // Если текущий узел не является своим родителем.
            node.Parent = findSet(node.Parent); // Рекурсивно находим корневой узел и выполняем сжатие пути.
            return node.Parent; // Возвращаем корневой узел.
        }

        return parent; // Возвращаем родительский узел.
    }

    // Объединяет множества, содержащие два указанных элемента.
    public void union(T memberA, T memberB) {
        T rootA = findSet(memberA); // Находим представителя множества A.
        T rootB = findSet(memberB); // Находим представителя множества B.

        if (Objects.equals(rootA, rootB)) { // Если элементы уже в одном множестве, ничего не делаем.
            return;
        }

        DisJointSetNode nodeA = set.get(rootA); // Получаем узел для множества A.
        DisJointSetNode nodeB = set.get(rootB); // Получаем узел для множества B.

        if (nodeA.Rank == nodeB.Rank) { // Если ранги равны.
            nodeB.Parent = nodeA; // Делаем A родителем B.
            nodeA.Rank++; // Увеличиваем ранг множества A.
            nodeA.Size += nodeB.Size; // Увеличиваем размер множества A.
        } else {
            if (nodeA.Rank < nodeB.Rank) { // Если ранг A меньше B.
                nodeA.Parent = nodeB; // Делаем B родителем A.
                nodeB.Size += nodeA.Size; // Увеличиваем размер множества B.
            } else { // Если ранг B меньше A.
                nodeB.Parent = nodeA; // Делаем A родителем B.
                nodeA.Size += nodeB.Size; // Увеличиваем размер множества A.
            }
        }
    }

    // Проверяет, существует ли элемент в системе.
    public boolean contains(T member) {
        return set.containsKey(member); // Возвращает true, если элемент есть в системе.
    }

    // Проверяет, принадлежат ли два элемента одному множеству.
    public boolean isConnected(T x, T y) {
        return Objects.equals(findSet(x), findSet(y)); // Возвращает true, если элементы имеют одного представителя.
    }

    // Возвращает размер множества, к которому относится указанный элемент.
    public int getClusterSize(T member) {
        // Проверка: элемент существует в системе.
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member."); // Исключение, если элемента нет.
        }

        // Возвращаем размер множества, найденного через представителя.
        return findSet(set.get(member)).Size;
    }

}
