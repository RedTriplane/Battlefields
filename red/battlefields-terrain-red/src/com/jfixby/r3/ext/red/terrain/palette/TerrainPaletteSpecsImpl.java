
package com.jfixby.r3.ext.red.terrain.palette;

import java.util.Collection;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteSpecs;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class TerrainPaletteSpecsImpl implements TerrainPaletteSpecs {

	final Set<TerrainType> terrain_types = Collections.newSet();

	@Override
	public void addTerrainBlock (TerrainType terrain_type) {
		terrain_types.add(terrain_type);
	}

	@Override
	public TerrainType getTerrainBlock (int i) {
		return terrain_types.getElementAt(i);
	}

	@Override
	public int size () {
		return terrain_types.size();
	}

	@Override
	public void addTerrainBlocks (Collection<TerrainType> javaList) {
		terrain_types.addJavaCollection(javaList);
	}

}
