package ru.nsu.fit.makhov.substring;

import java.io.StringReader;
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
    substringFinder = new SubstringFinder(5);
  }

  @Test
  public void substringFinderOneCharTest() {
    Assertions.assertEquals(
        Collections.emptyList(),
        substringFinder.findSubstring(fileName, "'"));
    Assertions.assertEquals(Arrays.asList(0, 24, 49, 87, 112, 136),
        substringFinder.findSubstring(fileName, "N"));
    Assertions.assertEquals(
        Arrays.asList(19, 21, 42, 62, 68, 85, 106, 164, 170),
        substringFinder.findSubstring(fileName, "u"));
    Assertions.assertEquals(
        Arrays.asList(23, 48, 86, 111, 135),
        substringFinder.findSubstring(fileName, "\n"));
  }

  @Test
  public void substringFinderWordTest() {
    Assertions.assertEquals(
        Arrays.asList(0, 24, 49, 87, 112, 136),
        substringFinder.findSubstring(fileName, "Never gonna"));
    Assertions.assertEquals(
        Arrays.asList(17, 40, 83, 104, 168),
        substringFinder.findSubstring(fileName, "you"));
    Assertions.assertEquals(
        Collections.emptyList(), substringFinder.findSubstring(fileName,
            "We're no strangers to love"));
  }

  @Test
  public void substringFinderReaderTest() {
    StringReader reader = new StringReader("Test string");
    Assertions.assertEquals(Arrays.asList(2, 5), substringFinder.findSubstring(reader, "s"));
  }
}
