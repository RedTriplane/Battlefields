
package com.jfixby.r3.ext.red.terrain.palette;

import com.jfixby.scarabei.api.gamedev.GameMeter;
import com.jfixby.scarabei.api.gamedev.GameMeterFixed;

public class TerrainMeasurementImpl implements GameMeter, GameMeterFixed {

	private double value;

	public TerrainMeasurementImpl (double d) {
		this.value = d;
	}

	@Override
	public double toDouble () {
		return value;
	}

	@Override
	public GameMeter setValue (GameMeterFixed other) {
		this.value = other.toDouble();
		return this;
	}

	@Override
	public String toString () {
		return "" + value + "Mt";
	}

}
