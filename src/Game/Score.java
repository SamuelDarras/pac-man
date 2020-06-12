package Game;

import java.io.*;
import java.util.ArrayList;

public class Score {
	int playerScore = 0;
	public Score() {}

	private static String SCOREFILEScore0 = "src/Game/score.txt";
	private static String SCOREFILETime = "src/Game/time.txt";
	private static String SCOREFILEScore2 = "src/Game/score2";

	public static ArrayList<String[]> readScoreFromFile(int mdj) {
		ArrayList<String[]> r = new ArrayList<>();
		String str = "";
		try {
			if (mdj == 0){
			int bestScore = 0;
			InputStream file = new FileInputStream(SCOREFILEScore0);
			InputStreamReader fileReader = new InputStreamReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while ((str = reader.readLine()) != null) {
				String[] splitted = str.split(";");
				String name = splitted[0];
				String score = splitted[1];
				String time = splitted[2];
				bestScore = bestScore < Integer.parseInt(score) ? bestScore : Integer.parseInt(score);

				r.add((bestScore != Integer.parseInt(score) ? 0 : r.size()-1), splitted);
			}
			return r;
			}
			if (mdj == 1) {
				int bestScore = 0;
				InputStream file = new FileInputStream(SCOREFILETime);
				InputStreamReader fileReader = new InputStreamReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				while ((str = reader.readLine()) != null) {
					String[] splitted = str.split(";");
					String name = splitted[0];
					String score = splitted[1];
					String time = splitted[2];

					int tmp = Integer.parseInt(time.split(":")[0])*60+Integer.parseInt(time.split(":")[1]);

					bestScore = bestScore > tmp ? bestScore : tmp;

					r.add((bestScore == tmp ? 0 : r.size()-1), splitted);
				}
				return r;
			}
			if (mdj == 2){
				int bestScore = 0;
				InputStream file = new FileInputStream(SCOREFILEScore2);
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
		}
		catch(IOException ioEx) {
			System.out.println("Erreur lors de la lecture du score : " + ioEx.getMessage());
		}
		return null;
	}

	public static void writeScoreToFile(String playerScore, String playerName, String playerTime, int mdj) {
		try {
			if (mdj == 0 || mdj == 1) {
				FileWriter fileWriter = new FileWriter(SCOREFILEScore2, true);
				fileWriter.write(playerName + ";" + playerScore + ";" + playerTime + "\n");
				fileWriter.close();
			}
			else {
				FileWriter fileWriter = new FileWriter(SCOREFILETime, true);
				fileWriter.write(playerName + ";" + playerScore + ";" + playerTime + "\n");
				fileWriter.close();
			}
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
