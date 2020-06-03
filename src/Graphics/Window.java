package Graphics;

import Entity.*;

import Game.Partie;
import Utils.Direction;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;

import static Utils.Constants.*;

public class Window extends Application {
    Direction dir = Direction.RIGHT;

    Partie partie;

    double margin = 1.1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("toto");

        final Group root = new Group();

        Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT * margin);
        GraphicsContext gc = canvas.getGraphicsContext2D();
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

        iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            menu(stage);
            /*root.getChildren().remove(iv);
            root.getChildren().remove((ivFtmes));

            try {

                new AnimationTimer() {
                    long prevtime;
                    long deltaTime;

                    Partie partie = new Partie("src/levels/level1V2.txt");

                    AudioClip chomp = Window.openAudio("src/music/pacman_chomp.wav");

                    public void handle(long currentNanoTime) {

                        deltaTime = currentNanoTime - prevtime;

                        //if (!chomp.isPlaying())
                            //chomp.play();

                        partie.getPacman().changeDir(dir);
                        partie.tick(deltaTime);

                        drawShapes(gc);

                        prevtime = currentNanoTime;
                    }

                    public void drawShapes(GraphicsContext gc) {
                        gc.clearRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT * margin);

                        for (Entity e : partie.getPlateau().getPlateau()) {
                            e.draw(gc);
                            //e.drawHitbox(gc);

                            if (e instanceof Ghost)
                                ((Ghost) e).move(partie.getPacman());

                            //String type = e.getClass().toString().substring(13);
                            //System.out.println(type);
                        }
                        gc.setFill(Color.WHITE);
                        gc.fillText("Score : " + partie.getScore().getScore(), (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);
                    }
                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }*/
        });

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


        stage.setScene(scene);

//        AudioClip son = Window.openAudio("src/music/pacman_beginning.wav");
//       son.play();

        stage.setResizable(true);
        stage.show();
    }

    private void menu(Stage stage) {
        System.out.println("azertyuiop");
        Image demo = new Image("img/demo.png",300,100,false,false);
        System.out.println(demo.getUrl());
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

        Image soundOn = new Image("img/son-on.png", 200, 100, false, false);
        ImageView ivSoundOn = new ImageView(soundOn);
        ivSoundOn.preserveRatioProperty();
        ivSoundOn.setX(0);
        ivSoundOn.setY(800);

        Image soundOff = new Image("img/son-off.png", 200, 100, false, false);
        ImageView ivSoundOff = new ImageView(soundOff);
        ivSoundOff.preserveRatioProperty();
        ivSoundOff.setX(0);
        ivSoundOff.setY(800);

        Group root = new Group();

        VBox vbox=new VBox();
        vbox.setSpacing(90.);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(ivDemo,ivMdj,ivCusto,ivOption);

        Canvas c = new Canvas(800,900);
        GraphicsContext bg = c.getGraphicsContext2D();
        Image image = new Image("img/bg.png");
        bg.drawImage(image, 0, 0, 800,900);

        root.getChildren().add(c);
        root.getChildren().add(vbox);
        root.getChildren().add(ivSoundOn);

        ivDemo.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->   {
            jeu(stage);
        });

        ivMdj.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("mdj");
        });

        ivCusto.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            custo(stage);
        });

        ivOption.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("option");
        });

        ivSoundOn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            root.getChildren().add(ivSoundOff);
            root.getChildren().remove(ivSoundOn);
        });

        ivSoundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            root.getChildren().add(ivSoundOn);
            root.getChildren().remove(ivSoundOff);
        });

        stage.setScene(new Scene(root,800,900));
    }

    private void jeu(Stage stage) {
        final Group root = new Group();

        Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT * margin);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
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
                default:
                    break;
            }
        });
        stage.setScene(scene);

        try {
            partie = new Partie("src/levels/level1V2.txt");
            new AnimationTimer() {
                long prevtime;
                long deltaTime;



                AudioClip chomp = Window.openAudio("src/music/pacman_chomp.wav");

                public void handle(long currentNanoTime) {

                    deltaTime = currentNanoTime - prevtime;

                    //if (!chomp.isPlaying())
                    //chomp.play();

                    partie.getPacman().changeDir(dir);
                    partie.tick(deltaTime);

                    drawShapes(gc);

                    prevtime = currentNanoTime;
                }

                public void drawShapes(GraphicsContext gc) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT * margin);

                    for (Entity e : partie.getPlateau().getPlateau()) {
                        e.draw(gc);
                        //e.drawHitbox(gc);

                        if (e instanceof Ghost)
                            ((Ghost) e).move(partie.getPacman());

                        //String type = e.getClass().toString().substring(13);
                        //System.out.println(type);
                    }
                    gc.setFill(Color.WHITE);
                    gc.fillText("Score : " + partie.getScore().getScore(), (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    private void custo(Stage stage) {
        HBox popVbox=new HBox();

        Group pop = new Group();

        Image blueWall = new Image("img/wall/blue/BBP.png", 200, 200, false, false);
        ImageView ivBlueWall = new ImageView(blueWall);

        popVbox.getChildren().add(ivBlueWall);

        ivBlueWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("blue");
            partie.getPlateau().setWalls("blue");
            menu(stage);
        });

        Image greenWall = new Image("img/wall/green/GBP.png", 200, 200, false, false);
        ImageView ivGreenWall = new ImageView(greenWall);

        popVbox.getChildren().add(ivGreenWall);

        ivGreenWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("green");
            partie.getPlateau().setWalls("green");
            menu(stage);
        });

        Image orangeWall = new Image("img/wall/orange/OBP.png", 200, 200, false, false);
        ImageView ivorangeWall = new ImageView(orangeWall);

        popVbox.getChildren().add(ivorangeWall);

        ivorangeWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("orange");
            partie.getPlateau().setWalls("orange");
            menu(stage);
        });

        Image purpleWall = new Image("img/wall/purple/PBP.png", 200, 200, false, false);
        ImageView ivpurpleWall = new ImageView(purpleWall);

        popVbox.getChildren().add(ivpurpleWall);

        ivpurpleWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("purple");
            partie.getPlateau().setWalls("purple");
            menu(stage);
        });

        Image redWall = new Image("img/wall/red/RBP.png", 200, 200, false, false);
        ImageView ivredWall = new ImageView(redWall);

        popVbox.getChildren().add(ivredWall);

        ivredWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("red");
            partie.getPlateau().setWalls("red");
            menu(stage);
        });

        Image yellowWall = new Image("img/wall/yellow/YBP.png", 200, 200, false, false);
        ImageView ivyellowWall = new ImageView(yellowWall);

        popVbox.getChildren().add(ivyellowWall);

        ivyellowWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("yellow");
            partie.getPlateau().setWalls("yellow");
            menu(stage);
        });

        pop.getChildren().add(popVbox);


        Scene popUp = new Scene(pop,1250,200);
        stage.setScene(popUp);
        stage.show();
    }
*/
    public static AudioClip openAudio(String path) {
        return new AudioClip(Paths.get(path).toUri().toString());
    }

}