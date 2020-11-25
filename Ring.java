import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Ring{
    private final Arc firstQuad;
    private final Arc secondQuad;
    private final Arc thirdQuad;
    private final Arc fourthQuad;
    private final boolean clockwise;
    private RotateTransition rotateTransition;

    public void pauseRotation(){
        rotateTransition.stop();
    }

    public Ring(double centerX, double centerY, double radius, double width, boolean clockwise){
        this.clockwise = clockwise;
        this.firstQuad = makeRing(0, centerX, centerY, radius, width, Color.web("#00EEFF"));
        this.secondQuad = makeRing(90, centerX, centerY, radius, width, Color.web("#FFE700"));
        this.thirdQuad = makeRing(180, centerX, centerY, radius, width, Color.web("#F30EA0"));
        this.fourthQuad = makeRing(270, centerX, centerY, radius, width, Color.web("#7900FF"));
        movePivot(firstQuad, -(0.5)*radius, (0.5)*radius);
        movePivot(secondQuad, (0.5)*radius, (0.5)*radius);
        movePivot(thirdQuad, (0.5)*radius, -(0.5)*radius);
        movePivot(fourthQuad, -(0.5)*radius, -(0.5)*radius);
        rotateNode(firstQuad);
        rotateNode(secondQuad);
        rotateNode(thirdQuad);
        rotateNode(fourthQuad);
    }

    private Arc makeRing(double startAngle, double centerX, double centerY, double radius, double thickness, Color color){
        Arc arc = new Arc();
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
        arc.setStrokeWidth(thickness);
        arc.setStartAngle(startAngle);
        arc.setStroke(color);
        arc.setFill(null);
        arc.setLength(90);
        arc.setType(ArcType.OPEN);
        return arc;
    }

    private void rotateNode(Node node){
        rotateTransition = new RotateTransition(Duration.millis(3000), node);{
            if (clockwise){
                rotateTransition.setByAngle(360);
            }
            else {
                rotateTransition.setByAngle(-360);
            }
            rotateTransition.setCycleCount(Timeline.INDEFINITE);
            rotateTransition.setInterpolator(Interpolator.LINEAR);
            rotateTransition.setAutoReverse(false);
            rotateTransition.setNode(node);
            rotateTransition.play();
        }
    }

    private void movePivot(Node node, double x, double y){
        node.getTransforms().add(new Translate(-x,-y));
        node.setTranslateX(x); node.setTranslateY(y);
    }

    public void addQuad(Group root){
        root.getChildren().addAll(firstQuad, secondQuad, thirdQuad, fourthQuad);
    }

    public void addQuad(GamePanel root){
        root.getChildren().addAll(firstQuad, secondQuad, thirdQuad, fourthQuad);
    }
}