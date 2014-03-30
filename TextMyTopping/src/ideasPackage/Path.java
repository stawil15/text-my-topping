package ideasPackage;

public class Path {

//	GridCoordinate playerLocation, enemyLocation;
//	PlayerCharacter
//	
//	public void findPath()
//	{
//		int playerX = 
//	}
	
	// needs to be recursive
	public GridCoordinate startLocation, endLocation;
	public float startX, startY, endX, endY;
	
	public Character player, enemy;
	
	// int playerX = playerLocation.getGridX();
	
	public Path(GridCoordinate startLocation, GridCoordinate endLocation)
	{
		int startX = startLocation.getGridX();
		int startY = startLocation.getGridY();
		int endX = endLocation.getGridX();
		int endY = endLocation.getGridY();
		
		int diffX = endX - startX;
		int diffY = endY - startY;
		
		
//		for (int index = 0; index <= diffX; index++)
//		{
//			if (Enemy.this.getCoordinates().getGridX() / Enemy.this.getCoordinates().getGridX() == 2)
//			{
//				
//			}
//		}
	}
	
}
