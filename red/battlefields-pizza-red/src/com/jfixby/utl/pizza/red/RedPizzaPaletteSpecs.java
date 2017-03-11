
package com.jfixby.utl.pizza.red;

import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.utl.pizza.api.PizzaPaletteSpecs;

public class RedPizzaPaletteSpecs implements PizzaPaletteSpecs {

	private P18TerrainPalette p18TerrainPalette;
	private IsoTransform transform;

	@Override
	public void setP18TerrainPalette (P18TerrainPalette p18TerrainPalette) {
		this.p18TerrainPalette = p18TerrainPalette;
	}

	@Override
	public void setIsoTransform (IsoTransform transform) {
		this.transform = transform;
	}

	@Override
	public P18TerrainPalette getP18TerrainPalette () {
		return p18TerrainPalette;
	}

	@Override
	public IsoTransform getIsoTransform () {
		return transform;
	}

}
