package ru.nsu.fit.makhov.heapsort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTests {
  @Test
  void testHeapsort1() {
    int[] arr = {5, 4, 3, 2, 1};
    int[] sortedArr = {1, 2, 3, 4, 5};
    HeapSort.sort(arr);
    assertArrayEquals(sortedArr, arr, "Not equal");
  }

  @Test
  void testHeapsort2() {
    int[] arr = {-1, 2, 1, 2, 1, 2};
    int[] sortedArr = {-1, 1, 1, 2, 2, 2};
    HeapSort.sort(arr);
    assertArrayEquals(sortedArr, arr, "Not equal");
  }

  @Test
  void testHeapsortEmpty() {
    int[] arr = {};
    int[] sortedArr = {};
    HeapSort.sort(arr);
    assertArrayEquals(sortedArr, arr, "Not equal");
  }

  @Test
  void testHeapsortEqual() {
    int[] arr = {1, 1, 1, 1, 1, 1, 1};
    int[] sortedArr = {1, 1, 1, 1, 1, 1, 1};
    HeapSort.sort(arr);
    assertArrayEquals(sortedArr, arr, "Not equal");
  }

  @Test
  void testHeapsortOneElement() {
    int[] arr = {0};
    int[] sortedArr = {0};
    HeapSort.sort(arr);
    assertArrayEquals(sortedArr, arr, "Not equal");
  }
}
