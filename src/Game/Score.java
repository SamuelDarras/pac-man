package Game;// Partie | Score + Chrono + Nombre de vies restantes + DEMO

import java.io.*;

public class Score {
	// Player score
	int playerScore;

	// Ghost
	int ghost = 200;
	int multiplicatorGhost =1;
	final int coefGhost = 2;

	int eatenGhost = 0;

	public Score() {}

	/*                                                                                                *\
				==========================================================================
	                                            AVEC FICHIER
				==========================================================================
	\*                                                                                                */

	private static String SCOREFILE = "score.txt"; // Mettre le chemin

	public static String readScoreFromFile() {
		String str = "";
		try {
			InputStream file = new FileInputStream(SCOREFILE);
			InputStreamReader fileReader = new InputStreamReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			str = reader.readLine();
		}
		catch(IOException ioEx) {
			System.out.println("Erreur lors de la lecture du score : " + ioEx.getMessage());
		}
		return str;
	}

	public static void writeScoreToFile(String playerScore) {
		try {
			FileWriter fileWriter = new FileWriter(SCOREFILE);
			fileWriter.write(playerScore);
			fileWriter.close();
		}
		catch(IOException ioEx) {
			System.out.println("Erreur lors de l'écriture du score : " + ioEx.getMessage());
		}
	}

	/*                                                                                                *\
				==========================================================================
	                                            SANS FICHIER
				==========================================================================
	\*                                                                                                */

	public int getScore(){
		return playerScore;
	}

	public void setScore(int playerScore){
		this.playerScore = playerScore;
	}

	/*                                                                                                *\
				==========================================================================
	                                            FONCTIONS
				==========================================================================
	\*                                                                                                */

	public void scoreAdd(int score){
		playerScore += score;
	}
}
