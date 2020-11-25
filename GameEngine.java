import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

public class GameEngine extends Application {
    private static final double WIDTH = 520;
    private static final double HEIGHT = 640;
    private final Group root = new Group();
    private final VBox menuBox = new VBox(-5);
    private final Group animation = new Group();
    private final HashMap<String, Player> playerQueue = new HashMap<>();
    private ImageView imageView;
    private Scene scene;
    private VirtualKeyboard virtualKeyboard;
    private final String fileName = "res/Database.txt";
    private Player currentPlayer;
    private GamePanel currentGame;
    private final Color[] colors = {Color.web("#00EEFF"), Color.web("#FFE700"), Color.web("#F30EA0"), Color.web("#7900FF")};

    private void addMenu(){
        menuBox.setTranslateX(95.0);
        menuBox.setTranslateY(410);

        StackPane key1 = makeKey("START", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, startAction(), animation));
        menuBox.getChildren().add(key1);

        StackPane key2 = makeKey("HOW TO PLAY", 330);
        key2.setOnMouseClicked(e -> root.getChildren().setAll(imageView, permanent("Just Press the 'UP' Key!!"), animation));
        menuBox.getChildren().add(key2);

        StackPane key3 = makeKey("LOAD", 330);
        key3.setOnMouseClicked(e -> root.getChildren().setAll(imageView, LeaderBoard(), animation));
        menuBox.getChildren().add(key3);

        StackPane key4 = makeKey("LEADERBOARD", 330);
        key4.setOnMouseClicked(e -> root.getChildren().setAll(imageView, LeaderBoard(), animation));
        menuBox.getChildren().add(key4);

        StackPane key5 = makeKey("CREDITS", 330);
        key5.setOnMouseClicked(e -> root.getChildren().setAll(imageView, permanent("Made by:\n  Kshitij Mohan \n  Piyush Sharma"), animation));
        menuBox.getChildren().add(key5);

        StackPane key6 = makeKey("EXIT", 330);
        key6.setOnMouseClicked(e -> System.exit(0));
        menuBox.getChildren().add(key6);

