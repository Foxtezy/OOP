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

  private Scanner scanner;

  private final File file;

  private static final boolean IN_SUBSTRING = true;

  private static final boolean NOT_SUBSTRING = false;

  public SubstringFinder(String fileName) throws FileNotFoundException {
    this.file = new File(fileName);
    this.scanner = new Scanner(file);
  }

  /**
   * Returns the indices of the beginning of a substring in a string.
   *
   * @param substring string to find
   * @return List of beginning substrings
   */
  public List<Integer> findSubstring(String substring) {
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
    try {
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return listSubstr;
  }
}
