package ru.nsu.fit.makhov.notebook.operations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that defines
 * classes of operations.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Operation {

  /**
   * Name of operation.
   */
  String name();

  /**
   * Min count of arguments.
   */
  int numOfArgs();

  /**
   * Is vararg operation.
   */
  boolean varArg() default false;

}
