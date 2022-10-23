package ru.nsu.fit.makhov.substring;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
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


  /**
   * Returns the indices of the beginning of a substring in a string.
   *
   * @param substring string to find
   * @return List of beginning substrings
   */
  public List<Integer> findSubstring(String fileName, String substring) {
    listSubstr = new ArrayList<>();
    this.substring = substring.toCharArray();
    countOfWritten = 0;
    stat = NOT_SUBSTRING;
    subPointer = 0;
    numSubstring = 0;
    CharBuffer buff = CharBuffer.allocate(100);
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

  public List<Integer> findSubstring(Reader reader, String substring) {
    listSubstr = new ArrayList<>();
    this.substring = substring.toCharArray();
    countOfWritten = 0;
    stat = NOT_SUBSTRING;
    subPointer = 0;
    numSubstring = 0;
    CharBuffer buff = CharBuffer.allocate(100);
    try (reader) {
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

  private void findSubstringInBuf(CharBuffer buf, int bufSize) {
    for (int i = 0; i < bufSize; i++) {
      if (buf.get(i) != substring[subPointer]) {
        subPointer = 0;
        stat = NOT_SUBSTRING;
        continue;
      }
      if (stat == IN_SUBSTRING && subPointer < substring.length) {
        subPointer++;
      }
      if (stat == NOT_SUBSTRING && (buf.get(i) == substring[subPointer])) {
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
