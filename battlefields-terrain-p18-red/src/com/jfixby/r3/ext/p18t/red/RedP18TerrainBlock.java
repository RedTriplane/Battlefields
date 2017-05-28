
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;
import com.jfixby.scarabei.api.floatn.Float3;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.p18t.api.P18TerrainBlock;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.util.terain.test.api.landscape.TerrainBlock;

public class RedP18TerrainBlock implements P18TerrainBlock<ReadOnlyInt2> {

	private P18TerrainTypeVariation variation;
	private TerrainBlock<ReadOnlyInt2> terrain_block;
	private ReadOnlyInt2 key;
	private RedP18Landscape master;

	private Float3 position_3d = Geometry.newFloat3();

	// private RedP18LandscapePointer pointer;

	public RedP18TerrainBlock (P18TerrainTypeVariation variation, TerrainBlock<ReadOnlyInt2> terain_block, RedP18Landscape master) {
		this.variation = variation;
		this.terrain_block = terain_block;
		key = terrain_block.getKeyPoint();
		this.master = master;
		RedP18LandscapePointer pointer = master.getBrush().getPointer();
		// this.pointer = pointer;
		// double z = variation.getProperties().getAltitude().toDouble();
		pointer.get3DPositionOf(key, position_3d);
		position_3d.setZ(0);

	}

	public TerrainBlock<ReadOnlyInt2> getTerrainBlock () {
		return terrain_block;
	}

	@Override
	public String toString () {
		return "P18TerrainBlock[" + variation + " at " + terrain_block.getKeyPoint() + "]";
	}

	@Override
	public P18TerrainTypeVariation getVariation () {
		return variation;
	}

	@Override
	public ReadOnlyFloat3 getPosition () {
		return position_3d;
	}

}
