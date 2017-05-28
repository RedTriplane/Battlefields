package com.jfixby.util.p18t.api;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat3;

public interface P18TerrainBlock<T> {

	public P18TerrainTypeVariation getVariation();

	public ReadOnlyFloat3 getPosition();

}
