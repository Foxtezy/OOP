package ru.nsu.fit.makhov.pizzeria.main;

/**
 * Config with name of jsons.
 */
public final class PizzeriaConfig {

  public static final String BAKERS_JSON_NAME = PizzeriaConfig.class.getClassLoader()
      .getResource("bakers.json").getFile();

  public static final String DRIVERS_JSON_NAME = PizzeriaConfig.class.getClassLoader()
      .getResource("drivers.json").getFile();

  private PizzeriaConfig() {
    throw new UnsupportedOperationException();
  }

}
