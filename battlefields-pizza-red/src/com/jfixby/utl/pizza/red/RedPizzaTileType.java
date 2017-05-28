
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.utl.pizza.api.PizzaTileType;

public class RedPizzaTileType implements PizzaTileType {

	private ID id;

	public RedPizzaTileType (P18TerrainTypeVariation variation) {
		String name = variation.getName();
		id = variation.getID();
	}

	@Override
	public ID getID () {
		return id;
	}

}
