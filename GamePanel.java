import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class GamePanel extends Pane {
    private final double WIDTH;
    private final double HEIGHT;
    private Physics model;
    private Timeline clock = new Timeline();
    private Circle ball = new Circle();
    private final Scene scene;
    private Boolean gameOver;

    public GamePanel(double WIDTH, double HEIGHT, Scene scene) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.scene = scene;
        gameOver = false;
        model = new Physics(HEIGHT);
        launchTimer();
        mainMenu();
    }

    private void mainMenu() {
        ball = new Circle(20);
        ball.setFill(Color.web("#7900FF"));
        ball.setCenterX(WIDTH/2);
        ball.setCenterY(HEIGHT-150);
        Ring ring = new Ring(260.0, 220, 150, 15,  true);
        ring.addQuad(this);
        this.getChildren().addAll(ball);
        clock.pause();
        scene.setOnKeyReleased(this::pressToStart);
    }

    private void launchTimer() {
        clock.setCycleCount(Animation.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(20), this::listen);
        clock.getKeyFrames().add(kf);
    }

    private void gameOver() {
        gameOver = model.ground_collision(ball);
        if (gameOver) {
            this.getChildren().remove(ball);
        }
    }

    private void listen(ActionEvent e) {
        gameOver();
        ball.setCenterY(this.model.gravity(ball));
        scene.setOnKeyReleased(this::pressUP);
    }

    private void pressUP(KeyEvent event) {
        String code = event.getCode().toString();
        if (code.equals("UP")){
            ball.setCenterY((int) ball.getCenterY() + this.model.jump());
        }
    }

    private void pressToStart(KeyEvent event) {
        String code = event.getCode().toString();
        if (code.equals("UP")){
            clock.play();
        }
    }
}
