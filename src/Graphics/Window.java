package Graphics;

import Entity.*;

import Game.CustoMap;
import Game.Partie;
import Game.Score;
import Utils.Direction;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static Utils.Constants.*;

public class Window extends Application {
    public Stage currentStage;

    Direction dir = Direction.RIGHT;
    String wallsColor = "blue";
    String skin = "classic";
    String levelPath ="";
    boolean sound = true;
    int mdj=0;
    double volume=0.1;
    double tempVolume=0.1;
    Partie partie;
    String timer = "";

    double margin = 1.1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("pac man");

        final Group root = new Group();

        Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT * margin);
        root.getChildren().add(canvas);

        Image img = new Image("img/menu1/menu1Animate.png", SCENE_WIDTH, SCENE_HEIGHT, false, false);
        ImageView iv = new ImageView(img);
        root.getChildren().add(iv);

        Image fantomes = new Image("img/menu1/fantomes.png", 350, 150, false, false);
        ImageView ivFtmes = new ImageView(fantomes);
        ivFtmes.preserveRatioProperty();
        ivFtmes.setX(-375);
        ivFtmes.setY(SCENE_HEIGHT - SCENE_HEIGHT/6.0);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(6));
        transition.setToX(1200);
        transition.setToY(0);
        transition.setAutoReverse(false);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(ivFtmes);
        transition.play();

        root.getChildren().add(ivFtmes);

        iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> menu(stage));

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT * margin, Color.BLACK);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    dir = Direction.UP;
                    break;
                case RIGHT:
                    dir = Direction.RIGHT;
                    break;
                case DOWN:
                    dir = Direction.DOWN;
                    break;
                case LEFT:
                    dir = Direction.LEFT;
                    break;
                default:
                    break;
            }
        });

        currentStage = stage;
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public void menu(Stage stage) {
        partie = null;

        Image jouer = new Image("img/menu2/jouer.png",300,100,false,false);
        ImageView ivJouer = new ImageView(jouer);
        ivJouer.preserveRatioProperty();

        Image demo = new Image("img/menu2/demo.png",300,100,false,false);
        /*System.out.println(demo.getUrl());*/
        ImageView ivDemo = new ImageView(demo);
        ivDemo.preserveRatioProperty();

        Image imdj = new Image("img/menu2/mdj.png",700,100,false,false);
        ImageView ivMdj = new ImageView(imdj);
        ivDemo.preserveRatioProperty();

        Image custo = new Image("img/menu2/custo.png",700,100,false,false);
        ImageView ivCusto = new ImageView(custo);
        ivDemo.preserveRatioProperty();

        Image options = new Image("img/menu2/options.png",500,100,false,false);
        ImageView ivOption = new ImageView(options);
        ivDemo.preserveRatioProperty();
        Image trophy = new Image("img/menu2/trophy.png",300,300,false,false);
        ImageView ivTrophy = new ImageView(trophy);
        ivTrophy.preserveRatioProperty();

        ivTrophy.setX(-100);
        ivTrophy.setY(550);

        Group root = new Group();

        VBox vbox=new VBox();
        vbox.setSpacing(20.);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(ivJouer,ivDemo,ivMdj,ivCusto,ivOption);

        Canvas c = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        GraphicsContext bg = c.getGraphicsContext2D();
        Image image = new Image("img/menu2/bg.png");
        bg.drawImage(image, 0, 0, SCENE_WIDTH,SCENE_HEIGHT*margin);

        root.getChildren().add(c);
        root.getChildren().addAll(ivTrophy, vbox);

        ivJouer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> select(stage));

        ivDemo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            levelPath="src/levels/level1V2.txt";
            jeu(stage,mdj);
        });

        ivMdj.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> menuMdj(stage));

        ivCusto.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> custo(stage));

        ivOption.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> settings(stage));

        ivTrophy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> scoreBoard(stage));

        stage.setScene(new Scene(root,SCENE_WIDTH,SCENE_HEIGHT*margin));
    }

    public void jeu(Stage stage,int mdj) {
        AtomicBoolean menu = new AtomicBoolean(false);
        final Group root = new Group();
        LocalTime ltDebut = LocalTime.now();


        final Canvas[] canvas = {new Canvas(SCENE_WIDTH, SCENE_HEIGHT * margin)};
        GraphicsContext gc = canvas[0].getGraphicsContext2D();
        root.getChildren().add(canvas[0]);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    dir = Direction.UP;
                    break;
                case RIGHT:
                    dir = Direction.RIGHT;
                    break;
                case DOWN:
                    dir = Direction.DOWN;
                    break;
                case LEFT:
                    dir = Direction.LEFT;
                    break;
                case ESCAPE:
                    menu.set(!menu.get());
                    break;
                default:
                    break;
            }
        });
        stage.setScene(scene);
        if(mdj==0 || mdj==2 || mdj==4) {
            root.getChildren().removeAll();
            mdj1(menu, ltDebut, stage, gc, mdj);
        }
        if(mdj==1){
            root.getChildren().removeAll();
            ltDebut = ltDebut.plusSeconds(120);
            mdj1(menu, ltDebut, stage, gc, mdj);
        }
        }



    public void mdj1(AtomicBoolean menu, LocalTime ltDebut, Stage stage, GraphicsContext gc, int mdj){
        try {
            partie = new Partie(levelPath, wallsColor, this, skin,volume);
            AudioClip chomp=Window.openAudio("src/music/pacman-ghostnoises.wav");
            chomp.setVolume(volume);


            new AnimationTimer() {
                long prevtime;
                long deltaTime;



                LocalTime ltnow;
                public void handle(long currentNanoTime) {
                    ltnow = LocalTime.now();

                    timer = String.format("%02d:%02d",
                            ChronoUnit.MINUTES.between(ltDebut,ltnow)%60,
                            ChronoUnit.SECONDS.between(ltDebut,ltnow)%60);

                    partie.getPlateau().setFruit();

                    if (partie.getPacman().getLife() <= 0) {
                        this.stop();
                        partie.getPacman().stopSound();
                        chomp.stop();
                        if(mdj==1)
                            finJeu(stage,"lose",partie.getScore(),String.format("%02d:%02d",
                                    Math.abs(ChronoUnit.MINUTES.between(ltDebut,ltnow)%60),
                                    Math.abs(ChronoUnit.SECONDS.between(ltDebut,ltnow)%60)),mdj);
                        else
                            finJeu(stage, "lose", partie.getScore(), timer, mdj);

                        return;
                    }

                    if (!menu.get() && sound && !chomp.isPlaying())
                        chomp.play();
                    if(!(partie.getPlateau().isAvailablePG()) && mdj!=2){
                        this.stop();
                        partie.getPacman().stopSound();
                        chomp.stop();
                        if(mdj==1)
                            finJeu(stage,"win",partie.getScore(),String.format("%02d:%02d",
                                    Math.abs(ChronoUnit.MINUTES.between(ltDebut,ltnow)%60),
                                    Math.abs(ChronoUnit.SECONDS.between(ltDebut,ltnow)%60)),mdj);
                        else
                            finJeu(stage, "win", partie.getScore(), timer, mdj);
                        return;
                    }

                    if(mdj==2 && !(partie.getPlateau().isAvailablePG())){
                        partie.getPacman().addSpeed(partie.getPacman().getSpeed()*.2);
                        for (Entity e : partie.getPlateau().getPlateau())
                            if (e instanceof Ghost)
                                ((Ghost) e).addSpeed(((Ghost)e).getSpeed()*.2);
                        partie.getPlateau().refillPG();
                    }

                    if(mdj==1 && Math.abs(ChronoUnit.MINUTES.between(ltDebut,ltnow)%60)==0 && Math.abs(ChronoUnit.SECONDS.between(ltDebut,ltnow)%60)==0){
                        this.stop();
                        partie.getPacman().stopSound();
                        chomp.stop();
                        finJeu(stage,"lose", partie.getScore(), "02:00", mdj);
                    }
                    deltaTime = currentNanoTime - prevtime;


                    if(partie.getPacman().getSuperPacMan()) {
                        if(ChronoUnit.SECONDS.between(partie.getPacman().getSuperPacManTime(),LocalTime.now())>=10)
                            partie.getPacman().setSuperPacMan(false);
                    }

                    if (!menu.get() && deltaTime < 1_000_000_000/5) {
                        partie.getPacman().changeDir(dir);
                        partie.tick(deltaTime);
                    }

                    drawShapes(gc);

                    if (menu.get()) {
                        drawMenu(gc);
                    }

                    prevtime = currentNanoTime;
                }

                public void drawShapes(GraphicsContext gc) {
                    LocalTime ltnow = LocalTime.now();
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT * margin);
                    for (Entity e : partie.getPlateau().getPlateau()) {
                        e.draw(gc);
                    }
                    gc.setFill(Color.WHITE);
                    gc.fillText("Score : " + partie.getScore().getScore(), (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.02);
                    if(mdj==0 || mdj==2 || mdj==4) {
                        gc.fillText("Timer : " + timer, (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);
                    }else {
                        gc.fillText("Timer : " + String.format("%02d:%02d",
                                Math.abs(ChronoUnit.MINUTES.between(ltDebut, ltnow) % 60),
                                Math.abs(ChronoUnit.SECONDS.between(ltDebut, ltnow) % 60)),
                                (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);
                    }
                    Image pac = new Image("img/Pacman/"+skin+"/pacManL.png");
                    int i;
                    for (i = partie.getPacman().getLife(); i > 0; i--) {
                        gc.drawImage(pac, 10 + i * 22, SCENE_HEIGHT*margin - 30, 20, 20);
                    }

                }
                public void drawMenu(GraphicsContext gc) {
                    gc.setFill(new Color(0, 0, 0, .5));
                    gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
                    gc.setFill(Color.WHITE);
                    gc.fillText("Pause", (1.0*SCENE_WIDTH-50)/2, 1.0*SCENE_HEIGHT/2, 200);
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void custo(Stage stage) {
        Group group = new Group();

        HBox box = new HBox();
        box.setPadding(new Insets(SCENE_WIDTH/2,0,0,SCENE_WIDTH/6));
        box.setSpacing(50.);

        Image iCreateLvl = new Image("img/menu2/custo/Level_creator.png", 150, 150, false, false);
        ImageView ivCreateLvl = new ImageView(iCreateLvl);
        ivCreateLvl.setY(SCENE_HEIGHT-75);
        ivCreateLvl.setX(SCENE_WIDTH-150);

        ivCreateLvl.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            fromCreateLvl(stage);
        });
        Image bg = new Image("img/menu2/custo/custoBg.png", SCENE_WIDTH, SCENE_HEIGHT*margin, false, false);
        ImageView ivBg = new ImageView(bg);

        Image custoWall = new Image("img/menu2/custo/custoWall.png", 195, 65, false, false);
        ImageView ivCustoWall = new ImageView(custoWall);
        ivCustoWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> custoWall(stage));

        Image custoAll = new Image("img/menu2/custo/PacmanCusto.png", 222, 70, false, false);
        ImageView ivCustoAll = new ImageView(custoAll);
        ivCustoAll.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> custoCarac(stage));

        Image iback = new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false);
        ImageView ivback = new ImageView(iback);
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        box.getChildren().addAll(ivCustoAll,ivCustoWall);

        group.getChildren().addAll(ivBg,ivback,box,ivCreateLvl);

        Scene scene = new Scene(group,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(scene);
        stage.show();
    }

    public void finJeu(Stage stage,String etat, Score score, String timer,int mdj){
        Image msg;
        AudioClip soundfin = Window.openAudio("src/music/pacman_beginning.wav");
        soundfin.setVolume(volume);
        soundfin.play();
        if(etat.equals("win")) {
            msg = new Image("img/endGame/win.png", SCENE_WIDTH, SCENE_HEIGHT * margin, false, false);
        }
        else {
            msg = new Image("img/endGame/lose.png", SCENE_WIDTH, SCENE_HEIGHT * margin, false, false);
        }

        ImageView ivMsg = new ImageView(msg);

        Group root = new Group();
        root.getChildren().addAll(ivMsg);

        ivMsg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (mdj == 4)
                menu(stage);
            else{
            if (mdj == 2 || etat.equals("win"))
                ecrireScore(score, stage, timer, mdj);
            else
                afficheScore(stage, mdj);
            }
        });

        Scene fin = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(fin);
        stage.show();
    }

    public void settings(Stage stage) {
        Image iback = new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false);
        ImageView ivback = new ImageView(iback);
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        Image soundOn = new Image("img/menu2/option/son-on.png", 200, 100, false, false);
        ImageView ivSoundOn = new ImageView(soundOn);
        ivSoundOn.preserveRatioProperty();
        ivSoundOn.setX(1.0*SCENE_WIDTH/2 - 100);
        ivSoundOn.setY(1.0*SCENE_HEIGHT/4);

        Image soundOff = new Image("img/menu2/option/son-off.png", 200, 100, false, false);
        ImageView ivSoundOff = new ImageView(soundOff);
        ivSoundOff.preserveRatioProperty();
        ivSoundOff.setX(1.0*SCENE_WIDTH/2 - 100);
        ivSoundOff.setY(1.0*SCENE_HEIGHT/4);

        Group root = new Group();
        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);

        VBox vVol = new VBox();

        Label labVol = new Label("Volume : ");
        labVol.setStyle("-fx-text-fill:WHITE;");
        Slider slider = new Slider();


        slider.setMin(0);

        // The maximum value.
        slider.setMax(100);

        // Current value
        slider.setValue(volume*100);

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.setBlockIncrement(5);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                volume = newValue.intValue()*1.0/100;
                tempVolume = volume;
            }
        });

        vVol.getChildren().addAll(labVol,slider);
        vVol.setPrefHeight(1.0*SCENE_HEIGHT/4);
        vVol.setPrefWidth(1.0*SCENE_WIDTH/2);
        vVol.setLayoutX(1.0*SCENE_WIDTH/2 - 150);
        vVol.setLayoutY((1.0*SCENE_HEIGHT/4)+150);
        root.getChildren().addAll(main,ivSoundOn,ivback,vVol);


        ivSoundOn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            volume=0.0;
            root.getChildren().add(ivSoundOff);
            root.getChildren().remove(ivSoundOn);
        });

        ivSoundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            volume=tempVolume;

            root.getChildren().add(ivSoundOn);
            root.getChildren().remove(ivSoundOff);
        });

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT*margin));
    }
    public void select(Stage stage) {
        double width = 1.0*SCENE_WIDTH/4;
        double height = 1.0*SCENE_HEIGHT/4;

        VBox popVbox=new VBox();
        popVbox.setSpacing(10.);
        popVbox.setPadding(new Insets(0,0,0, SCENE_WIDTH/3));
        popVbox.setAlignment(Pos.TOP_CENTER);

        Group pop = new Group();

        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);

        pop.getChildren().add(main);



        Image ilvl1 = new Image("img/menu2/jouer/0002_Level-1.png", width, height, false, false);
        ImageView ivlvl1 = new ImageView(ilvl1);

        popVbox.getChildren().add(ivlvl1);

        ivlvl1.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level1V2.txt";
            jeu(stage,mdj);
        });

        Image ilvl2 = new Image("img/menu2/jouer/Level_2.png", width, height, false, false);
        ImageView ivlvl2 = new ImageView(ilvl2);

        popVbox.getChildren().add(ivlvl2);

        ivlvl2.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level2.txt";
            jeu(stage,mdj);
        });

        Image ilvl3 = new Image("img/menu2/jouer/0001_presentation1.2.png", width, height, false, false);
        ImageView ivlvl3 = new ImageView(ilvl3);

        popVbox.getChildren().add(ivlvl3);

        ivlvl3.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level1Pres.txt";
            jeu(stage,mdj);
        });

        Image ilvl4 = new Image("img/menu2/jouer/0000_presentation-2.png", width, height, false, false);
        ImageView ivlvl4 = new ImageView(ilvl4);

        popVbox.getChildren().add(ivlvl4);

        ivlvl4.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level2Pres.txt";
            jeu(stage,mdj);
        });
        ImageView ivback = new ImageView(new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false));
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> menu(stage));

        ImageView lvlCusto = new ImageView(new Image("img/menu2/jouer/Custom_level.png",width,height,false,false));
        lvlCusto.setX(SCENE_WIDTH-width);
        lvlCusto.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> selectCusto(stage));

        pop.getChildren().addAll(popVbox, ivback,lvlCusto);
        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }
    public void selectCusto(Stage stage){
        Group root = new Group();

        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);
        File repertoire = new File("src//levels/custo//");
        File[] files= repertoire.listFiles();
        assert files != null;
        Button[] buttons = new Button[files.length];
        for(int i=0;i<buttons.length;i++){
            buttons[i]= new Button(files[i].getName().substring(0,files[i].getName().length()-4));
            int finalI = i;
            buttons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, click-> {
                levelPath = "src//levels//custo//" + files[finalI].getName();
                jeu(stage, mdj);
                    }
            );
        }
        ImageView ivback = new ImageView(new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false));
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> select(stage));


        GridPane gridPane = new GridPane();

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if (i*10+j<buttons.length)
                    gridPane.add(buttons[i*10+j],j,i);
            }
        }


        root.getChildren().addAll(main,gridPane,ivback);

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT*margin));

    }

    public void menuMdj(Stage stage) {
        double width = 1.0*SCENE_WIDTH/4;
        double height = 1.0*SCENE_HEIGHT/4;

        VBox popVbox=new VBox();
        popVbox.setSpacing(30.);
        popVbox.setPadding(new Insets(0,0,0,SCENE_WIDTH/3));
        popVbox.setAlignment(Pos.TOP_CENTER);

        Group pop = new Group();

        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);

        pop.getChildren().add(main);

        ImageView ivback = new ImageView(new Image("img/back.png", width/2, height/2, false, false));
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        Image imdj1 = new Image("img/menu2/mdj/Classique.png", width, height, false, false);
        ImageView ivmdj1 = new ImageView(imdj1);

        popVbox.getChildren().add(ivmdj1);

        ivmdj1.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            mdj=0;
            menu(stage);
        });

        Image imdj2 = new Image("img/menu2/mdj/Contre-la-montre.png", width, height, false, false);
        ImageView ivmdj2 = new ImageView(imdj2);

        popVbox.getChildren().add(ivmdj2);

        ivmdj2.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            mdj=1;
            menu(stage);
        });

        Image imdj3 = new Image("img/menu2/mdj/Infinity.png", width, height, false, false);
        ImageView ivmdj3 = new ImageView(imdj3);

        popVbox.getChildren().add(ivmdj3);

        ivmdj3.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            mdj=2;
            menu(stage);
        });


        pop.getChildren().addAll(popVbox, ivback);
        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }

    public void afficheScore(Stage stage, int mode){
        Label tmp0;
        Label tmp1;
        Label tmp2;
        Label sepa;

        VBox vbox = new VBox();

        Group group = new Group();

        int c = 5;

        for (String s : Score.readScoreFromFile(mode)){

            String[] str = s.split(";");

            if (c <= 0)
                break;
            c--;

            tmp0 = new Label(str[0]);
            tmp0.setStyle("-fx-text-fill:WHITE; -fx-font-size:30px;");
            tmp1 = new Label("Score: "+str[1]);
            tmp1.setStyle("-fx-text-fill:WHITE; -fx-font-size:20px;");
            tmp2 = new Label("Time: "+str[2]);
            tmp2.setStyle("-fx-text-fill:WHITE; -fx-font-size:20px;");
            sepa = new Label("----------------------------------");
            sepa.setStyle("-fx-text-fill:WHITE; -fx-font-size:10px;");

            vbox.getChildren().addAll(tmp0,tmp1,tmp2,sepa);
        }

        vbox.setPadding(new Insets(160, 0,0,150));

        Image submit = new Image("img/Ok.png", 300, 300, false, false);
        ImageView ivSubmit = new ImageView(submit);
        ivSubmit.setY(SCENE_HEIGHT*margin-100);
        ivSubmit.setX(-100);

        Image bg = new Image("img/bgBlack.png", 500, SCENE_HEIGHT * margin + 200, false, false);
        ImageView ivBg = new ImageView(bg);

        Image label = new Image("img/high_score.png", 400, 100, false, false);
        ImageView ivLabel = new ImageView(label);
        ivLabel.setX(20.);

        group.getChildren().addAll(ivBg, ivLabel , ivSubmit,vbox);

        ivSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            menu(stage);
        });

        Scene score = new Scene(group,450,SCENE_HEIGHT*margin+100);
        stage.setScene(score);
        stage.show();
    }

    public void ecrireScore(Score score, Stage stage, String timer, int mdj){
        if(mdj!=4) {
            Label label = new Label("Entrer votre nom :");
            label.setStyle("-fx-text-fill:WHITE;");

            final TextField inputName = new TextField();

            Image submit = new Image("img/Ok.png", 250, 250, false, false);
            ImageView ivSubmit = new ImageView(submit);


            Image bg = new Image("img/bgBlack.png", SCENE_WIDTH, SCENE_HEIGHT * margin, false, false);
            ImageView ivBg = new ImageView(bg);

            VBox vbox = new VBox();
            vbox.setPadding(new Insets(15, 12, 15, 12));
            vbox.getChildren().addAll(label, inputName, ivSubmit);

            Group group = new Group();
            group.getChildren().addAll(ivBg, vbox);

            Scene scene = new Scene(group, 300, 300);
            stage.setScene(scene);


            ivSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
                String name = inputName.getText();
                Score.writeScoreToFile("" + score.getScore(), name, timer, mdj);
                afficheScore(stage, mdj);
            });
        }else
            menu(stage);
    }

    public void scoreBoard(Stage stage){
        double width = 200;
        double height = 200;

        Image select = new Image("img/Select_mode.png", 350, 100, false, false);
        ImageView ivSelect = new ImageView(select);
        ivSelect.setX(150);

        Image bg = new Image("img/bgBlack.png", SCENE_WIDTH, SCENE_HEIGHT*margin, false, false);
        ImageView ivBg = new ImageView(bg);

        Image classic = new Image("img/menu2/mdj/Classique.png", width, height, false, false);
        ImageView ivClassic = new ImageView(classic);

        Image clm = new Image("img/menu2/mdj/Contre-la-montre.png", width, height, false, false);
        ImageView ivclm = new ImageView(clm);

        Image infini = new Image("img/menu2/mdj/Infinity.png", width, height, false, false);
        ImageView ivinfini = new ImageView(infini);


        ImageView ivBack = new ImageView(new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false));
        ivBack.setY(SCENE_HEIGHT-20);


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(100,0,0,SCENE_WIDTH/3));
        vbox.setSpacing(20.);

        vbox.getChildren().addAll(ivClassic,ivclm,ivinfini);

        Group group = new Group();
        group.getChildren().addAll(ivBg,vbox,ivBack,ivSelect);

        ivClassic.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            afficheScore(stage , 0);
        });

        ivclm.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            afficheScore(stage , 1);
        });

        ivinfini.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            afficheScore(stage , 2);
        });

        ivBack.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            menu(stage);
        });

        Scene scoreBoard = new Scene(group,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(scoreBoard);
        stage.show();
    }
    public void fromCreateLvl(Stage stage){
        Group root = new Group();

        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);
        GridPane form = new GridPane();

        Label height = new Label("height : ");
        Label width = new Label("width : ");
        Label name = new Label("lvl name : ");
        height.setTextFill(Color.WHITE);
        width.setTextFill(Color.WHITE);
        name.setTextFill(Color.WHITE);
        TextField inputHeight = new TextField();
        TextField inputWidth = new TextField();
        TextField inputName = new TextField();

        Image submit = new Image("img/createLevel/Ok_custo.png", 50, 40, false, false);
        ImageView ivSubmit = new ImageView(submit);
        ivSubmit.setX(SCENE_WIDTH/3.1);
        ivSubmit.setY(20);

        ivSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            if(inputHeight.getText()!=null && inputHeight.getText()!=null && inputName.getText()!=null)
               createLvl(Integer.valueOf(inputHeight.getText()),Integer.valueOf(inputWidth.getText()),inputName.getText(),stage);
        });

        form.add(height,0,0);
        form.add(inputHeight,1,0);
        form.add(width,0,1);
        form.add(inputWidth,1,1);
        form.add(name,0,2);
        form.add(inputName,1,2);

        form.setAlignment(Pos.CENTER);
        ImageView ivback = new ImageView(new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false));
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> custo(stage));

        root.getChildren().addAll(main,form,ivSubmit,ivback);

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT*margin));

    }
    public void createLvl(int height,int width,String name,Stage stage){
        Group root = new Group();
        AtomicReference<String> currentBlock = new AtomicReference<>("img/bgBlack.png");
        int tailleP=height*width;
        ImageView[] tabIV = new ImageView[tailleP];
        VBox vbox = new VBox();
        HBox hbox = new HBox();

        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);
        GridPane plateau = new GridPane();
        for(int i=0;i<tailleP;i++){
            tabIV[i]=new ImageView(new Image("img/bgBlack.png", ((SCENE_WIDTH*1.0)/width)-8, (SCENE_HEIGHT*1.0)/height-8, false, false));

            int finalI = i;
            tabIV[i].addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
                tabIV[finalI].setImage(new Image(currentBlock.get(), ((SCENE_WIDTH*1.0)/width)-8, (SCENE_HEIGHT*1.0)/height-8, false, false));

            });
            plateau.add(tabIV[i],i%width,i/width);
        }
        hbox.setMinWidth(100);
        hbox.setSpacing(10);
        hbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView[] select = {  new ImageView(new Image("img/bgBlack.png", 50, 50, false, false)),
                                new ImageView(new Image("img/wall/purple/Wall-purple-T-full.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/PacGomme.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/SuperPacGomme.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/all_fruits.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/pacManR.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/BlinkyGhost.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/ClydeGhost.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/InkyGhost.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/PinkyGhost.png", 50, 50, false, false)),
                                new ImageView(new Image("img/createLevel/Home.png", 50, 50, false, false))
                              };
        for (ImageView imageView : select) {
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
                currentBlock.set(imageView.getImage().getUrl());
            });
            hbox.getChildren().add(imageView);
        }
        HBox hbox2 = new HBox();
        ImageView back = new ImageView(new Image("img/back.png", 75, 75, false, false));

        back.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            menu(stage);
        });

        ImageView save = new ImageView(new Image("img/createLevel/Save.png", 75, 75, false, false));
        save.addEventHandler(MouseEvent.MOUSE_CLICKED, reset->{
                CustoMap custoMap = new CustoMap(tabIV,name,height,width);
                String txt=custoMap.verifPlateau();
                if(!(txt.equals(""))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Error: "+txt);
                    alert.showAndWait();
                }else {
                    try {
                        custoMap.createLvlFile();
                        menu(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        });
        hbox2.getChildren().addAll(save,back);
        plateau.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
        vbox.getChildren().addAll(plateau,hbox,hbox2);
        vbox.setPadding(new Insets(0,0,0,SCENE_WIDTH/30));
        root.getChildren().addAll(main,vbox);

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT*margin));
    }

    public static AudioClip openAudio(String path) {
        return new AudioClip(Paths.get(path).toUri().toString());
    }

    public void custoWall(Stage stage){
        HBox popVbox=new HBox();

        Group pop = new Group();

        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);

        pop.getChildren().add(main);

        Image blueWall = new Image("img/wall/blue/Wall-blue-Block.png", 1.0*SCENE_WIDTH/6, 1.0*SCENE_HEIGHT/6, false, false);
        ImageView ivBlueWall = new ImageView(blueWall);

        popVbox.getChildren().add(ivBlueWall);

        double width = 1.0*SCENE_WIDTH/6;
        double height = 1.0*SCENE_HEIGHT/6;

        ivBlueWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "blue";
        });

        Image greenWall = new Image("img/wall/green/Wall-green-Block.png", width, height, false, false);
        ImageView ivGreenWall = new ImageView(greenWall);

        popVbox.getChildren().add(ivGreenWall);

        ivGreenWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "green";
        });

        Image orangeWall = new Image("img/wall/orange/Wall-orange-Block.png", width, height, false, false);
        ImageView ivorangeWall = new ImageView(orangeWall);

        popVbox.getChildren().add(ivorangeWall);

        ivorangeWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "orange";
        });

        Image purpleWall = new Image("img/wall/purple/Wall-purple-Block.png", width, height, false, false);
        ImageView ivpurpleWall = new ImageView(purpleWall);

        popVbox.getChildren().add(ivpurpleWall);

        ivpurpleWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "purple";
        });

        Image redWall = new Image("img/wall/red/Wall-red-Block.png", width, height, false, false);
        ImageView ivredWall = new ImageView(redWall);

        popVbox.getChildren().add(ivredWall);

        ivredWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "red";
        });

        Image yellowWall = new Image("img/wall/yellow/Wall-yellow-Block.png", width, height, false, false);
        ImageView ivyellowWall = new ImageView(yellowWall);

        popVbox.getChildren().add(ivyellowWall);

        ivyellowWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "yellow";
        });

        ImageView ivback = new ImageView(new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false));
        ivback.setY(SCENE_HEIGHT-20);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> custo(stage));

        pop.getChildren().addAll(popVbox,ivback);
        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }

    public void custoCarac(Stage stage){
        double width = 150;
        double height = 150;

        VBox popVbox1=new VBox();
        popVbox1.setPadding(new Insets(50,0,0, 100));
        popVbox1.setSpacing(20.);

        VBox popVbox2=new VBox();
        popVbox2.setPadding(new Insets(50,0,0, 400));
        popVbox2.setSpacing(20.);

        Group pop = new Group();

        Image normal = new Image("img/Pacman/presentation/normal.png", width, height, false, false);
        ImageView ivNormal = new ImageView(normal);
        popVbox1.getChildren().add(ivNormal);

        ivNormal.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> skin = "classic");

        Image pokemon = new Image("img/Pacman/presentation/pokemon.png", width, height, false, false);
        ImageView ivPokemon = new ImageView(pokemon);
        popVbox1.getChildren().add(ivPokemon);

        ivPokemon.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> skin = "Pokemon");

        Image samus = new Image("img/Pacman/presentation/samus.png", width, height, false, false);
        ImageView ivSamus = new ImageView(samus);
        popVbox2.getChildren().add(ivSamus);

        ivSamus.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> skin = "Samus");

        Image star = new Image("img/Pacman/presentation/starwars.png", width, height, false, false);
        ImageView ivStar = new ImageView(star);
        popVbox2.getChildren().add(ivStar);

        ivStar.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> skin = "Starwars");

        Image tortue = new Image("img/Pacman/presentation/tortue-ninja.png", width, height, false, false);
        ImageView ivTortue = new ImageView(tortue);
        popVbox2.getChildren().add(ivTortue);

        ivTortue.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> skin = "Ninja Turtle");

        Image bg = new Image("img/bgBlack.png", SCENE_WIDTH, SCENE_HEIGHT*margin, false, false);
        ImageView ivBg = new ImageView(bg);

        Image back = new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false);
        ImageView ivBack = new ImageView(back);
        ivBack.setY(SCENE_HEIGHT-20);

        ivBack.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> custo(stage));

        pop.getChildren().addAll(ivBg, ivBack,popVbox2, popVbox1);

        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }
}