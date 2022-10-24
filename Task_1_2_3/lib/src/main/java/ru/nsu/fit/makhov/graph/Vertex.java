package ru.nsu.fit.makhov.graph;

import java.util.Set;

/**
 * Class which represents vertex in weighted, oriented graph.
 *
 * @param name   name of the vertex.
 * @param adjSet set of adjacency vertexes.
 * @param <T>    type of names of vertexes.
 */
public record Vertex<T>(T name, Set<T> adjSet) {

}
