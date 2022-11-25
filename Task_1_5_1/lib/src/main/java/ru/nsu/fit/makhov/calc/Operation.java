package ru.nsu.fit.makhov.calc;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which annotate operation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Operation {

  /**
   * Name of the operation.
   *
   * @return name.
   */
  String name();

  /**
   * Count of arguments.
   *
   * @return number
   */
  int numOfArgs();

}
