package by.it.group351004.dashkevich.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
    private static class DSU {
        int[] parent;
        int[] size;

        DSU(int size) {
            parent = new int[size];
            this.size = new int[size];
        }

        void makeSet(int member) {
            parent[member] = member;
            size[member] = 1;
        }

        int size(int member) {
            return size[findSet(member)];
        }

        int findSet(int member) {
            if (member == parent[member])
                return member;
            return parent[member] = findSet(parent[member]);
        }

        void unionSets(int memberA, int memberB) {
            memberA = findSet(memberA);
            memberB = findSet(memberB);
            if (memberA != memberB) {
                if (size[memberA] < size[memberB]) {
                    int temp = memberA;
                    memberA = memberB;
                    memberB = temp;
                }
                parent[memberB] = memberA;
                size[memberA] += size[memberB];
            }
        }
    }

    static int[] carryingOver(int N, int step, int k) {
        int axisX = 0, axisY, axisZ;
        axisY = (N % 2 == 0) ? 1 : 2;
        axisZ = (N % 2 == 0) ? 2 : 1;

        int[] result = new int[3];
        int currMovement = (step / (1 << (k - 1)) - 1) / 2;

        int[] mapping = (k % 2 != 0) ? new int[]{axisX, axisY, axisZ} : new int[]{axisX, axisZ, axisY};
        int from = mapping[currMovement % 3];
        int to = mapping[(currMovement + 1) % 3];

        result[from] = -1;
        result[to] = 1;
        return result;
    }

    static int countBits(int num) {
        int count = 0;
        while (num % 2 == 0) {
            count++;
            num /= 2;
        }
        return count;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfDisks = scanner.nextInt();
        int max_size = (1 << numOfDisks) - 1;
        int[] steps_heights = new int[numOfDisks];
        for (int i = 0; i < numOfDisks; i++)
            steps_heights[i] = -1;
        DSU dsu = new DSU(max_size);
        int[] heights = new int[3];
        heights[0] = numOfDisks;
        for (int i = 0; i < max_size; i++) {

            int step = i + 1;
            int[] delta = (step % 2 != 0) ? carryingOver(numOfDisks, step, 1) : carryingOver(numOfDisks, step, countBits(step) + 1);

            for (int j = 0; j < 3; j++)
                heights[j] += delta[j];

            int max = Math.max(heights[0], Math.max(heights[1], heights[2]));
            dsu.makeSet(i);

            int maxHeightIndex = max - 1;
            if (steps_heights[maxHeightIndex] == -1)
                steps_heights[maxHeightIndex] = i;
            else
                dsu.unionSets(steps_heights[maxHeightIndex], i);
        }

        int[] sizes = new int[numOfDisks];
        for (int i = 0; i < numOfDisks; i++)
            if (steps_heights[i] != -1)
                sizes[i] = dsu.size(steps_heights[i]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfDisks; i++) {
            int max = i;
            for (int j = i + 1; j < numOfDisks; j++)
                if (sizes[max] < sizes[j])
                    max = j;
            if (sizes[max] == 0)
                break;
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " ");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }
}