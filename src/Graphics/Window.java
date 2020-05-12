package Graphics;

import Game.Partie;
import Utils.Direction;
import Utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Window extends Application {
    Partie partie;
    Direction dir = Direction.RIGHT;

    private static final Image imR = new Image("img/pacManR.png");
    private static final Image imD = new Image("img/pacManD.png",false);
    private static final Image imL = new Image("img/pacManL.png",false);
    private static final Image imU = new Image("img/pacManU.png",false);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        partie = new Partie("src/levels/level1V2.txt");

        stage.setTitle("toto");
        final Group root = new Group();
        Canvas canvas = new Canvas(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        final Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT, Color.AQUAMARINE);

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

        new AnimationTimer() {
            long prevtime;

            long deltaTime;

            public void handle(long currentNanoTime) {

                deltaTime = currentNanoTime-prevtime;

                //partie.tick(deltaTime / 10000000.0);

                partie.getPlateau().getPacman().changeDir(dir);
                partie.getPlateau().getPacman().move(deltaTime / 10000000.0);

                drawShapes(gc);

                prevtime = currentNanoTime;
            }
        }.start();

        stage.show();
    }

    public void drawShapes(GraphicsContext gc) {
        gc.clearRect(0, 0, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        for (int i = 0; i < partie.getPlateau().getPlateau().length; i++) {
            String type = partie.getPlateau().getIndex(i).getClass().toString().substring(13);

            double x = partie.getPlateau().getIndex(i).getPos().getX();
            double y = partie.getPlateau().getIndex(i).getPos().getY();


            switch ( type ) {
                case "Inky":
                    gc.setFill(Color.DARKBLUE);
                    gc.fillOval(x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                    break;
                case "Blinky":
                    gc.setFill(Color.RED);
                    gc.fillOval(x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                    break;
                case "Clyde":
                    gc.setFill(Color.ORANGE);
                    gc.fillOval(x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                    break;
                case "Pinky":
                    gc.setFill(Color.PINK);
                    gc.fillOval(x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                    break;
                case "Wall":
                    gc.setFill(Color.BLACK);
                    gc.fillOval(x, y, Constants.WALL_SIZE, Constants.PERSONNAGE_SIZE);
                    break;
                case "Pacman":
                    //x = partie.getPacman().getX();
                    //y = partie.getPacman().getY();
                    switch (dir) {
                        case RIGHT:
                            gc.drawImage(imR, x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                            break;
                        case LEFT:
                            gc.drawImage(imL, x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                            break;
                        case UP:
                            gc.drawImage(imU, x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                            break;
                        case DOWN:
                            gc.drawImage(imD, x, y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
    }

}