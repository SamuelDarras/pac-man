package Game;

import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CustoMap {
    ImageView[] tabIV;
    String name;
    int height;
    int width;

    public CustoMap(ImageView[] tabIV,String name,int height,int width){
        this.tabIV=tabIV;
        this.name = name;                                                   //initialise avec le "plateau", le nom du fichier, la hauteur du plateau et ça largeur
        this.height=height;
        this.width=width;
    }

    public String verifPlateau(){
        String txt="";
        int compPG=0;
        int compSPG=0;
        int compPM=0;                                                 //permet de vérifier la présence ou non de certains éléments du plateau afin d'éviter un disfonctionnement du programme
        int compF=0;
        int compH=0;
        for (ImageView imageView : tabIV) {
            if (imageView.getImage().getUrl().contains("/PacGomme"))
                compPG++;
            if (imageView.getImage().getUrl().contains("SuperPacGomme"))
                compSPG++;
            if (imageView.getImage().getUrl().contains("pacManR"))
                compPM++;
            if (imageView.getImage().getUrl().contains("Ghost"))
                compF++;
            if (imageView.getImage().getUrl().contains("Home"))
                compH++;
        }
        if(compPG==0 && compSPG==0)
            txt+=" Au moin 1 PacGomme ou SuperPacGomme.";
        if(compPM!=1)
            txt+=" Il faut 1 seul PacMan.";
        if(compF!=0 && compH==0)
            txt+=" Il faut 1 maison pour les fantomes.";
        if(compH>=2)
            txt+=" Il faut qu'une seule maison";
        return txt;
    }
    public void createLvlFile() throws IOException {
        File file = new File("src//levels//custo//"+name+".txt");
        FileWriter fileWriter = new FileWriter("src//levels/custo//"+name+".txt");
        fileWriter.write(height+" "+width+"\n");
        for(int i=0;i<height;i++){                                                          //permet de créer le .txt du level avec toutes les informations du level
            for(int j=0;j<width;j++) {
                fileWriter.write(getCarac(tabIV[i * width + j]) + "");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
    public String getCarac(ImageView iv){
        String url = iv.getImage().getUrl();
        if(url.contains("img/bgBlack.png"))
            return "0";
        if(url.contains("img/wall/purple/Wall-purple-T-full.png"))
            return "1";
        if(url.contains("img/createLevel/PacGomme.png"))
            return "p";
        if(url.contains("img/createLevel/SuperPacGomme.png"))
            return "s";
        if(url.contains("img/createLevel/all_fruits.png"))
            return "F";
        if(url.contains("img/createLevel/pacManR.png"))                   //permet de renvoyer le caractère correspondant à l'image permettant la création du .txt
            return "M";
        if(url.contains("img/createLevel/BlinkyGhost.png"))
            return "B";
        if(url.contains("img/createLevel/ClydeGhost.png"))
            return "C";
        if(url.contains("img/createLevel/InkyGhost.png"))
            return "I";
        if(url.contains("img/createLevel/PinkyGhost.png"))
            return "P";
        if(url.contains("img/createLevel/Home.png"))
            return "H";
        return "";
    }
}
