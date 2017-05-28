
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.p18t.api.P18LandscapeListener;
import com.jfixby.util.p18t.api.P18TerrainBlock;
import com.jfixby.util.terain.test.api.landscape.TerrainBlock;

public class RedP18LandscapeBlocks {

	final Map<TerrainBlock<ReadOnlyInt2>, RedP18TerrainBlock> blocks = Collections.newMap();

	private P18LandscapeListener listener;

	public void setListener (final P18LandscapeListener listener) {
		this.listener = listener;
	}

	public P18LandscapeListener getListener () {
		return this.listener;
	}

	public void alsoRemove (final TerrainBlock<ReadOnlyInt2> e) {
		final P18TerrainBlock<ReadOnlyInt2> p18_block = this.blocks.remove(e);
		if (p18_block == null) {
			Err.reportError("Block not found: " + e);
		}
		L.d("remove", p18_block);
		if (this.listener != null) {
			this.listener.onBlockRemove(p18_block);
		}
	}

	public void alsoAdd (final RedP18TerrainBlock new_p18_block) {
		final TerrainBlock<ReadOnlyInt2> key = new_p18_block.getTerrainBlock();
		final RedP18TerrainBlock old_p18_block = this.blocks.remove(key);
		if (old_p18_block != null) {
			Err.reportError("Landscape corrupted! " + old_p18_block);
		}

		this.blocks.put(key, new_p18_block);
		L.d("add", new_p18_block);
		if (this.listener != null) {
			this.listener.onBlockAdd(new_p18_block);
		}
	}

	public void alsoPointAt (final RedP18TerrainBlock p18_block) {
		L.d("point at", p18_block);
		if (this.listener != null) {
			this.listener.onBlockFocus(p18_block);
		}
	}

	public RedP18TerrainBlock findBlock (final TerrainBlock<ReadOnlyInt2> terrain_block) {
		return this.blocks.get(terrain_block);
	}

}
