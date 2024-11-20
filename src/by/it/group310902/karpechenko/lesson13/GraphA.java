package by.it.group310902.karpechenko.lesson13;

import java.util.*;

public class GraphA {
    private Map<String, ArrayList<String>> gr = new HashMap<>();

    public GraphA(Scanner in) {
        for (var str : in.nextLine().split(", ")) {
            var vs = str.split(" -> ");
            if (!gr.containsKey(vs[0]))
                gr.put(vs[0], new ArrayList<>());
            gr.get(vs[0]).add(vs[1]);
        }
        in.close();
    }

    public GraphA sort() {
        for (var i : gr.values())
            i.sort((a, b) -> b.compareTo(a));
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Stack st = new Stack<String>();
        HashSet v = new HashSet<String>();

        for (var i : gr.keySet())
            if (!v.contains(i))
                dfs(i, v, st);

        sb.append(st.pop());
        while (!st.empty())
            sb.append(' ').append(st.pop());

        return sb.toString();
    }

    private void dfs(String u, Set<String> v, Stack<String> st) {
        v.add(u);
        if (gr.get(u) != null)
            for (var next : gr.get(u))
                if (!v.contains(next))
                    dfs(next, v, st);
        st.push(u);
    }

    public static void main(String[] args) {
        System.out.print(new GraphA(new Scanner(System.in)).sort());
    }
}