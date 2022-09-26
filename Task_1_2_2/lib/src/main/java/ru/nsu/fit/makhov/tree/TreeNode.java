package ru.nsu.fit.makhov.tree;

public class TreeNode<T> {
  private T value;
  private TreeNode<T> left = null;
  private TreeNode<T> right = null;


  public TreeNode(T value){
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public TreeNode<T> getLeft() {
    return left;
  }

  public TreeNode<T> getRight() {
    return right;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public void setLeft(TreeNode<T> left) {
    this.left = left;
  }

  public void setRight(TreeNode<T> right) {
    this.right = right;
  }
}
