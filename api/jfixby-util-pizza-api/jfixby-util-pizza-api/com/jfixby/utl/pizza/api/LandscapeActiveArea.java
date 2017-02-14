package com.jfixby.utl.pizza.api;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;

public interface LandscapeActiveArea {

	ReadOnlyFloat2 getTopLeftCorner();

	ReadOnlyFloat2 getTopRightCorner();

	ReadOnlyFloat2 getBottomLeftCorner();

	ReadOnlyFloat2 getBottomRightCorner();

}
