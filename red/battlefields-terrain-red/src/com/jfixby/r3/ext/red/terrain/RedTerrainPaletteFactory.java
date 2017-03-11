
package com.jfixby.r3.ext.red.terrain;

import com.jfixby.r3.ext.red.terrain.palette.TerrainMeasurementImpl;
import com.jfixby.r3.ext.red.terrain.palette.TerrainPaletteImpl;
import com.jfixby.r3.ext.red.terrain.palette.TerrainPaletteSpecsImpl;
import com.jfixby.r3.ext.red.terrain.palette.TerrainTypeImpl;
import com.jfixby.r3.ext.red.terrain.palette.TerrainTypeSpecsImpl;
import com.jfixby.scarabei.api.gamedev.GameMeter;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteFactory;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteSpecs;
import com.jfixby.util.terain.test.api.palette.TerrainType;
import com.jfixby.util.terain.test.api.palette.TerrainTypeSpecs;

public class RedTerrainPaletteFactory implements TerrainPaletteFactory {

	@Override
	public TerrainTypeSpecs newTerrainBlockSpecs () {
		return new TerrainTypeSpecsImpl();
	}

	@Override
	public TerrainType newTerrainType (TerrainTypeSpecs terrain_type_specs) {
		return new TerrainTypeImpl(terrain_type_specs, this);
	}

	@Override
	public GameMeter newMeasurement (double d) {
		return new TerrainMeasurementImpl(d);
	}

	@Override
	public GameMeter newMeasurement () {
		return new TerrainMeasurementImpl(0d);
	}

	@Override
	public TerrainPaletteSpecs newTerrainPaletteSpecs () {
		return new TerrainPaletteSpecsImpl();
	}

	@Override
	public TerrainPalette newTerrainPalette (TerrainPaletteSpecs palette_specs) {
		return new TerrainPaletteImpl(palette_specs);
	}

}
