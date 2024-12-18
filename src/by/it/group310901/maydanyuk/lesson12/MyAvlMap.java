package by.it.group310901.maydanyuk.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
// Класс MyAvlMap реализует интерфейс Map, используя AVL-дерево для хранения данных
public class MyAvlMap implements Map<Integer, String> {

    private int size = 0;
    private MyNode root;// Корневой узел
    // Внутренний класс для представления узла
    static private class MyNode{
        Integer key;// Ключ узла
        String value;// Значение узла
        int height;// Высота узла
        MyNode left, right;// Левые и правые поддеревья
        MyNode(Integer key,String value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.right = null;
            this.left = null;
        }

    }// Метод для получения высоты узла
    private int height(MyNode node){
        return (node!=null) ? node.height : 0;
    }
    // метод для вычисления разница высот правого и левого поддеревьев
    private int bfactor(MyNode node){
        return height(node.right)-height(node.left);
    }

    //    private void checkavl(MyNode node){
    //        if (bfactor(node)<-1 || bfactor(node)>1)
    //            System.out.println("failure!");
    //        if (node.right !=null)
    //            checkavl(node.right);
    //        if (node.left !=null)
    //            checkavl(node.left);
    //    }
    private void fixheight(MyNode node){
        int heightright = height(node.right);
        int heightleft = height(node.left);
        // Устанавливаем высоту текущего узла на единицу больше высоты его более высокого поддерева
        node.height = (heightright>heightleft ? heightright : heightleft)+1;
    }
    // Метод для добавления узла в строку представления дерева
    private void addtostring(MyNode parent, StringBuilder str){
        if(parent.left!=null)
            addtostring(parent.left, str);
        // Добавляем текущий узел
        str.append(parent.key);
        str.append("=");
        str.append(parent.value);
        str.append(", ");
        if(parent.right!=null)
            addtostring(parent.right, str);
    }
    // Метод для преобразования карты в строку
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (root!=null) {
            addtostring(root, sb);
            sb.delete(sb.length()-2,sb.length());
        }
        sb.append("}");
        return sb.toString();
    }
    // Метод для правого вращения узла
    private MyNode rotateright(MyNode node){
        MyNode nextnode = node.left;
        node.left = nextnode.right;
        nextnode.right = node;
        fixheight(node);// Обновляем высоту старого корня
        fixheight(nextnode);// Обновляем высоту нового корня
        return nextnode;
    }
    // Метод для левого вращения узла
    private MyNode rotateleft(MyNode node){
        MyNode nextnode = node.right;
        node.right = nextnode.left;
        nextnode.left = node;
        fixheight(node);
        fixheight(nextnode);
        return nextnode;
    }// Метод для балансировки узла
    private MyNode balance(MyNode node){
        fixheight(node);// Обновляем высоту узла
        // Получаем фактор баланса узла
        int h = bfactor(node);
        if (h == 2){// Если узел перегружен вправо
            // Если правое поддерево перегружено влево
            if (bfactor(node.right) < 0)
                // Выполняем правое вращение для правого поддерева
                node.right = rotateright(node.right);
            return rotateleft(node);
        }
        if (h == -2){
            if (bfactor(node.left) > 0)
                node.left = rotateleft(node.left);
            return rotateright(node);
        }
        return node;
    }
    // Метод для вставки нового узла
    private MyNode insert(MyNode node, Integer key, String value, StringBuilder oldvalue){
        if (node==null) {
            size++;//создаем новый узел
            return new MyNode(key, value);
        }
        if (node.key>key)
            //идем в левое поддерево
            node.left = insert(node.left, key, value,oldvalue);
        else if (node.key<key)
            node.right = insert(node.right, key, value, oldvalue);
        else {// Если ключ равен ключу текущего узла, обновляем значение
            oldvalue.append(node.value);
            node.value = value;
            return node;
        }
        return balance(node);
    }// Метод для вставки нового элемента в карту
    @Override
    public String put(Integer key, String value) {
        StringBuilder oldvalue = new StringBuilder();
        root = insert(root, key, value, oldvalue);
        //        checkavl(root);
        return oldvalue.isEmpty()?null:oldvalue.toString();
    }

    // Метод для нахождения минимального узла
    private MyNode findmin(MyNode node){
        if (node.left==null)
            return node;
        else
            return findmin(node.left);
    }
    // Метод для удаления узла
    private MyNode delete(MyNode node, Integer key, StringBuilder oldvalue){
        if (node.key.equals(key)){// Если ключ найден
            size--;
            if (oldvalue!=null)
                //сохраняем старое значение
                oldvalue.append(node.value);
            if (node.left==null && node.right==null)
                return null;
            if (node.left==null)
                return node.right;
            if (node.right==null)
                return node.left;
            size++;
            MyNode minnode = findmin(node.right);// Находим минимальный узел в правом поддереве
            // Копируем его значение в текущий узел
            // Копируем его ключ в текущий узел
            node.value = minnode.value;
            node.key = minnode.key;
            // Удаляем минимальный узел из правого поддерева
            node.right = delete(node.right, minnode.key, null);
            return node;
        }
        if (node.key>key) {
            if(node.left==null)
                return node;
            node.left = delete(node.left, key, oldvalue);
        }else{
            if(node.right==null)
                return node;
            node.right = delete(node.right, key, oldvalue);
        }
        return balance(node);
    }// Метод для удаления элемента из карты
    @Override
    public String remove(Object key) {
        int oldsize = size;
        StringBuilder oldvalue = new StringBuilder();
        root = delete(root, (Integer)key, oldvalue);
        //        checkavl(root);
        return oldsize==size?null:oldvalue.toString();
    }
    // Метод для получения значения по ключу
    @Override
    public String get(Object key) {
        MyNode x = root;
        while(x!=null){
            if (x.key.equals(key))
                return x.value;
            if (x.key>(Integer)key)
                x = x.left;
            else
                x = x.right;
        }
        return null;
    }
    // Метод для проверки наличия ключа
    @Override
    public boolean containsKey(Object key) {
        MyNode x = root;
        while(x!=null){
            if (x.key==key)
                return true;
            if (x.key>(Integer)key)
                x = x.left;
            else
                x = x.right;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    // Метод для очистки карты
    private MyNode eraseNode(MyNode node){
        if (node != null){
            node.right = eraseNode(node.right);
            node.left = eraseNode(node.left);
            node.key = null;
            node.value = null;
        }
        return null;
    }
    @Override
    public void clear() {
        size = 0;
        root = eraseNode(root);
    }// Метод для проверки, пуста ли карта
    @Override
    public boolean isEmpty() {
        return size==0;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean containsValue(Object value) {
        return false;
    }



    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }
}