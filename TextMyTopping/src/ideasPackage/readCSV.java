package ideasPackage;

import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  

/** 
 * @author Nagesh Chauhan 
 *  
 */  
public class readCSV {  
	public int[][] readCsv(String filename) {

		int[][] map = null;
		String csvFileToRead = "data/level/" + filename;  
		BufferedReader br = null;  
		String line = "";  
		String splitBy = ",";  

		try {  

			br = new BufferedReader(new FileReader(csvFileToRead));
			line = br.readLine();
			String[] dimmensions = line.split(",");

			map = new int[Integer.parseInt(dimmensions[0])][Integer.parseInt(dimmensions[1])];

			System.out.println(dimmensions[0]);
			System.out.println(dimmensions[1]);

			System.out.println(Integer.parseInt(dimmensions[0]));
			System.out.println(Integer.parseInt(dimmensions[1]));

			for (int i = 0; i < Integer.parseInt(dimmensions[0]); i++)
			{
				if ((line = br.readLine()) != null) 
				{  
					String[] parsed = line.split(splitBy);  
					for (int e = 0; e < Integer.parseInt(dimmensions[1]); e++)
					{
						map[i][e] = Integer.parseInt(parsed[e]);
						System.out.print(map[i][e]);
					}
					System.out.println();
				}
			}
			for (int x = 0; x < Integer.parseInt(dimmensions[0]); x ++)
			{
				for (int y = 0; y < Integer.parseInt(dimmensions[1]); y ++)
				{
					System.out.print(map[x][y]);
				}
				System.out.println();
			}

		} catch (FileNotFoundException e) {  
			e.printStackTrace();
			System.out.println("file not found");
		} catch (IOException e) {  
			e.printStackTrace();  
			System.out.println("io exception");
		} finally {  
			if (br != null) {  
				try {
					br.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
					System.out.println("io exception 2");
				}  
			}  
		}  

		System.out.println("Done with reading CSV");
		return map;  
	}  
}  