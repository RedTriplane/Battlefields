
package com.jfixby.r3.ext.red.terrain.palette;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;
import com.jfixby.scarabei.api.names.NamespaceRegistry;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class TerrainTypeRegister {

	NamespaceRegistry<TerrainType> registry = Names.newRegistry();

// public void print (String tag) {
// registry.print(tag);
// }

	public void register (final TerrainType block) {
		final ID asset_id = (block.getID());
		this.registry.put(asset_id, block);
	}

	public Collection<TerrainType> listBlocks (final ID tile_id) {
		final Collection<TerrainType> result = this.registry.get(tile_id);
		return result;
	}

	public Collection<TerrainType> listAllBlocks () {
		return this.registry.allValues();
	}

}
