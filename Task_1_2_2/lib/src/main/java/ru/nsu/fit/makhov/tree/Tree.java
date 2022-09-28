package ru.nsu.fit.makhov.tree;

import java.util.Comparator;

public class Tree<T> {

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

  private static class Node<E> {
    private E value;

    private Node<E> parent = null;
    private Node<E> left = null;
    private Node<E> right = null;
    private boolean whichSon = RIGHT;

    public Node(E value) {
      this.value = value;
    }
  }
}
