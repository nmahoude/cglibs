package fast.array;

import java.lang.reflect.Array;
import java.util.Iterator;

public class FastArray<T> implements Iterable<T> {
  public final T elements[];
  public int length;
  
  @SuppressWarnings("unchecked")
  public FastArray(Class<T> c, int maxSize) {
    elements = (T[]) Array.newInstance(c, maxSize);
    length = 0;
  }

  /**
   * warning no bound check
   */
  public T get(int i) {
    return elements[i];
  }
  
  /**
   * warning no bound check
   */
  public boolean add(T t) {
    elements[length++] = t;
    return true;
  }

  /**
   * slow
   */
  public int indexOf(Object t) {
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
  public boolean remove(Object t) {
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
  
  public void copyFrom(FastArray<T> src) {
    System.arraycopy(src.elements, 0, this.elements, 0, src.length);
    this.length = src.length;
  }
  public int size() {
    return length;
  }

  public T remove(int index) {
    if (index != length-1) {
      elements[index] = elements[length-1];
    }
    length--;
    return elements[length];
  }

  public boolean isEmpty() {
    return length==0;
  }

  /**
   * Warning super slow methods ! 
   * in critical parts, use elements directly
   * 
   * TODO Performances Analys�es avant de dire �a ?
   */
  public Iterator<T> iterator() {
    return new FastArrayIterator<T>(this);
  }

  public boolean contains(Object o) {
    return indexOf(o) != -1;
  }

  public T[] toArray() {
    return elements;
  }

  public T set(int index, T element) {
    T old = elements[index];
    elements[index] = element;
    return old;
  }

  public int lastIndexOf(Object o) {
    for (int i=length-1;i>=0;i--) {
      if (elements[i] == o) {
        return i;
      }
    }
    return -1;
  }

  public void addAll(FastArray<T> array) {
    for (int i=0;i<array.length;i++) {
      this.elements[length++] = array.elements[i];
    }
  }
  
  public void addAll(T[] elements, int elementsFE) {
    for (int i=0;i<elementsFE;i++) {
      this.elements[length++] = elements[i];
    }
  }
}
