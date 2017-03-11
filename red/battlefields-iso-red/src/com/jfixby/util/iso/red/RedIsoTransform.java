package com.jfixby.util.iso.red;

import java.util.Iterator;

import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.EditableCollection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.Float3;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.CustomAngle;
import com.jfixby.scarabei.api.transform.RectangleTransform;
import com.jfixby.scarabei.api.transform.Transform2D;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.iso.api.IsoTransformSpecs;

public class RedIsoTransform implements IsoTransform {

	private double h_scale;
	private double v_scale;
	private double off_x;
	private double off_y;
	private double z_project;
	private CustomAngle rot;
	private CustomAngle skew_x;
	private CustomAngle skew_y;
	private RectangleTransform prjector;
	private double pixelsToGameMeter;

	public RedIsoTransform(IsoTransformSpecs specs) {
		h_scale = specs.getHorizontalScale();
		v_scale = specs.getVerticalScale();
		off_x = specs.getOffsetX();
		off_y = specs.getOffsetY();
		rot = Angles.newAngle(specs.getRotation());
		z_project = specs.getZProjectionValue();
		skew_x = Angles.newAngle(specs.getHorizontalShiftAngle());
		skew_y = Angles.newAngle(specs.getVerticalShiftAngle());
		prjector = Transform2D.newRectangleTransform();

		pixelsToGameMeter = specs.getPixelsToGameMeter();

		prjector.setPosition(off_x, off_y);
		prjector.setRotation(rot);
		prjector.setSize(1, 1);
		prjector.setSkew(skew_x.toRadians(), skew_y.toRadians());
		prjector.setScale(h_scale, v_scale);

	}

	@Override
	public void project3Dto2D(ReadOnlyFloat3 input, Float2 output) {
		Debug.checkNull("input", input);
		Debug.checkNull("output", output);
		output.setX(input.getX());
		output.setY(input.getY());
		prjector.toAbsolute(output);
		output.setY(output.getY() - input.getZ() * z_project);
	}

	@Override
	public void project3Dto2D(Collection<? extends ReadOnlyFloat3> input, EditableCollection<Float2> output) {
		Debug.checkNull("input", input);
		Debug.checkNull("output", output);
		Geometry.fillUpFloat2(output, input.size());

		for (int i = 0; i < input.size(); i++) {
			this.project3Dto2D(input.getElementAt(i), output.getElementAt(i));
		}
	}

	@Override
	public double getPixelsToGameMeter() {
		return pixelsToGameMeter;
	}

	@Override
	public void project2DtoPixels(Float2 tmp) {
		tmp.setX(tmp.getX() * this.pixelsToGameMeter);
		tmp.setY(tmp.getY() * this.pixelsToGameMeter);
	}

	@Override
	public void project2DtoPixels(Collection<Float2> tmp) {
		Debug.checkNull("tmp", tmp);
		for (Iterator<Float2> i = tmp.iterator(); i.hasNext();) {
			this.project2DtoPixels(i.next());
		}
	}

	@Override
	public void unprojectPixelsTo2D(Float2 tmp) {
		tmp.setX(tmp.getX() / this.pixelsToGameMeter);
		tmp.setY(tmp.getY() / this.pixelsToGameMeter);
	}

	@Override
	public void unprojectPixelsTo2D(Collection<Float2> tmp) {
		Debug.checkNull("tmp", tmp);
		for (Iterator<Float2> i = tmp.iterator(); i.hasNext();) {
			this.unprojectPixelsTo2D(i.next());
		}
	}

	final Float2 tmp_vec = Geometry.newFloat2();

	@Override
	public void unproject2Dto3D(ReadOnlyFloat2 input_2d, double missing_default_z, Float3 output_3d) {
		Debug.checkNull("input_2d", input_2d);
		Debug.checkNull("output_3d", output_3d);
		tmp_vec.set(input_2d);
		prjector.toRelative(tmp_vec);
		output_3d.setX(tmp_vec.getX());
		output_3d.setY(tmp_vec.getY());
		output_3d.setZ(missing_default_z);
	}

	@Override
	public void unproject2Dto3D(Collection<ReadOnlyFloat2> input_2d, double missing_default_z,
			EditableCollection<Float3> output_3d) {
		Debug.checkNull("input_2d", input_2d);
		Debug.checkNull("output_3d", output_3d);

		Geometry.fillUpFloat3(output_3d, input_2d.size());

		final Iterator<Float3> j = output_3d.iterator();
		final Iterator<ReadOnlyFloat2> i = input_2d.iterator();
		for (; i.hasNext();) {
			this.unproject2Dto3D(i.next(), missing_default_z, j.next());
		}
	}
}
