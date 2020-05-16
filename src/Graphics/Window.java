package Graphics;

import Entity.Entity;
import Entity.Wall;

import Game.Partie;
import Utils.Direction;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.file.Paths;

import static Utils.Constants.*;

public class Window extends Application {
    Direction dir = Direction.RIGHT;

    double margin = 1.1;

    private static final Image imR = new Image("img/pacManR.png");
    private static final Image imD = new Image("img/pacManD.png", false);
    private static final Image imL = new Image("img/pacManL.png", false);
    private static final Image imU = new Image("img/pacManU.png", false);

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

        Image img = new Image("img/menu1.png", SCENE_WIDTH, SCENE_HEIGHT, false, false);
        ImageView iv = new ImageView(img);
        root.getChildren().add(iv);

        iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            root.getChildren().remove(iv);

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
                            e.drawHitbox(gc);

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
        });

        final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT * margin, Color.BLACK);

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

        stage.show();
    }



    public static AudioClip openAudio(String path) {
        return new AudioClip(Paths.get(path).toUri().toString());
    }

}