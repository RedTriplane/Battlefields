
package com.jfixby.util.patch18.red.maze;

import java.util.ArrayList;
import java.util.Random;

import com.jfixby.r3.ext.api.maze.BrickValue;
import com.jfixby.r3.ext.api.maze.Direction;
import com.jfixby.r3.ext.api.maze.MazeField;
import com.jfixby.r3.ext.api.maze.MazeFieldSpecs;
import com.jfixby.scarabei.api.log.L;

class FokkerMazeField implements MazeField {

	final BrickValue[][] f;
	final int cell_height;
	final int cell_width;
	final int blocks_heighth;
	final int blocks_width;
	private long seed;
	private Random random;
	private final int VARIATOR_2;
	private final int VARIATOR_1;

	public FokkerMazeField (final int W, final int H) {
		super();
		this.blocks_heighth = H * 2 + 1;
		this.blocks_width = W * 2 + 1;
		this.f = new BrickValue[this.blocks_width][this.blocks_heighth];
		this.cell_height = H;
		this.cell_width = W;
		this.VARIATOR_2 = this.cell_width + this.cell_height;
		this.VARIATOR_1 = this.cell_width + this.cell_height;

		for (int x = 0; x < W; x++) {
			final int ax = x * 2 + 1;
			for (int y = 0; y < H; y++) {
				final int ay = y * 2 + 1;
				this.f[ax][ay] = BrickValue.NOT_VISITED;
				this.f[ax + 1][ay] = BrickValue.WallV;
				this.f[ax - 1][ay] = BrickValue.WallV;
				this.f[ax][ay + 1] = BrickValue.WallH;
				this.f[ax][ay - 1] = BrickValue.WallH;
				this.f[ax + 1][ay + 1] = BrickValue.BrickP;
				this.f[ax - 1][ay - 1] = BrickValue.BrickP;
				this.f[ax + 1][ay - 1] = BrickValue.BrickP;
				this.f[ax - 1][ay + 1] = BrickValue.BrickP;

				if (x == 0) {
					this.f[ax - 1][ay - 1] = BrickValue.BrickTR;
					this.f[ax - 1][ay + 1] = BrickValue.BrickTR;
				}

				if (x + 1 == W) {
					this.f[ax + 1][ay - 1] = BrickValue.BrickTL;
					this.f[ax + 1][ay + 1] = BrickValue.BrickTL;
				}

				if (y == 0) {
					this.f[ax - 1][ay - 1] = BrickValue.BrickTD;
					this.f[ax + 1][ay - 1] = BrickValue.BrickTD;
				}

				if (y + 1 == H) {
					this.f[ax - 1][ay + 1] = BrickValue.BrickTU;
					this.f[ax + 1][ay + 1] = BrickValue.BrickTU;
				}

				if (x == 0 && y == 0) {
					this.f[ax - 1][ay - 1] = BrickValue.BrickDR;
				}

				if (x == 0 && y + 1 == H) {
					this.f[ax - 1][ay + 1] = BrickValue.BrickUR;
				}

				if (x + 1 == W && y == 0) {
					this.f[ax + 1][ay - 1] = BrickValue.BrickDL;
				}

				if (x + 1 == W && y + 1 == H) {
					this.f[ax + 1][ay + 1] = BrickValue.BrickUL;
				}

			}
		}

	}

	public FokkerMazeField (final MazeFieldSpecs maze_field_specs) {
		this(maze_field_specs.getWidth(), maze_field_specs.getHeight());
		this.seed = maze_field_specs.getRandomizationSeed();
		if (this.seed == MazeFieldSpecs.DEFAULT) {
			this.seed = System.nanoTime();
		}
		this.random = new Random(this.seed);
		L.d("seed", this.seed);
		this.buildMaze(this.cell_width / 2, this.cell_height / 2, 0.9f);
	}

	@Override
	public int getCellsHeight () {
		// TODO Auto-generated method stub
		return this.cell_height;
	}

	@Override
	public int getCellsWidth () {
		// TODO Auto-generated method stub
		return this.cell_width;
	}

	@Override
	public BrickValue getCellValue (final int x, final int y) {
		if (x < 0 || x >= this.cell_width || y < 0 || y >= this.cell_height) {
			return BrickValue.LIMBO;
		}
		return this.f[x * 2 + 1][y * 2 + 1];
	}

	@Override
	public BrickValue getCellWall (final int x, final int y, final Direction d) {

		return null;
	}

	@Override
	public void print () {
		// TODO Auto-generated method stub
		L.d(this.cell_width + "x" + this.cell_height + " Seed: " + this.seed);
		L.d("       " + this.blocks_width + "x" + this.blocks_heighth);
		for (int y = 0; y < this.blocks_heighth; y++) {
			for (int x = 0; x < this.blocks_width; x++) {
				L.d_appendChars(this.f[x][y].getSimbol() + "");
			}
			L.d();
		}

	}

