
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.GridActiveArea;
import com.jfixby.r3.ext.api.patch18.GridSpecs;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.grid.GridBrush;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.Int2;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.util.patch18.red.Log.Printer;
import com.jfixby.util.patch18.red.fields.acomposed.ComposedTile;
import com.jfixby.util.patch18.red.fields.acomposed.WallsComposition;
import com.jfixby.util.patch18.red.fields.acomposed.WallsCompositionSchematic;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;
import com.jfixby.util.patch18.red.fields.bool.BoolFunction;
import com.jfixby.util.patch18.red.fields.util.WallFunctionCore;
import com.jfixby.util.patch18.red.materials.JointLayer;
import com.jfixby.util.patch18.red.materials.JointLayerSchematic;

class GridImpl implements Grid {

	private final int MAX_EXPAND;
	final private P18Palette palette;
	final private GridBrushImpl brush = new GridBrushImpl(this);
	final private FabricsLevel fabric_level;

	private WallsComposition walls_composition;
	private final Fabric default_fabric;

	GridActiveArea active_area;

	public GridImpl (final GridSpecs field_specs) {
		this.palette = field_specs.getPalette();// !=null

		this.active_area = field_specs.getActiveGridArea();
		if (this.active_area == null) {
			this.active_area = new DefaultActiveArea();
		}

		final int n = field_specs.getPalette().listFabrics().size();
		this.default_fabric = field_specs.getPalette().listFabrics().getFabric(0);// size>0
		this.brush.setDefaultFabric(this.default_fabric);

		this.fabric_level = new FabricsLevel(this);
		this.compose(this.active_area);

		this.MAX_EXPAND = this.palette.listFabrics().size() - 1;

	}

	@Override
	public P18Palette getPalette () {
		return this.palette;
	}

	@Override
	public GridBrush getBrush () {
		return this.brush;
	}

	// public FabricsLevel getFabricLevel() {
	// return this.fabric_level;
	// }

	WallsComposition process (final P18Palette palette, final GridActiveArea active_area) {

		// L.d("rgb", Printer.toString(argb_function));

		final List<JointLayer> joint_layers = Collections.newList();
		final RelationsList materai_relationships = palette.listRelations();

		for (int i = 0; i < materai_relationships.size(); i++) {
			final FabricsRelation relationship = materai_relationships.getRelation(i);
			final Fabric foreground = relationship.getUpperFabric();
			final Fabric background = relationship.getLowerFabric();
			// L.d(foreground + "", background + "");

			final BoolFunction bool_field = new BoolFunction(this.fabric_level, foreground, background);

			final WallFunction walls = WallFunctionCore.process(bool_field);

			final JointLayerSchematic material_layer_schematic = new JointLayerSchematic();
			material_layer_schematic.setForegroundMaterial(foreground);
			material_layer_schematic.setBackgroundMaterial(background);
			material_layer_schematic.setWalls(walls);

			final JointLayer material_layer = new JointLayer(material_layer_schematic);
			joint_layers.add(material_layer);

			// Log.d(" ", Printer.toString(boolField));

			// WallsField a_patch_field = PNGtoWalls.process(file_path,
			// true_color, false_color);
			//
			// Log.d("a_patch_field", Printer.toString(a_patch_field));

		}
		if (joint_layers.size() == 0) {
			L.e("no joint layers found");
			System.exit(0);
		}
		final JointLayer first = joint_layers.getElementAt(0);

		final WallsCompositionSchematic composed_walls_field_schematic = new WallsCompositionSchematic();
		// int height = first.getHeight();
		// int width = first.getWidth();
		// composed_walls_field_schematic.setHeight(height);
		// composed_walls_field_schematic.setWidth(width);
		composed_walls_field_schematic.setJointLayers(joint_layers);

		// WallsCompositionFunction walls_composition = new
		// WallsCompositionStatic(
		// composed_walls_field_schematic, materai_relationships);

		final WallsComposition walls_composition = new WallsComposition(
			// composed_walls_field_schematic.getWidth(),
			// composed_walls_field_schematic.getHeight(),
			composed_walls_field_schematic.getJointLayers(), this.default_fabric, this.active_area);

		return walls_composition;

	}

