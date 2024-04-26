package by.it.group310902.kasperets.lesson04;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {
    int count = 0;
    int[] mergeSort(int[] arr, int left ,int right ){
        if(arr.length == 2){
            if(arr[left] > arr[right]){
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                count++;
            }
            return arr;
        }
        if(left == right){
            return arr;
        }


        int mid = (left + right) / 2;
        int[] leftArr = mergeSort(arr, left, mid);
        int[] rightArr = mergeSort(arr, mid + 1, right);

        int[] result = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while(i <= mid && j<= right){
            if(leftArr[i] <= rightArr[j]){
                result[k++] = leftArr[i++];
            }else{
                result[k++] = rightArr[j++];
                /*count += mid - i + 1;*/
                count++;
            }
        }
        while(i <= mid){
            result[k++] = leftArr[i++];
        }
        while(j <= right){
            result[k++] = rightArr[j++];
            count++;
        }
        for(int l = 0; l < result.length; l++){
            arr[l] = result[l];
        }
        return arr;
    }
    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием


        a = mergeSort(a, 0, a.length - 1);




        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