	//
	// public void setStartEnd(int sx, int sy, int ex, int ey) {
	// // TODO Auto-generated method stub
	// this.f[sx * 2 + 1][sy * 2 + 1] = CellValue.START;
	// this.f[ex * 2 + 1][ey * 2 + 1] = CellValue.FINISH;
	// }

	public void buildMaze (final int sx, final int sy, final float p_direct) {

		final XY marker = new XY(sx, sy);
		this.f[sx * 2 + 1][sy * 2 + 1] = BrickValue.VISITED;
		this.expandMazeFrom(marker, marker, p_direct, 0);
		this.f[sx * 2 + 1][sy * 2 + 1] = BrickValue.START;

		this.try_to_place_exit(this.f);

		for (int x = 0; x < this.cell_width; x++) {
			final int ax = x * 2 + 1;
			for (int y = 0; y < this.cell_height; y++) {
				final int ay = y * 2 + 1;

				this.filterBrick(ax + 1, ay + 1);
				this.filterBrick(ax - 1, ay + 1);
				this.filterBrick(ax + 1, ay - 1);
				this.filterBrick(ax - 1, ay - 1);

			}
		}
	}

	private void try_to_place_exit (final BrickValue[][] f) {
		// if (cell_width == 1 && cell_height == 1) {
		// return;
		// }
		long max_tries = 1000;

		while (max_tries > 0) {
			final int ex = (int)(this.cell_width * this.random.nextFloat());
			final int ey = (int)(this.cell_height * this.random.nextFloat());

			if (this.f[ex * 2 + 1][ey * 2 + 1] == BrickValue.VISITED) {
				this.f[ex * 2 + 1][ey * 2 + 1] = BrickValue.EXIT;
				return;
			}
			max_tries--;

		}
	}

