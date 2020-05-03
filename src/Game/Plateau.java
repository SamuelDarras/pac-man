package Game;
import Entity.*;

import java.io.*;

public class Plateau{
    Entity[] plateau;

    public Plateau(String levelPath) throws Exception{
        remplirPlateau(levelPath);
    }

    public void remplirPlateau(String levelPath)throws Exception{
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        String t = read.readLine();
        String[] lst = t.split(" ");
        int[][] murs = new int[Integer.parseInt(lst[0])][Integer.parseInt(lst[1])];
        Items[][] items = new Items[Integer.parseInt(lst[0])][Integer.parseInt(lst[1])];
        int larg = murs[0].length;
        for(int i = 0;i<murs.length;i++){
            t=read.readLine();
            for (int j = 0 ; j<larg;j++){
                if (t.charAt(j)=='1') {
                    plateau[larg*i+j] = new Wall(j,i);
                }
                if (t.charAt(j)!='0'){
                    if (t.charAt(j)=='p')
                        plateau[larg*i+j] = new PacGomme();
                    else
                        plateau[larg*i+j] = new SuperPacGomme();
                }else {
                    plateau[larg*i+j] = null;
                }

            }
        }
        read.close();
    }
    public Entity getIndex(int index){
        return plateau[index];
    }

    public static void main(String[] args)throws Exception {
        Plateau j = new Plateau("level1.txt");
        for(int i=0; i<j.plateau.length; i++)
            if (i%28==0){
                System.out.println();
                System.out.print(j.getIndex(i));
            }
    }

}