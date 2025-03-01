package fast.array;

public class FastIntArray {
  public final int elements[];
  public int length;
  
  public FastIntArray(int maxSize) {
    elements = new int[maxSize];
    length = 0;
  }

  /**
   * warning no bound check
   */
  public int get(int i) {
    return elements[i];
  }
  
  /**
   * warning no bound check
   */
  public boolean add(int t) {
    elements[length++] = t;
    return true;
  }

  public int indexOf(int t) {
    for (int i=0;i<length;i++) {
      if (elements[i] == t) {
        return i;
      }
    }
    return -1;
  }
  /**
   * warning : suppose the order is not important
   * @param t
   * @return 
   */
  public boolean remove(int t) {
    int indexOf = indexOf(t);
    if (indexOf != -1) {
      remove(indexOf);
      return true;
    }
    return false;
  }
  
  public void clear() {
    length = 0;
  }
  
  public void copyFrom(FastIntArray src) {
    System.arraycopy(src.elements, 0, this.elements, 0, src.length);
    this.length = src.length;
  }
  public int size() {
    return length;
  }

  public int removeAtIndex(int index) {
    if (index != length-1) {
      elements[index] = elements[length-1];
    }
    length--;
    return elements[length+1];
  }

  public boolean isEmpty() {
    return length==0;
  }

  public boolean contains(int o) {
    return indexOf(o) != -1;
  }

  public int[] toArray() {
    return elements;
  }

  public int set(int index, int element) {
    int old = elements[index];
    elements[index] = element;
    return old;
  }

  public int lastIndexOf(int o) {
    for (int i=length-1;i>=0;i--) {
      if (elements[i] == o) {
        return i;
      }
    }
    return -1;
  }
}
