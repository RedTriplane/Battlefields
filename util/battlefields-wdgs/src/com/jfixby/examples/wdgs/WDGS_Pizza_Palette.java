
package com.jfixby.examples.wdgs;

import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.iso.api.Isometry;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.utl.pizza.api.Pizza;
import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaPaletteFactory;
import com.jfixby.utl.pizza.api.PizzaPaletteSpecs;

public class WDGS_Pizza_Palette {

	public static final double PIXELS_PER_METER = 260;

	static {
		final P18TerrainPalette p18_terrain_palette = WDGS_P18Terrain_Palette.P18_TERRAIN_PALETTE;

		final PizzaPaletteFactory palette_factory = Pizza.invoke().getPizzaPaletteFactory();

		final PizzaPaletteSpecs specs = palette_factory.newPizzaPaletteSpecs();
		specs.setP18TerrainPalette(p18_terrain_palette);
		final IsoTransform fallout_iso = Isometry.getFallout(PIXELS_PER_METER);
		specs.setIsoTransform(fallout_iso);

		final PizzaPalette pizza_palette = palette_factory.newPizzaPalette(specs);
		PALETTE = pizza_palette;
	}

	public static final PizzaPalette PALETTE;

}
