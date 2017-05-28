
package com.jfixby.r3.ext.red.terrain.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.gamedev.GameMeter;
import com.jfixby.scarabei.api.gamedev.GameMeterFixed;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteFactory;
import com.jfixby.util.terain.test.api.palette.TerrainType;
import com.jfixby.util.terain.test.api.palette.TerrainTypeSpecs;

public class TerrainTypeImpl implements TerrainType {

	private final GameMeter z_height;
	private final GameMeter z_alt;

	private final GameMeter x_width;
	private final GameMeter y_width;
	private ID name;

	public TerrainTypeImpl (TerrainTypeSpecs terrain_type_specs, TerrainPaletteFactory factory) {

		name = terrain_type_specs.getName();
		Debug.checkNull("name", name);

		z_height = factory.newMeasurement();
		z_height.setValue(terrain_type_specs.getZHeight());

		x_width = factory.newMeasurement();
		x_width.setValue(terrain_type_specs.getXWidth());

		y_width = factory.newMeasurement();
		y_width.setValue(terrain_type_specs.getYWidth());

		z_alt = factory.newMeasurement();
		z_alt.setValue(terrain_type_specs.getZAltitude());

	}

	@Override
	public GameMeterFixed getHeight () {
		return this.z_height;
	}

	@Override
	public GameMeterFixed getAltitude () {
		return this.z_alt;
	}

	@Override
	public GameMeterFixed getXWidth () {
		return this.x_width;
	}

	@Override
	public GameMeterFixed getYWidth () {
		return this.y_width;
	}

	@Override
	public ID getID () {
		return name;
	}

	@Override
	public String toString () {
		return "" + name + "(" + x_width + " x " + y_width + " x " + z_height + " ~ " + z_alt + ")";
	}

}
