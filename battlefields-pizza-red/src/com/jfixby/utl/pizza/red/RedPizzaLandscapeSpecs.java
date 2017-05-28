
package com.jfixby.utl.pizza.red;

import com.jfixby.utl.pizza.api.PizzaLandscapeSpecs;
import com.jfixby.utl.pizza.api.PizzaPalette;

public class RedPizzaLandscapeSpecs implements PizzaLandscapeSpecs {

	private float canvas_origin_x;
	private float canvas_origin_y;
	private float canvas_width;
	private float canvas_height;
	private PizzaPalette pizza_palette;

	@Override
	public void setActiveArea (float canvas_origin_x, float canvas_origin_y, float canvas_width, float canvas_height) {
		this.canvas_origin_x = canvas_origin_x;
		this.canvas_origin_y = canvas_origin_y;
		this.canvas_width = canvas_width;
		this.canvas_height = canvas_height;
	}

	@Override
	public void setPalette (PizzaPalette pizza_palette) {
		this.pizza_palette = pizza_palette;
	}

	@Override
	public PizzaPalette getPalette () {
		return pizza_palette;
	}

	@Override
	public float getActiveAreaOriginX () {
		return canvas_origin_x;
	}

	@Override
	public float getActiveAreaOriginY () {
		return canvas_origin_y;
	}

	@Override
	public float getActiveAreaWidth () {
		return canvas_width;
	}

	@Override
	public float getActiveAreaHeight () {
		return canvas_height;
	}

}
