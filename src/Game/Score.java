package Game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
	int playerScore = 0;
	public Score() {}

	private static String SCOREFILEScore0 = "src/Game/score.txt";
	private static String SCOREFILETime = "src/Game/time.txt";
	private static String SCOREFILEScore2 = "src/Game/score2";

	public static List<String> readScoreFromFile(int mdj) {
		String str = "";
		try {
			if (mdj == 0){
				InputStream file = new FileInputStream(SCOREFILEScore0);
				InputStreamReader fileReader = new InputStreamReader(file);
				BufferedReader reader = new BufferedReader(fileReader);

				List<Integer> scores = new ArrayList<Integer>();
				List<String> donnee = new ArrayList<String>();

				while ((str = reader.readLine()) != null) {
					String[] splitted = str.split(";");

					String score = splitted[1];

					scores.add(Integer.parseInt(score));
					donnee.add(str);
				}
				Collections.sort(scores);
				List<String> sort = new ArrayList<>();

				for (int i=0 ; i < scores.size(); i++){
					for (int j=0 ; j < scores.size(); j++){
						if (donnee.get(j).contains(";"+Integer.toString(scores.get(scores.size()-i-1))+";")){
							sort.add(donnee.get(j));
							break;
						}
					}
				}
				fileReader.close();
				file.close();

				return sort;
			}
			if (mdj == 1) {
				InputStream file = new FileInputStream(SCOREFILETime);
				InputStreamReader fileReader = new InputStreamReader(file);
				BufferedReader reader = new BufferedReader(fileReader);

				List<Integer> times = new ArrayList<Integer>();
				List<String> donnee = new ArrayList<String>();

				while ((str = reader.readLine()) != null) {
					String[] splitted = str.split(";");

					String temps = splitted[2];

					String[] tmptemps = temps.split(":");

					times.add(Integer.parseInt(tmptemps[0])*60+(Integer.parseInt(tmptemps[1])));
					donnee.add(str);
				}
				Collections.sort(times);
				List<String> timeFinal = new ArrayList<>();

				for (int i=0; i<times.size(); i++){
					int min = times.get(i)/60;
					int sec = times.get(i)%60;

					timeFinal.add(String.format("%02d:%02d",min,sec));
				}

				List<String> sort = new ArrayList<>();

				for (int i=0 ; i < timeFinal.size(); i++){
					for (int j=0 ; j < timeFinal.size(); j++){

						if (donnee.get(j).contains(";"+(timeFinal.get(i)))){
							sort.add(donnee.get(j));
							break;
						}

					}
				}
				fileReader.close();
				file.close();

				return sort;
			}
			if (mdj == 2){
				InputStream file = new FileInputStream(SCOREFILEScore2);
				InputStreamReader fileReader = new InputStreamReader(file);
				BufferedReader reader = new BufferedReader(fileReader);

				List<Integer> scores = new ArrayList<Integer>();
				List<String> donnee = new ArrayList<String>();

				while ((str = reader.readLine()) != null) {
					String[] splitted = str.split(";");

					String score = splitted[1];

					scores.add(Integer.parseInt(score));
					donnee.add(str);
				}
				Collections.sort(scores);
				List<String> sort = new ArrayList<>();

				for (int i=0 ; i < scores.size(); i++){
					for (int j=0 ; j < scores.size(); j++){
						if (donnee.get(j).contains(";"+Integer.toString(scores.get(scores.size()-i-1))+";")){
							sort.add(donnee.get(j));
							break;
						}
					}
				}
				fileReader.close();
				file.close();

				return sort;

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
