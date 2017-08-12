
package com.jfixby.util.patch18.red.fields.afield;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.util.patch18.red.fields.util.WallFunctionCore;

public class WallFunction {

	private WallFunctionCore core;

	public WallFunction () {
		super();

	}

	public Patch18Cell getValue (final long grid_cell_x, final long grid_cell_y) {
		return this.core.getValue(grid_cell_x, grid_cell_y);
	}

	public Patch18Cell newValue (final Patch18 value) {
		final Patch18Cell cell = new Patch18Cell();
		cell.value = value;
		return cell;
	}

	public void setCore (final WallFunctionCore core) {
		this.core = core;
	}

// public void print (Rectangle area) {
// core.print(area);
// }

}
