
package com.jfixby.utl.pizza.red;

import com.jfixby.utl.pizza.api.PizzaComponent;
import com.jfixby.utl.pizza.api.PizzaLandscapeFactory;
import com.jfixby.utl.pizza.api.PizzaPaletteFactory;

public class RedPizza implements PizzaComponent {
	final RedPizzaPaletteFactory palette_factory = new RedPizzaPaletteFactory();
	final RedPizzaLandscapeFactory scape_factory = new RedPizzaLandscapeFactory();

	@Override
	public PizzaPaletteFactory getPizzaPaletteFactory () {
		return palette_factory;
	}

	@Override
	public PizzaLandscapeFactory getPizzaLandscapeFactory () {
		return scape_factory;
	}

}
