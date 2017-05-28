
package com.jfixby.r3.ext.red.terrain.landscape;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.util.terain.test.api.landscape.LandscapeOperationResult;
import com.jfixby.util.terain.test.api.landscape.TerrainBlock;

public class RedLandscapeOperationResult<T> implements LandscapeOperationResult<T> {

	private List<TerrainBlock<T>> removed = Collections.newList();
	private List<TerrainBlock<T>> added = Collections.newList();
	private RedLandscape<T> master;

	public RedLandscapeOperationResult (RedLandscape<T> redLandscape) {
		this.master = redLandscape;

	}

	public void reset () {
		this.added.clear();
		this.removed.clear();
	}

	public void registerAdded (TerrainBlock<T> block) {
		Debug.checkNull("registerAdded", block);
		this.added.add(block);
	}

	public void registerRemoved (TerrainBlock<T> block) {
		Debug.checkNull("registerRemoved", block);
		this.removed.add(block);
	}

	@Override
	public Collection<TerrainBlock<T>> removedBlocks () {
		return this.removed;
	}

	@Override
	public Collection<TerrainBlock<T>> addedBlocks () {
		return this.added;
	}

}
