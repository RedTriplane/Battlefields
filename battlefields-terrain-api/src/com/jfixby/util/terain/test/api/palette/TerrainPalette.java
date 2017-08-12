
package com.jfixby.util.terain.test.api.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface TerrainPalette {

// public void print();

	public Collection<TerrainType> listBlocks (ID terrain_block_id);

	public Collection<TerrainType> listAllBlocks ();

}
