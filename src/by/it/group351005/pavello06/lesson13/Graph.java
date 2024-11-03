package by.it.group351005.pavello06.lesson13;

import java.util.List;

public class Graph<T> {
    private class Vertex<T> {
        public T value;
        public List<Vertex<T>> neighboringVertices;

        public Vertex(T value) {
            this.value = value;
        }
    }
}
