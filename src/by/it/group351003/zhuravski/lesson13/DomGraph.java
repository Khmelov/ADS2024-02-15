package by.it.group351003.zhuravski.lesson13;

import java.util.*;

public class DomGraph {
    HashMap<Character, LinkedList<Character>> graph;

    private void insertVal(LinkedList<Character> list, char val) {
        int pos = 0;
        for (Character i : list) {
            if (i == val) {
                return;
            }
            if (i > val) {
                break;
            }
            pos++;
        }
        list.add(pos, val);
    }

    public void link(char parent, char son) {
        if (!graph.containsKey(parent)) {
            graph.put(parent, new LinkedList<>());
        }
        if (!graph.containsKey(son)) {
            graph.put(son, new LinkedList<>());
        }
        LinkedList<Character> list = graph.get(parent);
        insertVal(list, son);
    }

    public DomGraph(String coded) {
        graph = new HashMap<>();
        String[] lexemes = coded.split(" ");
        int cur = lexemes.length - 1;
        int sub = 0;
        while (cur > 0) {
            char son = lexemes[cur].charAt(lexemes[cur].length() - 1 + sub);
            char parent = lexemes[cur - 2].charAt(0);
            link(parent, son);
            cur -= 3;
            sub = -1;
        }
    }

    void pushC(String coded) {
        String[] lexemes = coded.split(", ");
        for (String lexeme : lexemes) {
            String[] small = lexeme.split("->");
            link(small[0].charAt(0), small[1].charAt(0));
        }
    }

    public Character[] topologicalSort() {
        Set<Character> visited = new TreeSet<>();
        Set<Character> marked = new TreeSet<>();
        Character[] result = new Character[graph.size()];
        int count = 0;
        Iterator<Character> keyIter = graph.keySet().iterator();
        while (count < graph.keySet().size()) {
            char curRoot = keyIter.next();
            if (visited.contains(curRoot)) {
                continue;
            }
            Stack<Character> stack = new Stack<>();
            stack.push(curRoot);
            while (!stack.empty()) {
                char cur = stack.peek();
                if (marked.contains(cur)) {
                    marked.remove(cur);
                    visited.add(cur);
                    result[result.length - 1 - count] = cur;
                    count++;
                    stack.pop();
                }
                else {
                    marked.add(cur);
                    LinkedList<Character> next = graph.get(cur);
                    for (char item : next) {
                        if (!visited.contains(item)) {
                            if (marked.contains(item)) {
                                throw new RuntimeException("The graph contains cycles.");
                            }
                            else {
                                stack.push(item);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public Character[] depthWalk() {
        Set<Character> visited = new TreeSet<>();
        Set<Character> marked = new TreeSet<>();
        Character[] result = new Character[graph.size()];
        int count = 0;
        Iterator<Character> keyIter = graph.keySet().iterator();
        while (count < graph.keySet().size()) {
            char curRoot = keyIter.next();
            if (visited.contains(curRoot)) {
                continue;
            }
            Stack<Character> stack = new Stack<>();
            stack.push(curRoot);
            while (!stack.empty()) {
                char cur = stack.peek();
                if (marked.contains(cur)) {
                    marked.remove(cur);
                    visited.add(cur);
                    result[count] = cur;
                    count++;
                    stack.pop();
                }
                else {
                    marked.add(cur);
                    LinkedList<Character> next = graph.get(cur);
                    for (char item : next) {
                        if (!visited.contains(item) && !marked.contains(item)) {
                            stack.push(item);
                        }
                    }
                }
            }
        }
        return result;
    }

    public int size() {
        return graph.size();
    }

    public DomGraph reversed() {
        DomGraph dst = new DomGraph("");
        for (Character c : graph.keySet()) {
            LinkedList<Character> sons = graph.get(c);
            for (Character son : sons) {
                dst.link(son, c);
            }
        }
        return dst;
    }

    public Character[][] findComponents() {
        Character[] route = depthWalk();
        DomGraph rev = reversed();

        Set<Character> visited = new TreeSet<>();
        Set<Character> marked = new TreeSet<>();
        ArrayList<Character> component = new ArrayList<>();
        ArrayList<Character[]> components = new ArrayList<>();
        int count = 0;
        int routeI = route.length - 1;
        while (count < rev.size()) {
            char curRoot = route[routeI];
            routeI--;
            if (visited.contains(curRoot)) {
                continue;
            }
            component.clear();
            Stack<Character> stack = new Stack<>();
            stack.push(curRoot);
            while (!stack.empty()) {
                char cur = stack.peek();
                if (marked.contains(cur)) {
                    marked.remove(cur);
                    visited.add(cur);
                    component.add(cur);
                    count++;
                    stack.pop();
                }
                else {
                    marked.add(cur);
                    LinkedList<Character> next = rev.graph.get(cur);
                    for (char item : next) {
                        if (!visited.contains(item) && !marked.contains(item)) {
                            stack.push(item);
                        }
                    }
                }
            }
            components.add(component.toArray(new Character[0]));
        }
        return components.toArray(new Character[0][]);
    }
}
