package ru.nsu.fit.makhov.stack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StackTests {

  @Test
  public void stackTestArrayPush() {
    Integer[] assertArray = {1, 2, 7, 4, 8};
    Stack<Integer> stack = new Stack<>(1);
    stack.push(2);
    stack.push(7);
    stack.pushArray(4, 8);
    assertArrayEquals(assertArray, stack.toArray());
    assertEquals(5, stack.count());
  }

  @Test
  public void stackTestArrayPop() {
    Integer[] assertArray = {2, 7, 4};
    Stack<Integer> stack = new Stack<>(1, 2, 7, 4, 8);
    assertEquals(8, stack.pop());
    assertArrayEquals(assertArray, stack.popArray(3));
    assertEquals(1, stack.pop());
    assertEquals(0, stack.count());
  }

  @Test
  public void stackTestStackPush() {
    Integer[] assertArray = {1, 2, 7, 4, 8};
    Stack<Integer> stack = new Stack<>(1);
    stack.push(2);
    stack.push(7);
    stack.pushStack(new Stack<>(4, 8));
    assertArrayEquals(assertArray, stack.toArray());
    assertEquals(5, stack.count());
  }

  @Test
  public void stackTestStackPop() {
    Integer[] assertArray = {2, 7, 4};
    Stack<Integer> stack = new Stack<>(1, 2, 7, 4, 8);
    assertEquals(8, stack.pop());
    assertArrayEquals(assertArray, stack.popStack(3).toArray());
    assertEquals(1, stack.pop());
    assertEquals(0, stack.count());
  }

  @Test
  public void stackTestEmpty() {
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    assertThrows(RuntimeException.class, () -> stack.popStack(2));
  }
}
