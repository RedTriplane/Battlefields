package com.jfixby.util.terain.test.api.palette;

import com.jfixby.scarabei.api.gamedev.GameMeter;

public interface TerrainPaletteFactory {

	GameMeter newMeasurement(double d);

	GameMeter newMeasurement();

	TerrainTypeSpecs newTerrainBlockSpecs();

	TerrainType newTerrainType(TerrainTypeSpecs terrain_type_specs);

	TerrainPaletteSpecs newTerrainPaletteSpecs();

	TerrainPalette newTerrainPalette(TerrainPaletteSpecs palette_specs);

	
}
