package rocnikovka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.application.Application; 
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class Rocnikovka extends Application {
    
    static int[][] mrizka = {
        {0,0,0,0},
        {0,0,0,0},
        {0,0,0,0},
        {0,0,0,0},
    };
    
    static int jdePohnout = 0;
    
    Button p00, p10, p20, p30, p01, p11, p21, p31, p02, p12, p22, p32, p03 ,p13, p23, p33;
    Label text = new Label("");
    
    int xx;
    int yy;
    
    @Override
    public void start(Stage stage) throws Exception {  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML1.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        GridPane grid = new GridPane();
        
        text.getStyleClass().add("napis");
        text.setAlignment(Pos.CENTER);
        text.setMaxWidth(Double.MAX_VALUE);
        Button novaHra = new Button("Nová hra");
        novaHra.getStyleClass().add("btn");
        novaHra.setAlignment(Pos.CENTER);
        novaHra.setTextAlignment(TextAlignment.CENTER);
        
        HBox hbox = new HBox(novaHra);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        
        FadeTransition fade = new FadeTransition(Duration.millis(2000), grid);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setNode(grid);
        
        grid(grid);
        VBox layout = new VBox(grid, text, hbox);
        
        root.getChildren().add(layout);

        Scene scene = new Scene(root,440,600); // padding v jar souboru se zobrazí pouze pokud je zde 450,600 ???
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("styl.css").toExternalForm());
        root.setStyle("-fx-background-color: #666666;");
        
        novaHra.setOnAction((ActionEvent e) -> {
            reset();
            genZacatek();
            render();
            text.setText("");
        });
        
        stage.setResizable(false);
        stage.setTitle("2048");
        stage.setScene(scene);
        fade.play();
        stage.show();
        
        scene.setOnKeyPressed((KeyEvent znak) -> {
            switch (znak.getCode()) {
                case DOWN:
                case S:
                    pohyb();
                    gen();
                    konec(layout);
                    vyhra(layout);
                    render();
                    break;
                case UP:
                case W:
                    otocitPoSmeru(mrizka);
                    otocitPoSmeru(mrizka);
                    pohyb();
                    otocitPoSmeru(mrizka);
                    otocitPoSmeru(mrizka);
                    gen();
                    konec(layout);
                    vyhra(layout);
                    render();
                    break;
                case RIGHT:
                case D:
                    otocitPoSmeru(mrizka);
                    pohyb();
                    otocitPoSmeru(mrizka);
                    otocitPoSmeru(mrizka);
                    otocitPoSmeru(mrizka);
                    gen();
                    konec(layout);
                    vyhra(layout);
                    render();
                    break;
                case LEFT:
                case A:
                    otocitPoSmeru(mrizka);
                    otocitPoSmeru(mrizka);
                    otocitPoSmeru(mrizka);
                    pohyb();
                    otocitPoSmeru(mrizka);
                    gen();
                    konec(layout);
                    vyhra(layout);
                    render();
                    break;
                case ENTER:
                    reset();
                    genZacatek();
                    render();
                    text.setText("");
                    break;
            }
        });
    }
    
    public static void main(String[] args) throws InterruptedException {
        Rocnikovka q = new Rocnikovka();
        q.genZacatek();
        launch(args);
    }
    
    void grid(GridPane grid){
        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        p00 = new Button("");
        p10 = new Button("");
        p20 = new Button("");
        p30 = new Button("");
        p01 = new Button("");
        p11 = new Button("");
        p21 = new Button("");
        p31 = new Button("");
        p02 = new Button("");
        p12 = new Button("");
        p22 = new Button("");
        p32 = new Button("");
        p03 = new Button("");
        p13 = new Button("");
        p23 = new Button("");
        p33 = new Button("");
        
        p00.getStyleClass().add("pane");
        p10.getStyleClass().add("pane");
        p20.getStyleClass().add("pane");
        p30.getStyleClass().add("pane");
        p01.getStyleClass().add("pane");
        p11.getStyleClass().add("pane");
        p21.getStyleClass().add("pane");
        p31.getStyleClass().add("pane");
        p02.getStyleClass().add("pane");
        p12.getStyleClass().add("pane");
        p22.getStyleClass().add("pane");
        p32.getStyleClass().add("pane");
        p03.getStyleClass().add("pane");
        p13.getStyleClass().add("pane");
        p23.getStyleClass().add("pane");
        p33.getStyleClass().add("pane");
        
        grid.add(p00, 0, 0, 1, 1);
        grid.add(p10, 0, 1, 1, 1);
        grid.add(p20, 0, 2, 1, 1);
        grid.add(p30, 0, 3, 1, 1);
        grid.add(p01, 1, 0, 1, 1);
        grid.add(p11, 1, 1, 1, 1);
        grid.add(p21, 1, 2, 1, 1);
        grid.add(p31, 1, 3, 1, 1);
        grid.add(p02, 2, 0, 1, 1);
        grid.add(p12, 2, 1, 1, 1);
        grid.add(p22, 2, 2, 1, 1);
        grid.add(p32, 2, 3, 1, 1);
        grid.add(p03, 3, 0, 1, 1);
        grid.add(p13, 3, 1, 1, 1);
        grid.add(p23, 3, 2, 1, 1);
        grid.add(p33, 3, 3, 1, 1);

        render();
    }
    
    void reset(){
        for (int[] row : mrizka){
            Arrays.fill(row, 0);
        }
        p00.setText("");
        p10.setText("");
        p20.setText("");
        p30.setText("");
        p01.setText("");
        p11.setText("");
        p21.setText("");
        p31.setText("");
        p02.setText("");
        p12.setText("");
        p22.setText("");
        p32.setText("");
        p03.setText("");
        p13.setText("");
        p23.setText("");
        p33.setText("");
    }
    
    void genZacatek(){
        Rocnikovka q = new Rocnikovka();
        q.generace(mrizka); //musí být dvakrát aby se vygenerovali 2 policka a zároven tím že jedno bude dřív zamezíme tomu aby vznikli na stejném místě 2
        q.generace(mrizka);
    }
    
    void gen(){
        Rocnikovka q = new Rocnikovka();
        q.generace(mrizka);
    }
    
    void generace(int[][] mrizka){
        Random random = new Random();
        
        ArrayList<Integer> moznosti = new ArrayList<>(16);
        ArrayList<Integer> moznosti2 = new ArrayList<>(16);
        
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (mrizka[y][x] == 0) {
                    moznosti.add(x);
                    moznosti2.add(y);
                }
            }
        }

        if (!moznosti.isEmpty()) {
            int R = random.nextInt(moznosti.size());
            xx = moznosti.get(R);
            yy = moznosti2.get(R);
            mrizka[yy][xx] = Math.random() > 0.1 ? 2 : 4;
        }
    }
    
    static void pohyb(){
        int a = 4;
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 4; x++) {  // x je sloupec
                for (int y = 0; y < 4; y++) { // y je řádek
                    if(mrizka[y][x] != 0) {
                        if (mrizka[1][x] == mrizka[0][x] && mrizka[1][x] == mrizka[2][x] && mrizka[2][x] == mrizka[3][x]) { // 2 2 2 2
                            mrizka[3][x] = mrizka[3][x] * 2;
                            mrizka[2][x] = mrizka[2][x] * 2;
                            mrizka[0][x] = 0;
                            mrizka[1][x] = 0;
                            a = x;
                        } else if (y <= 1 && mrizka[y][x] == mrizka[y+1][x] && mrizka[y][x] == mrizka[y+2][x]) { // 2 2 2
                            mrizka[y][x] = 0;
                            mrizka[y+2][x] = mrizka[y+2][x] * 2;
                        } else if (y <= 1 && mrizka[y][x] == mrizka[y+1][x] && mrizka[y][x] == (mrizka[y+2][x] / 2) && a != x) { // 2 2 4
                            mrizka[y][x] = 0;
                            mrizka[y+1][x] = mrizka[y+1][x] * 2;
                            a = x;
                        } else if (y <= 1 && mrizka[y][x] == (mrizka[y+1][x] * 2) && mrizka[y+1][x] == mrizka[y+2][x] && a != x) { // 4 2 2
                            mrizka[y+2][x] = mrizka[y+2][x] * 2;
                            mrizka[y+1][x] = mrizka[y][x];
                            mrizka[y][x] = 0;
                            a = x;
                        } else if (y == 0 && mrizka[0][x] == mrizka[1][x] && (mrizka[1][x] * 2)== mrizka[3][x] && mrizka[2][x] == 0) { // 2 2 0 4
                            mrizka[2][x] = mrizka[0][x] * 2;
                            mrizka[1][x] = 0;
                            mrizka[0][x] = 0;
                            a = x;
                        } else if (y == 0 && mrizka[0][x] == (mrizka[2][x] * 2) && mrizka[1][x] == 0 && mrizka[2][x] == mrizka[3][x] && a != x) { // 4 0 2 2
                            mrizka[2][x] = mrizka[0][x];
                            mrizka[0][x] = 0;
                            mrizka[3][x] = mrizka[3][x] * 2;
                            a = x;
                        } else if (y == 0 && mrizka[0][x] == (mrizka[1][x] * 2) && mrizka[2][x] == 0 && mrizka[1][x] == mrizka[3][x]) { // 4 2 0 2
                            mrizka[2][x] = mrizka[0][x];
                            mrizka[0][x] = 0;
                            mrizka[3][x] = mrizka[3][x] * 2;
                            a = x;
                        } else if (y == 0 && mrizka[3][x] == (mrizka[2][x] * 2) && mrizka[1][x] == 0 && mrizka[0][x] == mrizka[2][x]) { // 2 0 2 4
                            mrizka[2][x] = mrizka[2][x] * 2;
                            mrizka[0][x] = 0;
                            a = x;
                        } else if (y != 3) {
                            if (mrizka[y][x] == mrizka[y+1][x] && a != x) {
                                    mrizka[y+1][x] = mrizka[y+1][x] * 2;
                                    mrizka[y][x] = 0;
                            } else if(mrizka[y+1][x] == 0){
                                mrizka[y+1][x] = mrizka[y][x];
                                mrizka[y][x] = 0;
                            } 
                        }
                    }
                }
            }
        }
    }
    
    void vyhra(VBox layout){
        for (int x = 0; x < 4; x++) {  // x je sloupec
            for (int y = 0; y < 4; y++) { // y je řádek
                if (mrizka[y][x] == 2048) {
                    text.setText("Vyhráli jste!");
                }
            }
        }
    }
    
    void konec(VBox layout){
        jdePohnout = 0;
        for (int x = 0; x < 4; x++) {  // x je sloupec
            for (int y = 0; y < 4; y++) { // y je řádek
                if(mrizka[y][x] != 0) {
                    if (y != 3 && x != 3) {
                        if (mrizka[y][x] == mrizka[y+1][x]) {
                            jdePohnout++;
                        } else if (mrizka[y][x] == mrizka[y][x+1]){
                            jdePohnout++;
                        }
                    } else if (y == 3 && x == 3){
                        if (mrizka[y][x] == mrizka[y-1][x]) {
                            jdePohnout++;
                        } else if (mrizka[y][x] == mrizka[y][x-1]){
                            jdePohnout++;
                        }
                    } else if (y == 3 && x != 3) {
                        if (mrizka[y][x] == mrizka[y][x+1]){
                            jdePohnout++;
                        }
                    } else if (x == 3 && y != 3) {
                        if (mrizka[y][x] == mrizka[y+1][x]) {
                            jdePohnout++;
                        }
                    }
                } else {
                    jdePohnout++;
                }
            }
        }
        if (jdePohnout == 0) {
            text.setText("Konec hry!");
        }
    }
    
    static void otocitPoSmeru(int a[][]){ // ne můj kód
        
        int N = 4;
        // Traverse each cycle
        for (int i = 0; i < N / 2; i++)
        {
            for (int j = i; j < N - i - 1; j++)
            {

                // Swap elements of each cycle
                // in clockwise direction
                int temp = a[i][j];
                a[i][j] = a[N - 1 - j][i];
                a[N - 1 - j][i] = a[N - 1 - i][N - 1 - j];
                a[N - 1 - i][N - 1 - j] = a[j][N - 1 - i];
                a[j][N - 1 - i] = temp;
            }
        }
    }
    
    void render(){
        
        if(!"0".equals(String.valueOf(mrizka[0][0]))){
            p00.setText(String.valueOf(mrizka[0][0]));
            p00.getStyleClass().clear();
            p00.getStyleClass().add("b"+mrizka[0][0]);
        }else{
            p00.setText("");
            p00.getStyleClass().clear();
            p00.getStyleClass().add("b"+mrizka[0][0]);
        }
        if(!"0".equals(String.valueOf(mrizka[1][0]))){
            p10.setText(String.valueOf(mrizka[1][0]));
            p10.getStyleClass().clear();
            p10.getStyleClass().add("b"+mrizka[1][0]);
        }else{
            p10.setText("");
            p10.getStyleClass().clear();
            p10.getStyleClass().add("b"+mrizka[1][0]);
        }
        if(!"0".equals(String.valueOf(mrizka[2][0]))){
            p20.setText(String.valueOf(mrizka[2][0]));
            p20.getStyleClass().clear();
            p20.getStyleClass().add("b"+mrizka[2][0]);
        }else{
            p20.setText("");
            p20.getStyleClass().clear();
            p20.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[3][0]))){
            p30.setText(String.valueOf(mrizka[3][0]));
            p30.getStyleClass().clear();
            p30.getStyleClass().add("b"+mrizka[3][0]);
        }else{
            p30.setText("");
            p30.getStyleClass().clear();
            p30.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[0][1]))){
            p01.setText(String.valueOf(mrizka[0][1]));
            p01.getStyleClass().clear();
            p01.getStyleClass().add("b"+mrizka[0][1]);
        }else{
            p01.setText("");
            p01.getStyleClass().clear();
            p01.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[1][1]))){
            p11.setText(String.valueOf(mrizka[1][1]));
            p11.getStyleClass().clear();
            p11.getStyleClass().add("b"+mrizka[1][1]);
        }
        else{
            p11.setText("");
            p11.getStyleClass().clear();
            p11.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[2][1]))){
            p21.setText(String.valueOf(mrizka[2][1]));
            p21.getStyleClass().clear();
            p21.getStyleClass().add("b"+mrizka[2][1]);
        }
        else{
            p21.setText("");
            p21.getStyleClass().clear();
            p21.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[3][1]))){
            p31.setText(String.valueOf(mrizka[3][1]));
            p31.getStyleClass().clear();
            p31.getStyleClass().add("b"+mrizka[3][1]);
        }else{
            p31.setText("");
            p31.getStyleClass().clear();
            p31.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[0][2]))){
            p02.setText(String.valueOf(mrizka[0][2]));
            p02.getStyleClass().clear();
            p02.getStyleClass().add("b"+mrizka[0][2]);
        }
        else{
            p02.setText("");
            p02.getStyleClass().clear();
            p02.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[1][2]))){
            p12.setText(String.valueOf(mrizka[1][2]));
            p12.getStyleClass().clear();
            p12.getStyleClass().add("b"+mrizka[1][2]);
        }
        else{
            p12.setText("");
            p12.getStyleClass().clear();
            p12.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[2][2]))){
            p22.setText(String.valueOf(mrizka[2][2]));
            p22.getStyleClass().clear();
            p22.getStyleClass().add("b"+mrizka[2][2]);
        }
        else{
            p22.setText("");
            p22.getStyleClass().clear();
            p22.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[3][2]))){
            p32.setText(String.valueOf(mrizka[3][2]));
            p32.getStyleClass().clear();
            p32.getStyleClass().add("b"+mrizka[3][2]);
        }else{
            p32.setText("");
            p32.getStyleClass().clear();
            p32.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[0][3]))){
            p03.setText(String.valueOf(mrizka[0][3]));
            p03.getStyleClass().clear();
            p03.getStyleClass().add("b"+mrizka[0][3]);
        }else{
            p03.setText("");
            p03.getStyleClass().clear();
            p03.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[1][3]))){
            p13.setText(String.valueOf(mrizka[1][3]));
            p13.getStyleClass().clear();
            p13.getStyleClass().add("b"+mrizka[1][3]);
        }else{
            p13.setText("");
            p13.getStyleClass().clear();
            p13.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[2][3]))){
            p23.setText(String.valueOf(mrizka[2][3]));
            p23.getStyleClass().clear();
            p23.getStyleClass().add("b"+mrizka[2][3]);
        }else{
            p23.setText("");
            p23.getStyleClass().clear();
            p23.getStyleClass().add("pane");
        }
        if(!"0".equals(String.valueOf(mrizka[3][3]))){
            p33.setText(String.valueOf(mrizka[3][3]));
            p33.getStyleClass().clear();
            p33.getStyleClass().add("b"+mrizka[3][3]);
        }else{
            p33.setText("");
            p33.getStyleClass().clear();
            p33.getStyleClass().add("pane");
        }
    }
}