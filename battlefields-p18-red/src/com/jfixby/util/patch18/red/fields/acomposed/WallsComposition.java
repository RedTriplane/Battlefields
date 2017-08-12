
package com.jfixby.util.patch18.red.fields.acomposed;

import com.jfixby.r3.ext.api.patch18.GridActiveArea;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.util.patch18.red.materials.JointLayer;

public class WallsComposition {

	final private List<JointLayer> layers;
	private final GridActiveArea active_area;

	public WallsComposition (final List<JointLayer> jointLayers, final Fabric default_fabric, final GridActiveArea active_area) {

		this.layers = jointLayers;

		this.active_area = active_area;

	}

	public ComposedTile getValue (final long cell_x, final long cell_y) {
		final long grid_x = this.toGridX(cell_x);
		final long grid_y = this.toGridY(cell_y);

		Patch18 shape = null;
		JointLayer definitive_layer = this.extract(grid_x, grid_y, this.layers);
		if (definitive_layer == null) {
			if (this.active_area.isInside(cell_x, cell_y)) {
				shape = Patch18.ERR;
			} else {
				shape = Patch18.Irrelevant;
			}
			definitive_layer = this.layers.getLast();
			definitive_layer = this.layers.getElementAt(0);
		} else {
			shape = definitive_layer.getWallShape(grid_x, grid_y);
		}

		// if (this.active_area.isBorder(cell_x, cell_y)) {
		// shape = P18.Irrelevant;
		// } else {
		// if (definitive_layer == null) {
		// shape = P18.ERR;
		// definitive_layer = layers.getLast();
		// definitive_layer = layers.get(0);
		// } else {
		// shape = definitive_layer.getWallShape(grid_x, grid_y);
		// }
		// }

		final Fabric materialForeground = definitive_layer.getForegroundMaterial();
		final Fabric materialBackground = definitive_layer.getBackgroundMaterial();
		final ComposedTile value = new ComposedTile();
		value.setForegroundMaterial(materialForeground);
		value.setBackgroundMaterial(materialBackground);
		value.setShape(shape);
		return value;

	}

	private long toGridY (final long cell_y) {
		return cell_y;
	}

	private long toGridX (final long cell_x) {
		return cell_x;
	}

	private JointLayer extract (final long grid_x, final long grid_y, final List<JointLayer> layers) {
		for (int i = 0; i < layers.size(); i++) {
			final JointLayer layer = layers.getElementAt(i);
			final Patch18 shape = layer.getWallShape(grid_x, grid_y);
			if (!shape.isIrrelevant()) {
				return layer;
			}
		}
		return null;
	}

// public void printLayer (int i, Rectangle area) {
// this.layers.getElementAt(i).print(area);
// }

}
