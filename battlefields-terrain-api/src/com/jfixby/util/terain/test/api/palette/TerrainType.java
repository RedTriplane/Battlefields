package com.jfixby.util.terain.test.api.palette;

import com.jfixby.scarabei.api.gamedev.GameMeterFixed;
import com.jfixby.scarabei.api.names.ID;

public interface TerrainType {

	public ID getID();

	public GameMeterFixed getHeight();

	public GameMeterFixed getAltitude();

	public GameMeterFixed getXWidth();

	public GameMeterFixed getYWidth();

}
