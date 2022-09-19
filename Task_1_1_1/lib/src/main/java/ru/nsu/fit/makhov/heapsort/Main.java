package ru.nsu.fit.makhov.heapsort;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    int[] arr = {6, 3, 4, -2, 1};
    HeapSort.sort(arr);
    System.out.println(Arrays.toString(arr));
  }
}
