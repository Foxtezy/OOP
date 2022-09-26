package ru.nsu.fit.makhov.stack;

import java.util.Arrays;

/**
 * The <code>Stack</code> class represents a last-in-first-out (LIFO) stack of objects.
 *
 * @param <T> type of objects.
 */
public class Stack<T> {
  private T[] data;

  private int size = 0;

  /** Creates an empty Stack. */
  public Stack() {
    data = (T[]) new Object[1];
  }

  /**
   * Constructs a stack containing the elements of the specified array.
   *
   * @param content array of T.
   */
  @SafeVarargs
  public Stack(T... content) {
    data = Arrays.copyOf(content, content.length);
    size = content.length;
  }

  /**
   * Returns an array containing all of the elements in this stack in proper sequence (from first to
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
    if (this.size - size < 0) {
      throw new ArrayIndexOutOfBoundsException("Not enough value");
    }
    this.size -= size;
  }

  /**
   * Push item.
   *
   * @param item object of T
   */
  public void push(T item) {
    grow(1);
    data[size - 1] = item;
  }

  /**
   * Push array of items.
   *
   * @param items array of T
   */
  @SafeVarargs
  public final void pushArray(T... items) {
    grow(items.length);
    System.arraycopy(items, 0, data, size - items.length, items.length);
  }

  /**
   * Push stack of items.
   *
   * @param itemsStack stack of T
   */
  public final void pushStack(Stack<T> itemsStack) {
    grow(itemsStack.count());
    System.arraycopy(itemsStack.toArray(), 0, data, size - itemsStack.count(), itemsStack.count());
  }

  /**
   * Pop item.
   *
   * @return object of T
   * @throws ArrayIndexOutOfBoundsException if pop empty stack
   */
  public T pop() throws ArrayIndexOutOfBoundsException {
    trim(1);
    return data[size];
  }

  /**
   * Pop array of items.
   *
   * @param popSize size of pop array
   * @return array of items
   * @throws ArrayIndexOutOfBoundsException if popSize greater than size of stack
   */
  public T[] popArray(int popSize) throws ArrayIndexOutOfBoundsException {
    T[] popArray = (T[]) new Object[popSize];
    trim(popSize);
    System.arraycopy(data, size, popArray, 0, popSize);
    return popArray;
  }

  /**
   * Pop stack of items.
   *
   * @param popSize size of pop stack
   * @return stack of items
   * @throws ArrayIndexOutOfBoundsException if popSize greater than size of stack
   */
  public Stack<T> popStack(int popSize) throws ArrayIndexOutOfBoundsException {
    return new Stack<>(popArray(popSize));
  }

  /**
   * Returns size of stack.
   *
   * @return size of stack
   */
  public int count() {
    return size;
  }
}
