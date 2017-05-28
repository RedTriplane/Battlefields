
package com.jfixby.r3.ext.p18t.red;

import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.util.p18t.api.P18TerrainPaletteSpecs;

public class RedP18TerrainPaletteSpecs implements P18TerrainPaletteSpecs {

	private ID paletteNamespace;
	private P18Palette p18Palette;
	private double blockXWidth;
	private double blockYWidth;
	Map<FabricsRelation, Double> z_dims = Collections.newMap();

	@Override
	public double getBlockXWidth () {
		return this.blockXWidth;
	}

	@Override
	public double getBlockYWidth () {
		return this.blockYWidth;
	}

	@Override
	public P18Palette getP18Palette () {
		return this.p18Palette;
	}

	@Override
	public void setBlockXWidth (final double x_width) {
		this.blockXWidth = x_width;
	}

	@Override
	public void setBlockYWidth (final double y_width) {
		this.blockYWidth = y_width;
	}

	@Override
	public void setP18Palette (final P18Palette palette) {
		this.p18Palette = palette;
	}

	@Override
	public ID getPaletteNamespace () {
		return this.paletteNamespace;
	}

	@Override
	public void setPaletteNamespace (final ID paletteNamespace) {
		this.paletteNamespace = paletteNamespace;
	}

	@Override
	public double getBlockZHeight (final FabricsRelation fabric_relation) {
		Debug.checkNull("fabric_relation", fabric_relation);
		final Double value = this.z_dims.get(fabric_relation);
		if (value == null) {
			Err.reportError("BlockZHeight is not defined for " + fabric_relation);
		}
		return value;
	}

	@Override
	public void setBlockZHeight (final FabricsRelation fabric_relation, final double z_height) {
		Debug.checkNull("fabric_relation", fabric_relation);
		this.z_dims.put(fabric_relation, z_height);
	}

	final Map<FabricsRelation, Float2> relative_center = Collections.newMap();

	@Override
	public void setRelationRelativeCenterXY (final FabricsRelation relation, final float centerRelativeX,
		final float centerRelativeY) {
		final Float2 center = this.obtain(relation);
		center.setXY(centerRelativeX, centerRelativeY);
	}

	private Float2 obtain (final FabricsRelation relation) {
		Float2 center = this.relative_center.get(relation);
		if (center == null) {
			center = Geometry.newFloat2();
			this.relative_center.put(relation, center);
		}
		return center;
	}

	@Override
	public float getRelationRelativeCenterX (final FabricsRelation relation) {
		return (float)this.obtain(relation).getX();
	}

	@Override
	public float getRelationRelativeCenterY (final FabricsRelation relation) {
		return (float)this.obtain(relation).getY();
	}

	@Override
	public Mapping<FabricsRelation, Float2> getRelationRelativeCenters () {
		return this.relative_center;
	}

}
