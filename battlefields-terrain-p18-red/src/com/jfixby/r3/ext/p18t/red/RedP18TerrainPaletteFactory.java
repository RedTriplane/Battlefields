
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.p18t.api.P18TerrainPaletteFactory;
import com.jfixby.util.p18t.api.P18TerrainPaletteSpecs;

public class RedP18TerrainPaletteFactory implements P18TerrainPaletteFactory {

	@Override
	public P18TerrainPaletteSpecs newP18TerrainPaletteSpecs () {
		return new RedP18TerrainPaletteSpecs();
	}

	@Override
	public P18TerrainPalette newP18TerrainPalette (P18TerrainPaletteSpecs specs) {
		return new RedP18TerrainPalette(specs);
	}

}
