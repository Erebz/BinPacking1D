/*package sample;

import core.Bin;
import core.BinPacking;
import core.Item;
import core.PackingSolution;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("BinPacking1D");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 500);
        Group grpBins = new Group();

        BinPacking bp = new BinPacking("binpack1d_00.txt");
        PackingSolution solution = bp.firstFitDecreasing();
        List<Bin> bins = solution.getBins();
        int tailleBin = bp.getTailleBin();
        for (int i = 0; i < bins.size(); i++) {
            Rectangle r = new Rectangle();
            Bin bin = bins.get(i);
            r.setY(20);
            int X = i * 30 + 20;
            r.setX(X);
            r.setWidth(20);
            r.setHeight(tailleBin*10);
            r.setFill(Color.GRAY);
            grpBins.getChildren().add(r);
            List<Item> items = bin.getItems();
            for(int j=0; j < items.size(); j++){
                Item item = items.get(j);
                Rectangle itemRec = new Rectangle();
                itemRec.setX(X+1);
                itemRec.setWidth(18);
                itemRec.setWidth(18);
                itemRec.setY(item.getTaille()*10 + 1);
                itemRec.setFill(Color.SILVER);
            }
        }

        root.getChildren().add(grpBins);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
*/