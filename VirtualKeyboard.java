import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class VirtualKeyboard{
    private final ArrayList<String> name = new ArrayList<>();
    private final Scene scene;
    private final StackPane key;
    private final Rectangle bg;

    public StackPane getKey() {
        return key;
    }

    public String getName() {
        return this.list2string(name);
    }

    private Text text;
    private final HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();

    public VirtualKeyboard(Scene scene) {
        this.scene = scene;
        this.key = new StackPane();
        bg = new Rectangle(480,100);
        bg.setArcHeight(3);
        bg.setArcHeight(2);
        bg.setFill(Color.BLACK);
        bg.setOpacity(0.4);
        text = new Text("Enter name and press 'ENTER'");
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 20));
        key.getChildren().addAll(bg, text);
    }

    public void getKeys(){
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (!currentlyActiveKeys.containsKey(codeString)) {
                currentlyActiveKeys.put(codeString, true);
            }
        });
        scene.setOnKeyReleased(event -> currentlyActiveKeys.remove(event.getCode().toString()));

        AnimationTimer time = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(removeActiveKey("ENTER")){
                    if(name.size()!=0){
                        this.stop();
                    }
                }
                else if(removeActiveKey("BACK_SPACE")){
                    if(name.size()!=0){
                        name.remove(name.size()-1);
                    }
                }
                else if(removeActiveKey("A")){
                    name.add("A");
                    Display();
                }
                else if(removeActiveKey("B")){
                    name.add("B");
                    Display();
                }
                else if(removeActiveKey("C")){
                    name.add("C");
                    Display();
                }
                else if(removeActiveKey("D")){
                    name.add("D");
                    Display();
                }
                else if(removeActiveKey("E")){
                    name.add("E");
                    Display();
                }
                else if(removeActiveKey("F")){
                    name.add("F");
                    Display();
                }
                else if(removeActiveKey("G")){
                    name.add("G");
                    Display();
                }
                else if(removeActiveKey("H")){
                    name.add("H");
                    Display();
                }
                else if(removeActiveKey("I")){
                    name.add("I");
                    Display();
                }
                else if(removeActiveKey("J")){
                    name.add("J");
                    Display();
                }
                else if(removeActiveKey("K")){
                    name.add("K");
                    Display();
                }
                else if(removeActiveKey("L")){
                    name.add("L");
                    Display();
                }
                else if(removeActiveKey("M")){
                    name.add("M");
                    Display();
                }
                else if(removeActiveKey("N")){
                    name.add("N");
                    Display();
                }
                else if(removeActiveKey("O")){
                    name.add("O");
                    Display();
                }
                else if(removeActiveKey("P")){
                    name.add("P");
                    Display();
                }
                else if(removeActiveKey("Q")){
                    name.add("Q");
                    Display();
                }
                else if(removeActiveKey("R")){
                    name.add("R");
                    Display();
                }
                else if(removeActiveKey("S")){
                    name.add("S");
                    Display();
                }
                else if(removeActiveKey("T")){
                    name.add("T");
                    Display();
                }
                else if(removeActiveKey("U")){
                    name.add("U");
                    Display();
                }
                else if(removeActiveKey("V")){
                    name.add("V");
                    Display();
                }
                else if(removeActiveKey("W")){
                    name.add("W");
                    Display();
                }
                else if(removeActiveKey("X")){
                    name.add("X");
                    Display();
                }
                else if(removeActiveKey("Y")){
                    name.add("Y");
                    Display();
                }
                else if(removeActiveKey("Z")){
                    name.add("Z");
                    Display();
                }
            }
        };

        time.start();
    }

    private void Display(){
        if(name.size()>10){
            name.remove(name.size()-1);
        }
        text = new Text(list2string(name));
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 28));
        key.getChildren().setAll(bg, text);
    }

    private String list2string(ArrayList<String> name){
        StringBuilder temp = new StringBuilder();
        for (String s : name) {
            temp.append(s);
        }
        return temp.toString();
    }

    private boolean removeActiveKey(String codeString) {
        Boolean isActive = currentlyActiveKeys.get(codeString);

        if (isActive != null && isActive) {
            currentlyActiveKeys.put(codeString, false);
            return true;
        }
        else {
            return false;
        }
    }
}