
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class RedTerrainVariation implements P18TerrainTypeVariation {

	private Patch18 p18val;
	private FabricsRelation relation;
	private TerrainType block;

	public RedTerrainVariation (Patch18 p18val, FabricsRelation relation, TerrainType block) {
		this.p18val = p18val;
		this.relation = relation;
		this.block = block;
	}

	@Override
	public TerrainType getProperties () {
		return block;
	}

	@Override
	public String getName () {
		return block.getID().getLastStep();
	}

	@Override
	public ID getID () {
		return block.getID();
	}

	@Override
	public Patch18 getShape () {
		return p18val;
	}

	@Override
	public FabricsRelation getRelation () {
		return relation;
	}

	@Override
	public String toString () {
		return "TerrainVariation[" + relation + "." + p18val + "] " + block + "";
	}

}
