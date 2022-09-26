package ru.nsu.fit.makhov.heapsort;
//

/** Class which contains heap sort method. */
public class HeapSort {

  private static int[] heap;

  private static int size = 0;

  private HeapSort() {}

  private static void swap(int i, int j) {
    int c = heap[i];
    heap[i] = heap[j];
    heap[j] = c;
  }

  private static void siftUp(int v) {
    if (v < 0) {
      return;
    }
    int father = (v - 1) / 2;
    if (heap[father] > heap[v]) {
      swap(father, v);
      siftUp(father);
    }
  }

  private static void siftDown(int v) {
    int l = 2 * v + 1;
    int r = 2 * v + 2;
    if (l >= size) {
      return;
    }
    int minSon;
    if (r == size || (heap[l] < heap[r])) {
      minSon = l;
    } else {
      minSon = r;
    }
    if (heap[v] > heap[minSon]) {
      swap(minSon, v);
      siftDown(minSon);
    }
  }

  private static void add(int x) {
    heap[size] = x;
    size++;
    siftUp(size - 1);
  }

  private static int extractMin() {
    size--;
    int res = heap[0];
    swap(0, size);
    siftDown(0);
    return res;
  }

  /**
   * Sorts the specified array into ascending numerical order.
   *
   * @param arr - the not null array of integers
   */
  public static void sort(int[] arr) {
    heap = new int[arr.length];
    for (int x : arr) {
      add(x);
    }
    for (int i = 0; i < arr.length; i++) {
      arr[i] = extractMin();
    }
  }
}
