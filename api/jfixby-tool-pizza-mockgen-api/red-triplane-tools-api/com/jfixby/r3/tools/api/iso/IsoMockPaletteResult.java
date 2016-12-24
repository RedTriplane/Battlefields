package com.jfixby.r3.tools.api.iso;

import com.jfixby.r3.ext.api.scene2d.srlz.Scene2DPackage;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.file.File;

public interface IsoMockPaletteResult {

	void print();

	File getRasterOutputFolder();

	ID getNamespace();

	Scene2DPackage getScene2DPackage();

	Collection<ID> getAssetsUsed();

}
