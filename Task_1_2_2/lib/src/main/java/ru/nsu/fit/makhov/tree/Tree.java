package ru.nsu.fit.makhov.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The <code>Tree</code> class represents tree of objects.
 *
 * @param <T> type of objects
 */
public class Tree<T> implements Iterable<Tree.Node<T>> {

  private Node<T> root = null;
  private Search search = Search.BFS;

  private int changeCounter = 0;

  public Tree() {
  }

  public void setSearch(Search search) {
    this.search = search;
  }

  public List<T> toList() {
    return this.stream().map(Node::getValue).collect(Collectors.toList());
  }

  /**
   * Add new node in tree after root.
   *
   * @param value value of new node
   * @return new node
   */
  public Node<T> add(T value) {
    changeCounter++;
    Node<T> newNode = new Node<>(value);
    if (root == null) {
      root = newNode;
    } else {
      root.sons.add(newNode);
      newNode.parent = root;
    }
    return newNode;
  }

  /**
   * Add new node in tree after parentNode.
   *
   * @param parentNode parent of new node
   * @param value      value of new node
   * @return new node
   */
  public Node<T> add(Node<T> parentNode, T value) {
    changeCounter++;
    Node<T> newNode = new Node<>(value);
    parentNode.sons.add(newNode);
    newNode.parent = parentNode;
    return newNode;
  }

  /**
   * Remove node (sons of the node copy to parent node).
   *
   * @param delNode delete node
   */
  public void remove(Node<T> delNode) {
    changeCounter++;
    if (delNode == root) {
      root = null;
    } else {
      delNode.parent.sons.remove(delNode);
      delNode.parent.sons.addAll(delNode.sons);
    }
  }

  @Override
  public Iterator<Node<T>> iterator() {
    if (search == Search.BFS) {
      return new BreadthFirstSearchIterator(new ArrayDeque<>(), changeCounter);
    }
    return new DeepFirstSearchIterator(new ArrayDeque<>(), changeCounter);
  }

  public Stream<Node<T>> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  /**
   * The <code>Node</code> class represents node in <code>Tree</code>.
   *
   * @param <T> type of object
   */
  public static class Node<T> {

    private final T value;
    private Node<T> parent;
    private final List<Node<T>> sons = new ArrayList<>();

    public T getValue() {
      return value;
    }

    public List<Node<T>> getSons() {
      return sons;
    }

    public Node(T value) {
      this.value = value;
    }
  }

  private class DeepFirstSearchIterator implements Iterator<Node<T>> {

    private final Deque<Node<T>> stack;

    private final int countOfChanges;

    public DeepFirstSearchIterator(Deque<Node<T>> stack, int countOfChanges) {
      this.stack = stack;
      this.countOfChanges = countOfChanges;
      if (root != null) {
        stack.push(root);
      }
    }

    @Override
    public boolean hasNext() throws ConcurrentModificationException {
      if (countOfChanges != changeCounter) {
        throw new ConcurrentModificationException();
      }
      return !stack.isEmpty();
    }

    @Override
    public Node<T> next() throws ConcurrentModificationException, NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Node<T> currentNode = stack.pop();
      currentNode.sons.forEach(stack::push);
      return currentNode;
    }
  }

  private class BreadthFirstSearchIterator implements Iterator<Node<T>> {

    private final Deque<Node<T>> queue;

    private final int countOfChanges;

    public BreadthFirstSearchIterator(ArrayDeque<Node<T>> queue, int countOfChanges) {
      this.queue = queue;
      this.countOfChanges = countOfChanges;
      if (root != null) {
        queue.add(root);
      }
    }

    @Override
    public boolean hasNext() throws ConcurrentModificationException {
      if (countOfChanges != changeCounter) {
        throw new ConcurrentModificationException();
      }
      return !queue.isEmpty();
    }

    @Override
    public Node<T> next() throws ConcurrentModificationException, NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Node<T> currentNode = queue.remove();
      queue.addAll(currentNode.sons);
      return currentNode;
    }
  }
}
