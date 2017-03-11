
package com.jfixby.utl.pizza.test;

import com.jfixby.examples.wdgs.WDGS_Pizza_Palette;
import com.jfixby.r3.ext.api.patch18.P18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.p18t.red.RedP18Terrain;
import com.jfixby.r3.ext.red.terrain.RedTerrain;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.util.iso.api.Isometry;
import com.jfixby.util.iso.red.RedIsometry;
import com.jfixby.util.p18t.api.P18Terrain;
import com.jfixby.util.patch18.red.RedP18;
import com.jfixby.util.terain.test.api.palette.Terrain;
import com.jfixby.utl.pizza.api.Pizza;
import com.jfixby.utl.pizza.api.PizzaBrush;
import com.jfixby.utl.pizza.api.PizzaBrushPointer;
import com.jfixby.utl.pizza.api.PizzaLandscape;
import com.jfixby.utl.pizza.api.PizzaLandscapeFactory;
import com.jfixby.utl.pizza.api.PizzaLandscapeListener;
import com.jfixby.utl.pizza.api.PizzaLandscapeSpecs;
import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaTile;
import com.jfixby.utl.pizza.red.RedPizza;

public class TestPizza {

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();

		P18Terrain.installComponent(new RedP18Terrain());
		P18.installComponent(new RedP18());
		Terrain.installComponent(new RedTerrain());
		Pizza.installComponent(new RedPizza());
		Isometry.installComponent(new RedIsometry());
// Transform2D.installComponent(new RedIsometry());

		final PizzaPalette wdgs_pizza_palette = WDGS_Pizza_Palette.PALETTE;

		final PizzaLandscapeFactory landscape_fac = Pizza.invoke().getPizzaLandscapeFactory();

		final PizzaLandscapeSpecs landscape_specs = landscape_fac.newLandscapeSpecs();
		landscape_specs.setActiveArea(0, 0, 1024 * 4, 1024 * 3);
		landscape_specs.setPalette(wdgs_pizza_palette);

		final PizzaLandscape pizza_scape = landscape_fac.newPizzaLandscape(landscape_specs);

		final PizzaLandscapeListener listener = new PizzaLandscapeListener() {

			@Override
			public void onBlockRemove (final PizzaTile block) {
			}

			@Override
			public void onBlockAdd (final PizzaTile block) {
			}

			@Override
			public void onBlockFocus (final PizzaTile block) {
			}

		};

		pizza_scape.setLandscapeListener(listener);

		final PizzaBrush brush = pizza_scape.getBrush();
		final Fabric default_fabric = wdgs_pizza_palette.getP18Palette().listFabrics().getFabric(0);// size>0
		brush.setFabric(default_fabric);
		{
			final PizzaBrushPointer pointer = brush.pointAtCanvas(0, 0);
			// FixedFloat3 pointer_position = pointer.getTerrainPosition();
			brush.applyPaint();
		}

		pizza_scape.print();

	}
}
