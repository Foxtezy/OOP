package ru.nsu.fit.makhov.substring;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple substring finder in a file.
 */
public class SubstringFinder {

  private static final boolean IN_SUBSTRING = true;

  private static final boolean NOT_SUBSTRING = false;

  private static List<Integer> listSubstr;

  private static char[] substring;

  private static int countOfWritten = 0;

  private static boolean stat = NOT_SUBSTRING;
  private static int subPointer = 0;
  private static int numSubstring = 0;

  private SubstringFinder() {
  }

  /**
   * Returns the indices of the beginning of a substring in a string.
   *
   * @param substring string to find
   * @return List of beginning substrings
   */
  public static List<Integer> findSubstring(String fileName, String substring) {
    SubstringFinder.listSubstr = new ArrayList<>();
    SubstringFinder.substring = substring.toCharArray();
    countOfWritten = 0;
    stat = NOT_SUBSTRING;
    subPointer = 0;
    numSubstring = 0;
    char[] buff = new char[10];
    try (Reader reader = new FileReader(fileName)) {
      while (reader.ready()) {
        int bufSize = reader.read(buff);
        findSubstringInBuf(buff, bufSize);
        countOfWritten += bufSize;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return listSubstr;
  }

  private static void findSubstringInBuf(char[] buf, int bufSize) {
    for (int i = 0; i < bufSize; i++) {
      if (buf[i] != substring[subPointer]) {
        subPointer = 0;
        stat = NOT_SUBSTRING;
        continue;
      }
      if (stat == IN_SUBSTRING && subPointer < substring.length) {
        subPointer++;
      }
      if (stat == NOT_SUBSTRING && (buf[i] == substring[subPointer])) {
        numSubstring = i + countOfWritten;
        subPointer++;
        stat = IN_SUBSTRING;
      }
      if (stat == IN_SUBSTRING && (subPointer == substring.length)) {
        subPointer = 0;
        listSubstr.add(numSubstring);
      }
    }
  }
}
