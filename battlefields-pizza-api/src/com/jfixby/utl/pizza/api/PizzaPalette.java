package com.jfixby.utl.pizza.api;

import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;

public interface PizzaPalette {

	ID getNamespace();

	IsoTransform getIsoTransform();

	Mapping<FabricsRelation, Float2> getRelationRelativeCenters();

	P18Palette getP18Palette();

	TerrainPalette getTerrainPalette();

	P18TerrainPalette getP18TerrainPalette();

	FabricsList listFabrics();

	RelationsList listRelations();

	PizzaTilesList listTiles();

}
