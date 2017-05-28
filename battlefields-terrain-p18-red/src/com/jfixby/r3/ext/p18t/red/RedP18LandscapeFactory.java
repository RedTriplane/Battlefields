
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.util.p18t.api.P18Landscape;
import com.jfixby.util.p18t.api.P18LandscapeFactory;
import com.jfixby.util.p18t.api.P18LandscapeSpecs;

public class RedP18LandscapeFactory implements P18LandscapeFactory {

	@Override
	public P18LandscapeSpecs newP18LandscapeSpecs () {
		return new RedP18LandscapeSpecs();
	}

	@Override
	public P18Landscape newP18Landscape (P18LandscapeSpecs land_specs) {
		return new RedP18Landscape(land_specs);
	}

}
