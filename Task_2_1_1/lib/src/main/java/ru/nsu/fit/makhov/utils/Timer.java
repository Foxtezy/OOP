package ru.nsu.fit.makhov.utils;

public final class Timer {

  private Timer() {
  }

  public static long startTimer() {
    return System.nanoTime();
  }

  public static long stopTimer(long startTime) {
    return System.nanoTime() - startTime;
  }

}
