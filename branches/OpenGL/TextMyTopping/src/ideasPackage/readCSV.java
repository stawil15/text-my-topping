package ideasPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import javax.swing.text.rtf.RTFEditorKit;

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
			
			//First two positions in CSV contain array dimentions
			map = new int[Integer.parseInt(dimmensions[0])][Integer.parseInt(dimmensions[1])];

			//Print for debugging
			System.out.println(dimmensions[0]);
			System.out.println(dimmensions[1]);
			System.out.println(Integer.parseInt(dimmensions[0]));
			System.out.println(Integer.parseInt(dimmensions[1]));

			//Makes the rest of the CSV into a 2D array
			for (int i = 0; i < Integer.parseInt(dimmensions[1]); i++)
			{
				if ((line = br.readLine()) != null)
				{
					String[] parsed = line.split(splitBy);
					for (int e = 0; e < Integer.parseInt(dimmensions[0]); e++) //parsed.length; e++)
					{
						map[e][i] = Integer.parseInt(parsed[e]);
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

		NPC = new String[getLinesInFile(csvFileToRead)][5];

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
			for (int x = 0; x < NPC.length; x++)
			{
				for (int y = 0; y < NPC[x].length; y++)
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
		String rawDialogs[][] = new String[getLinesInFile(csvFileToRead)][20];
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
						if (i < rawDialogs.length)
						{
							rawDialogs[i][e] = parsed[e];
						}
					}
				}
				i++;
			}
			for (int x = 0; x < rawDialogs.length; x++)
			{
				for (int y = 0; y < rawDialogs[x].length; y++)
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

		for (int index = rawDialogs.length - 1; index >= 0; index--)
		{
			String[] dialogRow = rawDialogs[index];
			if (dialogRow != null && dialogRow.length > 0 && dialogRow[0] != null)
			{
				if (dialogRow[0].equals(TYPE_DEFAULT_DIALOG))
				{
					DialogManager.addDialog(new Dialog(new String[] { dialogRow[2] }),
							dialogRow[1], dialogRow);
					if (dialogRow[3] != null && !dialogRow[3].equals(NULL_DIALOG))
					{
						DialogManager.setNextDialog(dialogRow[3], dialogRow[1]);
					}

				} else if (dialogRow[0].equals(TYPE_BRANCHING_DIALOG))
				{
					ArrayList<String> choices = new ArrayList<String>();
					ArrayList<Dialog> responses = new ArrayList<Dialog>();

					int numberOfOptions = Integer.parseInt(dialogRow[2]);
					PApplet.println("NUM OF OPTIONS: " + numberOfOptions);

					for (int optionIndex = 0; optionIndex < numberOfOptions; optionIndex++)
					{
						choices.add(dialogRow[3 + optionIndex]);
						PApplet.println("Option " + numberOfOptions + ": " + dialogRow[3 + optionIndex]);
						responses.add(DialogManager.getDialog(dialogRow[3 + numberOfOptions + optionIndex]));

					}

					DialogManager.addDialog(new BranchingDialog(null, choices, responses), dialogRow[1], dialogRow);
				}

			}
		}

	}

	public int getLinesInFile(String filename)
	{
		try
		{
			LineNumberReader lnr = new LineNumberReader(new FileReader(new File(filename)));
			lnr.skip(Long.MAX_VALUE);
			return lnr.getLineNumber() + 1;
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
}