package by.it.group310902.strizhevskiy.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!





        temp = new int[a.length];
        result = (int) sort(a, 0, a.length);





        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);

        //  C_GetInversions o = new C_GetInversions();
        //  o.temp = new int[10000000];

        //  int[] s1 = new int[o.temp.length];
        //  int[] s2 = new int[o.temp.length];
        //  int[] s3 = new int[o.temp.length];

        //  for (int i = 0; i < o.temp.length; i++) {
        //      s1[i] = (int)(Math.random()*1000000);
        //      s2[i] = s1[i];
        //      s3[i] = s1[i];
        //  }

        //  long sortTime = System.currentTimeMillis();
        //  long inv1 = o.sort(s1, 0, s1.length);
        //  sortTime = System.currentTimeMillis() - sortTime;

        //  System.out.printf("sort: time=%d, inv=%d%n",sortTime,inv1);

        //  long pSortTime = System.currentTimeMillis();
        //  long inv2 = o.parallelSort(s2, 0, s2.length, 6);
        //  pSortTime = System.currentTimeMillis() - pSortTime;

        //  System.out.printf("psort: time=%d, inv=%d%n",pSortTime,inv2);

        //  for (int i = 0; i < s1.length; i++) {
        //      if (s1[i] != s2[i]) {
        //          System.out.println("Wrong");
        //          break;
        //      }
        //  }

        // System.out.println();

        //  // long inv = 0;
        //  // for (int i = 0; i < s3.length; i++) {
        //  //     for (int j = i+1; j < s3.length; j++) {
        //  //         if (s3[i] > s3[j]) inv++;
        //  //     }
        //  // }
        //  // System.out.println(inv);
    }

    
    private int[] temp;

    long merge(int[] s, int al, int ar, int bl, int br){
        int tl = al, tr = al; al--; ar--;
        long inv = 0;
        while (al < ar) {
            if (tl < tr) {
                inv += tr-tl;
                temp[tr++] = s[++al];
                s[al] = bl == br || temp[tl] <= s[bl] ? temp[tl++] : s[bl++];
                continue;
            }
            if (bl < br && s[bl] < s[++al]) {
                temp[tr++] = s[al];
                s[al] = s[bl++];
                continue;
            }
        }
        al = bl-tr+tl;
        while (tl < tr) {
            inv += tr-tl;
            s[al++] = bl == br || temp[tl] <= s[bl] ? temp[tl++] : s[bl++];
        }
        return inv;
    }

    long sort(int[] s, int from, int to){
        if (to - from <= 1) { return 0; }
        int al = from;
        int ar = from+(to-from)/2;
        int bl = ar;
        int br = to;
        return sort(s, al, ar) + sort(s, bl, br) + merge(s, al, ar, bl, br);
    }

    long parallelSort(int[] s, int from, int to, int threadCount){
        Thread[] sorters = new Thread[threadCount];
        class Sorter extends Thread {
            volatile int lvl = Integer.MAX_VALUE;
            int index, al, ar;
            long inv;
            Sorter(int index, int al, int ar) {
                this.index = index;
                this.al = al;
                this.ar = ar;
                start();
            }
            public void run(){
                inv = sort(s, al, ar);

                lvl--;

                int l = 0, r = threadCount-1, m = -1;
                int newLvl = 1;
                while (l != m) {
                    m = (l+r)/2;
                    if (m == index) { break; }
                    else if (m < index) { l = m+1; }
                    else { r = m-1; }
                    newLvl++;
                }
                lvl = newLvl;

                while (sorters[(r+m+1)/2] == null);
                Sorter sl = (Sorter) sorters[(l+m-1)/2];
                Sorter sr = (Sorter) sorters[(r+m+1)/2];

                // System.out.printf("index=%d%nsl=%s%nsr=%s%n",index,sl,sr);
                while (this != sl && this.lvl <= sl.lvl);
                while (this != sr && this.lvl <= sr.lvl);
                inv = sl == this ? sr.inv : sl.inv + sr.inv + merge(s, sl.al, sl.ar, sr.al, sr.ar);
                al = sl.al;
                ar = sr.ar;
                lvl = 0;
            }
        }
        
        
        int step = (to-from)/threadCount;
        int al = from, ar = al+step;

        for (int i = 0; i < threadCount-1; i++) {
            sorters[i] = new Sorter(i,al,ar);
            al += step;
            ar += step;
        }

        sorters[threadCount-1] = new Sorter(threadCount-1,al,to);

        Sorter fin = (Sorter) sorters[(threadCount-1)/2];

        while (fin.isAlive());

        return fin.inv;
    }
}
