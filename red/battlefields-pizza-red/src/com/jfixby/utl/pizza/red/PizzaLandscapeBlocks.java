
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.p18t.api.P18LandscapeListener;
import com.jfixby.util.p18t.api.P18TerrainBlock;
import com.jfixby.utl.pizza.api.PizzaLandscapeListener;
import com.jfixby.utl.pizza.api.PizzaTile;
import com.jfixby.utl.pizza.api.PizzaTileType;

public class PizzaLandscapeBlocks {
	private PizzaLandscapeListener pizza_landscape_listener;

	private final Map<P18TerrainBlock<ReadOnlyInt2>, RedPizzaTile> mapping = Collections.newMap();
	private final List<PizzaTile> all_blocks = Collections.newList();

	private final RedPizzaLandscape master;

	public PizzaLandscapeBlocks (final RedPizzaLandscape master) {
		this.master = master;
	}

	public PizzaLandscapeListener getListener () {
		return this.pizza_landscape_listener;
	}

	public void setListener (final PizzaLandscapeListener listener) {
		this.pizza_landscape_listener = listener;
	}

	public P18LandscapeListener getP18LandscapeListener () {
		return this.p18_landscape_listener;
	}

	public void print () {
		this.all_blocks.print("PizzaLandscapeBlocks");
	}

	private void doRemove (final RedPizzaTile to_remove) {
		final P18TerrainBlock<ReadOnlyInt2> key = to_remove.getP18TerrainBlock();
		final RedPizzaTile block = this.mapping.remove(key);
		L.d("remove", block);
		this.all_blocks.remove(block);
		if (this.pizza_landscape_listener != null) {
			this.pizza_landscape_listener.onBlockRemove(block);
		}
	}

	private void doAdd (final RedPizzaTile to_add) {
		final P18TerrainBlock<ReadOnlyInt2> key = to_add.getP18TerrainBlock();
		Debug.checkNull("key", key);
		this.mapping.put(key, to_add);
		L.d("add", to_add);
		this.all_blocks.add(to_add);
		if (this.pizza_landscape_listener != null) {
			this.pizza_landscape_listener.onBlockAdd(to_add);
		}
	}

	private void doFocus (final RedPizzaTile to_focus) {
		L.d("to_focus", to_focus);
		if (this.pizza_landscape_listener != null) {
			this.pizza_landscape_listener.onBlockFocus(to_focus);
		}
	}

	private final P18LandscapeListener p18_landscape_listener = new P18LandscapeListener() {
		@Override
		public void onBlockRemove (final P18TerrainBlock<ReadOnlyInt2> block) {
			Debug.checkNull("block", block);
			final RedPizzaTile to_remove = PizzaLandscapeBlocks.this.mapping.get(block);
			if (to_remove == null) {
				Err.reportError("Not found " + block);
			} else {
				PizzaLandscapeBlocks.this.doRemove(to_remove);
			}
		}

		@Override
		public void onBlockAdd (final P18TerrainBlock<ReadOnlyInt2> block) {
			Debug.checkNull("block", block);
			final RedPizzaTile to_remove = PizzaLandscapeBlocks.this.mapping.get(block);
			if (to_remove != null) {
				Err.reportError("Corruption " + block);
			} else {
				final PizzaTileType type = PizzaLandscapeBlocks.this.master.findPizzaType(block);
				final RedPizzaTile to_add = new RedPizzaTile(type, block, PizzaLandscapeBlocks.this.master);
				PizzaLandscapeBlocks.this.doAdd(to_add);
			}
		}

		@Override
		public void onBlockFocus (final P18TerrainBlock<ReadOnlyInt2> block) {
			// Debug.checkNull("block", block);
			if (block == null) {
				PizzaLandscapeBlocks.this.doFocus(null);
			} else {
				final RedPizzaTile to_focus = PizzaLandscapeBlocks.this.mapping.get(block);
				PizzaLandscapeBlocks.this.doFocus(to_focus);
			}
		}

	};

	public Collection<PizzaTile> listAll () {
		return this.all_blocks;
	}
}
