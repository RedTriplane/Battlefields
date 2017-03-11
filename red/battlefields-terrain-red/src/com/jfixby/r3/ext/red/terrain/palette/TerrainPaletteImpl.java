
package com.jfixby.r3.ext.red.terrain.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteSpecs;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class TerrainPaletteImpl implements TerrainPalette {

	final TerrainTypeRegister register = new TerrainTypeRegister();

	public TerrainPaletteImpl (TerrainPaletteSpecs palette_specs) {
		Debug.checkNull("palette_specs", palette_specs);

		for (int i = 0; i < palette_specs.size(); i++) {
			TerrainType block = palette_specs.getTerrainBlock(i);
			register.register(block);
		}

	}

	@Override
	public void print () {
		register.print("TerrainPalette");
	}

	@Override
	public Collection<TerrainType> listBlocks (ID tile_id) {
		return this.register.listBlocks(tile_id);
	}

	@Override
	public Collection<TerrainType> listAllBlocks () {
		return this.register.listAllBlocks();
	}

}
