package Graphics;

import Entity.*;

import Game.Partie;
import Game.Score;
import Utils.Constants;
import Utils.Direction;
import com.sun.prism.Graphics;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.nio.file.Paths;
import java.rmi.dgc.VMID;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static Utils.Constants.*;

public class Window extends Application {
    public Stage currentStage;

    Direction dir = Direction.RIGHT;
    String wallsColor = "blue";
    String levelPath ="";
    boolean sound = true;
    boolean boolSPM=true;
    long timerSPM=0;
    int mdj=0;
    double volume=0.3;
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

        Image img = new Image("img/menu1Animate.png", SCENE_WIDTH, SCENE_HEIGHT, false, false);
        ImageView iv = new ImageView(img);
        root.getChildren().add(iv);

        Image fantomes = new Image("img/fantomes.png", 350, 150, false, false);
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

//        AudioClip son = Window.openAudio("src/music/pacman_beginning.wav");
//       son.play();

        stage.setResizable(false);
        stage.show();
    }

    public void menu(Stage stage) {
        partie = null;

        Image jouer = new Image("img/jouer.png",300,100,false,false);
        ImageView ivJouer = new ImageView(jouer);
        ivJouer.preserveRatioProperty();

        Image demo = new Image("img/demo.png",300,100,false,false);
        /*System.out.println(demo.getUrl());*/
        ImageView ivDemo = new ImageView(demo);
        ivDemo.preserveRatioProperty();

        Image mdj = new Image("img/mdj.png",700,100,false,false);
        ImageView ivMdj = new ImageView(mdj);
        ivDemo.preserveRatioProperty();

        Image custo = new Image("img/custo.png",700,100,false,false);
        ImageView ivCusto = new ImageView(custo);
        ivDemo.preserveRatioProperty();

        Image options = new Image("img/options.png",500,100,false,false);
        ImageView ivOption = new ImageView(options);
        ivDemo.preserveRatioProperty();

        Image trophy = new Image("img/trophy.png",300,300,false,false);
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
        Image image = new Image("img/bg.png");
        bg.drawImage(image, 0, 0, SCENE_WIDTH,SCENE_HEIGHT*margin);

        root.getChildren().add(c);
        root.getChildren().addAll(ivTrophy, vbox);

        ivJouer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> select(stage));

        ivDemo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            levelPath="src/levels/level1V2.txt";
            jeu(stage);
        });

        ivMdj.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> mdj(stage));

        ivCusto.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> custo(stage));

        ivOption.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> settings(stage));

        ivTrophy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> afficheScore(stage));

        stage.setScene(new Scene(root,SCENE_WIDTH,SCENE_HEIGHT*margin));
    }

    public void jeu(Stage stage) {
        AtomicBoolean menu = new AtomicBoolean(false);
        final Group root = new Group();
        LocalTime ltDebut = LocalTime.now();
        AudioClip chomp = Window.openAudio("src/music/pacman_chomp.wav");
        chomp.setVolume(volume);

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
        if(mdj==0 || mdj==2)
            root.getChildren().removeAll();
            mdj1(menu, ltDebut, stage, gc, mdj,chomp);
        if(mdj==1){
            root.getChildren().removeAll();
            ltDebut = ltDebut.plusSeconds(120);
            mdj1(menu, ltDebut, stage, gc, mdj,chomp);
        }
        }



    public void mdj1(AtomicBoolean menu, LocalTime ltDebut, Stage stage, GraphicsContext gc, int mdj,AudioClip chomp){
        try {
            partie = new Partie(levelPath, wallsColor, this);

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
                        finJeu(stage,"lose", partie.getScore(), timer);
                        return;
                    }
                    if(!(partie.getPlateau().isAvailablePG()) && mdj!=2){
                        this.stop();
                        finJeu(stage,"win", partie.getScore(), timer);
                        return;
                    }

                    if(mdj==2 && !(partie.getPlateau().isAvailablePG())){
                        partie.getPlateau().refillPG();
                    }

                    if(mdj==1 && Math.abs(ChronoUnit.HOURS.between(ltDebut,ltnow))==0 && Math.abs(ChronoUnit.MINUTES.between(ltDebut,ltnow)%60)==0 && Math.abs(ChronoUnit.SECONDS.between(ltDebut,ltnow)%60)==0){
                        this.stop();
                        finJeu(stage,"lose", partie.getScore(), timer);
                    }
                    deltaTime = currentNanoTime - prevtime;

                    if (!menu.get() && sound && !chomp.isPlaying())
                        chomp.play();

                    if(partie.getPacman().getSuperPacMan()) {
                        if (boolSPM) {
                            timerSPM = ChronoUnit.SECONDS.between(ltDebut, ltnow);
                            boolSPM = false;
                        }
                        else if(timerSPM != 0 && (ChronoUnit.SECONDS.between(ltDebut,ltnow)-timerSPM>=10)){
                            boolSPM = true;
                            timerSPM=0;
                            partie.getPacman().setSuperPacMan(false);
                        }
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
                    if(mdj==0 || mdj==2)
                        gc.fillText("Timer : " + timer, (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);
                    else
                        gc.fillText("Timer : " + String.format("%02d:%02d",
                                Math.abs(ChronoUnit.MINUTES.between(ltDebut,ltnow)%60),
                                Math.abs(ChronoUnit.SECONDS.between(ltDebut,ltnow)%60)),
                                (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);


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
            menu(stage);
        });

        Image greenWall = new Image("img/wall/green/Wall-green-Block.png", width, height, false, false);
        ImageView ivGreenWall = new ImageView(greenWall);

        popVbox.getChildren().add(ivGreenWall);

        ivGreenWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "green";
            menu(stage);
        });

        Image orangeWall = new Image("img/wall/orange/Wall-orange-Block.png", width, height, false, false);
        ImageView ivorangeWall = new ImageView(orangeWall);

        popVbox.getChildren().add(ivorangeWall);

        ivorangeWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "orange";
            menu(stage);
        });

        Image purpleWall = new Image("img/wall/purple/Wall-purple-Block.png", width, height, false, false);
        ImageView ivpurpleWall = new ImageView(purpleWall);

        popVbox.getChildren().add(ivpurpleWall);

        ivpurpleWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "purple";
            menu(stage);
        });

        Image redWall = new Image("img/wall/red/Wall-red-Block.png", width, height, false, false);
        ImageView ivredWall = new ImageView(redWall);

        popVbox.getChildren().add(ivredWall);

        ivredWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "red";
            menu(stage);
        });

        Image yellowWall = new Image("img/wall/yellow/Wall-yellow-Block.png", width, height, false, false);
        ImageView ivyellowWall = new ImageView(yellowWall);

        popVbox.getChildren().add(ivyellowWall);

        ivyellowWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            wallsColor = "yellow";
            menu(stage);
        });

        pop.getChildren().add(popVbox);
        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }

    public void finJeu(Stage stage,String etat, Score score, String timer){
        Image msg;
        if(etat.equals("win")) {
            msg = new Image("img/win.png", SCENE_WIDTH, SCENE_HEIGHT * margin, false, false);
        }
        else {
            msg = new Image("img/lose.png", SCENE_WIDTH, SCENE_HEIGHT * margin, false, false);
        }

        ImageView ivMsg = new ImageView(msg);

        Group root = new Group();
        root.getChildren().addAll(ivMsg);

        ivMsg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ecrireScore(score, stage, timer));

        Scene fin = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(fin);
        stage.show();
    }

    public void settings(Stage stage) {
        Image iback = new Image("img/back.png", 1.0*SCENE_WIDTH/8, 1.0*SCENE_HEIGHT/8, false, false);
        ImageView ivback = new ImageView(iback);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        Image soundOn = new Image("img/son-on.png", 200, 100, false, false);
        ImageView ivSoundOn = new ImageView(soundOn);
        ivSoundOn.preserveRatioProperty();
        ivSoundOn.setX(1.0*SCENE_WIDTH/2 - 100);
        ivSoundOn.setY(1.0*SCENE_HEIGHT/4);

        Image soundOff = new Image("img/son-off.png", 200, 100, false, false);
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
            }
        });

        vVol.getChildren().addAll(labVol,slider);
        vVol.setPrefHeight(1.0*SCENE_HEIGHT/4);
        vVol.setPrefWidth(1.0*SCENE_WIDTH/2);
        vVol.setLayoutX(1.0*SCENE_WIDTH/2 - 150);
        vVol.setLayoutY((1.0*SCENE_HEIGHT/4)+150);
        root.getChildren().addAll(main,ivSoundOn,ivback,vVol);


        ivSoundOn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            sound = false;
            root.getChildren().add(ivSoundOff);
            root.getChildren().remove(ivSoundOn);
        });

        ivSoundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            sound = true;

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

        Image iback = new Image("img/back.png", width/2, height/2, false, false);
        ImageView ivback = new ImageView(iback);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        Image ilvl1 = new Image("img/0002_Level-1.png", width, height, false, false);
        ImageView ivlvl1 = new ImageView(ilvl1);

        popVbox.getChildren().add(ivlvl1);

        ivlvl1.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level1V2.txt";
            jeu(stage);
        });

        Image ilvl2 = new Image("img/Level_2.png", width, height, false, false);
        ImageView ivlvl2 = new ImageView(ilvl2);

        popVbox.getChildren().add(ivlvl2);

        ivlvl2.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level2.txt";
            jeu(stage);
        });

        Image ilvl3 = new Image("img/0001_presentation1.2.png", width, height, false, false);
        ImageView ivlvl3 = new ImageView(ilvl3);

        popVbox.getChildren().add(ivlvl3);

        ivlvl3.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level1Pres.txt";
            jeu(stage);
        });

        Image ilvl4 = new Image("img/0000_presentation-2.png", width, height, false, false);
        ImageView ivlvl4 = new ImageView(ilvl4);

        popVbox.getChildren().add(ivlvl4);

        ivlvl4.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            levelPath="src/levels/level2Pres.txt";
            jeu(stage);
        });

        pop.getChildren().addAll(popVbox, ivback);
        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }

    public void mdj(Stage stage) {
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

        Image iback = new Image("img/back.png", width/2, height/2, false, false);
        ImageView ivback = new ImageView(iback);
        ivback.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        Image imdj1 = new Image("img/Classique.png", width, height, false, false);
        ImageView ivmdj1 = new ImageView(imdj1);

        popVbox.getChildren().add(ivmdj1);

        ivmdj1.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            mdj=0;
            menu(stage);
        });

        Image imdj2 = new Image("img/Contre-la-montre.png", width, height, false, false);
        ImageView ivmdj2 = new ImageView(imdj2);

        popVbox.getChildren().add(ivmdj2);

        ivmdj2.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            mdj=1;
            menu(stage);
        });

        Image imdj3 = new Image("img/Infinity.png", width, height, false, false);
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

    public void afficheScore(Stage stage){
        Label tmp0;
        Label tmp1;
        Label tmp2;
        Label sepa;

        VBox vbox = new VBox();

        Group group = new Group();

        int c = 5;

        for (String[] s : Score.readScoreFromFile()){

            if (c <= 0)
                break;
            c--;

            tmp0 = new Label(s[0]);
            tmp0.setStyle("-fx-text-fill:WHITE; -fx-font-size:30px;");
            tmp0.setAlignment(Pos.TOP_CENTER);
            tmp1 = new Label("Score : "+ s[1]);
            tmp1.setStyle("-fx-text-fill:WHITE; -fx-font-size:15px;");
            tmp2 = new Label("Temps : "+s[2]);
            tmp2.setStyle("-fx-text-fill:WHITE; -fx-font-size:15px;");
            sepa = new Label("##########");
            sepa.setStyle("-fx-text-fill:WHITE; -fx-font-size:20px;");

            vbox.getChildren().addAll(tmp0, tmp1, tmp2, sepa);
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

    public void ecrireScore(Score score, Stage stage, String timer){
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
        group.getChildren().addAll(ivBg,vbox);

        Scene scene = new Scene(group, 300, 300);
        stage.setScene(scene);


        ivSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            String name = inputName.getText();
            Score.writeScoreToFile(""+score.getScore(), name, timer);
            afficheScore(stage);
        });

    }

    public static AudioClip openAudio(String path) {
        return new AudioClip(Paths.get(path).toUri().toString());
    }

}