
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.gamedev.GameMeter;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.p18t.api.P18TerrainPaletteSpecs;
import com.jfixby.util.p18t.api.P18TerrainTypeVariationsList;
import com.jfixby.util.terain.test.api.palette.Terrain;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteFactory;
import com.jfixby.util.terain.test.api.palette.TerrainPaletteSpecs;
import com.jfixby.util.terain.test.api.palette.TerrainType;
import com.jfixby.util.terain.test.api.palette.TerrainTypeSpecs;

public class RedP18TerrainPalette implements P18TerrainPalette {

	private TerrainPalette terrain_palette;
	private P18Palette patch18_palette;
	private Map<FabricsRelation, Float2> elativeCenters;
	private ID palette_namespace;
	final Map<Patch18, Map<FabricsRelation, P18TerrainTypeVariationsList>> reg = Collections.newMap();
	private GameMeter block_x_width;
	private GameMeter block_y_width;

	public RedP18TerrainPalette (P18TerrainPaletteSpecs specs) {
		{
			elativeCenters = Collections.newMap(specs.getRelationRelativeCenters());

			TerrainPaletteFactory terrain_factory = Terrain.getPaletteFactory();

			palette_namespace = Debug.checkNull("palette_namespace", specs.getPaletteNamespace());

			block_x_width = terrain_factory.newMeasurement(specs.getBlockXWidth());
			block_y_width = terrain_factory.newMeasurement(specs.getBlockYWidth());
			patch18_palette = Debug.checkNull("18Palette", specs.getP18Palette());

			// double[] d = new double[] { 0.4, 0.4, 0.4 };

			TerrainPaletteSpecs palette_specs = terrain_factory.newTerrainPaletteSpecs();

			RelationsList relations = patch18_palette.listRelations();
			for (int i = 0; i < relations.size(); i++) {
				FabricsRelation relation = patch18_palette.listRelations().getRelation(i);
				GameMeter block_z_height = terrain_factory.newMeasurement(specs.getBlockZHeight(relation));
				GameMeter block_z_altitude = terrain_factory.newMeasurement(sum(specs, relation, patch18_palette.listRelations()));
				ID relation_name = relation_name(palette_namespace, relation);
				List<TerrainType> blocks = createBlocks(relation_name, block_z_height, block_z_altitude, block_x_width, block_y_width,
					terrain_factory);

				palette_specs.addTerrainBlocks(blocks.toJavaList());

			}

			terrain_palette = terrain_factory.newTerrainPalette(palette_specs);
		}
		{

			FabricsList fabrics = patch18_palette.listFabrics();
			RelationsList relations = patch18_palette.listRelations();
			Collection<Patch18> p18list = Patch18.ALL_VALUES;
			// fabrics.print("fabrics");
			// relations.print("relations");
			for (int i = 0; i < relations.size(); i++) {
				for (int k = 0; k < p18list.size(); k++) {
					Patch18 p18val = p18list.getElementAt(k);
					FabricsRelation relation = relations.getRelation(i);

					ID terrain_block_id = this.getNamespace().child(relation.getIDString()).child(p18val.name()).child("*");
					Collection<TerrainType> blocks = terrain_palette.listBlocks(terrain_block_id);

					RedVariationsList list = new RedVariationsList(p18val, relation, blocks);

					put(p18val, relation, list);
				}
			}
		}

	}

	private double sum (P18TerrainPaletteSpecs specs, FabricsRelation relation, RelationsList listRelations) {

		double tmp = 0d;
		int N = listRelations.indexOf(relation);
		for (int i = 0; i < N; i++) {
			double block_z_height = specs.getBlockZHeight(relation);
			tmp = tmp + block_z_height;
		}
		return tmp;

	}

	private List<TerrainType> createBlocks (ID relation_name, GameMeter block_z_height, GameMeter block_z_altitude,
		GameMeter block_x_width, GameMeter block_y_width, TerrainPaletteFactory terrain_factory) {
		List<TerrainType> blocks = Collections.newList();
		List<Patch18> patch_18_list = Collections.newList(Patch18.values());
		for (int i = 0; i < patch_18_list.size(); i++) {
			String block_name_string = patch_18_list.getElementAt(i).name();
			ID block_name = relation_name.child(block_name_string).child("var").child("01");
			// L.d(" ", block_name);
			TerrainTypeSpecs block_specs = terrain_factory.newTerrainBlockSpecs();
			block_specs.setName(block_name);
			block_specs.setZHeight(block_z_height);
			block_specs.setZAltitude(block_z_altitude);
			block_specs.setXWidth(block_x_width);
			block_specs.setYWidth(block_y_width);
			TerrainType block = terrain_factory.newTerrainType(block_specs);
			blocks.add(block);
		}
		return blocks;
	}

	private ID relation_name (ID palette_namespace, FabricsRelation relation) {
		return palette_namespace.child(relation.getIDString());
	}

	@Override
	public P18Palette getP18Palette () {
		return patch18_palette;
	}

	@Override
	public TerrainPalette getTerrainPalette () {
		return terrain_palette;
	}

	@Override
	public void print () {
		L.d(this);
	}

	@Override
	public Mapping<FabricsRelation, Float2> getRelationRelativeCenters () {
		return elativeCenters;
	}

	@Override
	public ID getNamespace () {
		return palette_namespace;
	}

	@Override
	public P18TerrainTypeVariationsList listVariationsFor (FabricsRelation relation, Patch18 p18val) {
		return reg.get(p18val).get(relation);
	}

	@Override
	public Collection<P18TerrainTypeVariationsList> listVariationsAll () {
		return all;
	}

	final List<P18TerrainTypeVariationsList> all = Collections.newList();

	private void put (Patch18 p18val, FabricsRelation relation, RedVariationsList list) {
		Map<FabricsRelation, P18TerrainTypeVariationsList> map1 = this.reg.get(p18val);
		if (map1 == null) {
			map1 = Collections.newMap();
			reg.put(p18val, map1);
		}
		map1.put(relation, list);
		all.add(list);
	}

	@Override
	public P18TerrainTypeVariationsList listVariationsFor (Patch18 shape, Fabric upperFabric, Fabric lowerFabric) {
		FabricsRelation relation = this.patch18_palette.listRelations().findRelationFor(upperFabric, lowerFabric);
		return this.listVariationsFor(relation, shape);
	}

	@Override
	public FabricsList listFabrics () {
		return this.patch18_palette.listFabrics();
	}

	@Override
	public RelationsList listRelations () {
		return this.patch18_palette.listRelations();
	}

	@Override
	public ID getPaletteNamespace () {
		return this.palette_namespace;
	}

	@Override
	public GameMeter getBlockXWidth () {
		return block_x_width;
	}

	@Override
	public GameMeter getBlockYWidth () {
		return block_y_width;
	}

}
