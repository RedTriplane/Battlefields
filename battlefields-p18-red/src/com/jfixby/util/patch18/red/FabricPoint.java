
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public class FabricPoint {
	long X;
	long Y;
	Fabric fabricValue;
	boolean affected = false;

	public static String toString (long x, long y) {
		// return "[" + wrap(x, 3) + "," + wrap(y, 3) + "]";
		return "[" + wrap("", 3) + "," + wrap("", 3) + "]";
	}

	@Override
	public String toString () {
		if (fabricValue == null) {
			return toString(X, Y);
		}
		return "[" + wrap(fabricValue, 7) + "]";
	}

	private static String wrap (Object tmp, int N) {
		return wrap(tmp + "", N);
	}

	private static String wrap (String tmp, int N) {
		while (tmp.length() < N) {
			tmp = " " + tmp;
		}
		return tmp;
	}

	public FabricPoint (Fabric value, long i, long j) {
		this.fabricValue = value;
		this.X = i;
		this.Y = j;
	}

	public boolean setFabricValue (Fabric current_fabric) {
		this.affected = (fabricValue != current_fabric);
		this.fabricValue = current_fabric;
		return affected;
	}

	public Fabric getFabricValue () {
		return fabricValue;
	}

}
