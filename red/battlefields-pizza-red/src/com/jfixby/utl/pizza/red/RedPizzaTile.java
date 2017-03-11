
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.p18t.api.P18TerrainBlock;
import com.jfixby.utl.pizza.api.PizzaTile;
import com.jfixby.utl.pizza.api.PizzaTileType;

public class RedPizzaTile implements PizzaTile {

	private PizzaTileType type;
	private P18TerrainBlock<ReadOnlyInt2> block;
	private Float2 canvas_position = Geometry.newFloat2();
	private RedPizzaLandscape master;

	public RedPizzaTile (PizzaTileType type, P18TerrainBlock<ReadOnlyInt2> block, RedPizzaLandscape master) {
		this.block = block;
		this.type = type;
		this.master = master;
		ReadOnlyFloat3 block_position = block.getPosition();
		master.getIsometry().project3Dto2D(block_position, canvas_position);
		master.getIsometry().project2DtoPixels(canvas_position);
	}

	@Override
	public PizzaTileType getType () {
		return type;
	}

	public P18TerrainBlock<ReadOnlyInt2> getP18TerrainBlock () {
		return block;
	}

	@Override
	public ReadOnlyFloat2 getCanvasPosition () {
		return canvas_position;
	}

}
