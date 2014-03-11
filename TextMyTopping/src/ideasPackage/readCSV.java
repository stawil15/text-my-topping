package ideasPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readCSV
{
	public int[][] readMapData(String filename)
	{

		int[][] map = null;
		String csvFileToRead = "data/level/" + filename;
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";

		try
		{
			br = new BufferedReader(new FileReader(csvFileToRead));
			line = br.readLine(); // Read first line
			String[] dimmensions = line.split(",");

			map = new int[Integer.parseInt(dimmensions[0])][Integer
					.parseInt(dimmensions[1])]; // First two positions in CSV
												// contain dimensions.

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
					}
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("file not found");
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("io exception");
		} finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("io exception 2");
				}
			}
		}

		System.out.println("Done reading CSV");
		return map;
	}

	public String[][] readNPCData(String filename)
	{

		String[][] NPC;
		String csvFileToRead = "data/level/" + filename;
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";

		System.out.println("Started");

		NPC = new String[20][20];

		try
		{
			br = new BufferedReader(new FileReader(csvFileToRead));

			System.out.println("test");

			int i = 0;

			while (line != null)
			{
				if ((line = br.readLine()) != null)
				{
					String[] parsed = line.split(splitBy);
					for (int e = 0; e < parsed.length; e++)
					{
						NPC[i][e] = parsed[e];
					}
				}
				i++;
			}
			for (int x = 0; x < 20; x++)
			{
				for (int y = 0; y < 20; y++)
				{
					System.out.print(NPC[x][y]);
				}
				System.out.println();
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("file not found");
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("io exception");
		} finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("io exception 2");
				}
			}
		}

		System.out.println("Done reading CSV");
		return NPC;
	}
}