	public WallsComposition getComposed () {
		return this.walls_composition;
	}

	public void compose (final GridActiveArea active_area) {
		this.walls_composition = this.process(this.palette, active_area);
	}

	@Override
	public void print (final long offset_x, final long offset_y, final long width, final long height) {
		final long grid_node_width = width + 1;
		final long grid_node_height = height + 1;
		//
		L.d("grid[" + grid_node_width + "x" + grid_node_height + "]",
			Printer.toString(this.walls_composition, grid_node_width, grid_node_height, offset_x, offset_y));

		final Rectangle area = Geometry.newRectangle(width, height);
		area.setPosition(offset_x, offset_y);

		// walls_composition.printLayer(0, area);
	}

	public long toCellX (final long grid_x) {
		return grid_x;
	}

	public long toCellY (final long grid_y) {
		return grid_y;
	}

	public boolean isOutside (final int cell_x, final int cell_y) {
		return this.active_area.isOutside(cell_x, cell_y);
	}

	public void apply_at_node (final long grid_node_x, final long grid_node_y, final Fabric current_fabric,
		final BrushApplicationResultImpl brush_result) {
		this.set_node(grid_node_x, grid_node_y, current_fabric, brush_result);
	}

	public void apply_at_cell (final long cell_x, final long cell_y, final int brush_size, final Fabric current_fabric,
		final BrushApplicationResultImpl brush_result) {

		for (int i = -brush_size; i <= brush_size + 1; i++) {
			for (int j = -brush_size; j <= brush_size + 1; j++) {
				final long grid_node_x = cell_x + i;
				final long grid_node_y = cell_y + j;
				this.set_node(grid_node_x, grid_node_y, current_fabric, brush_result);
				// L.d("attack", IntegerMath.newInt2(grid_node_x, grid_node_y));
			}
		}

		final long origin_node_x = cell_x;
		final long origin_node_y = cell_y;

		this.expand_walls(current_fabric, origin_node_x, origin_node_y, brush_size, brush_result);

		// set_node(top_left_corner_node_x, top_left_corner_node_y,
		// current_fabric, brush_result);

	}

	private void expand_walls (final Fabric current_fabric, final long origin_node_x, final long origin_node_y,
		final int brush_size, final BrushApplicationResultImpl brush_result) {

		this.expand_bottom_wall(origin_node_x, origin_node_y, brush_size, brush_result);
		this.expand_right_wall(origin_node_x, origin_node_y, brush_size, brush_result);
		this.expand_left_wall(origin_node_x, origin_node_y, brush_size, brush_result);
		this.expand_top_wall(origin_node_x, origin_node_y, brush_size, brush_result);

	}

