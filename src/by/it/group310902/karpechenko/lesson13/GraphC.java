package by.it.group310902.karpechenko.lesson13;

import java.util.*;

public class GraphC {

    private Map<String, ArrayList<String>> gr = new HashMap<>();
    public GraphC(Scanner in) {
        for (var str : in.nextLine().split(", ")) {
            String[] vs = str.split("->");
            if (!gr.containsKey(vs[0]))
                gr.put(vs[0], new ArrayList<>());
            gr.get(vs[0]).add(vs[1]);
        }
        in.close();
    }

    public ArrayList<String> findSCC() {
        Stack<String> v = new Stack<String>();
        HashMap<String, Integer> time = new HashMap<String, Integer>();
        int times = 0;
        for (var node : gr.keySet())
            if (!v.contains(node))
                times = dfsv(node, v, time, times);
        return getPaths(sortV(time).toArray(new String[0]));
    }

    private int dfsv(String u, Stack<String> v, HashMap<String, Integer> time, int times) {
        v.add(u);
        if (gr.get(u) != null)
            for (var j : gr.get(u))
                if (!v.contains(j))
                    times = dfsv(j, v, time, ++times);
        time.put(u, times++);
        return times;
    }

    private ArrayList<String> sortV(HashMap<String, Integer> time) {
        var ans = new ArrayList<>(time.entrySet());
        ans.sort(
            (a, b) -> {
                int c = a.getValue().compareTo(b.getValue());
                return c == 0 ? a.getKey().compareTo(b.getKey()) : c;
            }
        );
        var vs = new ArrayList<String>();
        for (int i = ans.size() - 1; i >= 0; i--)
            vs.add(ans.get(i).getKey());
        return vs;
    }

    private ArrayList<String> getPaths(String[] vs) {
        Stack<String> v = new Stack<String>();
        HashMap <String, ArrayList<String>> rev = getrevgr();
        ArrayList<String> res = new ArrayList<String>();
        for (var j : vs)
            if (!v.contains(j)) {
                var path = new ArrayList<String>();
                dfs(j, rev, v, path);
                path.sort(String::compareTo);
                res.add(String.join("", path));
            }
        return res;
    }

    public HashMap<String, ArrayList<String>> getrevgr() {
        HashMap<String, ArrayList<String>> rev = new HashMap<String, ArrayList<String>>();
        gr.forEach((u, vs) -> {
            vs.forEach(j -> {
                rev.computeIfAbsent(j, k -> new ArrayList<>()).add(u);
            });
        });
        return rev;
    }

    private void dfs(String u, HashMap<String, ArrayList<String>> gr, Stack<String> v, ArrayList<String> path) {
        v.add(u);
        path.add(u);
        if (gr.get(u) != null)
            for (String j : gr.get(u))
                if (!v.contains(j))
                    dfs(j, gr, v, path);
    }


    public static void main(String[] args) {
        System.out.print(String.join("\n",new GraphC(new Scanner(System.in)).findSCC()));
    }
}