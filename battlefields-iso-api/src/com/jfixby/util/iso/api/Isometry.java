
package com.jfixby.util.iso.api;

import com.jfixby.scarabei.api.ComponentInstaller;

public class Isometry {

	static private ComponentInstaller<ISOComponent> componentInstaller = new ComponentInstaller<ISOComponent>("Isometry");

	public static final void installComponent (final ISOComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final ISOComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final ISOComponent component () {
		return componentInstaller.getComponent();
	}

	public static IsoTransformSpecs newTransformSpecs () {
		return invoke().newTransformSpecs();
	}

	public static IsoTransform newTransform (final IsoTransformSpecs specs) {
		return invoke().newTransform(specs);
	}

	public static IsoTransform getFallout (final double pixels_to_game_meter) {
		return invoke().getFallout(pixels_to_game_meter);
	}

}
