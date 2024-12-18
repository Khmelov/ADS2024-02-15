package by.it.group310901.maydanyuk.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
//который реализует бинарное дерево поиска для хранения элементов в отсортированном порядке
public class MyTreeSet<E> implements Set<E> {

    private int size = 0;
    private MyNode<E> root;//корневой узел бинарного дерева поиска


    static private class MyNode<E>{
        E data;
        int hash;
        MyNode<E> left, right;
        MyNode(E e, int hash, MyNode left, MyNode right){
            this.data = e;
            this.hash = hash;//хэш-код данных
            //ссылки на левый и правый дочерние узлы
            this.right = right;
            this.left = left;
        }

    }
//добавления элементов в строку в порядке возрастания
    private void addtostring(MyNode parent, StringBuilder str){
        if(parent.left!=null)
            addtostring(parent.left, str);
        str.append(parent.data);
        str.append(", ");
        if(parent.right!=null)
            addtostring(parent.right, str);
    }
    @Override
    public String toString() {
        // Создаем объект StringBuilder для построения строки
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        // Если корень дерева не равен null добавляем строковое представление узлов
        if (root!=null) {
            addtostring(root, sb);
            sb.delete(sb.length()-2,sb.length());// Удаляем последнюю запятую и пробел
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean add(E e) {
        // Если корень дерева равен null, создаем новый корневой узел
        if (root == null){
            root = new MyNode<>(e, e.hashCode(),null,null);
            size++;
            return true;
        }// Инициализируем переменные для текущего узла и родителя
        MyNode x = root;
        MyNode parent = null;
        int hash = e.hashCode();
        while(x!=null){
            // Если элемент с таким хэш-кодом уже существует
            if (x.hash==hash)
                return false;
            parent = x;
            // Переходим к левому узлу, если хэш текущего узла больше
            if (x.hash>hash)
                x = x.left;
            else
                x = x.right;
        }// Добавляем новый узел в левую или правую ветвь родительского узла
        if (parent.hash>hash)
            parent.left = new MyNode<>(e, e.hashCode(),null,null);
        else
            parent.right = new MyNode<>(e, e.hashCode(),null,null);
        size++;
        return true;
    }//метод для проверки наличия элемента в наборе
    @Override
    public boolean contains(Object o) {
        MyNode x = root;
        int hash = o.hashCode();
        while(x!=null){
            if (x.hash==hash)
                return true;
            if (x.hash>hash)
                x = x.left;
            else
                x = x.right;
        }
        return false;
    }
//для нахождения узла с минимальным значением в дереве
    private MyNode findmin(MyNode node){
        if (node.left==null)
            return node;
        else
            return findmin(node.left);
    }
    private MyNode delete(MyNode node, int hash){
        // Проверяем, совпадает ли хэш-код текущего узла с удаляемым
        if (node.hash ==hash){
            size--;
            // Если у узла нет потомков, просто удаляем его
            if (node.left==null && node.right==null)
                return null;
            // Если у узла нет левого потомка, заменяем узел его правым потомком
            if (node.left==null)
                return node.right;
            if (node.right==null)
                return node.left;
            size++;
            // Находим минимальный узел в правой ветви
            MyNode minnode = findmin(node.right);
            node.data = minnode.data;// Замещаем данные текущего узла данными минимального узла
            node.hash = minnode.hash;
            node.right = delete(node.right, minnode.hash);// Удаляем минимальный узел из правой ветви
            return node;
        }// Переходим к левому или правому потомку в зависимости от хэш-кода
        if (node.hash>hash) {
            if(node.left==null)
                return node;
            node.left = delete(node.left, hash);
        }else{
            if(node.right==null)
                return node;
            node.right = delete(node.right, hash);
        }
        return node;
    }//для удаления элемента из набора. Если элемент найден, он удаляется из дерева
    @Override
    public boolean remove(Object o) {
        MyNode x = root;
        int oldsize = size;
        if(x==null)
            return false;
        root = delete(root, o.hashCode());
        return oldsize!=size;
    }
    //метод для очистки набора.
    // Вспомогательный метод erase рекурсивно очищает дерево
    private void erase(MyNode node){
        if (node.left!=null)
            erase(node.left);
        if (node.right!=null)
            erase(node.right);
        node.left=null;
        node.right=null;
        node.hash=0;
        node.data=null;
    }
    @Override
    public void clear() {
        erase(root);
        root = null;
        size = 0;
    }
//для проверки, содержатся ли все элементы коллекции c в наборе
    @Override
    public boolean containsAll(Collection<?> c) {
        Object []cArray = c.toArray();
        for(int i = 0;i < cArray.length;i++){
            // Если хотя бы один элемент не найден, возвращаем false
            if(!contains(cArray[i]))
                return false;
        }
        return true;
    }
//метод для добавления всех элементов коллекции c в набор
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object []cArray = c.toArray();
        if(cArray.length == 0) {
            return false;
        }
        boolean result = false;
        for(int i = 0;i < cArray.length;i++){
            result |= add((E)cArray[i]);
        }
        return result;
    }
//вспомогательный метод для сохранения только тех элементов, которые содержатся в коллекции
    public boolean retain(MyNode node, Collection<?> c){
        boolean result = false;
        if(node.left!=null)
            result |= retain(node.left, c);
        if(node.right!=null)
            result |= retain(node.right, c);
        if(!c.contains(node.data)) {
            result = remove(node.data);///можно лучше
        }
        return result;
    }//метод для сохранения только тех элементов набора, которые содержатся в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        if(root==null)
            return false;
        return retain(root, c);
    }
    //метод для удаления только тех элементов набора, которые содержатся в коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        Object []cArray = c.toArray();
        if(cArray.length == 0) {
            return false;
        }
        boolean result = false;
        for(int i = 0;i < cArray.length;i++){
            result |= remove(cArray[i]);
        }
        return result;
    }
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }
}
