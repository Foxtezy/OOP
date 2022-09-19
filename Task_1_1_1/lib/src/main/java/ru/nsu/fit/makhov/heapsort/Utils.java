package ru.nsu.fit.makhov.heapsort;

public class Utils {

  private Utils() {}

  public static void swap(int[] heap, int i, int j) {
    int c = heap[i];
    heap[i] = heap[j];
    heap[j] = c;
  }

  public static void siftUp(int[] heap, int v) {
    if (v < 0) return;
    int father = (v - 1) / 2;
    if (heap[father] > heap[v]) {
      swap(heap, father, v);
      siftUp(heap, father);
    }
  }

  public static void siftDown(int[] heap, int size, int v) {
    int l = 2 * v + 1;
    int r = 2 * v + 2;
    if (l >= size) return;
    int minSon;
    if (r == size || (heap[l] < heap[r])) {
      minSon = l;
    } else {
      minSon = r;
    }
    if (heap[v] > heap[minSon]) {
      swap(heap, minSon, v);
      siftDown(heap, size, minSon);
    }
  }
}
