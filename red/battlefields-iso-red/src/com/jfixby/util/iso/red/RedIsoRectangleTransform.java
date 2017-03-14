
package com.jfixby.util.iso.red;

import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;
import com.jfixby.scarabei.api.geometry.projections.RotateProjection;
import com.jfixby.scarabei.api.geometry.projections.ScaleProjection;
import com.jfixby.scarabei.api.geometry.projections.SkewProjection;
import com.jfixby.scarabei.api.math.CustomAngle;

public class RedIsoRectangleTransform {

	private final OffsetProjection offset;
	private final RotateProjection rotate;
	private final ScaleProjection size;
	private final OffsetProjection origin;
	private final ScaleProjection scale;
	private final SkewProjection skew;

	public RedIsoRectangleTransform () {
		this.origin = Geometry.getProjectionFactory().newOffset();
		this.size = Geometry.getProjectionFactory().newScale();
		this.scale = Geometry.getProjectionFactory().newScale();
		this.skew = Geometry.getProjectionFactory().newSkew();
		this.rotate = Geometry.getProjectionFactory().newRotate();
		this.offset = Geometry.getProjectionFactory().newOffset();
	}

	public void setPosition (final double off_x, final double off_y) {
		this.offset.setOffsetXY(off_x, off_y);
	}

	public void setRotation (final CustomAngle rot) {
		this.rotate.setRotation(rot);
	}

	public void setSize (final double w, final double h) {
		this.size.setScaleXY(w, h);
	}

	public void setSkew (final CustomAngle skew_x, final CustomAngle skew_y) {
		this.skew.setSkewX(skew_x);
		this.skew.setSkewY(skew_y);
	}

	public void setScale (final double h_scale, final double v_scale) {
		this.scale.setScaleXY(h_scale, v_scale);
	}

	public void project (final Float2 pos) {
		this.origin.project(pos);
		this.size.project(pos);
		this.scale.project(pos);
		this.skew.project(pos);
		this.rotate.project(pos);
		this.offset.project(pos);
	}

	public void unProject (final Float2 pos) {
// Err.reportError("");
		this.offset.unProject(pos);
		this.rotate.unProject(pos);
		this.skew.unProject(pos);
		this.scale.unProject(pos);
		this.size.unProject(pos);
		this.origin.unProject(pos);
	}

}
