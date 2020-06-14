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
        this.name = name;
        this.height=height;
        this.width=width;
    }

    public String verifPlateau(){
        String txt="";
        int compPG=0;
        int compSPG=0;
        int compPM=0;
        int compF=0;
        int compH=0;
        for (ImageView imageView : this.tabIV) {
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
        File file = new File("src//levels//custo//"+this.name+".txt");
        FileWriter fileWriter = new FileWriter("src//levels/custo//"+this.name+".txt");
        fileWriter.write(this.height+" "+this.width+"\n");
        for(int i=0;i<this.height;i++){
            for(int j=0;j<this.width;j++) {
                fileWriter.write(getCarac(this.tabIV[i * this.width + j]) + "");
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
        if(url.contains("img/PacGomme.png"))
            return "p";
        if(url.contains("img/SuperPacGomme.png"))
            return "s";
        if(url.contains("img/all_fruits.png"))
            return "F";
        if(url.contains("img/pacManR.png"))
            return "M";
        if(url.contains("img/BlinkyGhost.png"))
            return "B";
        if(url.contains("img/ClydeGhost.png"))
            return "C";
        if(url.contains("img/InkyGhost.png"))
            return "I";
        if(url.contains("img/PinkyGhost.png"))
            return "P";
        if(url.contains("img/Home.png"))
            return "H";
        return "";
    }
}
