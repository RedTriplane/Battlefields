
package com.jfixby.r3.ext.red.terrain.landscape;

import com.jfixby.util.terain.test.api.landscape.Landscape;
import com.jfixby.util.terain.test.api.landscape.LandscapeFactory;
import com.jfixby.util.terain.test.api.landscape.LandscapeSpecs;

public class RedLandscapeFactory implements LandscapeFactory {

	public RedLandscapeSpecs newLandscapeSpecs () {
		return new RedLandscapeSpecs();
	}

	@Override
	public <T> Landscape<T> newLandscape (LandscapeSpecs landscape_specs) {
		return new RedLandscape<T>(landscape_specs);
	}

}
