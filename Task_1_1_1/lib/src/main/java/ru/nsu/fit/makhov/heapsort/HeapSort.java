package ru.nsu.fit.makhov.heapsort;

public class HeapSort {

  private HeapSort() {}

  private static int add(int[] heap, int size, int x) {
    heap[size] = x;
    size++;
    Utils.siftUp(heap, size - 1);
    return size;
  }

  private static int extractMin(int[] heap, int size) {
    int res = heap[0];
    Utils.swap(heap, 0, size - 1);
    size--;
    Utils.siftDown(heap, size, 0);
    return res;
  }

  public static void sort(int[] arr) {
    int[] heap = new int[arr.length];
    int size = 0;
    for (int x : arr) {
      size = add(heap, size, x);
    }
    for (int i = 0; i < arr.length; i++) {
      arr[i] = extractMin(heap, size--);
    }
  }
}
