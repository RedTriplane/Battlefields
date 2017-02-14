package com.jfixby.utl.pizza.api;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;

public interface PizzaBrush {

	void setFabric(Fabric fabric);

	PizzaBrushPointer pointAtCanvas(double canvas_x, double canvas_y);

	PizzaBrushPointer pointAtCanvas(ReadOnlyFloat2 canvas_xy);

	public void applyPaint();

	// void applyPaintAtCanvas(FixedFloat3 terrain_xyz);
	//
	// void applyPaintAtCanvas(double terrain_x, double terrain_y, double
	// terrain_z);

}
