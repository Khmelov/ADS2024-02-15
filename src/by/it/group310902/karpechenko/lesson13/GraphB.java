package by.it.group310902.karpechenko.lesson13;

import java.util.*;

public class GraphB {

    private Map<String, ArrayList<String>> gr = new HashMap<>();
    public GraphB(Scanner in) {
        for (var str : in.nextLine().split(", ")) {
            String[] vs = str.split(" -> ");
            if (!gr.containsKey(vs[0]))
                gr.put(vs[0], new ArrayList<>());
            gr.get(vs[0]).add(vs[1]);
        }
        in.close();
    }

    public boolean isCycle() {
        for (var i : gr.keySet())
            if (dfs(i, new Stack<String>()))
                return true;
        return false;
    }

    public boolean dfs(String u, Stack<String> v) {
        v.add(u);
        if (gr.get(u) != null)
            for (var j : gr.get(u))
                if (v.contains(j) || dfs(j, v))
                    return true;
        v.remove(u);
        return false;
    }

    public static void main(String[] args) {
        System.out.print(new GraphB(new Scanner(System.in)).isCycle() ? "yes" : "no");
    }
}