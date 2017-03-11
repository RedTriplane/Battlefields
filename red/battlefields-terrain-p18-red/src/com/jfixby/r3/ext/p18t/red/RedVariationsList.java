
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.util.p18t.api.P18TerrainTypeVariationsList;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public class RedVariationsList implements P18TerrainTypeVariationsList {

	final Set<P18TerrainTypeVariation> reg_list = Collections.newSet();
	private Patch18 p18val;
	private FabricsRelation relation;

	public RedVariationsList (Patch18 p18val, FabricsRelation relation, Collection<TerrainType> blocks) {
		this.p18val = p18val;
		this.relation = relation;
		for (int i = 0; i < blocks.size(); i++) {
			TerrainType block = blocks.getElementAt(i);
			RedTerrainVariation var = new RedTerrainVariation(p18val, relation, block);
			reg_list.add(var);
		}
	}

	public void register (P18TerrainTypeVariation variation) {
		reg_list.add(variation);
	}

	@Override
	public String toString () {
		return this.reg_list.toString();

	}

	@Override
	public P18TerrainTypeVariation getRandomElement () {
		if (this.reg_list.size() == 1) {
			P18TerrainTypeVariation e = reg_list.getElementAt(0);
			return e;
		} else {
			java.util.List<P18TerrainTypeVariation> tmp_list = reg_list.toJavaList();
			java.util.Collections.shuffle(tmp_list);
			return tmp_list.get(0);
		}
	}

	@Override
	public void print (String tag) {
		reg_list.print(tag);
	}

	@Override
	public FabricsRelation getRelation () {
		return relation;
	}

	@Override
	public Patch18 getShape () {
		return p18val;
	}

	@Override
	public int size () {
		return reg_list.size();
	}

	@Override
	public P18TerrainTypeVariation getVariation (int k) {
		return reg_list.getElementAt(k);
	}
}
