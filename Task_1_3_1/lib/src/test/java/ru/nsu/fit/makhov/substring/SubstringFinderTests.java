package ru.nsu.fit.makhov.substring;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class SubstringFinderTests {

  private static String fileName;
  private static SubstringFinder substringFinder;

  @BeforeAll
  public static void init() {
    fileName = "src/test/resources/input.txt";
    substringFinder = new SubstringFinder();
  }

  @Test
  public void substringFinderOneCharTest() {
    Assertions.assertEquals(
        Collections.emptyList(),
        substringFinder.findSubstring(fileName, "'"));
    Assertions.assertEquals(Arrays.asList(0, 25, 51, 90, 116, 141),
        substringFinder.findSubstring(fileName, "N"));
    Assertions.assertEquals(
        Arrays.asList(19, 21, 43, 64, 70, 87, 109, 169, 175),
        substringFinder.findSubstring(fileName, "u"));
    Assertions.assertEquals(
        Arrays.asList(24, 50, 89, 115, 140),
        substringFinder.findSubstring(fileName, "\n"));
  }

  @Test
  public void substringFinderWordTest() {
    Assertions.assertEquals(
        Arrays.asList(0, 25, 51, 90, 116, 141),
        substringFinder.findSubstring(fileName, "Never gonna"));
    Assertions.assertEquals(
        Arrays.asList(17, 41, 85, 107, 173),
        substringFinder.findSubstring(fileName, "you"));
    Assertions.assertEquals(
        Collections.emptyList(), substringFinder.findSubstring(fileName,
            "We're no strangers to love"));
  }
}
