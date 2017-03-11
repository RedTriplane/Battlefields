
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.utl.pizza.api.LandscapeActiveArea;

public class RedPizzaLandscapeActiveArea implements LandscapeActiveArea {

	private Float2 TL;
	private Float2 TR;
	private Float2 BL;
	private Float2 BR;

	public RedPizzaLandscapeActiveArea () {
		super();
		TL = Geometry.newFloat2();
		TR = Geometry.newFloat2();
		BL = Geometry.newFloat2();
		BR = Geometry.newFloat2();
	}

	@Override
	public ReadOnlyFloat2 getTopLeftCorner () {
		return TL;
	}

	@Override
	public ReadOnlyFloat2 getTopRightCorner () {
		return TR;
	}

	@Override
	public ReadOnlyFloat2 getBottomLeftCorner () {
		return BL;
	}

	@Override
	public ReadOnlyFloat2 getBottomRightCorner () {
		return BR;
	}

	public void setup (float or_x, float or_y, float width, float height) {
		TL.setXY(or_x, or_y);
		TR.setXY(or_x + width, or_y);
		BL.setXY(or_x, or_y + height);
		BR.setXY(or_x + width, or_y + height);

	}

}
