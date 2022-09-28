package ru.nsu.fit.makhov.tree;

public class Node<T> {
  private T value;
  private Node<T> left = null;
  private Node<T> right = null;


  public Node(T value){
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public Node<T> getLeft() {
    return left;
  }

  public Node<T> getRight() {
    return right;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public void setLeft(Node<T> left) {
    this.left = left;
  }

  public void setRight(Node<T> right) {
    this.right = right;
  }
}
