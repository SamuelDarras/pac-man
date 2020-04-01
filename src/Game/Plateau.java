import java.io.*;
package Game;

public class Plateau{
    int[][] plateau;

    public Plateau(String levelPath) throws Exception{
        creerPlateau(levelPath);
    }

    public int compteLigne(String levelPath)throws Exception{
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        int compteur = 0 ;
        while (read.readLine()!=null)
            compteur++;
        read.close();
        return compteur;
    }
    public int compteColone(String levelPath)throws Exception{
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        int cC = read.readLine().length();
        read.close();
        return cC;
    }

    public void creerPlateau(String levelPath)throws Exception{
        plateau = new int[compteLigne(levelPath)][compteColone(levelPath)];
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        String t;
        for(int i = 0;i<plateau.length;i++){
            t=read.readLine();
            for (int j = 0 ; j<plateau[0].length;j++){
                plateau[i][j]=t.charAt(j);
            }
        }
    }

    //public static void main(String[] args)throws Exception {
    //    Plateau j = new Plateau("level1.txt");
    //}
}