
package com.jfixby.util.patch18.red.core;

import com.jfixby.r3.ext.api.patch18.P18PaletteFactory;
import com.jfixby.r3.ext.api.patch18.PaletteSpecs;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricRelationSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.err.Err;

class PaletteSpecsImpl implements PaletteSpecs {

	final Set<Fabric> fabrics = Collections.newSet();
	final Set<FabricsRelation> relations = Collections.newSet();
	private String name;
	private final P18PaletteFactory patch18Factory;

	public PaletteSpecsImpl (final P18PaletteFactory patch18Factory) {
		super();
		this.patch18Factory = patch18Factory;
	}

	@Override
	public void addFabric (final Fabric fabric) {
		this.fabrics.add(fabric);
	}

	@Override
	public FabricsRelation defineRelation (final Fabric upper_fabric, final Fabric lower_fabric) {
		if (!this.fabrics.contains(upper_fabric)) {
			Err.reportError(
				"Unknown fabric: " + upper_fabric + ". Use addFabric() method to add the fabric before defining it's relations.");
		}
		if (!this.fabrics.contains(lower_fabric)) {
			Err.reportError(
				"Unknown fabric: " + lower_fabric + ". Use addFabric() method to add the fabric before defining it's relations.");
		}

		final FabricRelationSpecs relation_specs = this.patch18Factory.newFacricRelationSpecs();
		relation_specs.setUpperFabric(upper_fabric);
		relation_specs.setLowerFabric(lower_fabric);
		final FabricsRelation relation = this.patch18Factory.newFacricRelation(relation_specs);
		this.relations.add(relation);
		return relation;
	}

	@Override
	public void setPaletteName (final String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return this.name;
	}

	public Set<Fabric> getFabricsList () {
		return this.fabrics;
	}

	public Set<FabricsRelation> getRelationsList () {
		return this.relations;
	}

}
