package ru.nsu.fit.makhov.substring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simple substring finder in a file.
 */
public class SubstringFinder {

  private static final boolean IN_SUBSTRING = true;

  private static final boolean NOT_SUBSTRING = false;

  /**
   * Returns the indices of the beginning of a substring in a string.
   *
   * @param substring string to find
   * @return List of beginning substrings
   */
  public List<Integer> findSubstring(String fileName, String substring)
      throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(fileName));
    List<Integer> listSubstr = new ArrayList<>();
    int pointer = 0;
    int numSubstring = 0;
    boolean stat = NOT_SUBSTRING;
    scanner.useDelimiter("");
    for (int i = 0; scanner.hasNext(); i++) {
      char currFile = scanner.next().charAt(0);
      char currStr = substring.charAt(pointer);
      if (currFile != currStr) {
        pointer = 0;
        stat = NOT_SUBSTRING;
        continue;
      }
      if (stat == IN_SUBSTRING && pointer < substring.length()) {
        pointer++;
      }
      if (stat == NOT_SUBSTRING && (currFile == currStr)) {
        numSubstring = i;
        pointer++;
        stat = IN_SUBSTRING;
      }
      if (stat == IN_SUBSTRING && (pointer == substring.length())) {
        pointer = 0;
        listSubstr.add(numSubstring);
      }
    }
    scanner.close();
    return listSubstr;
  }
}
