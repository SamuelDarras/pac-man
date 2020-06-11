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
			int bestScore = 0;
			InputStream file = new FileInputStream(SCOREFILE);
			InputStreamReader fileReader = new InputStreamReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while ((str = reader.readLine()) != null) {
				String[] splitted = str.split(";");
				String name = splitted[0];
				String score = splitted[1];
				String time = splitted[2];
				bestScore = bestScore > Integer.parseInt(score) ? bestScore : Integer.parseInt(score);

				r.add((bestScore == Integer.parseInt(score) ? 0 : r.size()-1), splitted);
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

	public static void main(String[] args) {
		Score s = new Score();
		int c = 5;
		for (String[] strs : s.readScoreFromFile()) {
			if (c <= 0)
				break;
			c--;
			System.out.println(strs[0] + "  " + strs[1] + "  " + strs[2]);
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
