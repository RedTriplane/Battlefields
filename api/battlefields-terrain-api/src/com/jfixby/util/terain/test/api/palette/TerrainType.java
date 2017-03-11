package com.jfixby.util.terain.test.api.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.gamedev.GameMeterFixed;

public interface TerrainType {

	public ID getID();

	public GameMeterFixed getHeight();

	public GameMeterFixed getAltitude();

	public GameMeterFixed getXWidth();

	public GameMeterFixed getYWidth();

}
