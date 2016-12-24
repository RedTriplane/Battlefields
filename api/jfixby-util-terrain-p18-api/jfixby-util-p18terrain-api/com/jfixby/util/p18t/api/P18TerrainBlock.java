package com.jfixby.util.p18t.api;

import com.jfixby.scarabei.api.floatn.FixedFloat3;

public interface P18TerrainBlock<T> {

	public P18TerrainTypeVariation getVariation();

	public FixedFloat3 getPosition();

}
