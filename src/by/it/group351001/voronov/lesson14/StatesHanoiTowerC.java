package by.it.group351001.voronov.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
    private static class DisjointSetUnion {
        int[] parents;
        int[] setSizes;

        DisjointSetUnion(int size) {
            parents = new int[size];
            setSizes = new int[size];
        }

        void initializeSet(int vertex) {
            parents[vertex] = vertex;
            setSizes[vertex] = 1;
        }

        int getSize(int vertex) {
            return setSizes[findSet(vertex)];
        }

        int findSet(int vertex) {
            if (vertex == parents[vertex])
                return vertex;
            return parents[vertex] = findSet(parents[vertex]);
        }

        void mergeSets(int setA, int setB) {
            setA = findSet(setA);
            setB = findSet(setB);
            if (setA != setB) {
                if (setSizes[setA] < setSizes[setB]) {
                    int temp = setA;
                    setA = setB;
                    setB = temp;
                }
                parents[setB] = setA;
                setSizes[setA] += setSizes[setB];
            }
        }
    }

    static int[] calculateMove(int totalDisks, int stepNumber, int diskIndex) {
        int sourceRod = 0, intermediateRod, targetRod;
        intermediateRod = (totalDisks % 2 == 0) ? 1 : 2;
        targetRod = (totalDisks % 2 == 0) ? 2 : 1;

        int[] moveResult = new int[3];
        int position = (stepNumber / (1 << (diskIndex - 1)) - 1) / 2;

        int[] rodMapping = (diskIndex % 2 != 0) ? new int[]{sourceRod, intermediateRod, targetRod} : new int[]{sourceRod, targetRod, intermediateRod};
        int fromRod = rodMapping[position % 3];
        int toRod = rodMapping[(position + 1) % 3];

        moveResult[fromRod] = -1;
        moveResult[toRod] = 1;
        return moveResult;
    }

    static int countTrailingZeros(int number) {
        int zeroCount = 0;
        while (number % 2 == 0) {
            zeroCount++;
            number /= 2;
        }
        return zeroCount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfDisks = scanner.nextInt();
        int maxMoves = (1 << numberOfDisks) - 1;
        int[] diskSteps = new int[numberOfDisks];
        for (int i = 0; i < numberOfDisks; i++)
            diskSteps[i] = -1;
        DisjointSetUnion dsu = new DisjointSetUnion(maxMoves);
        int[] rodHeights = new int[3];
        rodHeights[0] = numberOfDisks;

        for (int move = 0; move < maxMoves; move++) {
            int stepNumber = move + 1;
            int[] deltaHeights = (stepNumber % 2 != 0) ? calculateMove(numberOfDisks, stepNumber, 1) : calculateMove(numberOfDisks, stepNumber, countTrailingZeros(stepNumber) + 1);

            for (int rod = 0; rod < 3; rod++)
                rodHeights[rod] += deltaHeights[rod];

            int maxRodHeight = Math.max(rodHeights[0], Math.max(rodHeights[1], rodHeights[2]));
            dsu.initializeSet(move);

            int maxHeightIndex = maxRodHeight - 1;
            if (diskSteps[maxHeightIndex] == -1)
                diskSteps[maxHeightIndex] = move;
            else
                dsu.mergeSets(diskSteps[maxHeightIndex], move);
        }

        int[] groupSizes = new int[numberOfDisks];
        for (int i = 0; i < numberOfDisks; i++)
            if (diskSteps[i] != -1)
                groupSizes[i] = dsu.getSize(diskSteps[i]);

        StringBuilder resultOutput = new StringBuilder();
        for (int i = 0; i < numberOfDisks; i++) {
            int largestGroup = i;
            for (int j = i + 1; j < numberOfDisks; j++)
                if (groupSizes[largestGroup] < groupSizes[j])
                    largestGroup = j;
            if (groupSizes[largestGroup] == 0)
                break;
            int temp = groupSizes[largestGroup];
            groupSizes[largestGroup] = groupSizes[i];
            groupSizes[i] = temp;
            resultOutput.insert(0, groupSizes[i] + " ");
        }
        resultOutput.deleteCharAt(resultOutput.length() - 1);
        System.out.println(resultOutput);
    }
}
