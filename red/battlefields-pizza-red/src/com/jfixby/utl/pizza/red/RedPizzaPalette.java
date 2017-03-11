
package com.jfixby.utl.pizza.red;

import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;
import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaPaletteSpecs;
import com.jfixby.utl.pizza.api.PizzaTilesList;

public class RedPizzaPalette implements PizzaPalette {

	private final IsoTransform transform;
	private final P18TerrainPalette p18_palette;
	private final RedPizzaPaletteTiles tiles = new RedPizzaPaletteTiles();

	public RedPizzaPalette (PizzaPaletteSpecs specs) {
		transform = specs.getIsoTransform();
		p18_palette = specs.getP18TerrainPalette();
		tiles.setup(p18_palette);

		// p18_palette.getTerrainPalette().listBlocks(tile_id)
	}

	@Override
	public ID getNamespace () {
		return p18_palette.getNamespace();
	}

	@Override
	public IsoTransform getIsoTransform () {
		return transform;
	}

	@Override
	public Mapping<FabricsRelation, Float2> getRelationRelativeCenters () {
		return p18_palette.getRelationRelativeCenters();
	}

	@Override
	public P18Palette getP18Palette () {
		return p18_palette.getP18Palette();
	}

	@Override
	public TerrainPalette getTerrainPalette () {
		return p18_palette.getTerrainPalette();
	}

	@Override
	public P18TerrainPalette getP18TerrainPalette () {
		return p18_palette;
	}

	@Override
	public FabricsList listFabrics () {
		return this.p18_palette.listFabrics();
	}

	@Override
	public RelationsList listRelations () {
		return this.p18_palette.listRelations();
	}

	@Override
	public PizzaTilesList listTiles () {
		return tiles;
	}

}
