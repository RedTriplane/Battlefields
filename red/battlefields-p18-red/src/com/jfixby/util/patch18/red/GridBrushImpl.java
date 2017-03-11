
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.grid.GridBrush;
import com.jfixby.r3.ext.api.patch18.grid.GridBrushApplicationResult;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.util.patch18.red.core.BRUSH_STATE;

public class GridBrushImpl implements GridBrush {

	Fabric default_fabric;
	Fabric current_fabric;
	private final GridImpl grid;

	public GridImpl getGrid () {
		return this.grid;
	}

	public GridBrushImpl (final GridImpl gridImpl) {
		this.grid = gridImpl;
	}

	@Override
	public void setFabric (final Fabric fabric) {
		this.current_fabric = fabric;
	}

	BrushApplicationResultImpl brush_result = new BrushApplicationResultImpl(this);

	BRUSH_STATE brush_state = BRUSH_STATE.READY;
	private int brush_size;

	@Override
	public void begin () {
		if (this.brush_state == BRUSH_STATE.READY) {
			this.setBrushState(BRUSH_STATE.DRAWING);
			this.brush_result.clear();
		} else {
			Err.reportError("Wrong brush state: " + this.brush_state);
		}
	}

	private void setBrushState (final BRUSH_STATE next) {
		this.brush_state = next;
	}

	@Override
	public GridBrushApplicationResult end () {
		if (this.brush_state == BRUSH_STATE.DRAWING) {
			// for (int i = 0; i < this.brush_result.affected.list.size(); i++)
			// {
			// IndexedPosition element = this.brush_result.affected.list
			// .getElementAt(i);
			// if (this.grid.getValueAt(element).getShape().isErr()) {
			// L.d("BAD", element);
			// L.d(" ", this.grid.getValueAt(element));
			// // RedTriplane.exit();
			// }
			// }
			this.setBrushState(BRUSH_STATE.READY);
		} else {
			Err.reportError("Wrong brush state: " + this.brush_state);
		}
		return this.brush_result;
	}

	@Override
	public void setPaintAtNode (final long grid_node_x, final long grid_node_y) {
		if (this.brush_state == BRUSH_STATE.DRAWING) {
			this.grid.apply_at_node(grid_node_x, grid_node_y, this.current_fabric, this.brush_result);
		} else {
			Err.reportError("Wrong brush state: " + this.brush_state);
		}
	}

	@Override
	public void applyPaintAtCell (final long cell_x, final long cell_y) {
		if (this.brush_state == BRUSH_STATE.DRAWING) {
			this.grid.apply_at_cell(cell_x, cell_y, this.brush_size, this.current_fabric, this.brush_result);
		} else {
			Err.reportError("Wrong brush state: " + this.brush_state);
		}
	}

	@Override
	public void clearHistory () {
		this.brush_result.clear();
	}

	public void setDefaultFabric (final Fabric default_fabric) {
		this.default_fabric = default_fabric;
		if (this.current_fabric == null) {
			this.setFabric(default_fabric);
		}
	}

	@Override
	public void revertBack () {
	}

	@Override
	public void setSize (final int brush_size) {
		this.brush_size = (int)IntegerMath.limit(0, brush_size, 1000);
	}

	@Override
	public void refresh () {
		this.grid.refresh(this.brush_result);
	}

	@Override
	public void applyPaintAtCell (final ReadOnlyInt2 cell_xy) {
		this.applyPaintAtCell(cell_xy.getX(), cell_xy.getY());
	}

}
