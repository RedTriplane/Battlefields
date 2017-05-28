
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class FabricsRelationListImpl implements RelationsList {

	private Set<FabricsRelation> relations;

	public void setup (final Set<FabricsRelation> relationsList) {
		this.relations = Debug.checkNull("relationsList", relationsList);
	}

	public FabricsRelation getElementAt (final int i) {
		return this.relations.getElementAt(i);
	}

	@Override
	public FabricsRelation findRelationFor (final Fabric fabric_a, final Fabric fabric_b) {
		if (fabric_a == null) {
			Err.reportError("Fabric A is null");
		}
		if (fabric_b == null) {
			Err.reportError("Fabric B is null");
		}

		for (int i = 0; i < this.relations.size(); i++) {
			final FabricsRelation relation = this.relations.getElementAt(i);
			if (relation.isAbout(fabric_a, fabric_b)) {
				return relation;
			}
		}
		return null;
	}

	@Override
	public int size () {
		return this.relations.size();
	}

	@Override
	public FabricsRelation getRelation (final int i) {
		return this.relations.getElementAt(i);
	}

	@Override
	public int indexOf (final FabricsRelation relation) {
		return this.relations.indexOf(relation);
	}

	@Override
	public void print (final String tag) {
		this.relations.print(tag);
	}

}
