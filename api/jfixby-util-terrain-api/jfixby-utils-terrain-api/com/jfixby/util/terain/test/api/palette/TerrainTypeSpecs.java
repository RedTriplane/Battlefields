package com.jfixby.util.terain.test.api.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.gamedev.GameMeter;

public interface TerrainTypeSpecs {

	void setName(ID name);

	ID getName();

	void setZHeight(GameMeter block_z_height);

	GameMeter getZHeight();

	GameMeter getXWidth();

	void setXWidth(GameMeter block_x_width);

	void setYWidth(GameMeter block_y_width);

	GameMeter getYWidth();

	void setZAltitude(GameMeter block_z_altitude);

	GameMeter getZAltitude();

}
