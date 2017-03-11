package com.jfixby.util.iso.red;

import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.util.iso.api.IsoTransformSpecs;

public class RedIsoTransformSpecs implements IsoTransformSpecs {
	double VerticalScale = 1;
	double HorizontalScale = 1;
	Angle HorizontalShiftAngle = Angles.newAngle();
	Angle VerticalShiftAngle = Angles.newAngle();
	Angle Rotation = Angles.newAngle();
	double OffsetX = 0;
	double OffsetY = 0;
	double ZProjectionValue = 1;

	public double getVerticalScale() {
		return VerticalScale;
	}

	public void setVerticalScale(double verticalScale) {
		VerticalScale = verticalScale;
	}

	public double getHorizontalScale() {
		return HorizontalScale;
	}

	public void setHorizontalScale(double horizontalScale) {
		HorizontalScale = horizontalScale;
	}

	public Angle getHorizontalShiftAngle() {
		return HorizontalShiftAngle;
	}

	public void setHorizontalShiftAngle(Angle horizontalShiftAngle) {
		HorizontalShiftAngle = horizontalShiftAngle;
	}

	public Angle getVerticalShiftAngle() {
		return VerticalShiftAngle;
	}

	public void setVerticalShiftAngle(Angle verticalShiftAngle) {
		VerticalShiftAngle = verticalShiftAngle;
	}

	public Angle getRotation() {
		return Rotation;
	}

	public void setRotation(Angle rotation) {
		Rotation = rotation;
	}

	public double getOffsetX() {
		return OffsetX;
	}

	public void setOffsetX(double offsetX) {
		OffsetX = offsetX;
	}

	public double getOffsetY() {
		return OffsetY;
	}

	public void setOffsetY(double offsetY) {
		OffsetY = offsetY;
	}

	@Override
	public void setZProjectionValue(double ZProjectionValue) {
		this.ZProjectionValue = ZProjectionValue;
	}

	@Override
	public double getZProjectionValue() {
		return ZProjectionValue;
	}

	// ---------------

	private double pixels_to_tile_meter = 256;

	@Override
	public void setPixelsToGameMeter(double pixels_to_tile_meter) {
		this.pixels_to_tile_meter = pixels_to_tile_meter;
	}

	@Override
	public double getPixelsToGameMeter() {
		return pixels_to_tile_meter;
	}

}
