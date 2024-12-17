package by.it.group351002.skubakov.lesson14;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
//
public class DisJointSet<T> implements Iterable<T> {

    //Этот внутренний класс представляет узел в структуре данных.
    private class DisJointSetNode {
        public T Data;
        public int Rank;
        public DisJointSetNode Parent;
        public int Size;
        public DisJointSetNode() {
            this.Size = 1;
        }
    }

    private final Map<T, DisJointSetNode> set = new HashMap<>();
    private int count = 0;

    public int getCount() {
        return count;
    }

    //Этот метод позволяет итерироваться по элементам множества, возвращая итератор по данным узлов.
    @Override
    public Iterator<T> iterator() {
        return set.values().stream().map(node -> node.Data).iterator();
    }

    /*Создает новое множество с одним элементом. Если элемент уже существует, выбрасывается исключение.
    Новый узел инициализируется и добавляется в карту.*/
    public void makeSet(T member) {
        if (set.containsKey(member)) {
            throw new IllegalArgumentException("A set with the given member already exists.");
        }

        DisJointSetNode newSet = new DisJointSetNode();
        newSet.Data = member;
        newSet.Rank = 0;
        newSet.Parent = newSet;

        set.put(member, newSet);
        count++;
    }

    //Возвращает представителя множества, к которому принадлежит элемент.
    public T findSet(T member) {
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member.");
        }

        return findSet(set.get(member)).Data;
    }

    /*Этот метод реализует путь сжатия (path compression) для оптимизации поиска.
    Если узел не является своим родителем, он рекурсивно находит корневой узел и
    обновляет родителя текущего узла.*/
    DisJointSetNode findSet(DisJointSetNode node) {
        if (node != node.Parent) {
            node.Parent = findSet(node.Parent);
        }
        return node.Parent;
    }

    /*Объединяет два множества. Сначала находит корневые узлы для обоих элементов.
    Если они уже принадлежат одному множеству, метод возвращает.
    В противном случае, объединяет множества, обновляя родительские ссылки и ранги.*/
    public void union(T memberA, T memberB) {
        T rootA = findSet(memberA);
        T rootB = findSet(memberB);

        if (Objects.equals(rootA, rootB)) {
            return;
        }

        DisJointSetNode nodeA = set.get(rootA);
        DisJointSetNode nodeB = set.get(rootB);

        if (nodeA.Rank == nodeB.Rank) {
            nodeB.Parent = nodeA;
            nodeA.Rank++;
            nodeA.Size += nodeB.Size;
        } else if (nodeA.Rank < nodeB.Rank) {
            nodeA.Parent = nodeB;
            nodeB.Size += nodeA.Size;
        } else {
            nodeB.Parent = nodeA;
            nodeA.Size += nodeB.Size;
        }
    }

    //Проверяет, принадлежат ли два элемента одному множеству.
    public boolean contains(T member) {
        return set.containsKey(member);
    }

    //Проверяет, принадлежат ли два элемента одному множеству.
    public boolean isConnected(T x, T y) {
        return Objects.equals(findSet(x), findSet(y));
    }

    //Возвращает размер множества, к которому принадлежит элемент.
    public int getClusterSize(T member) {
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member.");
        }

        return findSet(set.get(member)).Size;
    }}