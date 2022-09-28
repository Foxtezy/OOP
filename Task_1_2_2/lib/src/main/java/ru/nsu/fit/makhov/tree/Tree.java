package ru.nsu.fit.makhov.tree;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Tree<T> implements Iterable<T> {

  private final Comparator<T> comparator;
  private Node<T> root = null;
  private static final boolean RIGHT = true;
  private static final boolean LEFT = false;

  public Tree(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  public void add(T value) {
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

  private Node<T> findNode(T value) {
    if (root == null) {
      throw new RuntimeException("This value is not exist");
    }
    Node<T> currentNode = root;
    while (comparator.compare(currentNode.value, value) != 0) {
      if (comparator.compare(currentNode.value, value) > 0) {
        currentNode = currentNode.left;
      } else if (comparator.compare(currentNode.value, value) < 0) {
        currentNode = currentNode.right;
      }
      if (currentNode == null) {
        throw new RuntimeException("This value is not exist");
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
    // private boolean visited = false;

    public Node(T value) {
      this.value = value;
    }
  }

  private class DFSIterator implements Iterator<T> {

    private final Stack<Node<T>> stack;

    private boolean isNextLaunched = false;

    public DFSIterator(Stack<Node<T>> stack) {
      this.stack = stack;
    }

    @Override
    public boolean hasNext() {
      if (root == null || (stack.isEmpty() && isNextLaunched)) {
        return false;
      }
      return true;
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      isNextLaunched = true;
      if (stack.isEmpty()) {
        stack.push(root);
      }
      Node<T> currentNode = stack.pop();
      if (currentNode == null) {
        currentNode = root;
      }
      if (currentNode.left != null) {
        stack.push(currentNode.left);
      }
      if (currentNode.right != null) {
        stack.push(currentNode.right);
      }
      return currentNode.value;
    }
  }
}
