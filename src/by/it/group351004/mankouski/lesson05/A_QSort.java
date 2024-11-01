package by.it.group351004.mankouski.lesson05;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class A_QSort {

    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            } else {
                return Integer.compare(o.stop, this.stop);
            }
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        Segment[] segments=new Segment[n];
        int m = scanner.nextInt();
        int[] points=new int[m];
        int[] result=new int[m];

        for (int i = 0; i < n; i++) {
            segments[i]=new Segment(scanner.nextInt(),scanner.nextInt());
        }
        for (int i = 0; i < m; i++) {
            points[i]=scanner.nextInt();
        }

        Arrays.sort(segments);

        for (int i = 0; i < m; i++) {
            for (Segment segment : segments) {
                if (points[i] >= segment.start && points[i] <= segment.stop) {
                    result[i]++;
                } else if (points[i] < segment.start) {
                    break;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }
}