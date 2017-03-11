
package com.jfixby.utl.pizza.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.p18t.api.P18Landscape;
import com.jfixby.util.p18t.api.P18LandscapeBrush;
import com.jfixby.utl.pizza.api.PizzaBrush;
import com.jfixby.utl.pizza.api.PizzaBrushPointer;

public class RedPizzaBrush implements PizzaBrush {

	private RedPizzaLandscape landscape;
	private P18Landscape p18_landscape;
	private IsoTransform transform;

	final RedPizzaBrushPointer pointer;

	private P18LandscapeBrush p18_brush;

	public RedPizzaBrush (RedPizzaLandscape redPizzaLandscape) {
		this.landscape = redPizzaLandscape;
		transform = landscape.getIsometry();
		p18_landscape = landscape.getP18Landscape();
		p18_brush = p18_landscape.getBrush();
		pointer = new RedPizzaBrushPointer(this);
	}

	@Override
	public void setFabric (Fabric fabric) {
		p18_brush.setFabric(fabric);
	}

	@Override
	public PizzaBrushPointer pointAtCanvas (double canvas_x, double canvas_y) {
		pointer.pointAtCanvasPoint(canvas_x, canvas_y);
		p18_brush.pointAt(pointer.getTerrainPosition());
		return pointer;
	}

	@Override
	public PizzaBrushPointer pointAtCanvas (ReadOnlyFloat2 canvas_xy) {
		return this.pointAtCanvas(canvas_xy.getX(), canvas_xy.getY());
	}

	@Override
	public void applyPaint () {
		p18_brush.applyPaint();
	}

	public RedPizzaLandscape getLandscape () {
		return this.landscape;
	}

	public IsoTransform getIsometry () {
		return this.transform;
	}

}
