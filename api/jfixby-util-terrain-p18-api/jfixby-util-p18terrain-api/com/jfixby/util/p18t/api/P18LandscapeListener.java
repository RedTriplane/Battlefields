package com.jfixby.util.p18t.api;

import com.jfixby.scarabei.api.math.ReadOnlyInt2;

public interface P18LandscapeListener {

	void onBlockRemove(P18TerrainBlock<ReadOnlyInt2> block);

	void onBlockAdd(P18TerrainBlock<ReadOnlyInt2> block);

	void onBlockFocus(P18TerrainBlock<ReadOnlyInt2> block);

}
