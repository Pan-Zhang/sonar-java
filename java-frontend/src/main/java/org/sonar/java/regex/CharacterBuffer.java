package org.sonar.java.regex;

import org.sonar.java.regex.ast.JavaCharacter;

public class CharacterBuffer {

  private static final int RESIZE_FACTOR = 2;

  private JavaCharacter[] contents;

  private int startIndex = 0;

  private int size = 0;

  public CharacterBuffer(int initialCapacity) {
    contents = new JavaCharacter[initialCapacity];
  }

  public JavaCharacter get(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException("Invalid index " + index + " for buffer of size " + size + ".");
    }
    return contents[(startIndex + index) % contents.length];
  }

  public void add(JavaCharacter character) {
    if (size + 1 == contents.length) {
      resize(contents.length * RESIZE_FACTOR);
    }
    contents[(startIndex + size) % contents.length] = character;
    size++;
  }

  public void removeFirst() {
    startIndex++;
    size--;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  private void resize(int newCapacity) {
    JavaCharacter[] newContents = new JavaCharacter[newCapacity];
    System.arraycopy(contents, startIndex, newContents, 0, contents.length - startIndex);
    System.arraycopy(contents, 0, newContents, contents.length - startIndex, startIndex);
    contents = newContents;
    startIndex = 0;
  }

}
