package ideasPackage;

import java.util.HashSet;

public class GridHelper
{
	private Drawable[][] grid;
	private Camera camera;
	private HashSet<Drawable> duplicateDrawables;

	public GridHelper(Drawable[][] grid, Camera camera)
	{
		this.grid = grid;
		this.camera = camera;
		duplicateDrawables = new HashSet<Drawable>();
	}

	public void addDuplicate(Drawable entity)
	{
		if (!duplicateDrawables.contains(entity))
			duplicateDrawables.add(entity);
	}

	public void draw(Drawable[][] grid)
	{
		if (camera != null)
		{
			int minDrawX = getMinDrawX();
			int maxDrawX = getMaxDrawX();
			int minDrawY = getMinDrawY();
			int maxDrawY = getMaxDrawY();
			HashSet<Drawable> updatedAnimations = new HashSet<>();

			for (int x = minDrawX; x < maxDrawX; x++)
			{
				for (int y = minDrawY; y < maxDrawY; y++)
				{
					if (grid[x][y] != null)
					{
						if (duplicateDrawables.contains(grid[x][y]))
						{
							if (!updatedAnimations.contains(grid[x][y]))
							{
								updatedAnimations.add(grid[x][y]);
								grid[x][y].drawAtExactly(x * Main.GRID_SIZE + camera.getCameraOffsetX(), y
										* Main.GRID_SIZE + camera.getCameraOffsetY(), true);
							} else
							{
								grid[x][y].drawAtExactly(x * Main.GRID_SIZE + camera.getCameraOffsetX(), y
										* Main.GRID_SIZE + camera.getCameraOffsetY(), false);
							}
						} else
						{
							grid[x][y].draw(camera.getCameraOffsetX(), camera.getCameraOffsetY());
						}

					}
				}
			}
		}
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	public int getMinDrawX()
	{
		return max(0, camera.getLocation().getGridX() - 1);
	}

	public int getMaxDrawX()
	{
		return min((camera.getLocation().getGridX() + Main.SCREEN_WIDTH / Main.GRID_SIZE) + 1, grid.length);
	}

	public int getMinDrawY()
	{
		return max(0, camera.getLocation().getGridY() - 1);
	}

	public int getMaxDrawY()
	{
		return min((camera.getLocation().getGridY() + Main.SCREEN_HEIGHT / Main.GRID_SIZE) + 1, grid[0].length);
	}

	public int min(int a, int b)
	{
		if (a < b)
		{
			return a;
		} else
		{
			return b;
		}
	}

	public int max(int a, int b)
	{
		if (a > b)
		{
			return a;
		} else
		{
			return b;

		}
	}

	public boolean isValidPosition(GridCoordinate coordinates)
	{
		if (coordinates == null)
		{
			return false;
		}
		return isValidPosition(coordinates.getGridX(), coordinates.getGridY());
	}

	public boolean isValidPosition(int xPosition, int yPosition)
	{
		return (xPosition >= 0 && xPosition < grid.length && yPosition >= 0 && yPosition < grid[0].length);
	}

}
