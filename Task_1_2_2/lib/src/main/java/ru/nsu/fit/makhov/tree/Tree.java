package ru.nsu.fit.makhov.tree;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Tree<T> implements Iterable<T> {

  private final Comparator<T> comparator;
  private Node<T> root = null;
  private static final boolean RIGHT = true;
  private static final boolean LEFT = false;
  private Searches search = Searches.BFS;

  private boolean treeChanged = false;

  public Tree(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  @SafeVarargs
  public Tree(Comparator<T> comparator, T... values) {
    this.comparator = comparator;
    for (T value : values) {
      add(value);
    }
  }

  @SafeVarargs
  public Tree(Comparator<T> comparator, Searches search, T... values) {
    this.comparator = comparator;
    this.search = search;
    for (T value : values) {
      add(value);
    }
  }

  public Tree(Comparator<T> comparator, Searches search) {
    this.comparator = comparator;
    this.search = search;
  }

  public void setSearch(Searches search) {
    this.search = search;
  }

  public List<T> toList(){
    return this.stream().collect(Collectors.toList());
  }

  public void add(T value) {
    treeChanged = true;
    Node<T> newNode = new Node<>(value);
    if (root == null) {
      root = newNode;
      return;
    }
    Node<T> currentNode = root;
    while (currentNode != null) {
      Node<T> parentNode = currentNode;
      if (comparator.compare(currentNode.value, value) > 0) {
        currentNode = currentNode.left;
        if (currentNode == null) {
          parentNode.left = newNode;
          newNode.parent = parentNode;
          newNode.whichSon = LEFT;
        }
      } else if (comparator.compare(currentNode.value, value) < 0) {
        currentNode = currentNode.right;
        if (currentNode == null) {
          parentNode.right = newNode;
          newNode.parent = parentNode;
          newNode.whichSon = RIGHT;
        }
      } else {
        throw new RuntimeException("This value is already exist");
      }
    }
  }

  private Node<T> findNode(T value) throws NoSuchElementException{
    if (root == null) {
      throw new NoSuchElementException("This value is not exist");
    }
    Node<T> currentNode = root;
    while (comparator.compare(currentNode.value, value) != 0) {
      if (comparator.compare(currentNode.value, value) > 0) {
        currentNode = currentNode.left;
      } else if (comparator.compare(currentNode.value, value) < 0) {
        currentNode = currentNode.right;
      }
      if (currentNode == null) {
        throw new NoSuchElementException("This value is not exist");
      }
    }
    return currentNode;
  }

  private void delete(Node<T> delNode) {
    if (delNode.left == null && delNode.right == null) {
      if (delNode == root) {
        root = null;
      } else if (delNode.whichSon == LEFT) {
        delNode.parent.left = null;
      } else {
        delNode.parent.right = null;
      }
    } else if (delNode.right == null) {
      if (delNode == root) {
        root = delNode.left;
      } else if (delNode.whichSon == LEFT) {
        delNode.parent.left = delNode.left;
      } else {
        delNode.parent.right = delNode.left;
      }
    } else if (delNode.left == null) {
      if (delNode == root) {
        root = delNode.right;
      } else if (delNode.whichSon == LEFT) {
        delNode.parent.left = delNode.right;
      } else {
        delNode.parent.right = delNode.right;
      }
    } else {
      Node<T> successorNode = findSuccessor(delNode);
      delNode.value = successorNode.value;
      delete(successorNode);
    }
  }

  public void remove(T value) {
    treeChanged = true;
    Node<T> delNode = findNode(value);
    delete(delNode);
  }

  private Node<T> findSuccessor(Node<T> node) {
    Node<T> successorNode = node.right;
    while (successorNode.left != null) {
      successorNode = successorNode.left;
    }
    return successorNode;
  }

  @Override
  public Iterator<T> iterator() {
    treeChanged = false;
    if (search == Searches.BFS) {
      return new BFSIterator(new ArrayDeque<>());
    }
    return new DFSIterator(new Stack<>());
  }

  public Stream<T> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  private static class Node<T> {
    private T value;
    private Node<T> parent = null;
    private Node<T> left = null;
    private Node<T> right = null;
    private boolean whichSon = RIGHT;

    public Node(T value) {
      this.value = value;
    }
  }

  private class DFSIterator implements Iterator<T> {

    private final Stack<Node<T>> stack;

    public DFSIterator(Stack<Node<T>> stack) {
      this.stack = stack;
      if (root != null) {
        stack.push(root);
      }
    }

    @Override
    public boolean hasNext() throws ConcurrentModificationException{
      if (treeChanged){
        throw new ConcurrentModificationException();
      }
      return !stack.isEmpty();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Node<T> currentNode = stack.pop();
      if (currentNode.left != null) {
        stack.push(currentNode.left);
      }
      if (currentNode.right != null) {
        stack.push(currentNode.right);
      }
      return currentNode.value;
    }
  }

  private class BFSIterator implements Iterator<T> {

    private final ArrayDeque<Node<T>> queue;

    public BFSIterator(ArrayDeque<Node<T>> queue) {
      this.queue = queue;
      if (root != null) {
        queue.add(root);
      }
    }

    @Override
    public boolean hasNext() throws ConcurrentModificationException{
      if (treeChanged){
        throw new ConcurrentModificationException();
      }
      return !queue.isEmpty();
    }

    @Override
    public T next() throws NoSuchElementException{
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Node<T> currentNode = queue.remove();
      if (currentNode.left != null) {
        queue.add(currentNode.left);
      }
      if (currentNode.right != null) {
        queue.add(currentNode.right);
      }
      return currentNode.value;
    }
  }

  public enum Searches {
    DFS,
    BFS
  }
}
