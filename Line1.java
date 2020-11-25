import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;


public class Line1 {
    private final Rectangle pos_x;
    private final Rectangle neg_x;
    private final Rectangle pos_y;
    private final Rectangle neg_y;
    private RotateTransition rotateTransition;
    private final boolean clockwise;

    public void pauseRotation(){
        rotateTransition.stop();
    }

    public Line1(double centerX, double centerY, double side, boolean clockwise){
        this.clockwise = clockwise;
        double width = side / 6;
        pos_x = makeLine(centerX- width /2, centerY- width /2, side, width, Color.web("#00EEFF"), 0);
        neg_x = makeLine(centerX- width /2, centerY- width /2, side, width, Color.web("#FFE700"), 90);
        pos_y = makeLine(centerX- width /2, centerY- width /2, side, width, Color.web("#F30EA0"), 180);
        neg_y = makeLine(centerX- width /2, centerY- width /2, side, width, Color.web("#7900FF"), 270);

        rotateNode(pos_x);
        rotateNode(neg_y);
        rotateNode(neg_x);
        rotateNode(pos_y);
    }

    public void addQuad(Group root){
        root.getChildren().addAll(pos_x, neg_y, neg_x, pos_y);
    }

    private Rectangle makeLine(double centerX, double centerY, double height, double width, Color color, double angle){
        Rectangle rect = new Rectangle();
        rect.setFill(color);
        rect.setHeight(height);
        rect.setWidth(width);
        rect.setX(centerX);
        rect.setY(centerY);
        rect.setArcWidth(width);
        rect.setArcHeight(width);

        movePivot(rect, 0, -(0.5)*height+width/2);

        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(centerX+width/2);
        rotate.setPivotY(centerY+width/2);
        rect.getTransforms().addAll(rotate);

        return rect;
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
}
