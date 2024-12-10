package by.it.group351001.sosnovski.lesson14;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class DisJointSet<T> implements Iterable<T> {

    /**
     Класс DisJointSet<T> реализует структуру данных "Система непересекающихся множеств"
     (Union-Find). Она используется для эффективного объединения и проверки принадлежности
     элементов множествам. Этот код поддерживает операции "создание множества",
     "объединение множеств" и "поиск представителя множества", используя
     оптимизации сжатия пути (path compression) и объединения по рангу (union by rank).
     */

    // Внутренний класс, представляющий узел в системе множеств
    private class DisJointSetNode {
        public T Data; // Данные, хранящиеся в узле
        public int Rank; // Ранг узла (глубина дерева представления)
        public DisJointSetNode Parent; // Ссылка на родителя
        public int Size; // Размер множества (число элементов)

        public DisJointSetNode() {
            this.Size = 1; // Изначально множество состоит только из самого себя
        }
    }

    // Хранение всех узлов (ключ — данные, значение — узел)
    private final Map<T, DisJointSetNode> set = new HashMap<>();
    private int count = 0; // Количество независимых множеств

    // Возвращает количество множеств
    public int getCount() {
        return count;
    }

    // Итератор для перебора всех элементов множества
    @Override
    public Iterator<T> iterator() {
        return set.values().stream().map(node -> node.Data).iterator();
    }

    // Создаёт новое множество с единственным элементом `member`
    public void makeSet(T member) {
        // Проверяем, что элемент ещё не существует
        if (set.containsKey(member)) {
            throw new IllegalArgumentException("A set with the given member already exists.");
        }

        // Создаём новый узел и инициализируем его
        DisJointSetNode newSet = new DisJointSetNode();
        newSet.Data = member; // Сохраняем данные
        newSet.Rank = 0; // Ранг новой вершины — 0
        newSet.Parent = newSet; // Новый элемент указывает на самого себя как на родителя

        // Добавляем узел в карту
        set.put(member, newSet);
        count++; // Увеличиваем счётчик множеств
    }

    // Находит представителя множества для элемента `member`
    public T findSet(T member) {
        // Проверяем, что элемент существует
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member.");
        }

        // Возвращаем данные представителя множества
        return findSet(set.get(member)).Data;
    }

    // Рекурсивная функция для поиска представителя множества с сжатием пути
    private DisJointSetNode findSet(DisJointSetNode node) {
        DisJointSetNode parent = node.Parent;

        // Если узел не является корнем, находим корня и сжимаем путь
        if (node != parent) {
            node.Parent = findSet(node.Parent); // Переназначаем родителя
            return node.Parent;
        }

        return parent; // Возвращаем корень
    }

    // Объединяет множества, содержащие элементы `memberA` и `memberB`
    public void union(T memberA, T memberB) {
        // Находим представителей множеств
        T rootA = findSet(memberA);
        T rootB = findSet(memberB);

        // Если они уже принадлежат одному множеству, ничего не делаем
        if (Objects.equals(rootA, rootB)) {
            return;
        }

        // Получаем узлы представителей
        DisJointSetNode nodeA = set.get(rootA);
        DisJointSetNode nodeB = set.get(rootB);

        // Объединяем множества по рангу
        if (nodeA.Rank == nodeB.Rank) {
            // Если ранги равны, делаем nodeA родителем и увеличиваем его ранг
            nodeB.Parent = nodeA;
            nodeA.Rank++;
            nodeA.Size += nodeB.Size; // Обновляем размер множества
        } else if (nodeA.Rank < nodeB.Rank) {
            // Если ранг nodeA меньше, присоединяем его к nodeB
            nodeA.Parent = nodeB;
            nodeB.Size += nodeA.Size;
        } else {
            // Если ранг nodeB меньше, присоединяем его к nodeA
            nodeB.Parent = nodeA;
            nodeA.Size += nodeB.Size;
        }
    }

    // Проверяет, содержится ли элемент `member` в системе множеств
    public boolean contains(T member) {
        return set.containsKey(member);
    }

    // Проверяет, находятся ли два элемента в одном множестве
    public boolean isConnected(T x, T y) {
        return Objects.equals(findSet(x), findSet(y));
    }

    // Возвращает размер множества, содержащего элемент `member`
    public int getClusterSize(T member) {
        // Проверяем, что элемент существует
        if (!set.containsKey(member)) {
            throw new IllegalArgumentException("No such set with the given member.");
        }

        // Возвращаем размер множества
        return findSet(set.get(member)).Size;
    }
}

