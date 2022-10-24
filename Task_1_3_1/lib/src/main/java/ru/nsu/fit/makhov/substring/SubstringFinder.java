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

  private List<Integer> listSubstr;

  private char[] substring;

  private int countOfWritten = 0;

  private boolean stat = NOT_SUBSTRING;
  private int subPointer = 0;
  private int numSubstring = 0;

  private int bufSize = 10;

  public SubstringFinder() {
  }

  public SubstringFinder(int bufSize) {
    this.bufSize = bufSize;
  }

  /**
   * Returns the indices of the beginning of a substring in a string.
   *
   * @param fileName  name of file
   * @param substring string to find
   * @return List of beginning substrings
   */
  public List<Integer> findSubstring(String fileName, String substring) {
    try (Reader reader = new FileReader(fileName)) {
      findSubstring(reader, substring);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return listSubstr;
  }

  /**
   * Returns the indices of the beginning of a substring in a string.
   *
   * @param reader    reader
   * @param substring string to find
   * @return List of beginning substrings
   */
  public List<Integer> findSubstring(Reader reader, String substring) {
    listSubstr = new ArrayList<>();
    this.substring = substring.toCharArray();
    countOfWritten = 0;
    stat = NOT_SUBSTRING;
    subPointer = 0;
    numSubstring = 0;
    char[] buff = new char[bufSize];
    try (reader) {
      while (true) {
        int bufSize = reader.read(buff);
        if (bufSize == -1) {
          break;
        }
        findSubstringInBuf(buff, bufSize);
        countOfWritten += bufSize;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return listSubstr;
  }

  private void findSubstringInBuf(char[] buf, int bufSize) {
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
