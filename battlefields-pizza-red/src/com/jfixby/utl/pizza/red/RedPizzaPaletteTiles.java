
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.util.p18t.api.P18TerrainTypeVariationsList;
import com.jfixby.utl.pizza.api.PizzaTileType;
import com.jfixby.utl.pizza.api.PizzaTilesList;

public class RedPizzaPaletteTiles implements PizzaTilesList {

	final List<PizzaTileType> tiles = Collections.newList();
	final Map<P18TerrainTypeVariation, RedPizzaTileType> reg = Collections.newMap();

	public Collection<PizzaTileType> listAll () {
		return tiles;
	}

	public void setup (P18TerrainPalette p18_palette) {

		Collection<P18TerrainTypeVariationsList> variations = p18_palette.listVariationsAll();

		for (int i = 0; i < variations.size(); i++) {
			P18TerrainTypeVariationsList var = variations.getElementAt(i);
			for (int k = 0; k < var.size(); k++) {
				P18TerrainTypeVariation variation = var.getVariation(k);
				spawn_tile(variation);
			}

		}

	}

	private void spawn_tile (P18TerrainTypeVariation variation) {
		RedPizzaTileType tile_type = new RedPizzaTileType(variation);
		reg.put(variation, tile_type);
		tiles.add(tile_type);
	}

	@Override
	public PizzaTileType findPizzaType (P18TerrainTypeVariation var) {
		return reg.get(var);
	}

	@Override
	public int size () {
		return tiles.size();
	}

	@Override
	public PizzaTileType getElementAt (int index) {
		return tiles.getElementAt(index);
	}

}
