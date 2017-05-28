
package com.jfixby.r3.ext.red.terrain.landscape;

import com.jfixby.util.terain.test.api.landscape.TerrainBlock;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class RedTerrain<T> implements TerrainBlock<T> {

	private T keypoint;
	private TerrainType terrain_type;

	public RedTerrain (T keypoint, TerrainType terrain_type) {
		this.keypoint = keypoint;
		this.terrain_type = terrain_type;
	}

	@Override
	public TerrainType getType () {
		return terrain_type;
	}

	@Override
	public T getKeyPoint () {
		return keypoint;
	}

	@Override
	public String toString () {
		return "Terrain<" + this.terrain_type + "> " + keypoint + "";
	}

}
