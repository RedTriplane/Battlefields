
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.util.p18t.api.P18LandscapeFactory;
import com.jfixby.util.p18t.api.P18TerrainComponent;
import com.jfixby.util.p18t.api.P18TerrainPaletteFactory;

public class RedP18Terrain implements P18TerrainComponent {
	RedP18TerrainPaletteFactory palette_fac = new RedP18TerrainPaletteFactory();

	RedP18LandscapeFactory landscape_fac = new RedP18LandscapeFactory();

	@Override
	public P18TerrainPaletteFactory getP18TerrainPaletteFactory () {
		return this.palette_fac;
	}

	@Override
	public P18LandscapeFactory getP18LandscapeFactory () {
		return this.landscape_fac;
	}

}
