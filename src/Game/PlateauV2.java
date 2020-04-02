package Game;
import java.io.*;

public class PlateauV2{
    Entity[] plateau;
    public PlateauV2(String levelPath) throws Exception{
        remplirPlateau(levelPath);
    }

    public void remplirPlateau(String levelPath)throws Exception{
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        String t = read.readLine();
        String[] lst = read.split(" ");
        murs = new int[Integer.parseInt(lst[0])][Integer.parseInt(lst[1])];
        items = new Items[Integer.parseInt(lst[0])][Integer.parseInt(lst[1])];
        int larg = murs[0].length;
        for(int i = 0;i<murs.length;i++){
            t=read.readLine();
            for (int j = 0 ; j<larg;j++){
                if (t.charAt(j)=="1") {
                    plateau[larg*i+j] = new Wall(j,i);
                }
                if (t.charAt(j)!="0"){
                    if (t.charAt(j)=="p")
                        plateau[larg*i+j] = new Pac-gomme();
                    else
                        plateau[larg*i+j] = new SuperPac-gomme();
                }else {
                    plateau[larg*i+j] = null;
                }

            }
        }
        read.close();
    }

    //public static void main(String[] args)throws Exception {
    //    Plateau j = new Plateau("level1.txt");
    //}
}