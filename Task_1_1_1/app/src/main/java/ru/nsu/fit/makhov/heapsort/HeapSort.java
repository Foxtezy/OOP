package ru.nsu.fit.makhov;

public class HeapSort {

  private final int[] arr;
  private int[] heap;
  private int size = 0;

  public HeapSort(int[] arr) {
    this.arr = arr;
  }

  public int[] getArr() {
    return arr;
  }

  private void swap(int i, int j) {
    int c = heap[i];
    heap[i] = heap[j];
    heap[j] = c;
  }

  private void siftUp(int v) {
    if (v < 0) return;
    int father = (v - 1) / 2;
    if (heap[father] > heap[v]) {
      swap(father, v);
      siftUp(father);
    }
  }

  private void siftDown(int v) {
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
      swap(minSon, v);
      siftDown(minSon);
    }
  }

  private void add(int x) {
    heap[size] = x;
    size++;
    siftUp(size - 1);
  }

  private int extractMin() {
    int res = heap[0];
    swap(0, size - 1);
    size--;
    siftDown(0);
    return res;
  }

  public void heapSort() {
    this.heap = new int[arr.length];
    for (int x : arr) {
      add(x);
    }
    for (int i = 0; i < arr.length; i++) {
      arr[i] = extractMin();
    }
  }
}
