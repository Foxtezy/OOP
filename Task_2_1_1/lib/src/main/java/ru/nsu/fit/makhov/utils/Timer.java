package ru.nsu.fit.makhov.utils;

/**
 * Timer with static methods start and stop the timer.
 */
public final class Timer {

  private Timer() {
  }

  public static long startTimer() {
    return System.currentTimeMillis();
  }

  public static long stopTimer(long startTime) {
    return System.currentTimeMillis() - startTime;
  }

}
