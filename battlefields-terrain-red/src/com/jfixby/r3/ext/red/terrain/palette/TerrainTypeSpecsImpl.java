
package com.jfixby.r3.ext.red.terrain.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.gamedev.GameMeter;
import com.jfixby.util.terain.test.api.palette.TerrainTypeSpecs;

public class TerrainTypeSpecsImpl implements TerrainTypeSpecs {
	@Override
	public ID getName () {
		return variationName;
	}

	@Override
	public void setName (ID variationName) {
		this.variationName = variationName;
	}

	@Override
	public GameMeter getXWidth () {
		return XWidth;
	}

	@Override
	public void setXWidth (GameMeter xWidth) {
		XWidth = xWidth;
	}

	@Override
	public GameMeter getYWidth () {
		return YWidth;
	}

	@Override
	public void setYWidth (GameMeter yWidth) {
		YWidth = yWidth;
	}

	@Override
	public GameMeter getZHeight () {
		return ZHeight;
	}

	@Override
	public void setZHeight (GameMeter zHeight) {
		ZHeight = zHeight;
	}

	@Override
	public GameMeter getZAltitude () {
		return ZAltitude;
	}

	@Override
	public void setZAltitude (GameMeter zAltitude) {
		ZAltitude = zAltitude;
	}

	ID variationName;

	GameMeter XWidth;
	GameMeter YWidth;
	GameMeter ZHeight;
	GameMeter ZAltitude;

}
