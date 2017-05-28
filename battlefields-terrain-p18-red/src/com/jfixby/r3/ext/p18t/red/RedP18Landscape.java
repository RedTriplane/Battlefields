
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.GridFactory;
import com.jfixby.r3.ext.api.patch18.GridSpecs;
import com.jfixby.r3.ext.api.patch18.P18;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.p18t.api.P18Landscape;
import com.jfixby.util.p18t.api.P18LandscapeListener;
import com.jfixby.util.p18t.api.P18LandscapeSpecs;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.terain.test.api.landscape.Landscape;
import com.jfixby.util.terain.test.api.landscape.LandscapeFactory;
import com.jfixby.util.terain.test.api.landscape.LandscapeSpecs;
import com.jfixby.util.terain.test.api.palette.Terrain;

public class RedP18Landscape implements P18Landscape {
	private final RedP18LandscapeBrush brush;
	private final Grid grid;
	private final Landscape<ReadOnlyInt2> terrain_landscape;
	private final P18TerrainPalette palette;
	private final RedP18LandscapeActiveArea active_area = new RedP18LandscapeActiveArea();
	private final RedP18LandscapeBlocks blocks = new RedP18LandscapeBlocks();

	public RedP18Landscape (P18LandscapeSpecs land_specs) {
		palette = Debug.checkNull("getPalette", land_specs.getPalette());
		{
			LandscapeFactory factory = Terrain.getLandscapeFactory();
			LandscapeSpecs t_landscape_specs = factory.newLandscapeSpecs();
			t_landscape_specs.setPalette(palette.getTerrainPalette());
			terrain_landscape = factory.newLandscape(t_landscape_specs);
		}
		{
			GridFactory grid_factory = P18.getGridFactory();
			GridSpecs grid_specs = grid_factory.newGridSpecs();
			grid_specs.setPalette(palette.getP18Palette());
			grid_specs.setActiveGridArea(active_area);
			grid = grid_factory.newGrid(grid_specs);

		}
		brush = new RedP18LandscapeBrush(this);
	}

	@Override
	public void print () {
		terrain_landscape.print();
	}

	public Grid getGrid () {
		return grid;
	}

	public P18Palette getP18Palette () {
		return palette.getP18Palette();
	}

	@Override
	public RedP18LandscapeBrush getBrush () {
		return brush;
	}

	public Landscape<ReadOnlyInt2> getLandscape () {
		return this.terrain_landscape;
	}

	public P18TerrainPalette getPalette () {
		return this.palette;
	}

	public RedP18LandscapeBlocks getLandscapeBlocks () {
		return this.blocks;
	}

	@Override
	public void setListener (P18LandscapeListener listener) {
		blocks.setListener(listener);
	}

	@Override
	public P18LandscapeListener getListener () {
		return blocks.getListener();
	}

}
