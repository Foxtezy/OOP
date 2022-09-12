package ru.nsu.fit.makhov;

import java.util.Arrays;

public class Stack<T> {
  private Object[] data;

  private int size;

  public Stack() {
    data = new Object[1];
  }

  public Stack(T[] content) {
    data = Arrays.copyOf(content, content.length);
  }

  public Object[] toArray() {
    return Arrays.copyOf(data, size);
  }

  private void grow(int size) {
    this.size += size;
    if (data.length <= size) {
      data = Arrays.copyOf(data, data.length * 2);
    }
  }

  public void push(T item) {
    grow(1);
    data[size - 1] = item;
  }

  public void push(T[] items) {
    size += items.length;
    grow(items.length);
    System.arraycopy(items, 0, data, size, items.length);
  }

  public void pushStack(Stack<T> addStack) {
    Object[] array = addStack.toArray();
    push((T[]) array); // TODO: 07.09.2022 unchecked cast
  }

  public T pop() {
    size--;
    return (T) data[size]; // TODO: 07.09.2022 unchecked cast
  }

  public Stack<T> popStack(int stackSize) {
    Object[] popArray = new Object[stackSize];
    System.arraycopy(data, size - stackSize, popArray, 0, stackSize);
    size -= stackSize;
    return new Stack<T>((T[]) popArray); // TODO: 07.09.2022 unchecked cast
  }

  public int length() {
    return size;
  }
}
