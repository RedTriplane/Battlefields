
package com.jfixby.util.iso.red;

import java.util.Iterator;

import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.EditableCollection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.Float3;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.CustomAngle;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.iso.api.IsoTransformSpecs;

public class RedIsoTransform implements IsoTransform {

	private final double h_scale;
	private final double v_scale;
	private final double off_x;
	private final double off_y;
	private final double z_project;
	private final CustomAngle rot;
	private final CustomAngle skew_x;
	private final CustomAngle skew_y;
	private final RedIsoRectangleTransform prjector;
	private final double pixelsToGameMeter;

	public RedIsoTransform (final IsoTransformSpecs specs) {
		this.h_scale = specs.getHorizontalScale();
		this.v_scale = specs.getVerticalScale();
		this.off_x = specs.getOffsetX();
		this.off_y = specs.getOffsetY();
		this.rot = Angles.newAngle(specs.getRotation());
		this.z_project = specs.getZProjectionValue();
		this.skew_x = Angles.newAngle(specs.getHorizontalShiftAngle());
		this.skew_y = Angles.newAngle(specs.getVerticalShiftAngle());
		this.prjector = new RedIsoRectangleTransform();

		this.pixelsToGameMeter = specs.getPixelsToGameMeter();

		this.prjector.setPosition(this.off_x, this.off_y);
		this.prjector.setRotation(this.rot);
		this.prjector.setSize(1, 1);
		this.prjector.setSkew(this.skew_x, this.skew_y);
		this.prjector.setScale(this.h_scale, this.v_scale);

	}

	@Override
	public void project3Dto2D (final ReadOnlyFloat3 input, final Float2 output) {
		Debug.checkNull("input", input);
		Debug.checkNull("output", output);
		output.setX(input.getX());
		output.setY(input.getY());
		this.prjector.project(output);
		output.setY(output.getY() - input.getZ() * this.z_project);
	}

	@Override
	public void project3Dto2D (final Collection<? extends ReadOnlyFloat3> input, final EditableCollection<Float2> output) {
		Debug.checkNull("input", input);
		Debug.checkNull("output", output);
		Geometry.fillUpFloat2(output, input.size());

		for (int i = 0; i < input.size(); i++) {
			this.project3Dto2D(input.getElementAt(i), output.getElementAt(i));
		}
	}

	@Override
	public double getPixelsToGameMeter () {
		return this.pixelsToGameMeter;
	}

	@Override
	public void project2DtoPixels (final Float2 tmp) {
		tmp.setX(tmp.getX() * this.pixelsToGameMeter);
		tmp.setY(tmp.getY() * this.pixelsToGameMeter);
	}

	@Override
	public void project2DtoPixels (final Collection<Float2> tmp) {
		Debug.checkNull("tmp", tmp);
		for (final Iterator<Float2> i = tmp.iterator(); i.hasNext();) {
			this.project2DtoPixels(i.next());
		}
	}

	@Override
	public void unprojectPixelsTo2D (final Float2 tmp) {
		tmp.setX(tmp.getX() / this.pixelsToGameMeter);
		tmp.setY(tmp.getY() / this.pixelsToGameMeter);
	}

	@Override
	public void unprojectPixelsTo2D (final Collection<Float2> tmp) {
		Debug.checkNull("tmp", tmp);
		for (final Iterator<Float2> i = tmp.iterator(); i.hasNext();) {
			this.unprojectPixelsTo2D(i.next());
		}
	}

	final Float2 tmp_vec = Geometry.newFloat2();

	@Override
	public void unproject2Dto3D (final ReadOnlyFloat2 input_2d, final double missing_default_z, final Float3 output_3d) {
		Debug.checkNull("input_2d", input_2d);
		Debug.checkNull("output_3d", output_3d);
		this.tmp_vec.set(input_2d);
		this.prjector.unProject(this.tmp_vec);
		output_3d.setX(this.tmp_vec.getX());
		output_3d.setY(this.tmp_vec.getY());
		output_3d.setZ(missing_default_z);
	}

	@Override
	public void unproject2Dto3D (final Collection<ReadOnlyFloat2> input_2d, final double missing_default_z,
		final EditableCollection<Float3> output_3d) {
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
