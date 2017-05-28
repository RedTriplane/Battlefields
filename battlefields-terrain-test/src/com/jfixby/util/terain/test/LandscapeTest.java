
package com.jfixby.util.terain.test;

import com.jfixby.examples.wdgs.WDGS_P18Terrain_Palette;
import com.jfixby.r3.ext.api.patch18.P18;
import com.jfixby.r3.ext.p18t.red.RedP18Terrain;
import com.jfixby.r3.ext.red.terrain.RedTerrain;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.math.Int2;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.util.p18t.api.P18Terrain;
import com.jfixby.util.patch18.red.RedP18;
import com.jfixby.util.terain.test.api.landscape.Landscape;
import com.jfixby.util.terain.test.api.landscape.LandscapeFactory;
import com.jfixby.util.terain.test.api.landscape.LandscapeSpecs;
import com.jfixby.util.terain.test.api.palette.Terrain;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;

public class LandscapeTest {

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();
		P18Terrain.installComponent(new RedP18Terrain());
		Terrain.installComponent(new RedTerrain());
		P18.installComponent(new RedP18());
		final LandscapeFactory factory = Terrain.getLandscapeFactory();

		final LandscapeSpecs landscape_specs = factory.newLandscapeSpecs();
		final TerrainPalette palette = WDGS_P18Terrain_Palette.P18_TERRAIN_PALETTE.getTerrainPalette();
		landscape_specs.setPalette(palette);

		final Landscape<Int2> landscape = factory.newLandscape(landscape_specs);

		landscape.newBlock(IntegerMath.newInt2(0, 0), palette.listAllBlocks().getElementAt(0));

		landscape.print();

	}
}
