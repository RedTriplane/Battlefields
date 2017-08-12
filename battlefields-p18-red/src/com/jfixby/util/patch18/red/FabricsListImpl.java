
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.debug.Debug;

public class FabricsListImpl implements FabricsList {

	private Set<Fabric> fabricsList;

	public void setup (final Set<Fabric> fabricsList) {
		this.fabricsList = Debug.checkNull("fabricsList", fabricsList);
	}

	@Override
	public int size () {
		return this.fabricsList.size();
	}

	public Fabric getElementAt (final int i) {
		return this.fabricsList.getElementAt(i);
	}

	@Override
	public Fabric findFabricByName (final String string) {
		for (int i = 0; i < this.fabricsList.size(); i++) {
			final Fabric fabric_i = this.fabricsList.getElementAt(i);
			if (fabric_i.getName().equals(string)) {
				return fabric_i;
			}
		}

		return null;
	}

	@Override
	public Fabric getLast () {
		return this.fabricsList.getLast();
	}

	@Override
	public Fabric getFabric (final int i) {
		return this.fabricsList.getElementAt(i);
	}

	@Override
	public int indexOf (final Fabric material_a) {
		return this.fabricsList.indexOf(material_a);
	}

// @Override
// public void print (String tag) {
// fabricsList.print(tag);
// }

	@Override
	public boolean contains (final Fabric fabric) {
		return this.fabricsList.contains(fabric);
	}

}
