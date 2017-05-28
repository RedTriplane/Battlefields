package com.jfixby.utl.pizza.api;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;

public interface PizzaTile {

	PizzaTileType getType();

	ReadOnlyFloat2 getCanvasPosition();

}
