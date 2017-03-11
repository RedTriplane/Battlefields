
package com.jfixby.r3.ext.red.terrain.landscape;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.util.terain.test.api.landscape.Landscape;
import com.jfixby.util.terain.test.api.landscape.LandscapeOperationResult;
import com.jfixby.util.terain.test.api.landscape.LandscapeSpecs;
import com.jfixby.util.terain.test.api.landscape.TerrainBlock;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class RedLandscape<T> implements Landscape<T> {

	private final Map<T, TerrainBlock<T>> mapping = Collections.newMap();
	private final TerrainPalette palette;
	private final RedLandscapeOperationResult<T> result = new RedLandscapeOperationResult<T>(this);

	public RedLandscape (final LandscapeSpecs landscape_specs) {
		this.palette = Debug.checkNull("palette", landscape_specs.getPalette());
	}

	@Override
	public void print () {
		this.mapping.print("landscape");
	}

	@Override
	public TerrainPalette getPalette () {
		return this.palette;
	}

	@Override
	public LandscapeOperationResult<T> newBlock (final T keypoint, final TerrainType terrain_type) {
		if (!this.palette.listAllBlocks().contains(terrain_type)) {
			Err.reportError("TerrainType does not belong to the palette: " + this.palette);
		}
		this.result.reset();
		final TerrainBlock<T> removed = this.mapping.remove(keypoint);
		final TerrainBlock<T> added = new RedTerrain<T>(keypoint, terrain_type);
		this.mapping.put(keypoint, added);
		this.result.registerAdded(added);
		if (removed != null) {
			this.result.registerRemoved(removed);
		}
		return this.result;
	}

	@Override
	public TerrainBlock<T> getBlock (final T keypoint) {
		return this.mapping.get(keypoint);
	}
}
