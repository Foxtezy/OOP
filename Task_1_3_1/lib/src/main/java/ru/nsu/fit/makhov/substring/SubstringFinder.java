package ru.nsu.fit.makhov.substring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubstringFinder {

  private final Scanner scanner;

  private static final boolean INSUBSTRING = true;

  private static final boolean NOTSUBSTRING = false;

  public SubstringFinder(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    this.scanner = new Scanner(file);
  }

  public List<Integer> findSubstring(String substring) {
    List<Integer> listSubstr = new ArrayList<>();
    int pointer = 0;
    int nSubstring = 0;
    boolean stat = NOTSUBSTRING;
    scanner.useDelimiter("");
    for (int i = 0; scanner.hasNext(); i++) {
      char currFile = scanner.next().charAt(0);
      char currStr = substring.charAt(pointer);
      if (currFile != currStr) {
        pointer = 0;
        stat = NOTSUBSTRING;
        continue;
      }
      if (stat == INSUBSTRING && pointer < substring.length()) {
        pointer++;
      }
      if (stat == NOTSUBSTRING && (currFile == currStr)) {
        nSubstring = i;
        pointer++;
        stat = INSUBSTRING;
      }
      if (stat == INSUBSTRING && (pointer == substring.length())) {
        pointer = 0;
        listSubstr.add(nSubstring);
      }
    }
    return listSubstr;
  }
}
