
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.ZxZ_Functuion;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.log.L;

public class FabricsLevel {

	// final Map<IndexedPosition, FabricPoint> function_set =
	// RedTriplane.newMap();

	final ZxZ_Functuion<FabricPoint> function_set = Collections.newZxZ_Function();
	private GridImpl grid;

	public FabricsLevel (GridImpl gridImpl) {
		this.grid = gridImpl;

	}

	public boolean set (long grid_node_x, long grid_node_y, Fabric current_fabric) {
		boolean changed = false;
		FabricPoint value = this.function_set.getValueAt(grid_node_x, grid_node_y);

		if (value == null) {
			value = new FabricPoint(current_fabric, grid_node_x, grid_node_y);
			this.function_set.setValueAt(grid_node_x, grid_node_y, value);
			changed = true;
		} else {
			changed = value.setFabricValue(current_fabric);
		}

		return changed;

	}

	public Fabric getValueAt (final long grid_node_x, final long grid_node_y) {
		// final IndexedPosition position = newIndexedPosition(grid_node_x,
		// grid_node_y);
		final FabricPoint value = this.function_set.getValueAt(grid_node_x, grid_node_y);
		if (value != null) {
			return value.fabricValue;
		}
		return null;
	}

	public void print (Rectangle area) {
		for (long y = (long)area.getTopLeftCorner().getY(); y < area.getBottomRightCorner().getY(); y++) {
			for (long x = (long)area.getTopLeftCorner().getX(); x < area.getBottomRightCorner().getX(); x++) {
				FabricPoint e = function_set.getValueAt(x, y);
				if (e == null) {
					L.d_appendChars(FabricPoint.toString(x, y) + " ");
				} else {
					L.d_appendChars(e + " ");
				}
			}
			L.d();
		}
	}
}
