package fast.random;

public final class XORShiftRandom {
  private long seed;

  public XORShiftRandom() {
  	this.seed = System.nanoTime();
  }
  
  public XORShiftRandom(long seed) {
      this.seed = seed;
  }

  public final int nextInt(int range) {
    return nextInt(nextInt() & Integer.MAX_VALUE, range);
  }
  
  final int nextInt(int value, int range) {
    int nextInt = value & Integer.MAX_VALUE;
    return nextInt - (nextInt / range)*range;
  }

  public final int nextInt() {
      seed ^= (seed << 21);
      seed ^= (seed >>> 35);
      seed ^= (seed << 4);
      return (int) seed;
  }

  // return in [0..1]
  public int nextInt2() {
  	return nextInt() & 0b1;
  }
  
  // return in [0..3]
  public int nextInt4() {
  	return nextInt() & 0b11;
  }
  
  // return in [0..7]
  public int nextInt8() {
  	return nextInt() & 0b111;
  }

  
	public final boolean nextBoolean() {
		return (nextInt() & 0x1b) == 0;
	}
	
  public double nextDouble() {
    return (nextInt()) / 0x7FFF;
  }
  
  public double nextDouble(double a, double b) {
    return a + (nextInt() / 0x7FFF)*(b-a);
  }
}
