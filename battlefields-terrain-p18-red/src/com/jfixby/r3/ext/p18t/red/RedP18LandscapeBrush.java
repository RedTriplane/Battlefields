
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.grid.AffectedCells;
import com.jfixby.r3.ext.api.patch18.grid.Cell;
import com.jfixby.r3.ext.api.patch18.grid.GridBrush;
import com.jfixby.r3.ext.api.patch18.grid.GridBrushApplicationResult;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.p18t.api.P18LandscapeBrush;
import com.jfixby.util.p18t.api.P18LandscapePointer;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.util.p18t.api.P18TerrainTypeVariationsList;
import com.jfixby.util.terain.test.api.landscape.Landscape;
import com.jfixby.util.terain.test.api.landscape.LandscapeOperationResult;
import com.jfixby.util.terain.test.api.landscape.TerrainBlock;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class RedP18LandscapeBrush implements P18LandscapeBrush {

	private RedP18Landscape master;

	private Grid grid;

	private GridBrush grid_brush;

	private final Landscape<ReadOnlyInt2> landscape;

	private P18TerrainPalette palette;
	private RedP18LandscapeBlocks blocks;

	public RedP18LandscapeBrush (RedP18Landscape redP18Landscape) {
		super();
		this.master = redP18Landscape;
		grid = master.getGrid();
		grid_brush = grid.getBrush();
		palette = this.master.getPalette();
		landscape = master.getLandscape();
		pointer = new RedP18LandscapePointer(this, palette);
		blocks = redP18Landscape.getLandscapeBlocks();
	}

	@Override
	public void setFabric (Fabric fabric) {
		grid_brush.setFabric(fabric);
	}

	public Grid getGrid () {
		return grid;
	}

	public Landscape<ReadOnlyInt2> getLandscape () {
		return this.landscape;
	}

	public P18TerrainPalette getPalette () {
		return this.master.getPalette();
	}

	@Override
	public void applyPaint () {
		this.grid_brush.begin();
		ReadOnlyInt2 application_position = pointer.getBlockGridPosition();
		this.grid_brush.applyPaintAtCell(application_position);
		final GridBrushApplicationResult grid_result = grid_brush.end();
		consumeGridBrushResult(grid_result, application_position);
	}

	public void consumeGridBrushResult (GridBrushApplicationResult grid_result, ReadOnlyInt2 application_position) {
		AffectedCells changed = grid_result.getChangedCells();
		// L.d("changed");

		for (int i = 0; i < changed.numberOfElements(); i++) {
			Cell cell = changed.getCell(i);
			ReadOnlyInt2 cell_position = cell.getPosition();

			P18TerrainTypeVariationsList variations = palette.listVariationsFor(cell.getShape(), cell.getUpperFabric(),
				cell.getLowerFabric());

			P18TerrainTypeVariation variation = variations.getRandomElement();
			TerrainType terrain_type = variation.getProperties();

			LandscapeOperationResult<ReadOnlyInt2> operation_result = landscape.newBlock(cell_position, terrain_type);

			process_operation_result(variation, operation_result, application_position);

		}

	}

	private void process_operation_result (P18TerrainTypeVariation variation, LandscapeOperationResult<ReadOnlyInt2> operation_result,
		ReadOnlyInt2 application_position) {

		{
			Collection<TerrainBlock<ReadOnlyInt2>> removed_list = operation_result.removedBlocks();
			for (int i = 0; i < removed_list.size(); i++) {
				TerrainBlock<ReadOnlyInt2> e = removed_list.getElementAt(i);
				this.blocks.alsoRemove(e);
			}

		}

		{
			Collection<TerrainBlock<ReadOnlyInt2>> added_list = operation_result.addedBlocks();
			for (int i = 0; i < added_list.size(); i++) {
				TerrainBlock<ReadOnlyInt2> e = added_list.getElementAt(i);
				RedP18TerrainBlock p18_block = new RedP18TerrainBlock(variation, e, this.master);
				this.blocks.alsoAdd(p18_block);

			}

		}

	}

	final RedP18LandscapePointer pointer;

	@Override
	public P18LandscapePointer pointAt (double x, double y, double z) {
		pointer.pointAt(x, y, z);
		final ReadOnlyInt2 key_point = pointer.getBlockGridPosition();
		final TerrainBlock<ReadOnlyInt2> terrain_block = landscape.getBlock(key_point);
		final RedP18TerrainBlock block_to_point_at = blocks.findBlock(terrain_block);
		this.blocks.alsoPointAt(block_to_point_at);
		return pointer;
	}

	@Override
	public P18LandscapePointer pointAt (ReadOnlyFloat3 terrain_xyz) {
		return this.pointAt(terrain_xyz.getX(), terrain_xyz.getY(), terrain_xyz.getZ());
	}

	public RedP18Landscape getP18Landscape () {
		return this.master;
	}

	public RedP18LandscapePointer getPointer () {
		return this.pointer;
	}
}
