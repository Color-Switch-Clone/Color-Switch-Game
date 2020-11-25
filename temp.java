import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class temp extends Application {
    public void start(Stage primaryStage) {
        Line l1 = new Line(20, 80, 70, 80);
        Line l2 = new Line(70, 30, 70, 80);
        Line l3 = new Line(70, 80, 120, 80);
        Line l4 = new Line(70, 80, 70, 130);
        Line l5 = new Line(200, 200, 300, 200);
        Line l6 = new Line(200, 200,200 , 300);
        Line l7 = new Line(200, 300, 300, 300);
        Line l8 = new Line(300, 300, 300, 200);
        Line l9 = new Line(300, 30,250 , 80);
        Line l10 = new Line(250, 80, 350, 80);
        Line l11 = new Line(300, 30, 350, 80);
        l1.setStrokeWidth(7);
        l2.setStrokeWidth(7);
        l3.setStrokeWidth(7);
        l4.setStrokeWidth(7);
        l5.setStrokeWidth(7);
        l6.setStrokeWidth(7);
        l7.setStrokeWidth(7);
        l8.setStrokeWidth(7);
        l9.setStrokeWidth(7);
        l10.setStrokeWidth(7);
        l11.setStrokeWidth(7);

        l1.setStroke(Color.BLUE);
        l2.setStroke(Color.GREEN);
        l3.setStroke(Color.YELLOW);
        l4.setStroke(Color.PINK);
        l5.setStroke(Color.RED);
        l6.setStroke(Color.LIMEGREEN);
        l7.setStroke(Color.CYAN);
        l8.setStroke(Color.YELLOW);
        l9.setStroke(Color.RED);
        l10.setStroke(Color.CYAN);
        l11.setStroke(Color.YELLOW);
        movePivot(l1,25,0);
        movePivot(l2,0,25);
        movePivot(l3,-25,0);
        movePivot(l4,0,-25);
        rotateNode(l1);
        rotateNode(l2);
        rotateNode(l3);
        rotateNode(l4);

        Group nr = new Group();
        Square yo = new Square(400, 400, 200, true);
        Line1 bro = new Line1(200, 200, 150, false);
        bro.addQuad(nr);
        yo.addQuad(nr);



        Scene scene = new Scene(nr, 700, 700, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Path Transition Example");
        primaryStage.show();

    }

    private void rotateNode(Node node){
        RotateTransition rtl1 = new RotateTransition(Duration.millis(2000),node);
        {
            rtl1.setByAngle(360);
            rtl1.setCycleCount(Timeline.INDEFINITE);
            rtl1.setInterpolator(Interpolator.LINEAR);
            rtl1.setAutoReverse(false);
            rtl1.setNode(node);
            rtl1.play();
        }
    }
    private void movePivot(Node node, double x, double y){
        node.getTransforms().add(new Translate(-x,-y));
        node.setTranslateX(x); node.setTranslateY(y);
    }
    public static void main(String[] args) {
        Application.launch(args);

    }
}