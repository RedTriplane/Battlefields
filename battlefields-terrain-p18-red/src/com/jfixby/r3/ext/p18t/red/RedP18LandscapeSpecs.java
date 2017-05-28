
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.util.p18t.api.P18LandscapeSpecs;
import com.jfixby.util.p18t.api.P18TerrainPalette;

public class RedP18LandscapeSpecs implements P18LandscapeSpecs {

	private P18TerrainPalette palette;

	@Override
	public void setPalette (P18TerrainPalette palette) {
		this.palette = palette;
	}

	@Override
	public P18TerrainPalette getPalette () {
		return palette;
	}

}
