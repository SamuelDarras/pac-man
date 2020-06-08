package Graphics;

import Entity.*;

import Game.Partie;
import Utils.Direction;
import com.sun.prism.Graphics;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

import static Utils.Constants.*;

public class Window extends Application {
    public Stage currentStage;

    Direction dir = Direction.RIGHT;
    String wallsColor = "blue";
    boolean sound = true;

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

        stage.setResizable(true);
        stage.show();
    }

    public void menu(Stage stage) {
        partie = null;
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

        Group root = new Group();

        VBox vbox=new VBox();
        vbox.setSpacing(50.);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(ivDemo,ivMdj,ivCusto,ivOption);

        Canvas c = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        GraphicsContext bg = c.getGraphicsContext2D();
        Image image = new Image("img/bg.png");
        bg.drawImage(image, 0, 0, SCENE_WIDTH,SCENE_HEIGHT*margin);

        root.getChildren().add(c);
        root.getChildren().add(vbox);

        ivDemo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> jeu(stage));

        ivMdj.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("mdj"));

        ivCusto.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> custo(stage));

        ivOption.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> settings(stage));



        stage.setScene(new Scene(root,SCENE_WIDTH,SCENE_HEIGHT*margin));
    }

    public void finJeu(Stage stage){
        Image retour = new Image("img/back.png",300,200,false,false);
        ImageView ivRetour = new ImageView(retour);

        Image bg = new Image("img/bgBlack.png",700,800,false,false);
        ImageView ivbg = new ImageView(bg);

        Image text = new Image("img/lose.png",1300,1500,false,false);
        ImageView ivtext = new ImageView(text);

        Group root = new Group();

        ivRetour.setY(1.0*SCENE_HEIGHT - 100);

        VBox vbox=new VBox();
        vbox.getChildren().addAll(ivRetour, ivtext);

        root.getChildren().addAll(ivbg, vbox);

        ivRetour.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> menu(stage));

        stage.setScene(new Scene(root,SCENE_WIDTH,SCENE_HEIGHT*margin));
    }

    public void jeu(Stage stage) {
        AtomicBoolean menu = new AtomicBoolean(false);
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
                case ESCAPE:
                    menu.set(!menu.get());
                    break;
                default:
                    break;
            }
        });

        stage.setScene(scene);

        try {
            partie = new Partie("src/levels/test.txt", wallsColor, this);
            new AnimationTimer() {
                long prevtime;
                long deltaTime;



                AudioClip chomp = Window.openAudio("src/music/pacman_chomp.wav");

                public void handle(long currentNanoTime) {
                    if (partie.getPacman().getLife() <= 0) {
                        this.stop();
                        finJeu(stage);
                    }

                    deltaTime = currentNanoTime - prevtime;

                    if (!menu.get() && sound && !chomp.isPlaying())
                        chomp.play();

                    if (!menu.get() && deltaTime < 1_000_000_000/5) {
                        partie.getPacman().changeDir(dir);
                        partie.tick(deltaTime);
                    }

                    drawShapes(gc);

                    if (menu.get())
                        drawMenu(gc);

                    prevtime = currentNanoTime;
                }

                public void drawShapes(GraphicsContext gc) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT * margin);

                    for (Entity e : partie.getPlateau().getPlateau()) {
                        e.draw(gc);
                        //e.drawHitbox(gc);

                        if (e instanceof Inky)
                            ((Inky) e).move(partie.getPacman(), partie.getPlateau());
                        if (e instanceof Pinky)
                            ((Pinky) e).move(partie.getPacman(), partie.getPlateau());
                        if (e instanceof Blinky)
                            ((Blinky) e).move(partie.getPacman(), partie.getPlateau());
                        if (e instanceof Clyde)
                            ((Clyde) e).move(partie.getPacman(), partie.getPlateau());

                    }
                    gc.setFill(Color.WHITE);
                    gc.fillText("Score : " + partie.getScore().getScore(), (1.0 * SCENE_WIDTH / 2) * .9, SCENE_HEIGHT * 1.05);
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



        double width = 1.0*SCENE_WIDTH/6;
        double height = 1.0*SCENE_HEIGHT/6;

        Image back = new Image("img/back.png", 100, 100, false, false);
        ImageView ivBack = new ImageView(back);
        ivBack.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));

        Image blueWall = new Image("img/wall/blue/Wall-blue-Block.png", 1.0*SCENE_WIDTH/6, 1.0*SCENE_HEIGHT/6, false, false);
        ImageView ivBlueWall = new ImageView(blueWall);
        popVbox.getChildren().add(ivBlueWall);
        ivBlueWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("blue");
            wallsColor = "blue";
            menu(stage);
        });

        Image greenWall = new Image("img/wall/green/Wall-green-Block.png", width, height, false, false);
        ImageView ivGreenWall = new ImageView(greenWall);
        popVbox.getChildren().add(ivGreenWall);
        ivGreenWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("green");
            wallsColor = "green";
            menu(stage);
        });

        Image orangeWall = new Image("img/wall/orange/Wall-orange-Block.png", width, height, false, false);
        ImageView ivorangeWall = new ImageView(orangeWall);
        popVbox.getChildren().add(ivorangeWall);
        ivorangeWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("orange");
            wallsColor = "orange";
            menu(stage);
        });

        Image purpleWall = new Image("img/wall/purple/Wall-purple-Block.png", width, height, false, false);
        ImageView ivpurpleWall = new ImageView(purpleWall);
        popVbox.getChildren().add(ivpurpleWall);
        ivpurpleWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("purple");
            wallsColor = "purple";
            menu(stage);
        });

        Image redWall = new Image("img/wall/red/Wall-red-Block.png", width, height, false, false);
        ImageView ivredWall = new ImageView(redWall);
        popVbox.getChildren().add(ivredWall);
        ivredWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("red");
            wallsColor = "red";
            menu(stage);
        });

        Image yellowWall = new Image("img/wall/yellow/Wall-yellow-Block.png", width, height, false, false);
        ImageView ivyellowWall = new ImageView(yellowWall);
        popVbox.getChildren().add(ivyellowWall);
        ivyellowWall.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> {
            System.out.println("yellow");
            wallsColor = "yellow";
            menu(stage);
        });

        pop.getChildren().add(popVbox);
        ivBack.setY(1.0*SCENE_HEIGHT - 100);
        pop.getChildren().add(ivBack);
        Scene popUp = new Scene(pop,SCENE_WIDTH,SCENE_HEIGHT*margin);
        stage.setScene(popUp);
        stage.show();
    }

    public void settings(Stage stage) {

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

        Image back = new Image("img/back.png", 100, 100, false, false);
        ImageView ivBack = new ImageView(back);
        ivBack.addEventHandler(MouseEvent.MOUSE_CLICKED, reset -> menu(stage));
        ivBack.setY(1.0*SCENE_HEIGHT - 100);

        Group root = new Group();
        Canvas main = new Canvas(SCENE_WIDTH, SCENE_HEIGHT*margin);
        main.getGraphicsContext2D().setFill(Color.BLACK);
        main.getGraphicsContext2D().fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT*margin);

        root.getChildren().add(main);
        root.getChildren().add(ivSoundOn);
        root.getChildren().add(ivBack);

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

    public static AudioClip openAudio(String path) {
        return new AudioClip(Paths.get(path).toUri().toString());
    }

}