
package com.jfixby.r3.ext.red.terrain;

import com.jfixby.r3.ext.red.terrain.landscape.RedLandscapeFactory;
import com.jfixby.util.terain.test.api.landscape.LandscapeFactory;
import com.jfixby.util.terain.test.api.palette.TerrainComponent;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteFactory;

public class RedTerrain implements TerrainComponent {
	final RedTerrainPaletteFactory fact = new RedTerrainPaletteFactory();
	final RedLandscapeFactory lfac = new RedLandscapeFactory();

	@Override
	public TerrainPaletteFactory getPaletteFactory () {
		return fact;
	}

	@Override
	public LandscapeFactory getLandscapeFactory () {
		return lfac;
	}

}
