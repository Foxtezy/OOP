package ru.nsu.fit.makhov.stack;

import java.util.Arrays;

public class Stack<T> {
  private T[] data;

  private int size = 0;

  public Stack() {
    data = (T[]) new Object[1];
  }

  @SafeVarargs
  public Stack(T... content) {
    data = Arrays.copyOf(content, content.length);
    size = content.length;
  }

  public T[] toArray() {
    return Arrays.copyOf(data, size);
  }

  private void grow(int size) {
    this.size += size;
    if (data.length <= this.size) {
      data = Arrays.copyOf(data, data.length * 2);
    }
  }

  private void trim(int size) throws RuntimeException {
    if (this.size - size < 0) throw new RuntimeException("Not enough value");
    this.size -= size;
  }

  public void push(T item) {
    grow(1);
    data[size - 1] = item;
  }

  @SafeVarargs
  public final void pushArray(T... items) {
    grow(items.length);
    System.arraycopy(items, 0, data, size - items.length, items.length);
  }

  public final void pushStack(Stack<T> addStack) {
    grow(addStack.count());
    System.arraycopy(addStack.toArray(), 0, data, size - addStack.count(), addStack.count());
  }

  public T pop() throws RuntimeException {
    trim(1);
    return data[size];
  }

  public T[] popArray(int popSize) throws RuntimeException {
    T[] popArray = (T[]) new Object[popSize];
    trim(popSize);
    System.arraycopy(data, size, popArray, 0, popSize);
    return popArray;
  }

  public Stack<T> popStack(int popSize) throws RuntimeException {
    return new Stack<>(popArray(popSize));
  }

  public int count() {
    return size;
  }
}
