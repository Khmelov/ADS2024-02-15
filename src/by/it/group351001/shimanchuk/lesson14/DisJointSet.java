package by.it.group351001.shimanchuk.lesson14; // Объявление пакета, к которому относится класс

import java.util.HashMap; // Импорт класса HashMap из библиотеки Java
import java.util.Iterator; // Импорт интерфейса Iterator
import java.util.Map; // Импорт интерфейса Map
import java.util.Objects; // Импорт утилит для работы с объектами

/**
 * Класс DisJointSet реализует структуру данных "Система непересекающихся множеств" (Union-Find).
 * @param <T> Тип элементов, хранящихся в системе непересекающихся множеств.
 */
public class DisJointSet<T> implements Iterable<T> { // Объявление класса DisJointSet

    // Внутренний класс, представляющий узел в системе множеств
    private class DisJointSetNode {
        public T Data; // Поле для хранения данных узла
        public int Rank; // Поле для хранения ранга узла
        public DisJointSetNode Parent; // Поле для ссылки на родительский узел
        public int Size; // Поле для хранения размера множества

        public DisJointSetNode() { // Конструктор класса DisJointSetNode
            this.Size = 1; // Установка начального размера множества
        }
    }

    private final Map<T, DisJointSetNode> set = new HashMap<>(); // Хранение всех узлов в системе множеств
    private int count = 0; // Количество множеств в системе

    /**
     * Метод для получения количества множеств.
     * @return количество непересекающихся множеств.
     */
    public int getCount() {
        return count; // Возвращает количество множеств
    }

    /**
     * Реализация метода iterator для итерации по элементам.
     * @return итератор по элементам.
     */
    @Override
    public Iterator<T> iterator() {
        return set.values().stream().map(node -> node.Data).iterator(); // Итерация по элементам в системе множеств
    }

    /**
     * Метод для создания нового множества с одним элементом.
     * @param member элемент множества.
     */
    public void makeSet(T member) {
        if (set.containsKey(member)) { // Проверка, существует ли уже множество с данным элементом
            throw new IllegalArgumentException("A set with the given member already exists."); // Генерация исключения
        }

        DisJointSetNode newSet = new DisJointSetNode(); // Создание нового узла для множества
        newSet.Data = member; // Установка данных узла
        newSet.Rank = 0; // Установка начального ранга
        newSet.Parent = newSet; // Установка узла как его собственного родителя

        set.put(member, newSet); // Добавление нового узла в карту
        count++; // Увеличение количества множеств
    }

    /**
     * Метод для нахождения представителя множества для данного элемента.
     * @param member элемент множества.
     * @return представитель множества.
     */
    public T findSet(T member) {
        if (!set.containsKey(member)) { // Проверка, существует ли множество с данным элементом
            throw new IllegalArgumentException("No such set with the given member."); // Генерация исключения
        }

        return findSet(set.get(member)).Data; // Вызов рекурсивного метода для нахождения представителя множества
    }

    /**
     * Рекурсивный метод для нахождения представителя множества с путевой компрессией.
     * @param node узел для поиска.
     * @return представитель множества.
     */
    private DisJointSetNode findSet(DisJointSetNode node) {
        DisJointSetNode parent = node.Parent; // Получение родителя узла

        if (node != parent) { // Если узел не является своим собственным родителем
            node.Parent = findSet(node.Parent); // Выполняется путевая компрессия
            return node.Parent; // Возврат нового родителя
        }

        return parent; // Возврат родителя, если узел является своим собственным родителем
    }

    /**
     * Метод для объединения двух множеств, содержащих данные элементы.
     * @param memberA первый элемент.
     * @param memberB второй элемент.
     */
    public void union(T memberA, T memberB) {
        T rootA = findSet(memberA); // Нахождение представителя первого множества
        T rootB = findSet(memberB); // Нахождение представителя второго множества

        if (Objects.equals(rootA, rootB)) { // Если элементы уже в одном множестве
            return; // Объединение не требуется
        }

        DisJointSetNode nodeA = set.get(rootA); // Получение узла для первого множества
        DisJointSetNode nodeB = set.get(rootB); // Получение узла для второго множества

        if (nodeA.Rank == nodeB.Rank) { // Если ранги узлов равны
            nodeB.Parent = nodeA; // Присвоение родителя для второго множества
            nodeA.Rank++; // Увеличение ранга первого множества
            nodeA.Size += nodeB.Size; // Увеличение размера первого множества
        } else {
            if (nodeA.Rank < nodeB.Rank) { // Если ранг первого множества меньше
                nodeA.Parent = nodeB; // Присвоение родителя для первого множества
                nodeB.Size += nodeA.Size; // Увеличение размера второго множества
            } else { // Если ранг второго множества меньше
                nodeB.Parent = nodeA; // Присвоение родителя для второго множества
                nodeA.Size += nodeB.Size; // Увеличение размера первого множества
            }
        }
    }

    /**
     * Метод для проверки, содержится ли элемент в системе множеств.
     * @param member элемент для проверки.
     * @return true, если элемент содержится; false, если нет.
     */
    public boolean contains(T member) {
        return set.containsKey(member); // Проверка наличия элемента в карте
    }

    /**
     * Метод для проверки, принадлежат ли два элемента одному множеству.
     * @param x первый элемент.
     * @param y второй элемент.
     * @return true, если элементы принадлежат одному множеству; false, если нет.
     */
    public boolean isConnected(T x, T y) {
        return Objects.equals(findSet(x), findSet(y)); // Проверка равенства представителей множеств
    }

    /**
     * Метод для получения размера множества, содержащего данный элемент.
     * @param member элемент множества.
     * @return размер множества.
     */
    public int getClusterSize(T member) {
        if (!set.containsKey(member)) { // Проверка, существует ли множество с данным элементом
            throw new IllegalArgumentException("No such set with the given member."); // Генерация исключения
        }

        return findSet(set.get(member)).Size; // Возвращает размер множества
    }

}
