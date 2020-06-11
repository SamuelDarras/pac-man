package Game;

import java.io.*;
import java.util.ArrayList;

public class Score {
	int playerScore = 0;
	public Score() {}

	private static String SCOREFILE = "src/Game/score.txt"; // Mettre le chemin

	public static ArrayList<String[]> readScoreFromFile() {
		ArrayList<String[]> r = new ArrayList<>();
		String str = "";
		try {
			InputStream file = new FileInputStream(SCOREFILE);
			InputStreamReader fileReader = new InputStreamReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while ((str = reader.readLine()) != null) {
				String[] splitted = str.split(";");
				String name = splitted[0];
				String score = splitted[1];
				String time = splitted[2];
				r.add(splitted);
			}
			return r;
		}
		catch(IOException ioEx) {
			System.out.println("Erreur lors de la lecture du score : " + ioEx.getMessage());
		}
		return null;
	}

	public static void writeScoreToFile(String playerScore, String playerName, String playerTime) {
		try {
			FileWriter fileWriter = new FileWriter(SCOREFILE, true);
			fileWriter.write(playerName + ";" + playerScore + ";" + playerTime + "\n");
			fileWriter.close();
		}
		catch(IOException ioEx) {
			System.out.println("Erreur lors de l'écriture du score : " + ioEx.getMessage());
		}
	}

	public int getScore(){
		return playerScore;
	}

	public void setScore(int playerScore){
		this.playerScore = playerScore;
	}

	public void scoreAdd(int score){
		playerScore += score;
	}
}
