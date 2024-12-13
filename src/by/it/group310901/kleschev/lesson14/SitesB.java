package by.it.group310901.kleschev.lesson14;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//по рангу или размеру поддерева
//по сокращению пути поддерева
public class SitesB{
    public static class DSU{
        int[] parent = new int[0];

        void inicialize_arr(int n){
            parent = Arrays.copyOf(parent, n);
        }

        void make_set (int v) {
            parent[v] = v;
        }

        int find_set(int v) {
            if (v == parent[v])
                return v;
            parent[v] = find_set(parent[v]);
            return parent[v];
        }

        void union_sets(int a, int b) {
            a = find_set(a);
            b = find_set(b);
            if (a != b)
                parent[b] = a;
        }
    }
//Вычисляет квадрат расстояния между двумя точками
    public static int dist(int[] f, int[]s){
        return (f[0] - s[0])*(f[0] - s[0]) + (f[1] - s[1])*(f[1] - s[1]) + (f[2] - s[2])*(f[2] - s[2]);
    }

    public static boolean less_dist(int d, int[] f, int[] s){
        return dist(f, s) <= d * d;
    }
    //Чтение входных данных и создание системы непересекающихся множеств
    public static void main(String[] args) {
        String s;
        Scanner in = new Scanner(System.in);
        DSU myDSU = new DSU();
        Map<String, Integer> toInd = new HashMap<>();
        int size = 0;
        while(true){
            s = in.next();
            if(s.equals("end"))
                break;
            int temp = -1;
            while(s.charAt(++temp) != '+');
            String f = s.substring(0, temp), d = s.substring(temp + 1, s.length());
            if(!toInd.containsKey(f)) {
                toInd.put(f, size++);
                myDSU.inicialize_arr(size);
                myDSU.make_set(size - 1);;
            }
            if(!toInd.containsKey(d)) {
                toInd.put(d, size++);
                myDSU.inicialize_arr(size);
                myDSU.make_set(size - 1);
            }
            myDSU.union_sets(toInd.get(f), toInd.get(d));
        }
//Используется DSU для объединения множеств и проверки расстояния между точками.
        Map<Integer, Integer> cnt = new HashMap<>();
        int free = 0;
        int[] ans = new int[size];
        for(int i = 0; i < size; ++i) {
            int p = myDSU.find_set(i);
            if(cnt.containsKey(p))
                ans[cnt.get(p)]++;
            else {
                ans[free] = 1;
                cnt.put(p, free++);
            }
        }
        //Сортировка размеров множеств в убывающем порядке и вывод этих размеров.
        System.out.println();
        ans = Arrays.copyOf(ans, cnt.size());
        Arrays.sort(ans);
        for(int i = ans.length - 1; i >= 0;) {
            System.out.print(ans[i--]);
            System.out.print(' ');
        }
    }
}