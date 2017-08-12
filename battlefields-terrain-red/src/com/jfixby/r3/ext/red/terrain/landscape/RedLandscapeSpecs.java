
package com.jfixby.r3.ext.red.terrain.landscape;

import com.jfixby.util.terain.test.api.landscape.LandscapeSpecs;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;

public class RedLandscapeSpecs implements LandscapeSpecs {

	private TerrainPalette palette;

	@Override
	public void setPalette (TerrainPalette palette) {
		this.palette = palette;
	}

	@Override
	public TerrainPalette getPalette () {
		return palette;
	}

}
