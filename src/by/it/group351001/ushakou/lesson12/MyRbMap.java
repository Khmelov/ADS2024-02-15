package by.it.group351001.ushakou.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    // Корень дерева
    Node Root;

    // Перечисление цветов узлов
    enum Color {
        RED, BLACK
    }

    // Класс для представления узла дерева
    class Node {
        Integer key;        // Ключ
        String value;       // Значение
        Node parent, left, right;  // Указатели на родителя, левый и правый дочерние узлы
        Color color;        // Цвет узла

        // Конструктор узла
        public Node(Color color, Integer key, String value) {
            this.color = color;
            this.key = key;
            this.value = value;
        }
    }

    // Метод для вывода карты в строковом представлении
    @Override
    public String toString() {
        if (Root == null) // Если дерево пустое, возвращаем пустую карту
            return "{}";
        StringBuilder sb = new StringBuilder().append("{");
        inOrderTraversal(Root, sb); // Выполняем обход дерева в порядке возрастания ключей
        sb.replace(sb.length() - 2, sb.length(), ""); // Убираем последнюю запятую и пробел
        sb.append("}");
        return sb.toString();
    }

    // Метод для обхода дерева в порядке возрастания ключей
    private void inOrderTraversal(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);  // Рекурсивно обходим левое поддерево
            sb.append(node.key + "=" + node.value + ", ");  // Добавляем ключ и значение
            inOrderTraversal(node.right, sb);  // Рекурсивно обходим правое поддерево
        }
    }

    // Метод для получения размера дерева
    @Override
    public int size() {
        return size(Root);  // Рекурсивный вызов метода для подсчета количества узлов
    }

    // Рекурсивный метод для подсчета количества узлов в поддереве
    int size(Node node) {
        if (node == null) {
            return 0;
        }
        return size(node.left) + size(node.right) + 1;  // Суммируем количество узлов
    }

    // Проверка, является ли дерево пустым
    @Override
    public boolean isEmpty() {
        return Root == null;
    }

    // Проверка наличия ключа в дереве
    @Override
    public boolean containsKey(Object key) {
        return SearchKey((Integer) key, Root) != null;  // Ищем ключ в дереве
    }

    // Рекурсивный поиск узла по ключу
    Node SearchKey(Integer key, Node node) {
        if (node == null)
            return null;
        int comparison = key.compareTo(node.key);  // Сравниваем ключи
        if (comparison == 0)
            return node;  // Если ключ найден, возвращаем узел

        return SearchKey(key, comparison < 0 ? node.left : node.right);  // Иначе ищем в соответствующем поддереве
    }

    // Проверка наличия значения в дереве
    @Override
    public boolean containsValue(Object value) {
        return containsValue(Root, value);  // Рекурсивный поиск значения в поддереве
    }

    // Рекурсивный метод для поиска значения
    boolean containsValue(Node node, Object value) {
        if (node == null) {
            return false;
        }
        if (value.equals(node.value)) {  // Если значение найдено, возвращаем true
            return true;
        }
        return containsValue(node.left, value) || containsValue(node.right, value);  // Ищем в обоих поддеревьях
    }

    // Метод для получения значения по ключу
    @Override
    public String get(Object key) {
        Node node = SearchKey((Integer) key, Root);  // Ищем узел с данным ключом
        return (node != null) ? node.value : null;  // Если узел найден, возвращаем его значение
    }

    // Метод для вставки нового ключа и значения
    @Override
    public String put(Integer key, String value) {
        if (Root == null) {
            Root = new Node(Color.BLACK, key, value);  // Если дерево пустое, создаем корень
        } else {
            Node newNode = new Node(Color.RED, key, value);  // Новый узел будет красным
            Node current = Root;
            while (true) {
                newNode.parent = current;  // Устанавливаем родителя
                if (key.compareTo(current.key) < 0) {
                    if (current.left == null) {  // Если левого ребенка нет, вставляем
                        current.left = newNode;
                        break;
                    } else {
                        current = current.left;  // Иначе переходим в левое поддерево
                    }
                } else if (key.compareTo(current.key) > 0) {
                    if (current.right == null) {  // Если правого ребенка нет, вставляем
                        current.right = newNode;
                        break;
                    } else {
                        current = current.right;  // Иначе переходим в правое поддерево
                    }
                } else {
                    String oldValue = current.value;  // Если ключ уже существует, заменяем значение
                    current.value = value;
                    return oldValue;  // Возвращаем старое значение
                }
            }

            balanceAfterInsert(newNode);  // Балансируем дерево после вставки
        }
        return null;
    }

    // Метод для балансировки дерева после вставки узла
    void balanceAfterInsert(Node node) {
        while (node != Root && node.color == Color.RED && node.parent.color == Color.RED) {
            Node parent = node.parent;
            Node grandparent = parent.parent;

            // Если родитель узла является левым ребенком дедушки
            if (parent == grandparent.left) {
                Node uncle = grandparent.right;
                // Если дядя красный, делаем переворот
                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;  // Черный родитель
                    uncle.color = Color.BLACK;   // Черный дядя
                    grandparent.color = Color.RED;  // Красный дедушка
                    node = grandparent;  // Перемещаемся вверх
                } else {
                    // Если узел является правым ребенком родителя, выполняем левый поворот
                    if (node == parent.right) {
                        node = parent;
                        RotateLeft(node);
                    }
                    parent.color = Color.BLACK;  // Черный родитель
                    grandparent.color = Color.RED;  // Красный дедушка
                    RotateRight(grandparent);  // Поворот вправо
                }
            } else {
                Node uncle = grandparent.left;
                // Если дядя красный, делаем переворот
                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandparent.color = Color.RED;
                    node = grandparent;
                } else {
                    // Если узел является левым ребенком родителя, выполняем правый поворот
                    if (node == parent.left) {
                        node = parent;
                        RotateRight(node);
                    }
                    parent.color = Color.BLACK;
                    grandparent.color = Color.RED;
                    RotateLeft(grandparent);  // Поворот влево
                }
            }
        }

        Root.color = Color.BLACK;  // Корень всегда черный
    }

    // Метод для получения преемника (successor) для заданного узла
    Node getSuccessor(Node node) {
        Node successor = null;
        Node current = node.right; // Начинаем с правого поддерева

        // Идем по самому левому узлу правого поддерева
        while (current != null) {
            successor = current;
            current = current.left;
        }

        return successor; // Возвращаем найденный преемник
    }

    // Метод для удаления узла из дерева
    void deleteNode(Node node) {
        Node replacement;

        // Если у узла есть оба потомка (левый и правый), находим преемника
        if (node.left != null && node.right != null) {
            Node successor = getSuccessor(node); // Получаем преемника
            node.key = successor.key; // Переносим данные из преемника в удаляемый узел
            node.value = successor.value;
            node = successor; // Теперь удаляем узел-преемник
        }

        // Если у узла остался только один потомок, заменяем его на родителя
        replacement = (node.left != null) ? node.left : node.right;

        if (replacement != null) {
            replacement.parent = node.parent; // Обновляем родителя заменяемого узла
            if (node.parent == null) {
                Root = replacement; // Если удаляемый узел был корнем, заменяем корень
            } else if (node == node.parent.left) {
                node.parent.left = replacement; // Если узел был левым потомком
            } else {
                node.parent.right = replacement; // Если узел был правым потомком
            }

            // Если удаляемый узел был черным, выполняем балансировку
            if (node.color == Color.BLACK) {
                balanceDeletion(replacement);
            }
        } else if (node.parent == null) {
            Root = null; // Если узел был корнем и не имел потомков
        } else {
            // Если узел был черным, балансируем
            if (node.color == Color.BLACK) {
                balanceDeletion(node);
            }

            // Убираем ссылку на узел у родителя
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null; // Удаляем ссылку на левый потомок
                } else if (node == node.parent.right) {
                    node.parent.right = null; // Удаляем ссылку на правый потомок
                }
                node.parent = null;
            }
        }
    }

    // Получение цвета узла (если узел null, возвращаем черный цвет)
    Color getColor(Node node) {
        return (node == null) ? Color.BLACK : node.color;
    }

    // Установка цвета узла
    void setColor(Node node, Color color) {
        if (node != null) {
            node.color = color; // Обновляем цвет узла
        }
    }

    // Поворот влево относительно узла
    void RotateLeft(Node node) {
        Node right = node.right; // Правый сын текущего узла
        node.right = right.left; // Переносим левое поддерево правого сына на место правого поддерева узла
        if (right.left != null)
            right.left.parent = node; // Обновляем родителя для левого поддерева правого сына
        right.parent = node.parent; // Переносим родителя правого сына на родителя текущего узла
        if (node.parent == null)
            Root = right; // Если текущий узел был корнем, обновляем корень
        else if (node == node.parent.left)
            node.parent.left = right; // Если текущий узел был левым сыном, обновляем ссылку на левое поддерево
        else
            node.parent.right = right; // Если текущий узел был правым сыном, обновляем ссылку на правое поддерево
        right.left = node; // Переносим текущий узел влево
        node.parent = right; // Обновляем родителя текущего узла
    }

    // Поворот вправо относительно узла
    void RotateRight(Node node) {
        Node left = node.left; // Левый сын текущего узла
        node.left = left.right; // Переносим правое поддерево левого сына на место левого поддерева узла
        if (left.right != null)
            left.right.parent = node; // Обновляем родителя для правого поддерева левого сына
        left.parent = node.parent; // Переносим родителя левого сына на родителя текущего узла
        if (node.parent == null)
            Root = left; // Если текущий узел был корнем, обновляем корень
        else if (node == node.parent.right)
            node.parent.right = left; // Если текущий узел был правым сыном, обновляем ссылку на правое поддерево
        else
            node.parent.left = left; // Если текущий узел был левым сыном, обновляем ссылку на левое поддерево
        left.right = node; // Переносим текущий узел вправо
        node.parent = left; // Обновляем родителя текущего узла
    }

    // Балансировка дерева после удаления узла
    void balanceDeletion(Node node) {
        // Пока узел не является корнем и его цвет черный, продолжаем балансировку
        while (node != Root && getColor(node) == Color.BLACK) {
            if (node == node.parent.left) {
                Node sibling = node.parent.right; // Получаем брата (правый сын родителя)
                // Если брат красный, делаем его черным и родителя красным, а затем вращаем влево
                if (getColor(sibling) == Color.RED) {
                    setColor(sibling, Color.BLACK);
                    setColor(node.parent, Color.RED);
                    RotateLeft(node.parent);
                    sibling = node.parent.right;
                }
                // Если оба поддерева брата черные, делаем брата красным
                if (getColor(sibling.left) == Color.BLACK && getColor(sibling.right) == Color.BLACK) {
                    setColor(sibling, Color.RED);
                    node = node.parent; // Переходим к родителю
                } else {
                    // Если правое поддерево брата черное, вращаем вправо левое поддерево брата
                    if (getColor(sibling.right) == Color.BLACK) {
                        setColor(sibling.left, Color.BLACK);
                        setColor(sibling, Color.RED);
                        RotateRight(sibling);
                        sibling = node.parent.right;
                    }
                    // Переносим цвет родителя на брата и делаем родителя черным
                    setColor(sibling, getColor(node.parent));
                    setColor(node.parent, Color.BLACK);
                    setColor(sibling.right, Color.BLACK); // Убираем красный цвет с правого поддерева брата
                    RotateLeft(node.parent); // Поворот влево на родителе
                    node = Root; // Обновляем узел на корень
                }
            } else {
                Node sibling = node.parent.left; // Получаем брата (левый сын родителя)
                // Если брат красный, делаем его черным и родителя красным, а затем вращаем вправо
                if (getColor(sibling) == Color.RED) {
                    setColor(sibling, Color.BLACK);
                    setColor(node.parent, Color.RED);
                    RotateRight(node.parent);
                    sibling = node.parent.left;
                }
                // Если оба поддерева брата черные, делаем брата красным
                if (getColor(sibling.right) == Color.BLACK && getColor(sibling.left) == Color.BLACK) {
                    setColor(sibling, Color.RED);
                    node = node.parent; // Переходим к родителю
                } else {
                    // Если левое поддерево брата черное, вращаем влево правое поддерево брата
                    if (getColor(sibling.left) == Color.BLACK) {
                        setColor(sibling.right, Color.BLACK);
                        setColor(sibling, Color.RED);
                        RotateLeft(sibling);
                        sibling = node.parent.left;
                    }
                    // Переносим цвет родителя на брата и делаем родителя черным
                    setColor(sibling, getColor(node.parent));
                    setColor(node.parent, Color.BLACK);
                    setColor(sibling.left, Color.BLACK); // Убираем красный цвет с левого поддерева брата
                    RotateRight(node.parent); // Поворот вправо на родителе
                    node = Root; // Обновляем узел на корень
                }
            }
        }

        // Устанавливаем черный цвет на текущий узел после балансировки
        setColor(node, Color.BLACK);
    }


    // Метод для удаления элемента по ключу
    @Override
    public String remove(Object key) {
        Node node = SearchKey((Integer) key, Root); // Ищем узел по ключу
        if (node != null) {
            String oldValue = node.value; // Сохраняем старое значение
            deleteNode(node); // Удаляем узел
            return oldValue; // Возвращаем старое значение
        }
        return null; // Если узел не найден, возвращаем null
    }

    // Метод для очистки всей карты
    @Override
    public void clear() {
        Root = clear(Root); // Очищаем дерево, начиная с корня
    }

    // Рекурсивный метод для очистки дерева
    Node clear(Node node) {
        if (node == null)
            return null; // Если узел пустой, возвращаем null
        node.left = clear(node.left); // Очищаем левое поддерево
        node.right = clear(node.right); // Очищаем правое поддерево
        return null; // Возвращаем null, так как все узлы удалены
    }

    // Метод для получения первого ключа
    @Override
    public Integer firstKey() {
        Node first = firstNode(Root); // Ищем первый узел в дереве
        return (first != null) ? first.key : null; // Если узел найден, возвращаем ключ, иначе null
    }

    // Рекурсивный метод для нахождения первого узла в дереве
    Node firstNode(Node node) {
        while (node != null && node.left != null) { // Идем по левому поддереву
            node = node.left;
        }
        return node; // Возвращаем самый левый узел
    }

    // Метод для получения последнего ключа
    @Override
    public Integer lastKey() {
        Node last = lastNode(Root); // Ищем последний узел в дереве
        return (last != null) ? last.key : null; // Если узел найден, возвращаем ключ, иначе null
    }

    // Рекурсивный метод для нахождения последнего узла в дереве
    Node lastNode(Node node) {
        while (node != null && node.right != null) { // Идем по правому поддереву
            node = node.right;
        }
        return node; // Возвращаем самый правый узел
    }

    // Метод для получения подкарты (SortedMap), которая содержит ключи меньше указанного значения
    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap subMap = new MyRbMap(); // Создаем новую подмапу
        headMap(Root, toKey, subMap); // Заполняем подмапу элементами, которые меньше toKey
        return subMap; // Возвращаем подмапу
    }

    // Рекурсивный метод для заполнения подмапы элементами, которые меньше toKey
    void headMap(Node node, Integer toKey, MyRbMap subMap) {
        if (node == null) {
            return; // Если узел пустой, выходим из метода
        }

        // Если ключ узла меньше toKey, добавляем его в подмапу
        if (node.key.compareTo(toKey) < 0) {
            subMap.put(node.key, node.value); // Добавляем ключ и значение в подмапу
            headMap(node.right, toKey, subMap); // Рекурсивно идем по правому поддереву
        }

        headMap(node.left, toKey, subMap); // Рекурсивно идем по левому поддереву
    }

    // Метод для получения подкарты (SortedMap), которая содержит ключи больше или равные fromKey
    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap subMap = new MyRbMap(); // Создаем новую подмапу
        tailMap(Root, fromKey, subMap); // Заполняем подмапу элементами, которые больше или равны fromKey
        return subMap; // Возвращаем подмапу
    }

    // Рекурсивный метод для заполнения подмапы элементами, которые больше или равны fromKey
    void tailMap(Node node, Integer fromKey, MyRbMap subMap) {
        if (node == null) {
            return; // Если узел пустой, выходим из метода
        }

        // Если ключ узла больше или равен fromKey, добавляем его в подмапу
        if (node.key.compareTo(fromKey) >= 0) {
            subMap.put(node.key, node.value); // Добавляем ключ и значение в подмапу
            tailMap(node.left, fromKey, subMap); // Рекурсивно идем по левому поддереву
        }

        tailMap(node.right, fromKey, subMap); // Рекурсивно идем по правому поддереву
    }


    ///////////////////////

    // Методы, которые пока не реализованы или не нужны для текущего задания:

    @Override
    public Set<Integer> keySet() {
        return null; // Метод для получения набора ключей (не реализован)
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Метод для добавления всех элементов из другой карты (не реализован)
    }

    @Override
    public Collection<String> values() {
        return null; // Метод для получения коллекции значений (не реализован)
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null; // Метод для получения набора записей (не реализован)
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null; // Метод для получения компаратора (не реализован)
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null; // Метод для получения подкарты с ключами от fromKey до toKey (не реализован)
    }
}

