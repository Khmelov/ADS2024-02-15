package by.it.group351005.pavello06.lesson13;

import java.util.List;

public class Graph<T> {
    public class Vertex {
        public T value;
        public List<Vertex> neighboringVertices;

        public Vertex(T value) {
            this.value = value;
        }
    }

    public List<Vertex> vertices;

    public void addVertex(T value) {
        vertices.add(new Vertex(value));
    }
}
