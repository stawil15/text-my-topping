package ideasPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

// This class reads csv files and makes them meaningful
public class readCSV
{
	// The types of dialogs that can be loaded dynamically
	private final static String TYPE_DEFAULT_DIALOG = "Default";
	private final static String TYPE_BRANCHING_DIALOG = "Branched";
	private final static String TYPE_BOOLEAN_DIALOG = "Boolean";
	private final static String TYPE_SETVALUE_DIALOG = "SetValue";
	private final static String TYPE_GAMEOVER_DIALOG = "GameOver";
	private final static String TYPE_GAMEWIN_DIALOG = "GameWin";
	private final static String TYPE_DESTROYOBJECT_DIALOG = "Destroy";
	public final static String NULL_DIALOG = "null";

	// Read the map
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
			String songName = br.readLine(); // Read first line
			MusicManager.addSong(filename, songName);
			line = br.readLine();
			String[] dimmensions = line.split(",");
			//First two positions in CSV contain array dimensions
			map = new int[Integer.parseInt(dimmensions[0])][Integer.parseInt(dimmensions[1])];

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
		return map;
	}

	// Read the NPC data
	public String[][] readNPCData(String filename)
	{

		String[][] NPC;
		String csvFileToRead = "data/level/" + filename;
		BufferedReader br = null;
		String line = "";
		String splitBy = " ,";


		NPC = new String[getLinesInFile(csvFileToRead)][5];

		try
		{
			br = new BufferedReader(new FileReader(csvFileToRead));
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

		return NPC;
	}

	// read all the dialogs
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
				}
			}
			
			br.close();

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

					for (int optionIndex = 0; optionIndex < numberOfOptions; optionIndex++)
					{
						choices.add(dialogRow[3 + optionIndex]);
						responses.add(DialogManager.getDialog(dialogRow[3 + numberOfOptions + optionIndex]));

					}

					DialogManager.addDialog(new BranchingDialog(null, choices, responses), dialogRow[1], dialogRow);
				}
				else if (dialogRow[0].equals(TYPE_BOOLEAN_DIALOG))
				{
					String variableName = dialogRow[2];
					DialogManager.addDialog(new BooleanDialog(variableName), dialogRow[1], dialogRow);
					DialogManager.setFalseDialog(dialogRow[3], dialogRow[1]);
					DialogManager.setTrueDialog(dialogRow[4], dialogRow[1]);
				}
				else if (dialogRow[0].equals(TYPE_SETVALUE_DIALOG))
				{
					String variableName = dialogRow[2];
					boolean value = Boolean.parseBoolean(dialogRow[3]);
					DialogManager.addDialog(new ValueSetBooleanDialog(variableName, value), dialogRow[1], dialogRow);
					if (dialogRow[4] != null && !dialogRow[4].equals(NULL_DIALOG))
					{
						DialogManager.setNextDialog(dialogRow[4], dialogRow[1]);
					}
				}
				else if (dialogRow[0].equals(TYPE_GAMEOVER_DIALOG))
				{
					DialogManager.addDialog(new GameOverDialog(new String[] {dialogRow[2]}), dialogRow[1], dialogRow);
					if (dialogRow[3] != null && !dialogRow[3].equals(NULL_DIALOG))
					{
						DialogManager.setNextDialog(dialogRow[3], dialogRow[1]);
					}

				}
				else if (dialogRow[0].equals(TYPE_GAMEWIN_DIALOG))
				{
					DialogManager.addDialog(new GameWinDialog(new String[] {dialogRow[2]}), dialogRow[1], dialogRow);
					if (dialogRow[3] != null && !dialogRow[3].equals(NULL_DIALOG))
					{
						DialogManager.setNextDialog(dialogRow[3], dialogRow[1]);
					}

				}
				else if (dialogRow[0].equals(TYPE_DESTROYOBJECT_DIALOG))
				{
					DialogManager.addDialog(new DestroyCollidableDialog(),dialogRow[1],dialogRow);
					if (dialogRow[2] != null && !dialogRow[2].equals(NULL_DIALOG))
					{
						DialogManager.setNextDialog(dialogRow[2], dialogRow[1]);
					}
				}

			}
		}

	}

	// Returns how many liens are in a file
	public int getLinesInFile(String filename)
	{
		try
		{
			LineNumberReader lnr = new LineNumberReader(new FileReader(new File(filename)));
			lnr.skip(Long.MAX_VALUE);
			lnr.close();
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