package com.jfixby.r3.tools.api.iso;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.utl.pizza.api.PizzaPalette;

public interface GeneratorParams {

	void setOutputFolder(File mock_palette_folder);

	File getOutputFolder();

	void setPizzaPalette(PizzaPalette palette);

	PizzaPalette getPizzaPalette();

	void setPadding(int pixels);

	int getPadding();

	void setFabricColor(Fabric fabric, Color color);

	Mapping<Fabric, Color> getFabricColors();

}
