
package com.jfixby.utl.pizza.red;

import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaPaletteFactory;
import com.jfixby.utl.pizza.api.PizzaPaletteSpecs;

public class RedPizzaPaletteFactory implements PizzaPaletteFactory {

	@Override
	public PizzaPaletteSpecs newPizzaPaletteSpecs () {
		return new RedPizzaPaletteSpecs();
	}

	@Override
	public PizzaPalette newPizzaPalette (PizzaPaletteSpecs specs) {
		return new RedPizzaPalette(specs);
	}

}