	private void expand_left_wall (final long origin_node_x, final long origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			final Set<Int2> ignore_elements = Collections.newSet();
			final Set<Int2> wall_expantion = Collections.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				final long left_node_x = origin_node_x - brush_size - deep;
				final long top_node_y = origin_node_y - brush_size - deep;
				final long bottom_node_y = origin_node_y + (brush_size + 1) + deep;

				final Int2 top_corner = IntegerMath.newInt2(left_node_x, top_node_y);
				final Int2 bottom_corner = IntegerMath.newInt2(left_node_x, bottom_node_y);

				for (long i = top_node_y; i <= bottom_node_y; i++) {
					wall_expantion.add(IntegerMath.newInt2(left_node_x, i));
				}

				// wall_expantion.print("expand-left: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = this.fix_vertical_wall(wall_expantion, brush_result, ignore_elements, top_corner, bottom_corner,
					-1);

			} while (!no_fixing_was_made && deep < this.MAX_EXPAND);
		}
	}

	private void expand_bottom_wall (final long origin_node_x, final long origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			final Set<Int2> ignore_elements = Collections.newSet();
			final Set<Int2> wall_expantion = Collections.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				final long right_node_x = origin_node_x + (brush_size + 1) + deep;
				final long left_node_x = origin_node_x - brush_size - deep;
				final long bottom_node_y = origin_node_y + (brush_size + 1) + deep;

				final Int2 right_corner = IntegerMath.newInt2(right_node_x, bottom_node_y);
				final Int2 left_corner = IntegerMath.newInt2(left_node_x, bottom_node_y);

				for (long i = left_node_x; i <= right_node_x; i++) {
					wall_expantion.add(IntegerMath.newInt2(i, bottom_node_y));
				}

				// wall_expantion.print("expand-bottom: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = this.fix_horizontal_wall(wall_expantion, brush_result, ignore_elements, right_corner,
					left_corner, +1);

			} while (!no_fixing_was_made && deep < this.MAX_EXPAND);
		}
	}

	private void expand_top_wall (final long origin_node_x, final long origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			final Set<Int2> ignore_elements = Collections.newSet();
			final Set<Int2> wall_expantion = Collections.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				final long right_node_x = origin_node_x + (brush_size + 1) + deep;
				final long left_node_x = origin_node_x - brush_size - deep;
				final long top_node_y = origin_node_y - brush_size - deep;

				final Int2 right_corner = IntegerMath.newInt2(right_node_x, top_node_y);
				final Int2 left_corner = IntegerMath.newInt2(left_node_x, top_node_y);

				for (long i = left_node_x; i <= right_node_x; i++) {
					wall_expantion.add(IntegerMath.newInt2(i, top_node_y));
				}

				// wall_expantion.print("expand-top: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = this.fix_horizontal_wall(wall_expantion, brush_result, ignore_elements, right_corner,
					left_corner, -1);

			} while (!no_fixing_was_made && deep < this.MAX_EXPAND);
		}
	}

	private void expand_right_wall (final long origin_node_x, final long origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			final Set<Int2> ignore_elements = Collections.newSet();
			final Set<Int2> wall_expantion = Collections.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				final long right_node_x = origin_node_x + (brush_size + 1) + deep;
				final long top_node_y = origin_node_y - brush_size - deep;
				final long bottom_node_y = origin_node_y + (brush_size + 1) + deep;

				final Int2 top_corner = IntegerMath.newInt2(right_node_x, top_node_y);
				final Int2 bottom_corner = IntegerMath.newInt2(right_node_x, bottom_node_y);

				for (long i = top_node_y; i <= bottom_node_y; i++) {
					wall_expantion.add(IntegerMath.newInt2(right_node_x, i));
				}

				// wall_expantion.print("expand-right: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = this.fix_vertical_wall(wall_expantion, brush_result, ignore_elements, top_corner, bottom_corner,
					+1);

			} while (!no_fixing_was_made && deep < this.MAX_EXPAND);
		}
	}

	private boolean fix_vertical_wall (final Set<Int2> wall, final BrushApplicationResultImpl brush_result,
		final Set<Int2> ignore_elements, final Int2 top_corner, final Int2 bottom_corner, final int direction) {

		boolean no_fixing_was_made = true;
		for (int i = 0; i < wall.size(); i++) {
			final Int2 neigbour = wall.getElementAt(i);
			Int2 source = null;
			if (neigbour.equals(top_corner)) {
				source = IntegerMath.newInt2(neigbour, -direction, +1);
			} else if (neigbour.equals(bottom_corner)) {
				source = IntegerMath.newInt2(neigbour, -direction, -1);
			} else {
				source = IntegerMath.newInt2(neigbour, -direction, 0);
			}

			final Fabric against_fabric = this.fabric_level.getValueAt(source.getX(), source.getY());
			if (against_fabric == null) {
				L.e("Wall element is bad: ", source);
				Err.throwNotImplementedYet();
			}
			if (!this.is_compatible(neigbour, against_fabric)) {
				this.fix(neigbour, against_fabric, brush_result);
				no_fixing_was_made = false;
			} else {
				ignore_elements.add(neigbour);
			}
		}
		return no_fixing_was_made;

	}

	private boolean fix_horizontal_wall (final Set<Int2> wall, final BrushApplicationResultImpl brush_result,
		final Set<Int2> ignore_elements, final Int2 right_corner, final Int2 left_corner, final int direction) {

		boolean no_fixing_was_made = true;
		for (int i = 0; i < wall.size(); i++) {
			final Int2 neigbour = wall.getElementAt(i);
			Int2 source = null;
			if (neigbour.equals(right_corner)) {
				source = IntegerMath.newInt2(neigbour, -1, -direction);
			} else if (neigbour.equals(left_corner)) {
				source = IntegerMath.newInt2(neigbour, +1, -direction);
			} else {
				source = IntegerMath.newInt2(neigbour, 0, -direction);
			}
			final Fabric against_fabric = this.fabric_level.getValueAt(source.getX(), source.getY());
			if (against_fabric == null) {
				L.e("Wall element is bad: ", source);
				Err.throwNotImplementedYet();
			}
			if (!this.is_compatible(neigbour, against_fabric)) {
				this.fix(neigbour, against_fabric, brush_result);
				no_fixing_was_made = false;
			} else {
				ignore_elements.add(neigbour);
			}
		}
		return no_fixing_was_made;

	}

	private void expand_left_wall (final int origin_node_x, final int origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result, final int deep, final Set<Int2> wall_expantion) {

		final int left_node_x = origin_node_x - brush_size - deep;
		final int top_node_y = origin_node_y - brush_size - deep;
		final int bottom_node_y = origin_node_y + (brush_size + 1) + deep;

		for (int i = top_node_y + 1; i < bottom_node_y; i++) {
			wall_expantion.add(IntegerMath.newInt2(left_node_x, i));
		}

	}

	private void expand_bottom_wall (final int origin_node_x, final int origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result, final int deep, final Set<Int2> wall_expantion) {

		final int left_node_x = origin_node_x - brush_size - deep;
		final int right_node_x = origin_node_x + (brush_size + 1) + deep;
		// int top_node_y = origin_node_y - brush_size - deep;
		final int bottom_node_y = origin_node_y + (brush_size + 1) + deep;

		for (int i = left_node_x + 1; i < right_node_x; i++) {
			wall_expantion.add(IntegerMath.newInt2(i, bottom_node_y));
		}

	}

	private void expand_top_wall (final int origin_node_x, final int origin_node_y, final int brush_size,
		final BrushApplicationResultImpl brush_result, final int deep, final Set<Int2> wall_expantion) {

		final int left_node_x = origin_node_x - brush_size - deep;
		final int right_node_x = origin_node_x + (brush_size + 1) + deep;
		final int top_node_y = origin_node_y - brush_size - deep;
		// int bottom_node_y = origin_node_y + (brush_size + 1) + deep;

		for (int i = left_node_x + 1; i < right_node_x; i++) {
			wall_expantion.add(IntegerMath.newInt2(i, top_node_y));
		}

	}

	private void expand_corners (final int left_node_x, final int right_node_x, final int top_node_y, final int bottom_node_y,
		final BrushApplicationResultImpl brush_result) {

		final Int2 top_left = IntegerMath.newInt2(left_node_x, top_node_y);
		final Int2 top_right = IntegerMath.newInt2(right_node_x, top_node_y);

		final Int2 bottom_left = IntegerMath.newInt2(left_node_x, bottom_node_y);
		final Int2 bottom_right = IntegerMath.newInt2(right_node_x, bottom_node_y);

		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = this.expand_corner(how_deep, top_right, brush_result, 1, -1);
				how_deep++;

			} while (processing && how_deep <= this.MAX_EXPAND);
		}
		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = this.expand_corner(how_deep, bottom_right, brush_result, 1, 1);
				how_deep++;
			} while (processing && how_deep <= this.MAX_EXPAND);
		}
		// -------------------------
		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = this.expand_corner(how_deep, top_left, brush_result, -1, -1);
				how_deep++;

			} while (processing && how_deep <= this.MAX_EXPAND);
		}
		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = this.expand_corner(how_deep, bottom_left, brush_result, -1, 1);
				how_deep++;
			} while (processing && how_deep <= this.MAX_EXPAND);
		}
		// JUtils.exit();
	}

	private boolean expand_corner (final int how_deep, final Int2 source, final BrushApplicationResultImpl brush_result,
		final int direction_x, final int direction_y) {

		final Int2 neigbour = IntegerMath.newInt2(source.getX() + direction_x * how_deep, source.getY() + direction_y * how_deep);
		L.d("expanding corner", neigbour);
		final Fabric against_fabric = this.fabric_level.getValueAt(source.getX(), source.getY());
		if (against_fabric == null) {
			L.e("Wall element is bad: ", source);
			Err.throwNotImplementedYet();
		}
		if (!this.is_compatible(neigbour, against_fabric)) {
			this.fix(neigbour, against_fabric, brush_result);
			return true;
		}
		return false;
	}

	private void set_node (final long grid_node_x, final long grid_node_y, final Fabric current_fabric,
		final BrushApplicationResultImpl brush_result) {

		final boolean changed = this.fabric_level.set(grid_node_x, grid_node_y, current_fabric);

		brush_result.markAffected(grid_node_x - 1, grid_node_y - 1);
		brush_result.markAffected(grid_node_x - 0, grid_node_y - 1);
		brush_result.markAffected(grid_node_x - 1, grid_node_y - 0);
		brush_result.markAffected(grid_node_x - 0, grid_node_y - 0);

		if (changed) {
			brush_result.markChanged(grid_node_x - 1, grid_node_y - 1);
			brush_result.markChanged(grid_node_x - 0, grid_node_y - 1);
			brush_result.markChanged(grid_node_x - 1, grid_node_y - 0);
			brush_result.markChanged(grid_node_x - 0, grid_node_y - 0);

		}
	}

	public void draw (final int grid_node_x, final int grid_node_y, final int brush_size, final Fabric current_fabric,
		final BrushApplicationResultImpl brush_result, final boolean leak) {
		final int deep = 0;
		final Set<Int2> directly_painted_nodes = Collections.newSet();
		final Set<Int2> border_nodes = Collections.newSet();
		final Set<Int2> good_nodes = Collections.newSet();

		for (int i = -brush_size - 1; i <= brush_size + 1; i++) {
			for (int j = -brush_size - 1; j <= brush_size + 1; j++) {
				final Int2 point = IntegerMath.newInt2(grid_node_x + i, grid_node_y + j);
				if (i >= -brush_size && i <= brush_size && j >= -brush_size && j <= brush_size) {
					directly_painted_nodes.add(point);

				} else {
					border_nodes.add(point);
				}

			}
		}
		if (!leak) {
			return;
		}
		good_nodes.addAll(directly_painted_nodes);

		this.check_and_fix(current_fabric, good_nodes, border_nodes, brush_result, deep);

	}

	private void check_and_fix (final Fabric current_fabric, final Set<Int2> good_nodes, final Set<Int2> border_nodes,
		final BrushApplicationResultImpl brush_result, int deep) {
		if (deep >= 4) {
			return;
		}

		final Set<Int2> to_check_n_fix = Collections.newSet();
		to_check_n_fix.addAll(border_nodes);

		final Set<Int2> checked_ok = Collections.newSet();

		for (int i = 0; i < to_check_n_fix.size(); i++) {
			final Int2 element_to_check = to_check_n_fix.getElementAt(i);
			if (this.is_compatible(element_to_check, current_fabric)) {
				checked_ok.add(element_to_check);
			}
		}
		// good_nodes.addAll(checked_ok);
		to_check_n_fix.removeAll(checked_ok);

		final Map<Int2, Set<Int2>> suburbs = Collections.newMap();

		for (int i = 0; i < to_check_n_fix.size(); i++) {
			final Set<Int2> next_border_nodes = Collections.newSet();
			final Int2 element_to_fix = to_check_n_fix.getElementAt(i);
			this.fix(element_to_fix, current_fabric, brush_result);
			// good_nodes.add(element_to_fix);

			this.collectNeighbours(element_to_fix, next_border_nodes);
			next_border_nodes.removeAll(good_nodes);
			suburbs.put(element_to_fix, next_border_nodes);
			// deep++;
			// check_and_fix(current_fabric, good_nodes, next_border_nodes,
			// brush_result, deep);
			// deep--;
		}

		for (int k = 0; k < suburbs.size(); k++) {
			final Int2 origin = suburbs.getKeyAt(k);
			final Set<Int2> his_border_nodes = suburbs.getValueAt(k);
			final Fabric next_fabric = this.fabric_level.getValueAt(origin.getX(), origin.getY());
			deep++;
			this.check_and_fix(next_fabric, good_nodes, his_border_nodes, brush_result, deep);
			deep--;
		}

		// suburbs.print("suburbs");
	}

	private void collectNeighbours (final Int2 origin, final Set<Int2> next_border_nodes) {
		final long grid_node_x = origin.getX();
		final long grid_node_y = origin.getY();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= +1; j++) {
				final Int2 point = IntegerMath.newInt2(grid_node_x + i, grid_node_y + j);
				if (i == 0 && j == 0) {
				} else {
					next_border_nodes.add(point);
				}

			}
		}

	}

	private boolean is_compatible (final Int2 position_to_check, final Fabric origin_value) {
		final Fabric neighbour_value = this.fabric_level.getValueAt(position_to_check.getX(), position_to_check.getY());

		if (neighbour_value == null) {
			return false;
		}

		if (this.compatible(neighbour_value, origin_value)) {
			return true;
		}

		return false;
	}

	private Fabric fix (final Int2 position_to_check, final Fabric origin_value, final BrushApplicationResultImpl brush_result) {
		Fabric neighbour_value = this.fabric_level.getValueAt(position_to_check.getX(), position_to_check.getY());

		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
		}

		final Fabric compatible_fabric = this.findCompatibleFabric(origin_value, neighbour_value);

		final long grid_node_x = position_to_check.getX();
		final long grid_node_y = position_to_check.getY();

		this.set_node(grid_node_x, grid_node_y, compatible_fabric, brush_result);

		return compatible_fabric;
	}

	private void check_and_fix (final Int2 position_to_check, final Set<Int2> directly_painted_nodes,
		final BrushApplicationResultImpl brush_result, final Fabric origin_value, final Set<Int2> good_nodes,
		final Set<Int2> border_nodes, final Set<Int2> second_border_nodes) {

		Fabric neighbour_value = this.fabric_level.getValueAt(position_to_check.getX(), position_to_check.getY());

		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
		}

		if (this.compatible(neighbour_value, origin_value)) {
			return;
		}

		final Fabric compatible_fabric = this.findCompatibleFabric(origin_value, neighbour_value);

		final long grid_node_x = position_to_check.getX();
		final long grid_node_y = position_to_check.getY();

		this.fix(grid_node_x, grid_node_y, compatible_fabric, brush_result, good_nodes, border_nodes, second_border_nodes);

	}

	private void fix (final long grid_node_x, final long grid_node_y, final Fabric compatible_fabric,
		final BrushApplicationResultImpl brush_result, final Set<Int2> good_nodes, final Set<Int2> border_nodes,
		final Set<Int2> second_border_nodes) {

		final Set<Int2> new_border_nodes = Collections.newSet();
		for (long i = -1; i <= 1; i++) {
			for (long j = -1; j <= +1; j++) {
				final Int2 point = IntegerMath.newInt2(grid_node_x + i, grid_node_y + j);
				if (i == 0 && j == 0) {
					this.set_node(grid_node_x + i, grid_node_y + j, compatible_fabric, brush_result);
					good_nodes.add(point);
				} else {
					new_border_nodes.add(point);
				}

			}
		}
		new_border_nodes.removeAll(good_nodes);

	}

	private void check_and_fix_neighbours (final Int2 position_to_check, final List<Int2> directly_painted_nodes,
		final BrushApplicationResultImpl brush_result) {

		final List<Int2> neighbours = Collections.newList();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i != 0 && j != 0) {
					final Int2 position = IntegerMath.newInt2(position_to_check.getX() + i, position_to_check.getY() + j);
					if (!directly_painted_nodes.contains(position)) {
						neighbours.add(position);
					}
				}
			}
		}
		final Int2 origin = directly_painted_nodes.getElementAt(0);

		for (int i = 0; i < neighbours.size(); i++) {
			final Int2 neighbour = neighbours.getElementAt(i);
			this.check_and_fix(neighbour, origin, brush_result);
		}

	}

	private void check_and_fix (final Int2 neighbour, final Int2 origin, final BrushApplicationResultImpl brush_result) {

		Fabric neighbour_value = this.fabric_level.getValueAt(neighbour.getX(), neighbour.getY());

		final Fabric origin_value = this.fabric_level.getValueAt(origin.getX(), origin.getY());

		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
		}

		if (this.compatible(neighbour_value, origin_value)) {
			return;
		}

		final Fabric compatible_fabric = this.findCompatibleFabric(origin_value, neighbour_value);

		final long grid_node_x = neighbour.getX();
		final long grid_node_y = neighbour.getY();

		this.set_node(grid_node_x, grid_node_y, compatible_fabric, brush_result);

	}

	int[] grid_cell_x = new int[4];
	int[] grid_cell_y = new int[4];

	private void checkAndFixNeighbours (final int grid_node_x, final int grid_node_y,
		final BrushApplicationResultImpl brush_result, final Set<Int2> ignore) {
		final Int2 origin = IntegerMath.newInt2(grid_node_x, grid_node_y);

		final List<Int2> neighbours = Collections.newList();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i != 0 && j != 0) {
					final Int2 position = IntegerMath.newInt2(grid_node_x + i, grid_node_y + j);
					if (!ignore.contains(position)) {
						neighbours.add(position);

					}
				}
			}
		}

		for (int i = 0; i < neighbours.size(); i++) {
			final Int2 neighbour = neighbours.getElementAt(i);
			ignore.add(neighbour);
			this.check_and_fix(neighbour, ignore, origin, brush_result);
		}
	}

	private void check_and_fix (final Int2 neighbour, final Set<Int2> ignore, final Int2 origin,
		final BrushApplicationResultImpl brush_result) {

		Fabric neighbour_value = this.fabric_level.getValueAt(neighbour.getX(), neighbour.getY());

		final Fabric origin_value = this.fabric_level.getValueAt(origin.getX(), origin.getY());
		boolean stop_here = false;
		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
			// return;
			stop_here = true;
		} else if (this.compatible(neighbour_value, origin_value)) {
			return;
		}
		final Fabric current_fabric = this.findCompatibleFabric(origin_value, neighbour_value);

		final long grid_node_x = neighbour.getX();
		final long grid_node_y = neighbour.getY();

		// if (set_node(grid_node_x, grid_node_y, current_fabric, brush_result)
		// && !stop_here) {
		//
		// checkAndFixNeighbours(grid_node_x, grid_node_y, brush_result,
		// ignore);
		// }

	}

	private Fabric findCompatibleFabric (final Fabric origin_value, final Fabric neighbour_value) {

		// Fabric neighbour_value = this.fabric_level.getValueAt(
		// neighbour.x_index, neighbour.y_index);

		// Fabric origin_value = this.fabric_level.getValueAt(origin.x_index,
		// origin.y_index);

		final Fabric next = this.palette.findClosestFabric(origin_value, neighbour_value);

		return next;
	}

	private boolean compatible (final Fabric fabric_a, final Fabric fabric_b) {
		if (fabric_a == fabric_b) {
			return true;
		}
		final FabricsRelation relation = this.palette.listRelations().findRelationFor(fabric_a, fabric_b);
		if (relation == null) {
			return false;
		}
		return true;
	}

	public void refresh (final BrushApplicationResultImpl brush_result) {
		final Collection<FabricPoint> values = this.fabric_level.function_set.getAllValues();
		for (int i = 0; i < values.size(); i++) {
			final FabricPoint element = values.getElementAt(i);
			final long cell_x = element.X;
			final long cell_y = element.Y;
			brush_result.markChanged(cell_x, cell_y);
		}

	}

	public ComposedTile getValueAt (final Int2 element) {
		return this.walls_composition.getValue(element.getX(), element.getY());
	}

}
