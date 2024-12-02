package by.it.group351001.voronov.lesson11;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {

    class TreeNode {
        E value;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(E value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        traverseInOrder(rootNode, stringBuilder);
        return stringBuilder.append("]").toString();
    }

    void traverseInOrder(TreeNode node, StringBuilder stringBuilder) {
        if (node == null) return;
        traverseInOrder(node.leftChild, stringBuilder);
        if (stringBuilder.length() > 1)
            stringBuilder.append(", ");
        stringBuilder.append(node.value);
        traverseInOrder(node.rightChild, stringBuilder);
    }

    TreeNode rootNode;
    int elementCount;

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }

    boolean containsElement(TreeNode node, E targetValue) {
        if (node == null) return false;
        int comparison = targetValue.compareTo(node.value);
        if (comparison < 0)
            return containsElement(node.leftChild, targetValue);
        else if (comparison > 0)
            return containsElement(node.rightChild, targetValue);
        else
            return true;
    }

    @Override
    public boolean contains(Object o) {
        return containsElement(rootNode, (E) o);
    }

    TreeNode insertElement(TreeNode node, E newValue) {
        if (node == null)
            return new TreeNode(newValue);
        int comparison = newValue.compareTo(node.value);
        if (comparison < 0)
            node.leftChild = insertElement(node.leftChild, newValue);
        else if (comparison > 0)
            node.rightChild = insertElement(node.rightChild, newValue);
        return node;
    }

    @Override
    public boolean add(E e) {
        if (!contains(e)) {
            rootNode = insertElement(rootNode, e);
            elementCount++;
            return true;
        }
        return false;
    }

    TreeNode findMinimum(TreeNode node) {
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    TreeNode removeElement(TreeNode node, E valueToRemove) {
        if (node == null) return null;
        int comparison = valueToRemove.compareTo(node.value);
        if (comparison < 0) {
            node.leftChild = removeElement(node.leftChild, valueToRemove);
        } else if (comparison > 0) {
            node.rightChild = removeElement(node.rightChild, valueToRemove);
        } else {
            if (node.leftChild == null) {
                return node.rightChild;
            } else if (node.rightChild == null) {
                return node.leftChild;
            }
            node.value = findMinimum(node.rightChild).value;
            node.rightChild = removeElement(node.rightChild, node.value);
        }
        return node;
    }

    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            rootNode = removeElement(rootNode, (E) o);
            elementCount--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj : collection) {
            if (!contains(obj))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E element : collection) {
            if (add(element))
                modified = true;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            this.clear();
            return true;
        }
        boolean modified = false;
        MyTreeSet<E> retainSet = new MyTreeSet<>();
        for (Object obj : collection) {
            if (contains(obj)) {
                retainSet.add((E) obj);
                modified = true;
            }
        }
        rootNode = retainSet.rootNode;
        elementCount = retainSet.elementCount;

        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object obj : collection) {
            if (remove(obj))
                modified = true;
        }
        return modified;
    }

    @Override
    public void clear() {
        rootNode = null;
        elementCount = 0;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }
}