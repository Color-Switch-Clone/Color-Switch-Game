import javafx.scene.shape.Circle;


public class Physics{
    private int timer;
    private int velocity;
    private final double H;

    Physics(double H) {
        this.H = H;
        this.timer = 0;
        this.velocity = 0;
    }

    public int gravity(Circle Ball){
        this.timer++;
        if (this.timer % 2 == 0 && this.velocity < 15) {
            this.velocity = this.velocity + 2;
        }
        return this.velocity + (int) Ball.getCenterY();
    }

    public int jump() {
        if (this.velocity > 0) {
            this.velocity = 0;
        }
        this.velocity = this.velocity - 10;
        return this.velocity;
    }

    public boolean ground_collision(Circle Ball){
        return Ball.getCenterY() > H ;
    }
}
