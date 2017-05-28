
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.GridActiveArea;

public class RedP18LandscapeActiveArea implements GridActiveArea {
	@Override
	public boolean isBorder (long cell_x, long cell_y) {
		return false;
	}

	@Override
	public boolean isInside (long cell_x, long cell_y) {
		return true;
	}

	@Override
	public boolean isOutside (long cell_x, long cell_y) {
		return false;
	}
}
