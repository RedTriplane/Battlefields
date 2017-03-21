
package com.jfixby.util.iso.red;

import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.util.iso.api.ISOComponent;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.iso.api.IsoTransformSpecs;

public class RedIsometry implements ISOComponent {

	private IsoTransform FALLOUT;

	@Override
	public IsoTransformSpecs newTransformSpecs () {
		return new RedIsoTransformSpecs();
	}

	@Override
	public IsoTransform newTransform (final IsoTransformSpecs specs) {
		return new RedIsoTransform(specs);
	}

	@Override
	public IsoTransform getFallout (final double pixelsToGameMeter) {
		if (this.FALLOUT == null) {
			final IsoTransformSpecs specs = this.newTransformSpecs();

			final boolean ORTO = !true;
			double m1 = 0;
			double m2 = 0;
			if (ORTO) {
				m1 = 0;
				m2 = 1;
			} else {
				m1 = 01;
				m2 = 0;
			}

			specs.setRotation(Angles.newAngle(FloatMath.toRadians(m1 * -15)));

			specs.setHorizontalShiftAngle(Angles.newAngle(FloatMath.toRadians(m1 * 38)));
			specs.setVerticalShiftAngle(Angles.newAngle());

			specs.setHorizontalScale(1);
			specs.setPixelsToGameMeter(pixelsToGameMeter);
			specs.setVerticalScale((FloatMath.cos(FloatMath.toRadians(38)) * m1 + m2 * 1));

			this.FALLOUT = this.newTransform(specs);

		}
		return this.FALLOUT;
	}

}
