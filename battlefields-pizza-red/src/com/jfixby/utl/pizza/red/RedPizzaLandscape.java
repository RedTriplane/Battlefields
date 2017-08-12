
package com.jfixby.utl.pizza.red;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.math.ReadOnlyInt2;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.p18t.api.P18Landscape;
import com.jfixby.util.p18t.api.P18LandscapeFactory;
import com.jfixby.util.p18t.api.P18LandscapeSpecs;
import com.jfixby.util.p18t.api.P18Terrain;
import com.jfixby.util.p18t.api.P18TerrainBlock;
import com.jfixby.util.p18t.api.P18TerrainTypeVariation;
import com.jfixby.utl.pizza.api.LandscapeActiveArea;
import com.jfixby.utl.pizza.api.PizzaBrush;
import com.jfixby.utl.pizza.api.PizzaLandscape;
import com.jfixby.utl.pizza.api.PizzaLandscapeListener;
import com.jfixby.utl.pizza.api.PizzaLandscapeSpecs;
import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaTile;
import com.jfixby.utl.pizza.api.PizzaTileType;

public class RedPizzaLandscape implements PizzaLandscape {
	final private RedPizzaBrush brush;
	final private PizzaPalette palette;
	final private RedPizzaLandscapeActiveArea active_area;
	final private IsoTransform isometry;
	private final PizzaLandscapeBlocks pizza_blocks;
	private final P18Landscape p18_landscape;

	public RedPizzaLandscape (final PizzaLandscapeSpecs landscape_specs) {
		final float or_x = landscape_specs.getActiveAreaOriginX();
		final float or_y = landscape_specs.getActiveAreaOriginY();
		final float height = landscape_specs.getActiveAreaHeight();
		final float width = landscape_specs.getActiveAreaWidth();
		this.palette = landscape_specs.getPalette();
		this.isometry = Debug.checkNull("isometry", this.palette.getIsoTransform());
		this.active_area = new RedPizzaLandscapeActiveArea();
		this.active_area.setup(or_x, or_y, width, height);

		final P18LandscapeFactory landscape_factory = P18Terrain.invoke().getP18LandscapeFactory();

		final P18LandscapeSpecs land_specs = landscape_factory.newP18LandscapeSpecs();
		land_specs.setPalette(this.palette.getP18TerrainPalette());
		this.p18_landscape = landscape_factory.newP18Landscape(land_specs);
		this.brush = new RedPizzaBrush(this);

		this.pizza_blocks = new PizzaLandscapeBlocks(this);
		this.p18_landscape.setListener(this.pizza_blocks.getP18LandscapeListener());
	}

	@Override
	public PizzaBrush getBrush () {
		return this.brush;
	}

	@Override
	public void setLandscapeListener (final PizzaLandscapeListener listener) {
		this.pizza_blocks.setListener(listener);
	}

	@Override
	public LandscapeActiveArea getActiveArea () {
		return this.active_area;
	}

// @Override
// public void print () {
// pizza_blocks.print();
// }

	public PizzaPalette getPalette () {
		return this.palette;
	}

	public IsoTransform getIsometry () {
		return this.isometry;
	}

	public P18Landscape getP18Landscape () {
		return this.p18_landscape;
	}

	public PizzaLandscapeListener getListener () {
		return this.pizza_blocks.getListener();
	}

	@Override
	public Collection<PizzaTile> listAllBlocks () {
		return this.pizza_blocks.listAll();
	}

	public PizzaTileType findPizzaType (final P18TerrainBlock<ReadOnlyInt2> block) {
		final P18TerrainTypeVariation var = block.getVariation();
		final PizzaTileType pizza_type = this.palette.listTiles().findPizzaType(var);
		return pizza_type;
	}
}
