package Graphics;

import Entity.*;

import Game.Partie;
import Utils.Direction;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

import java.nio.file.Paths;

import static Utils.Constants.*;

public class Window extends Application {
    Direction dir = Direction.RIGHT;

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
            root.getChildren().remove(iv);
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
            }
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



    public static AudioClip openAudio(String path) {
        return new AudioClip(Paths.get(path).toUri().toString());
    }

}