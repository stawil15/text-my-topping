package ideasPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;

public class readCSV
{
	private final static String TYPE_DEFAULT_DIALOG = "Default";
	private final static String TYPE_BRANCHING_DIALOG = "Branched";
	public final static String NULL_DIALOG = "null";

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

			map = new int[Integer.parseInt(dimmensions[0])][Integer.parseInt(dimmensions[1])]; // First
																								// two
																								// positions
																								// in
																								// CSV
																								// contain
																								// dimensions.

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
		String splitBy = " ,";

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
					if (NPC[x][y] != null)
					{
						NPC[x][y] = NPC[x][y].replaceAll("\\\\n", "\n");
					}
					System.out.print(NPC[x][y] + " | ");
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

	public void readDialogueData(String filename)
	{
		String csvFileToRead = "data/level/Dialog" + filename;
		String rawDialogs[][] = new String[20][20];
		String line = "";
		String splitBy = " ,";

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(csvFileToRead));
			int i = 0;

			while (line != null)
			{
				if ((line = br.readLine()) != null)
				{
					String[] parsed = line.split(splitBy);
					for (int e = 0; e < parsed.length; e++)
					{
						rawDialogs[i][e] = parsed[e];
					}
				}
				i++;
			}
			for (int x = 0; x < 20; x++)
			{
				for (int y = 0; y < 20; y++)
				{
					if (rawDialogs[x][y] != null)
					{
						rawDialogs[x][y] = rawDialogs[x][y].replaceAll("\\\\n", "\n");
						rawDialogs[x][y] = rawDialogs[x][y].replaceAll("\\\\c", ",");
					}
					System.out.print(rawDialogs[x][y] + " | ");
				}
				System.out.println();
			}

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int index = rawDialogs.length-1; index>=0; index--)
		{
			String[] dialogRow = rawDialogs[index];
			if (dialogRow != null && dialogRow.length > 0 && dialogRow[0] != null)
			{
				if (dialogRow[0].equals(TYPE_DEFAULT_DIALOG))
				{
					Dialog addedDialog = DialogManager.addDialog(new Dialog(new String[] { dialogRow[2] }),
							dialogRow[1]);
					if (dialogRow[3] != null && !dialogRow[3].equals(NULL_DIALOG))
					{
						addedDialog.setNextDialog(DialogManager.getDialog(dialogRow[3]));
					}

				} else if (dialogRow[0].equals(TYPE_BRANCHING_DIALOG))
				{
					ArrayList<String> choices = new ArrayList<String>();
					ArrayList<Dialog> responses = new ArrayList<Dialog>();
					
					int numberOfOptions = Integer.parseInt(dialogRow[2]);
					PApplet.println("NUM OF OPTIONS: " + numberOfOptions);

					for (int optionIndex = 0; optionIndex < numberOfOptions; optionIndex++)
					{
						choices.add(dialogRow[3+optionIndex]);
						PApplet.println("Option " + numberOfOptions + ": " + dialogRow[3+optionIndex]);
						responses.add(DialogManager.getDialog(dialogRow[3+numberOfOptions+optionIndex]));
						
					}
					
					DialogManager.addDialog(new BranchingDialog(null, choices, responses), dialogRow[1]);
				}
				
				
			}
		}

	}
}