        root.getChildren().add(menuBox);

    }

    private Group LeaderBoard(){
        Group start = new Group();
        VBox menuBox1 = new VBox(-5);
        menuBox1.setTranslateX((WIDTH-330)/2);
        menuBox1.setTranslateY(410);
        StackPane key1 = makeKey("BACK", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, menuBox, animation));
        menuBox1.getChildren().addAll(key1);
        start.getChildren().add(menuBox1);
        return start;
    }

    private Group permanent(String line){
        Group start = new Group();
        VBox menuBox1 = new VBox(-5);
        menuBox1.setTranslateX(20);
        menuBox1.setTranslateY(410);

        StackPane key = new StackPane();
        Rectangle bg = new Rectangle(WIDTH-40,150);
        bg.setArcHeight(3);
        bg.setArcHeight(2);
        bg.setFill(Color.BLACK);
        bg.setOpacity(0.4);
        Text text = new Text(line);
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 28));
        key.setAlignment(Pos.CENTER);
        key.getChildren().addAll(bg, text);

        StackPane key1 = makeKey("BACK", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, menuBox, animation));
        menuBox1.getChildren().addAll(key, key1);

        start.getChildren().add(menuBox1);
        return start;
    }

    private Group startAction(){
        Group start = new Group();
        VBox menuBox1 = new VBox(-5);
        menuBox1.setTranslateX((WIDTH-330)/2);
        menuBox1.setTranslateY(410);

        StackPane key1 = makeKey("NEW GAME", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, newGame(), animation));
        menuBox1.getChildren().add(key1);

        StackPane key2 = makeKey("CONTINUE", 330);
        key2.setOnMouseClicked(e -> root.getChildren().setAll(imageView, newGame(), animation));
        menuBox1.getChildren().add(key2);

        StackPane key3 = makeKey("BACK", 330);
        key3.setOnMouseClicked(e -> root.getChildren().setAll(imageView, menuBox, animation));
        menuBox1.getChildren().add(key3);

        start.getChildren().addAll(menuBox1);
        return start;
    }

    private Group newGame(){
        Group group = new Group();
        virtualKeyboard = new VirtualKeyboard(scene);
        virtualKeyboard.getKeys();

        StackPane key1 = makeKey("CONFIRM", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, gameMenu(), animation));

        StackPane key2 = makeKey("BACK", 330);
        key2.setOnMouseClicked(e -> root.getChildren().setAll(imageView, startAction(), animation));

        VBox box = new VBox(-5);
        box.setTranslateX(20);
        box.setTranslateY(410);
        box.getChildren().setAll(virtualKeyboard.getKey(), key1, key2);
        group.getChildren().add(box);
        return group;
    }

    private Group gameMenu(){
        if(virtualKeyboard.getName().length()==0){
            return newGame();
        }
        currentGame = new GamePanel(WIDTH, HEIGHT, scene);
        playerQueue.put(virtualKeyboard.getName(), new Player(virtualKeyboard.getName(), currentGame));
        currentPlayer = playerQueue.get(virtualKeyboard.getName());
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(currentPlayer.getName()+" Score: "+currentPlayer.getScore());
            myWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        Group start = new Group();
        VBox menuBox1 = new VBox(-5);
        menuBox1.setTranslateX((WIDTH-330)/2);
        menuBox1.setTranslateY(410);

        StackPane key1 = makeKey("PLAY", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, currentGame, pauseButton()));
        menuBox1.getChildren().add(key1);

        StackPane key2 = makeKey("LOAD GAME", 330);
        key2.setOnMouseClicked(e -> root.getChildren().setAll(imageView, currentGame, pauseButton()));
        menuBox1.getChildren().add(key2);

        StackPane key3 = makeKey("BACK", 330);
        key3.setOnMouseClicked(e -> root.getChildren().setAll(imageView, newGame(), animation));
        menuBox1.getChildren().add(key3);

        start.getChildren().addAll(menuBox1);
        return start;
    }

    private Group pauseButton(){
        Group root = new Group();
        StackPane gameKey1 = makeKey("↑↑", 40);
        gameKey1.setOnMouseClicked(e -> root.getChildren().add(pauseMenu()));

        VBox box = new VBox();
        box.setTranslateX(10);
        box.setTranslateY(10);
        box.getChildren().setAll(gameKey1);
        root.getChildren().addAll(box);
        return root;
    }

    private Group pauseMenu(){
        Group root = new Group();
        Pane bg = new Pane();
        Rectangle mp = new Rectangle(WIDTH, HEIGHT);
        mp.setFill(Color.WHITE);
        mp.setOpacity(0.4);
        bg.getChildren().add(mp);
        GaussianBlur blur = new GaussianBlur();
        bg.setEffect(blur);

        VBox main = new VBox(8);
        main.setLayoutY(HEIGHT/2-100);
        main.setLayoutX(70);

        StackPane sp = new StackPane();
        Rectangle temp = new Rectangle(400, 100);
        temp.setFill(Color.BLACK);
        temp.setOpacity(0.4);
        Text text = new Text("PAUSED");
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 60));
        sp.getChildren().addAll(temp, text);

        VBox menu = new VBox(1);
        menu.setAlignment(Pos.CENTER);

        StackPane key1 = makeKey("MENU", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, gameMenu(), animation));
        menu.getChildren().add(key1);

        StackPane key2 = makeKey("RESUME", 330);
        key2.setOnMouseClicked(e -> root.getChildren().setAll(imageView, currentGame, pauseButton()));
        menu.getChildren().add(key2);

        StackPane key3 = makeKey("RESTART", 330);
        key3.setOnMouseClicked(e -> root.getChildren().setAll(imageView, currentGame, pauseButton()));
        menu.getChildren().add(key3);

        StackPane key4 = makeKey("SAVE GAME", 330);
        key4.setOnMouseClicked(e -> root.getChildren().setAll(imageView, gameMenu(), animation));
        menu.getChildren().add(key4);

        StackPane key5 = makeKey("GAME OVER", 330);
        key5.setOnMouseClicked(e -> root.getChildren().setAll(imageView, currentGame, gameoverMenu()));
        menu.getChildren().add(key5);

        main.getChildren().addAll(sp, menu);
        root.getChildren().addAll(bg, main);
        return root;
    }

    private Group gameoverMenu(){
        Group root = new Group();

        Pane bg = new Pane();
        Rectangle mp = new Rectangle(WIDTH, HEIGHT);
        mp.setFill(Color.WHITE);
        mp.setOpacity(0.4);
        bg.getChildren().add(mp);
        GaussianBlur blur = new GaussianBlur();
        bg.setEffect(blur);

        VBox main = new VBox(8);
        main.setLayoutY(HEIGHT/2-100);
        main.setLayoutX(70);

        StackPane sp = new StackPane();
        Rectangle temp = new Rectangle(400, 100);
        temp.setFill(Color.BLACK);
        temp.setOpacity(0.4);
        Text text = new Text("PAUSED");
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 60));
        sp.getChildren().addAll(temp, text);

        VBox menu = new VBox(1);
        menu.setAlignment(Pos.CENTER);

        StackPane key1 = makeKey("MENU", 330);
        key1.setOnMouseClicked(e -> root.getChildren().setAll(imageView, gameMenu(), animation));
        menu.getChildren().add(key1);

        StackPane key3 = makeKey("RESTART LEVEL", 330);
        key3.setOnMouseClicked(e -> root.getChildren().setAll(imageView, currentGame, pauseButton()));
        menu.getChildren().add(key3);

        main.getChildren().addAll(sp, menu);
        root.getChildren().addAll(bg, main);
        return root;
    }

    private StackPane makeKey(String str, double width){
        StackPane key = new StackPane();
        Random rand = new Random();
        Color randomColor = Color.hsb(rand.nextInt(360), 1, 1);

        Rectangle bg = new Rectangle(width,38);
        bg.setFill(Color.BLACK);

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, randomColor),
                new Stop(0.2, Color.BLACK),
                new Stop(0.8, Color.BLACK),
                new Stop(1, randomColor));
        bg.setOpacity(0.4);

        Text text = new Text(str);
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 28));
        key.setAlignment(Pos.CENTER);
        key.getChildren().addAll(bg, text);

        key.setOnMouseEntered(e -> bg.setFill(randomColor));
        key.setOnMouseExited(e -> bg.setFill(Color.BLACK));
        key.setOnMousePressed(e -> bg.setFill(gradient));
        return key;
    }

    private void addEffects(){
        Text text = new Text("C  L  R");
        text.setFill(Color.WHITE);
        text.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 50));

        Text text2 = new Text("SWITCH");
        text2.setFill(Color.WHITE);
        text2.setFont(Font.loadFont("file:res/MathmosOriginal-MBnp.ttf", 50));

        VBox title = new VBox();
        title.setAlignment(Pos.TOP_CENTER);
        title.getChildren().addAll(text, text2);
        title.setLayoutX((WIDTH-225)/2);
        title.setLayoutY(3);

        animation.getChildren().addAll(title);

        ImageView smile = new ImageView(new Image(getClass().getResource("res/face.png").toExternalForm()));
        smile.setX(225);
        smile.setY(225);
        smile.setFitHeight(63);
        smile.setFitWidth(63);

        Random rand = new Random();
        Circle face = new Circle();
        face.setCenterX(260.0);
        face.setCenterY(260);
        face.setRadius(48);
        face.setFill(colors[rand.nextInt(4)]);
        ScaleTransition st = new ScaleTransition(Duration.millis(290), face);
        st.setByX(0.08);
        st.setByY(0.08);
        st.setCycleCount(400);
        st.setAutoReverse(true);
        st.play();
        animation.getChildren().addAll(face, smile);

        root.getChildren().addAll(animation);

        Ring ring1 = new Ring(260.0, 260, 87, 12,  false);
        Ring ring2 = new Ring(260.0, 260, 107, 17,  true);
        Ring ring3 = new Ring(260.0, 260, 132, 20,  false);
        Ring ring4 = new Ring(217, 32, 20, 8,  false);
        Ring ring5 = new Ring(299, 32, 20, 8,  true);
        ring1.addQuad(animation);
        ring2.addQuad(animation);
        ring3.addQuad(animation);
        ring4.addQuad(animation);
        ring5.addQuad(animation);

//        Timer timer = new Timer(140, arg0 -> {
//            ring1.setAngle();
//            ring1.move();
//            ring2.setAngle();
//            ring2.move();
//            ring3.setAngle();
//            ring3.move();
//            ring4.setAngle();
//            ring4.move();
//            ring5.setAngle();
//            ring5.move();
//        });
//        timer.start();
    }

    private void addBackground() {
        imageView = new ImageView(new Image(getClass().getResource("res/background.png").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        root.getChildren().addAll(imageView);
    }

    private Parent createContent() {
        addBackground();
        addMenu();
        addEffects();
        return root;
    }

    @Override
    public void start(Stage primaryStage){
        scene = new Scene(createContent());
        String line;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" Score: ", 2);
                Player player = new Player(parts[0], new GamePanel(WIDTH, HEIGHT, scene));
                playerQueue.put(parts[0], player);
                player.setScore(Integer.parseInt(parts[1]));
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        primaryStage.setTitle("ColorSwitch");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });
    }
}