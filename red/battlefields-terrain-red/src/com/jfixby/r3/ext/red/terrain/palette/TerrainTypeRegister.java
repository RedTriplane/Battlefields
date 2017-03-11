
package com.jfixby.r3.ext.red.terrain.palette;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.assets.NamespaceRegistry;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class TerrainTypeRegister {

	NamespaceRegistry<TerrainType> registry = Names.newRegistry();

	public void print (String tag) {
		registry.print(tag);
	}

	public void register (TerrainType block) {
		ID asset_id = (block.getID());
		registry.put(asset_id, block);
	}

	public Collection<TerrainType> listBlocks (ID tile_id) {
		Collection<TerrainType> result = registry.get(tile_id);
		return result;
	}

	public Collection<TerrainType> listAllBlocks () {
		return registry.allValues();
	}

}
