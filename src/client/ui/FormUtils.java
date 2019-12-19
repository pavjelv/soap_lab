package client.ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import server.entity.Printable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class FormUtils {

    public static GridPane createGrid () {
        GridPane createGrid = new GridPane();
        createGrid.setAlignment(Pos.CENTER);
        createGrid.setHgap(10);
        createGrid.setPadding(new Insets(10, 20, 10, 20));
        createGrid.setVgap(20);
        return createGrid;
    }

    public static HBox createHboxAndAppendButtons (Button... buttons) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20, 20, 30, 20));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(buttons);
        return hBox;
    }

    public static Button createButtonWithTitle (String title) {
        Button btn = new Button(title);
        btn.setPrefSize(90, 20);
        return btn;
    }

    public static Text createText (String content) {
        Text txt = new Text(content);
        txt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        return txt;
    }

    public static void go(BorderPane previousPane, Node previousTop, Node previousGrid, Node previousBottom) {
        previousPane.setTop(previousTop);
        previousPane.setCenter(previousGrid);
        previousPane.setBottom(previousBottom);
    }

    public static ListView<String> createListAndFill(Printable... items) {
        ListView<String> list = new ListView<>();
        if(items != null) {
            fillList(list, items);
        }
        return list;
    }

    public static void fillList(ListView<String> listView, Printable... printables) {
        ArrayList<String> qNames = new ArrayList<>();
        if(printables == null) {
            return;
        }
        for (Printable item : printables) {
            if (item != null && item.getText() != null) {
                qNames.add(item.getText());
            }
        }
        listView.setItems(FXCollections.observableArrayList(qNames));
    }

    public static ListView<String> createAndFillWithParsedValues(String... items) {
        ListView<String> listView = new ListView<>();
        if(items == null) {
            return listView;
        }
        listView.setItems(FXCollections.observableArrayList(parse(items)));
        return listView;
    }

    public static List<String> parse(String... items) {
        List<String> result = new ArrayList<>();
        for (String item : items) {
            String[] nameAndTime = item.split("&");
            LocalTime date = LocalTime.ofSecondOfDay(Long.valueOf(nameAndTime[0]));
            if(nameAndTime.length == 1) {
                result.add(date.toString());
            } else {
                result.add(date + " " + nameAndTime[1]);
            }
        }
        return result;
    }

}
