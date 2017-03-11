
package com.jfixby.util.terain.test.api.palette;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.util.terain.test.api.landscape.LandscapeFactory;

public class Terrain {
	static private ComponentInstaller<TerrainComponent> componentInstaller = new ComponentInstaller<TerrainComponent>("Terrain");

	public static final void installComponent (final TerrainComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final TerrainComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final TerrainComponent component () {
		return componentInstaller.getComponent();
	}

	public static TerrainPaletteFactory getPaletteFactory () {
		return invoke().getPaletteFactory();
	}

	public static LandscapeFactory getLandscapeFactory () {
		return invoke().getLandscapeFactory();
	}
}
