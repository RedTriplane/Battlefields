package com.jfixby.util.p18t.api;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.floatn.FixedFloat3;

public interface P18LandscapeBrush {

	void setFabric(Fabric fabric);

	P18LandscapePointer pointAt(double terrain_x, double terrain_y,
			double terrain_z);

	P18LandscapePointer pointAt(FixedFloat3 terrain_xyz);

	void applyPaint();

}
