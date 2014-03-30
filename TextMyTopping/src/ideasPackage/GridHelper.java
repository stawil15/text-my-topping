package ideasPackage;

import java.util.HashSet;

public class GridHelper
{
	// This class contains valuable methods the sceneryGrid and collisionGrid can use.
	// It cuts down on cpu usage by only drawing objects that are within the view of the
	// camera. 
	private Drawable[][] grid;
	private Camera camera;
	private HashSet<Drawable> duplicateDrawables;
	private boolean drewPlayer = false;

	public GridHelper(Drawable[][] grid, Camera camera)
	{
		this.grid = grid;
		this.camera = camera;
		duplicateDrawables = new HashSet<Drawable>();
	}

	// Add a duplicate object
	public void addDuplicate(Drawable entity)
	{
		if (!duplicateDrawables.contains(entity))
			duplicateDrawables.add(entity);
	}

	// Draw the entire grid
	public void draw(Drawable[][] grid)
	{
		// Remember if we drew the player. This is so the player is only drawn once.
		// This fixes a movement animation glitch where the movement looks buggy
		// when moving down or to the left
		drewPlayer = false;
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
								grid[x][y].drawAtExactly(x * Main.GRID_SIZE + camera.getAbsoluteCameraOffsetX(), y
										* Main.GRID_SIZE + camera.getAbsoluteCameraOffsetY(), true);
							} else
							{
								grid[x][y].drawAtExactly(x * Main.GRID_SIZE + camera.getAbsoluteCameraOffsetX(), y
										* Main.GRID_SIZE + camera.getAbsoluteCameraOffsetY(), false);
							}
						} else
						{
							if (grid[x][y].getClass() == PlayerCharacter.class)
							{
								if (!drewPlayer)
								{
									drewPlayer = true;
									grid[x][y].draw(camera.getAbsoluteCameraOffsetX(), camera.getAbsoluteCameraOffsetY());
								}
							} else
							{
								grid[x][y].draw(camera.getAbsoluteCameraOffsetX(), camera.getAbsoluteCameraOffsetY());
							}

						}

					}
				}
			}
		}
	}

	// Set the camera
	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	// Get the minimum x that should be drawn
	public int getMinDrawX()
	{
		return max(0, camera.getLocation().getGridX() - 2);
	}

	// Get the maximum x that should be drawn
	public int getMaxDrawX()
	{
		return min((camera.getLocation().getGridX() + Main.SCREEN_WIDTH / Main.GRID_SIZE) + 2, grid.length);
	}

	// Get the minimum y that should be drawn
	public int getMinDrawY()
	{
		return max(0, camera.getLocation().getGridY() - 2);
	}

	// Get the maximum y that should be drawn
	public int getMaxDrawY()
	{
		return min((camera.getLocation().getGridY() + Main.SCREEN_HEIGHT / Main.GRID_SIZE) + 2, grid[0].length);
	}

	// An integer minimum function
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

	// An integer maximum function
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

	// Gets whether a position on the grid is valid. the position is valid
	// if it is on the grid and not occupied.
	public boolean isValidPosition(GridCoordinate coordinates)
	{
		if (coordinates == null)
		{
			return false;
		}
		return isValidPosition(coordinates.getGridX(), coordinates.getGridY());
	}

	// another way to call isValidPoisition
	public boolean isValidPosition(int xPosition, int yPosition)
	{
		return (xPosition >= 0 && xPosition < grid.length && yPosition >= 0 && yPosition < grid[0].length);
	}

}
