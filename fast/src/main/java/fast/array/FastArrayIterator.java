package fast.array;

import java.util.Iterator;

public class FastArrayIterator<T> implements Iterator<T> {

  FastArray<T> array;
  int current = 0;
  public FastArrayIterator(FastArray<T> array) {
    this.array = array;
  }

  public boolean hasNext() {
    return current < array.length;
  }

  public T next() {
    return array.elements[current++];
  }
  
  public void reset() {
    current = 0;
  }
}
