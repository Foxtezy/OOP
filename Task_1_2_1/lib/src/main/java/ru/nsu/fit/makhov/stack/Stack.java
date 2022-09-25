package ru.nsu.fit.makhov.stack;

import java.util.Arrays;

/**
 * The <code>Stack</code> class represents a last-in-first-out (LIFO) stack of objects.
 *
 * @param <T>
 */
public class Stack<T> {
  private T[] data;

  private int size = 0;

  /** Creates an empty Stack. */
  public Stack() {
    data = (T[]) new Object[1];
  }

  /** Constructs a Stack containing the elements of the specified array. */
  @SafeVarargs
  public Stack(T... content) {
    data = Arrays.copyOf(content, content.length);
    size = content.length;
  }

  /**
   * Returns an array containing all of the elements in this Stack in proper sequence (from first to
   * last element).
   *
   * @return array of T
   */
  public T[] toArray() {
    return Arrays.copyOf(data, size);
  }

  private void grow(int size) {
    this.size += size;
    if (data.length <= this.size) {
      data = Arrays.copyOf(data, data.length * 2);
    }
  }

  private void trim(int size) throws ArrayIndexOutOfBoundsException {
    if (this.size - size < 0) throw new ArrayIndexOutOfBoundsException("Not enough value");
    this.size -= size;
  }

  /** Push item */
  public void push(T item) {
    grow(1);
    data[size - 1] = item;
  }

  /** Push array of items */
  @SafeVarargs
  public final void pushArray(T... items) {
    grow(items.length);
    System.arraycopy(items, 0, data, size - items.length, items.length);
  }

  /** Push stack of items */
  public final void pushStack(Stack<T> addStack) {
    grow(addStack.count());
    System.arraycopy(addStack.toArray(), 0, data, size - addStack.count(), addStack.count());
  }

  /** Pop item */
  public T pop() throws ArrayIndexOutOfBoundsException {
    trim(1);
    return data[size];
  }

  /** Pop array of items */
  public T[] popArray(int popSize) throws ArrayIndexOutOfBoundsException {
    T[] popArray = (T[]) new Object[popSize];
    trim(popSize);
    System.arraycopy(data, size, popArray, 0, popSize);
    return popArray;
  }

  /** Pop stack of items */
  public Stack<T> popStack(int popSize) throws ArrayIndexOutOfBoundsException {
    return new Stack<>(popArray(popSize));
  }

  /**
   * @return size of stack
   */
  public int count() {
    return size;
  }
}
