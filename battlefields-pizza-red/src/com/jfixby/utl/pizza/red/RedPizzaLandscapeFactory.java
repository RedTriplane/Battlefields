
package com.jfixby.utl.pizza.red;

import com.jfixby.utl.pizza.api.PizzaLandscape;
import com.jfixby.utl.pizza.api.PizzaLandscapeFactory;
import com.jfixby.utl.pizza.api.PizzaLandscapeSpecs;

public class RedPizzaLandscapeFactory implements PizzaLandscapeFactory {

	@Override
	public PizzaLandscapeSpecs newLandscapeSpecs () {
		return new RedPizzaLandscapeSpecs();
	}

	@Override
	public PizzaLandscape newPizzaLandscape (PizzaLandscapeSpecs landscape_specs) {
		return new RedPizzaLandscape(landscape_specs);
	}

}
