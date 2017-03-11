
package com.jfixby.util.patch18.red.fields.bool;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.util.patch18.red.FabricsLevel;

public class BoolFunction {

	private final Fabric falseColor;
	private final Fabric trueColor;
	private final FabricsLevel fabric_function;

	public BoolFunction (final FabricsLevel fabric_function, final Fabric trueColor, final Fabric falseColor) {
		super();
		this.fabric_function = fabric_function;
		this.falseColor = falseColor;
		this.trueColor = trueColor;
		if (this.trueColor == null) {
			Err.throwNotImplementedYet();
		}
		if (this.falseColor == null) {
			Err.throwNotImplementedYet();
		}
	}

	public BOOL getValue (final long node_x, final long node_y) {

		final Fabric color_value = this.fabric_function.getValueAt(node_x, node_y);
		if (color_value == this.trueColor) {
			return BOOL.TRUE;
		} else if (color_value == this.falseColor) {
			return BOOL.FALSE;
		} else {
			return BOOL.UNKNOWN;
		}
	}

	public void print (final Rectangle area) {
		this.fabric_function.print(area);
	}

}
