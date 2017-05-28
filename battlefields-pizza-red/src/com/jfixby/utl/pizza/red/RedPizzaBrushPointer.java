
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.Float3;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.utl.pizza.api.PizzaBrushPointer;

public class RedPizzaBrushPointer implements PizzaBrushPointer {

	private final Float2 application_point_2d = Geometry.newFloat2();
	private final Float3 application_point_3d = Geometry.newFloat3();
	private IsoTransform transform;

	public RedPizzaBrushPointer (RedPizzaBrush redPizzaBrush) {
		transform = redPizzaBrush.getIsometry();

	}

	public ReadOnlyFloat3 getTerrainPosition () {
		return application_point_3d;
	}

	@Override
	public ReadOnlyFloat2 getCanvasPosition () {
		return application_point_2d;
	}

	public void pointAtCanvasPoint (double canvas_x, double canvas_y) {
		application_point_2d.setXY(canvas_x, canvas_y);
		transform.unprojectPixelsTo2D(application_point_2d);
		transform.unproject2Dto3D(application_point_2d, 0, application_point_3d);

		L.d("application_point_3d", application_point_3d);
	}

}
