
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.scarabei.api.floatn.Float3;
import com.jfixby.scarabei.api.gamedev.GameMeter;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.math.Int2;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.util.p18t.api.P18LandscapePointer;
import com.jfixby.util.p18t.api.P18TerrainPalette;

public class RedP18LandscapePointer implements P18LandscapePointer {

	final Float3 tmp_3 = Geometry.newFloat3();

	private RedP18LandscapeBrush brush;

	final Int2 position_2d = IntegerMath.newInt2();

	private P18TerrainPalette palette;

	public RedP18LandscapePointer (RedP18LandscapeBrush redP18LandscapeBrush, P18TerrainPalette palette) {
		this.brush = redP18LandscapeBrush;
		this.palette = palette;
	}

	public ReadOnlyInt2 getBlockGridPosition () {
		return position_2d;
	}

	public void set (Int2 tmp_2) {
		position_2d.setXY(tmp_2);
	}

	public void pointAt (double x, double y, double z) {
		tmp_3.setXYZ(x, y, z);
		unproject(tmp_3, position_2d);

	}

	private void unproject (final Float3 tmp_3, final Int2 tmp_2) {
		final GameMeter w_x = palette.getBlockXWidth();
		final GameMeter w_y = palette.getBlockYWidth();
		final double f_x = (tmp_3.getX() / w_x.toDouble());
		final double f_y = (tmp_3.getY() / w_y.toDouble());
		final long i_x = FloatMath.round(f_x);
		final long i_y = FloatMath.round(f_y);
		tmp_2.setX(i_x);
		tmp_2.setY(i_y);
	}

	public void get3DPositionOf (ReadOnlyInt2 position_2d, Float3 position_3d) {
		final GameMeter w_x = palette.getBlockXWidth();
		final GameMeter w_y = palette.getBlockYWidth();
		final double f_x = (position_2d.getX() * w_x.toDouble());
		final double f_y = (position_2d.getY() * w_y.toDouble());
		position_3d.setZ(0);
		position_3d.setX(f_x);
		position_3d.setY(f_y);
	}

}
