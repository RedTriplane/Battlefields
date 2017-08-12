
package com.jfixby.util.patch18.red.materials;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;

public class JointLayer {

	final private Fabric materialF;
	final private Fabric materialB;;
	private final WallFunction walls;

	public Fabric getForegroundMaterial () {
		return this.materialF;
	}

	public Fabric getBackgroundMaterial () {
		return this.materialB;
	}

	public JointLayer (final JointLayerSchematic material_layer_schematic) {
		this.materialF = material_layer_schematic.getForegroundMaterial();
		this.materialB = material_layer_schematic.getBackgroundMaterial();
		this.walls = material_layer_schematic.getWalls();
	}

	public Patch18 getWallShape (final long grid_x, final long grid_y) {
		return this.walls.getValue(grid_x, grid_y).value;
	}

// public void print (Rectangle area) {
// walls.print(area);
// }

}