/*Задача, выполняемая кодом:
Этот код реализует основные операции, которые могут быть выполнены с красно-черным деревом. Основной задачей является поддержание сбалансированности дерева при добавлении, удалении и поиске элементов. В данном случае код выполняет несколько операций над ключами и значениями, хранящимися в дереве:

Удаление элемента (remove) — по ключу удаляется соответствующий узел из дерева. Если узел имеет два дочерних узла, то на его место ставится преемник (минимальный элемент в правом поддереве), и дерево балансируется.

Очистка дерева (clear) — удаляются все узлы дерева. Для этого рекурсивно удаляются все дочерние элементы.

Получение первого элемента (firstKey) — поиск самого левого узла в дереве (с минимальным значением ключа).

Получение последнего элемента (lastKey) — поиск самого правого узла в дереве (с максимальным значением ключа).

Получение подкарты:

headMap — возвращает все элементы с ключами, меньшими чем заданный.
tailMap — возвращает все элементы с ключами, большими или равными заданному.
Эти методы полезны для извлечения подмножеств данных с определенным диапазоном значений.

Места для других методов:

keySet, values, entrySet — стандартные методы интерфейса SortedMap, которые не реализованы в этом фрагменте, но их предполагается добавить для работы с ключами, значениями и записями (ключ-значение).
subMap и comparator — еще два метода для работы с диапазонами ключей и получения компаратора, но они не реализованы.
Теоретические сведения о красно-черных деревьях:
Красно-черное дерево — это самобалансирующееся бинарное дерево поиска, которое имеет дополнительные ограничения для того, чтобы гарантировать сбалансированность:

Каждый узел имеет цвет: красный или черный.
Корень всегда черный.
Все листья (пустые узлы) черные.
Если красный узел имеет потомков, то они всегда черные (нет двух красных узлов подряд).
Каждый путь от узла до всех его потомков-листьев содержит одинаковое количество черных узлов. Это свойство называется черной высотой.
Эти ограничения обеспечивают, что:

В худшем случае глубина дерева пропорциональна логарифму от количества элементов в дереве (O(log n)).
Все операции, такие как вставка, удаление, и поиск, могут быть выполнены за время O(log n).
Операции в красно-черном дереве:
Поиск: Стандартная операция в бинарном дереве поиска. В красно-черном дереве она выполняется за время O(log n), так как дерево сбалансировано.
Вставка: При вставке нового элемента в дерево нужно сохранить свойства красно-черного дерева. Это достигается с помощью поворотов (левый и правый повороты) и изменения цветов узлов, что позволяет сохранить баланс.
Удаление: Удаление узла в красно-черном дереве сложнее, чем в обычном бинарном дереве поиска, из-за необходимости поддержания балансировки. Это требует особой процедуры балансировки, включающей дополнительные повороты и перекраску узлов.
Ключевая цель этого кода:
Основной задачей является поддержание баланса дерева при изменении его структуры. Все методы взаимодействуют с деревом таким образом, чтобы оно оставалось сбалансированным, что гарантирует эффективное выполнение операций поиска, вставки и удаления.
*/