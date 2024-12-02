package by.it.group351004.narivonchik.lesson14;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

// Класс, реализующий структуру данных "непересекающиеся множества"
public class DisJointSet<T> implements Iterable<T> {

    // Внутренний класс для представления узла множества
    private class DisJointSetNode {
        public T Data; // Данные, хранящиеся в узле
        public int Rank; // Ранг узла (для оптимизации объединения)
        public DisJointSetNode Parent; // Родительский узел
        public int Size; // Размер множества

        // Конструктор по умолчанию
        public DisJointSetNode() {
            this.Size = 1; // Изначально размер множества равен 1
        }
    }

    // Хранилище для узлов множеств, где ключ - элемент множества, значение - узел
    private final Map<T, DisJointSetNode> set = new HashMap<>();
    private int count = 0; // Количество множеств

    // Метод для получения количества множеств
    public int getCount() {
        return count;
    }

    // Метод для получения итератора по элементам множества
    @Override
    public Iterator<T> iterator() {
        return set.values().stream().map(node -> node.Data).iterator();
    }

    // Метод для создания нового множества с указанным элементом
    public void makeSet(T member) {
        if (set.containsKey(member)) {
            throw new IllegalArgumentException("A set with the given member already exists.");
        }

        // Создание нового узла множества
        DisJointSetNode newSet = new DisJointSetNode();
        newSet.Data = member;
        newSet.Rank = 0; // Изначально ранг равен 0
        newSet.Parent = newSet; // Родитель - сам узел

        // Добавление узла в хранилище
        set.put(member, newSet);
        count++; // Увеличение количества множеств
    }

    // Метод для нахождения представителя множества (объединение сжатия)
    public T findSet(T member) {
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member.");
        }

        return findSet(set.get(member)).Data; // Возврат данных представителя
    }

    // Вспомогательный метод для нахождения представителя узла
    DisJointSetNode findSet(DisJointSetNode node) {
        if (node != node.Parent) {
            node.Parent = findSet(node.Parent); // Рекурсивный вызов для сжатия
        }
        return node.Parent; // Возврат родительского узла
    }

    // Метод для объединения двух множеств
    public void union(T memberA, T memberB) {
        T rootA = findSet(memberA); // Представитель первого множества
        T rootB = findSet(memberB); // Представитель второго множества

        if (Objects.equals(rootA, rootB)) {
            return; // Если уже в одном множестве, ничего не делаем
        }

        // Получение узлов представителя
        DisJointSetNode nodeA = set.get(rootA);
        DisJointSetNode nodeB = set.get(rootB);

        // Объединение по рангу
        if (nodeA.Rank == nodeB.Rank) {
            nodeB.Parent = nodeA; // nodeB становится дочерним
            nodeA.Rank++; // Увеличение ранга
            nodeA.Size += nodeB.Size; // Обновление размера
        } else if (nodeA.Rank < nodeB.Rank) {
            nodeA.Parent = nodeB; // nodeA становится дочерним
            nodeB.Size += nodeA.Size; // Обновление размера
        } else {
            nodeB.Parent = nodeA; // nodeB становится дочерним
            nodeA.Size += nodeB.Size; // Обновление размера
        }
    }

    // Метод для проверки наличия элемента в множестве
    public boolean contains(T member) {
        return set.containsKey(member);
    }

    // Метод для проверки, связаны ли два элемента
    public boolean isConnected(T x, T y) {
        return Objects.equals(findSet(x), findSet(y));
    }

    // Метод для получения размера множества, к которому принадлежит элемент
    public int getClusterSize(T member) {
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member.");
        }

        return findSet(set.get(member)).Size; // Возврат размера множества
    }
}