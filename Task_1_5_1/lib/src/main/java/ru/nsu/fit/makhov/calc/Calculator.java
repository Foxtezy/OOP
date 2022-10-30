package ru.nsu.fit.makhov.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Main class of calculator.
 */
public class Calculator {

  private static final ExpirationListener expirationListener = new ExpirationListener();

  /**
   * Main.
   *
   * @param args args.
   */
  public static void main(String[] args) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String expr = reader.readLine();
      System.out.println(expirationListener.calcExpiration(expr));
    } catch (NoSuchOperationException e) {
      System.err.println("No such operation: " + e.getMessage());
    } catch (ExpirationException e) {
      System.err.println("Expiration exception");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
