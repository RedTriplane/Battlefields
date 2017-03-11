
package com.jfixby.r3.tools.grid.test;

import com.jfixby.examples.wdgs.WDGS_P18_Palette;
import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.GridFactory;
import com.jfixby.r3.ext.api.patch18.GridSpecs;
import com.jfixby.r3.ext.api.patch18.P18;
import com.jfixby.r3.ext.api.patch18.grid.GridBrush;
import com.jfixby.r3.ext.api.patch18.grid.GridBrushApplicationResult;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.util.patch18.red.RedP18;

public class GridTest {

	public static void main (final String[] args) {

		ScarabeiDesktop.deploy();

		P18.installComponent(new RedP18());

		final GridFactory factory = P18.getGridFactory();

		final GridSpecs grid_specs = factory.newGridSpecs();
		grid_specs.setPalette(WDGS_P18_Palette.PALETTE);
		grid_specs.setActiveGridArea(null);

		final Grid grid = factory.newGrid(grid_specs);

		final GridBrush brush = grid.getBrush();
		brush.begin();
		brush.applyPaintAtCell(5, 5);
		brush.applyPaintAtCell(8, 8);
		brush.applyPaintAtCell(8, 5);

		final GridBrushApplicationResult result = brush.end();
		grid.print(0, 0, 30, 20);
	}

}
