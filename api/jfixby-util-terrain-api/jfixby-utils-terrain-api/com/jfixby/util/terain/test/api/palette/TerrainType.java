package com.jfixby.util.terain.test.api.palette;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.cmns.api.gamedev.GameMeterFixed;

public interface TerrainType {

	public ID getID();

	public GameMeterFixed getHeight();

	public GameMeterFixed getAltitude();

	public GameMeterFixed getXWidth();

	public GameMeterFixed getYWidth();

}
