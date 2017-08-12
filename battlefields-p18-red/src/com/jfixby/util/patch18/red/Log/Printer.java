
package com.jfixby.util.patch18.red.Log;

import java.util.ArrayList;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.util.patch18.red.fields.acomposed.ComposedTile;
import com.jfixby.util.patch18.red.fields.acomposed.WallsComposition;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;
import com.jfixby.util.patch18.red.fields.bool.BoolFunction;

public class Printer {

	static String L = "\n";

	public static String toString (final BoolFunction bool_field, final int w, final int h) {
		String result = L;
		for (int j = -1; j < h + 1; j++) {
			String line = "";
			for (int i = -1; i < w + 1; i++) {
				String char_value = bool_field.getValue(i, j).getChar();
				if (i == -1 || j == -1 || i == w || j == h) {
					char_value = char_value.replaceAll("\\[ \\]", "#□#");
					char_value = char_value.replaceAll("\\[■\\]", "#■#");
					char_value = char_value.replaceAll("\\[\\?\\]", "#?#");
				}

				line = line + "" + char_value + "";
			}
			result = result + line + L;
		}
		return result;
	}

	public static String toString (final WallFunction field, final int w, final int h) {
		String result = L;
		for (int j = -1; j < h + 1; j++) {
			String line = "";
			for (int i = -1; i < w; i++) {
				// char char_value = ' ';
				// if (bool_field.getValue(i, j).toBool()) {
				// char_value = 'X';
				// }
				final Patch18 value = field.getValue(i, j).value;

				String char_value = " ? ";
				if (value != null) {
					char_value = value.getChar();
				}
				if (i == -1 || j == -1 || i == w || j == h) {
					char_value = char_value.replaceAll(" ▫ ", "#□#");
					char_value = char_value.replaceAll(Patch18.Blocked.getChar(), "┼╬┼");
					char_value = char_value.replaceAll(Patch18.Irrelevant.getChar(), "###");
				}
				line = line + "" + char_value + "";
			}
			result = result + line + "" + L;
		}
		return result;
	}

	private static final String ASCI_palette = "@#$%&8BMW*mwqpdbkhaoQ0OZXYUJCLtfjzxnuvcr[]{}1()|/?Il!i><+_~-;,. ";
	static final ArrayList<String> palette = new ArrayList<String>();
	static float delta;
	static {
		final boolean use_grayscale_symbols = true;
		if (use_grayscale_symbols) {
			// String color0 = "█";
			// String color1 = "▓";
			// String color2 = "▒";
			// String color3 = "░";
			// String color4 = " ";
			palette.add("█");
			palette.add("▓");
			palette.add("▒");
			palette.add("░");
			palette.add(" ");
		} else {
			// int N = ASCI_palette.length();
			// for (int i = 0; i < N; i++) {
			// palette.add(ASCI_palette.charAt(i) + "");
			// }
			// palette.add("█");
			// palette.add("▓");
			// palette.add("▓");
			// palette.add("▒");
			// palette.add("▒");
			// palette.add("░");
			// palette.add("░");
			// palette.add(" ");
			// palette.add(" ");

		}

		delta = 1f / palette.size();

	}

	private static String palette (final Color color) {
		return palette(color, color);
	}

	private static String palette (final Color a, final Color b) {
		final float gray = (a.gray() + b.gray()) / 2f;
		return palette(gray);
	}

	private static String palette (final float gray_a, final float gray_b) {
		final float gray = (gray_a + gray_b) / 2f;
		return palette(gray);
	}

	// private static String palette(int index_a, int index_b) {
	// int index = index_a;
	// if (index == -1) {
	// index = 0;
	// }
	// index = 0;
	// return palette.get(index);
	// }

	private static String palette (final int index_a) {
		// int index = index_a;
		return palette.get(index_a + 1);
	}

	private static String palette (final float gray) {
		int index = (int)((gray) * (1f / delta));
		if (index == palette.size()) {
			index--;
		}
		if (index < 0) {
			index = 0;
		}
		final String val = palette.get(index);
		return val;
	}

	public static String toString (final WallsComposition walls_composition, final long w, final long h, final long x,
		final long y) {
		String result = L;

		// Log.d("delta", delta);

		for (long j = -1; j < h + 1; j++) {
			String line = "";
			for (long i = -1; i < w + 1; i++) {

				final ComposedTile tile = walls_composition.getValue(i + x, j + y);

				final Patch18 shape = tile.getShape();

				final String val = shape.getChar();

				line = line + "" + val + "";

			}
			result = result + line + L;
		}
		return result;
	}

	public static String toStringArea (final WallsComposition walls_composition, final FabricsList mcolors, final long w,
		final long h, final long x, final long y) {
		String result = L;

		for (long j = -1 + y; j < h + 1; j++) {
			String line = "";
			for (long i = -1 + x; i < w + 1; i++) {

				final ComposedTile tile = walls_composition.getValue(i, j);

				//
				final Fabric material_a = tile.getUpperFabric();

				final Fabric material_b = tile.getLowerFabric();

				final Patch18 shape = tile.getShape();
				String val;
				if (shape == Patch18.Blocked) {
					val = palette(mcolors.indexOf(material_a)) + palette(mcolors.indexOf(material_a));
				} else if (shape == Patch18.Free) {
					val = palette(mcolors.indexOf(material_b)) + palette(mcolors.indexOf(material_b));
				} else {
					val = palette(mcolors.indexOf(material_a)) + palette(mcolors.indexOf(material_b));
					// Log.d(val);
				}

				line = line + "" + val + "";

			}
			result = result + line + L;
		}
		return result;
	}

	public static String toString (final WallsComposition walls_composition, final MaterialGRBColors mcolors, final long w,
		final long h, final long x, final long y) {
		String result = L;

		for (long j = y - 1; j < h + 1; j++) {
			String line = "";
			for (long i = x - 1; i < w + 1; i++) {

				final ComposedTile tile = walls_composition.getValue(i, j);

				//
				final Fabric material_a = tile.getUpperFabric();
				final float color_a = mcolors.colorOf(material_a).gray();
				final Fabric material_b = tile.getLowerFabric();
				final float color_b = mcolors.colorOf(material_b).gray();

				final Patch18 shape = tile.getShape();
				String val;
				if (shape == Patch18.Blocked) {
					val = palette(color_a);
				} else if (shape == Patch18.Free) {
					val = palette(color_b);
				} else {
					val = palette(color_a, color_b);
				}

				line = line + "" + val + val + "";

			}
			result = result + line + L;
		}
		return result;
	}

}