	private void filterBrick (final int x, final int y) {

		final BrickValue U = this.getWall(x, y - 1);
		final BrickValue D = this.getWall(x, y + 1);
		final BrickValue R = this.getWall(x + 1, y);
		final BrickValue L = this.getWall(x - 1, y);

		if (U == BrickValue.NO_WALL_V || U == BrickValue.LIMBO) {
			if (D == BrickValue.NO_WALL_V || D == BrickValue.LIMBO) {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickE;
					} else {
						this.f[x][y] = BrickValue.BrickL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickR;
					} else {
						this.f[x][y] = BrickValue.BrickH;
					}
				}
			} else {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickD;
					} else {
						this.f[x][y] = BrickValue.BrickDL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickDR;
					} else {
						this.f[x][y] = BrickValue.BrickTD;
					}
				}
			}
		} else {
			if (D == BrickValue.NO_WALL_V || D == BrickValue.LIMBO) {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickU;
					} else {
						this.f[x][y] = BrickValue.BrickUL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickUR;
					} else {
						this.f[x][y] = BrickValue.BrickTU;
					}
				}
			} else {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickV;
					} else {
						this.f[x][y] = BrickValue.BrickTL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						this.f[x][y] = BrickValue.BrickTR;
					} else {
						this.f[x][y] = BrickValue.BrickP;
					}
				}
			}
		}

		if (x == 0 && y == 0) {
			this.f[x][y] = BrickValue.BrickDR;
		}

		if (x == 0 && y + 1 == this.blocks_heighth) {
			this.f[x][y] = BrickValue.BrickUR;
		}

		if (x + 1 == this.blocks_width && y == 0) {
			this.f[x][y] = BrickValue.BrickDL;
		}

		if (x + 1 == this.blocks_width && y + 1 == this.blocks_heighth) {
			this.f[x][y] = BrickValue.BrickUL;
		}
	}

	private BrickValue getWall (final int x, final int y) {
		if (x < 0 || x >= this.blocks_width || y < 0 || y >= this.blocks_heighth) {
			return BrickValue.LIMBO;
		}
		return this.f[x][y];
	}

	private void expandMazeFrom (final XY previous, final XY marker, final float p_direct, final int depth) {
		{
			final ArrayList<XY> options = new ArrayList<XY>();

			this.addCellIfValid(options, marker.x + 1, marker.y);
			this.addCellIfValid(options, marker.x - 1, marker.y);
			this.addCellIfValid(options, marker.x, marker.y + 1);
			this.addCellIfValid(options, marker.x, marker.y - 1);
			final int roulette = this.random.nextInt(this.VARIATOR_1);

			if (options.size() == 0 || roulette < depth) {
				this.setCellValue(marker.x, marker.y, BrickValue.Treasure);
				return;
			}
			final float step = 1f / options.size();
			final XY next = options.remove((int)(this.random.nextFloat() / step));
			this.setWall(marker.x, marker.y, this.resolve(marker.x, marker.y, next.x, next.y),
				this.noWall(this.resolve(marker.x, marker.y, next.x, next.y)));
			this.setCellValue(next.x, next.y, BrickValue.VISITED);
			// print();
			this.expandMazeFrom(marker, next, p_direct, depth + 1);

			//
		}
		{
			final ArrayList<XY> options = new ArrayList<XY>();

			this.addCellIfValid(options, marker.x + 1, marker.y);
			this.addCellIfValid(options, marker.x - 1, marker.y);
			this.addCellIfValid(options, marker.x + 1, marker.y);
			this.addCellIfValid(options, marker.x - 1, marker.y);
			this.addCellIfValid(options, marker.x + 1, marker.y);
			this.addCellIfValid(options, marker.x - 1, marker.y);
			this.addCellIfValid(options, marker.x, marker.y + 1);
			this.addCellIfValid(options, marker.x, marker.y - 1);
			final int roulette = this.random.nextInt(this.VARIATOR_2);

			if (options.size() == 0 || roulette < depth) {
				return;
			}
			final float step = 1f / options.size();
			final XY next = options.remove((int)(this.random.nextFloat() / step));
			this.setWall(marker.x, marker.y, this.resolve(marker.x, marker.y, next.x, next.y),
				this.noWall(this.resolve(marker.x, marker.y, next.x, next.y)));
			this.setCellValue(next.x, next.y, BrickValue.MONSTER);
			// print();
			this.expandMazeFrom(marker, next, p_direct, depth + 1);

			//
		}

	}

	private BrickValue noWall (final Direction resolve) {
		if (resolve == Direction.Down) {
			return BrickValue.NO_WALL_H;
		}
		if (resolve == Direction.Up) {
			return BrickValue.NO_WALL_H;
		}

		return BrickValue.NO_WALL_V;
	}

	private void setWall (final int x, final int y, final Direction resolve, final BrickValue no) {
		if (resolve == Direction.Down) {
			this.f[x * 2 + 1][y * 2 + 1 + 1] = no;
			return;
		}
		if (resolve == Direction.Up) {
			this.f[x * 2 + 1][y * 2 + 1 - 1] = no;
			return;
		}
		if (resolve == Direction.DownRight) {
			this.f[x * 2 + 1 + 1][y * 2 + 1 + 1] = no;
			return;
		}
		if (resolve == Direction.UpRight) {
			this.f[x * 2 + 1 + 1][y * 2 + 1 - 1] = no;
			return;
		}

		if (resolve == Direction.DownLeft) {
			this.f[x * 2 + 1 - 1][y * 2 + 1 + 1] = no;
			return;
		}
		if (resolve == Direction.UpLeft) {
			this.f[x * 2 + 1 - 1][y * 2 + 1 - 1] = no;
			return;
		}

		if (resolve == Direction.Right) {
			this.f[x * 2 + 1 + 1][y * 2 + 1] = no;
			return;
		}
		if (resolve == Direction.Left) {
			this.f[x * 2 + 1 - 1][y * 2 + 1] = no;
			return;
		}

	}

	private Direction resolve (final int x, final int y, final int x2, final int y2) {
		if (x < x2) {
			if (y < y2) {
				return Direction.DownRight;
			}
			if (y > y2) {
				return Direction.UpRight;
			}
			return Direction.Right;
		}
		if (x > x2) {
			if (y < y2) {
				return Direction.DownLeft;
			}
			if (y > y2) {
				return Direction.UpLeft;
			}
			return Direction.Left;
		}

		if (y < y2) {
			return Direction.Down;
		}
		if (y > y2) {
			return Direction.Up;
		}
		if (y < y2) {
			return Direction.DownLeft;
		}
		if (y > y2) {
			return Direction.UpLeft;
		}
		return Direction.NO;
	}

	public void setCellValue (final int sx, final int sy, final BrickValue visited) {
		this.f[sx * 2 + 1][sy * 2 + 1] = visited;
	}

	private void addCellIfValid (final ArrayList<XY> options, final int x, final int y) {
		final BrickValue v = this.getCellValue(x, y);
		if (v == BrickValue.NOT_VISITED) {
			options.add(new XY(x, y));
		}

	}

	class XY {
		int x;
		int y;

		public XY (final int sx, final int sy) {
			this.x = sx;
			this.y = sy;
		}

		public XY set (final int sx, final int sy) {
			this.x = sx;
			this.y = sy;
			return this;
		}

		XY copy () {
			return new XY(this.x, this.y);
		}

	}

	public OriginalGameField build () {
		// OriginalGameField
		final OriginalGameField of = new OriginalGameField(this.blocks_width, this.blocks_heighth);
		of.useWall(false);
		for (int i = 0; i < this.blocks_width; i++) {
			for (int j = 0; j < this.blocks_heighth; j++) {
				// of.setGround(i, j, this.f[i][j].isEmpty());
				of.setGround(i, j, this.f[i][j].isConcrete());
			}
		}
		return of;
	}

	@Override
	public int getBricksWidth () {
		return this.blocks_width;
	}

	@Override
	public int getBricksHeight () {
		return this.blocks_heighth;
	}

	@Override
	public BrickValue getBrickValue (final int x, final int y) {
		return this.f[x][y];
	}

}
