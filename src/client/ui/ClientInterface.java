package client.ui;

import client.ScheduleFacade;
import client.TaskFacade;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static client.ui.FormUtils.*;

public class ClientInterface {
    private Stage stage;
    private double width = 550;
    private double height = 550;
    private int result = 0;
    LocalTime currentSelectedTime = null;

    public ClientInterface(Stage stage) {
        this.stage = stage;
        showClientPanel();
    }

    public void showClientPanel() {
        GridPane joinGrid = createGrid();

        Text tasksTitle = createText("Available tasks");
        joinGrid.add(tasksTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");

        Button createOrderBtn = createButtonWithTitle("Create order");

        HBox hBox = createHboxAndAppendButtons(createOrderBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFill(TaskFacade.getAllTasks());

        joinGrid.add(list, 0, 2);

        exit.setOnMouseClicked(event1 -> stage.close());


        createOrderBtn.setOnMouseClicked(event2 -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Task task = TaskFacade.getTaskByName(selectedTest);
                go(borderPane, new HBox(), createOrder(borderPane ,joinGrid, task), new HBox());
            }
        });

        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane createOrder(BorderPane previousPane, GridPane previousGrid, Task selectedTask) {
        GridPane grid = createGrid();
        int gridRowIndex = 0;

        Text taskTitle = createText(selectedTask.getText());
        grid.add(taskTitle, 0, gridRowIndex++, 2, 1);

        Label availableTimeLabel = new Label("Available time:");
        grid.add(availableTimeLabel, 0, gridRowIndex++);

        ListView<String> list = createAndFillWithParsedValues(ScheduleFacade.getAvailableHours());
        grid.add(list, 0, gridRowIndex++);

        JFXTimePicker timePicker = new JFXTimePicker();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentSelectedTime = timePicker.getValue();
            }
        };

        timePicker.setOnAction(event);

        grid.add(timePicker, 0, gridRowIndex++);

        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(onClicked ->{
            if(currentSelectedTime != null) {
                ScheduleFacade.createOrder(selectedTask.getName(), Long.valueOf(currentSelectedTime.toSecondOfDay()));
                list.setItems(FXCollections.observableArrayList(parse(ScheduleFacade.getAvailableHours())));
            }
        });
        grid.add(createHboxAndAppendButtons(okBtn), 1, gridRowIndex++);

        Button finishBtn = createButtonWithTitle("Close");
        finishBtn.setOnMouseClicked(onFinish -> showClientPanel());
        grid.add(createHboxAndAppendButtons(finishBtn), 1, gridRowIndex);


        return grid;
    }


}
