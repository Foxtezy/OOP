package ru.nsu.fit.makhov.tree;


import java.util.Comparator;
import java.util.Iterator;

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
      if (comparator.compare(currentNode.getValue(), value) > 0) {
        currentNode = currentNode.getLeft();
        if (currentNode == null) {
          parentNode.setLeft(newNode);
        }
      } else if (comparator.compare(currentNode.getValue(), value) < 0) {
        currentNode = currentNode.getRight();
        if (currentNode == null) {
          parentNode.setRight(newNode);
        }
      } else {
        throw new RuntimeException("This value is already exist");
      }
    }
  }

  public void remove(T value) {
    if (root == null) {
      throw new RuntimeException("This value not exist");
    }
    Node<T> currentNode = root;
    boolean whichSon = RIGHT;
    Node<T> parentNode = currentNode;
    while (comparator.compare(currentNode.getValue(), value) != 0) {
      parentNode = currentNode;
      if (comparator.compare(currentNode.getValue(), value) > 0) {
        whichSon = LEFT;
        currentNode = currentNode.getLeft();
      } else if (comparator.compare(currentNode.getValue(), value) < 0) {
        whichSon = RIGHT;
        currentNode = currentNode.getRight();
      }
      if (currentNode == null) {
        throw new RuntimeException("This value not exist");
      }
    }
    if (currentNode.getLeft() == null && currentNode.getRight() == null) {
      if (currentNode == root) {
        root = null;
      } else if (whichSon == LEFT) {
        parentNode.setLeft(null);
      } else {
        parentNode.setRight(null);
      }
    } else if (currentNode.getRight() == null) {
      if (currentNode == root) {
        root = currentNode.getLeft();
      } else if (whichSon == LEFT) {
        parentNode.setLeft(currentNode.getLeft());
      } else {
        parentNode.setRight(currentNode.getLeft());
      }
    } else if (currentNode.getLeft() == null) {
      if (currentNode == root) {
        root = currentNode.getRight();
      } else if (whichSon == LEFT) {
        parentNode.setLeft(currentNode.getRight());
      } else {
        parentNode.setRight(currentNode.getRight());
      }
    } else {
      Node<T> successorNode = findSuccessor(currentNode);
      if (currentNode == root) {
        root = successorNode;
      } else if (whichSon == LEFT) {
        parentNode.setLeft(successorNode);
      } else {
        parentNode.setRight(successorNode);
      }
    }
  }

  private Node<T> findSuccessor(Node<T> node) {
    Node<T> parentNode = node;
    Node<T> successorNode = node;
    for (Node<T> currentNode = node.getRight();
        currentNode != null;
        currentNode = currentNode.getLeft()) {
      parentNode = successorNode;
      successorNode = currentNode;
    }
    if (successorNode != node.getRight()) {
      parentNode.setLeft((successorNode.getRight()));
      successorNode.setLeft(node.getRight());
    }
    return successorNode;
  }

  @Override
  public Iterator<T> iterator() {
    return null;
  }
}
