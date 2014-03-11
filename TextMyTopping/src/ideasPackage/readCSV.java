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
			
			for (int i = 0; i < Integer.parseInt(dimmensions[1]); i++)
			{
				while ((line = br.readLine()) != null) {  

					String[] parsed = line.split(splitBy);  
					for (int e = 0; e < Integer.parseInt(dimmensions[0]); e++)
					{
						map[e][i] = Integer.parseInt(parsed[e]);
					}

				}
			}

		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			if (br != null) {  
				try {  
					br.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}  

		System.out.println("Done with reading CSV");
		return map;  
	}  
}  