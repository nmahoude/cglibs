package fast.maths;

public class FastMath {

	public static double sqrt(double value) {
		double sqrt = Double.longBitsToDouble( ( ( Double.doubleToLongBits( value )-(1l<<52) )>>1 ) + ( 1l<<61 ) );
		double better = (sqrt + value/sqrt)/2.0;
		double evenbetter = (better + value/better)/2.0;
		return evenbetter;
	}
}
