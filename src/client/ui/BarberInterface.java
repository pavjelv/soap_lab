package client.ui;

import client.ScheduleFacade;
import client.TaskFacade;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static client.ui.FormUtils.*;
import static client.ui.FormUtils.createHboxAndAppendButtons;

public class BarberInterface {
    private Stage stage;
    private double width = 550;
    private double height = 550;
    private static int id = 0;

    public BarberInterface(Stage stage) {
        this.stage = stage;
        showBarberPanel();
    }

    public GridPane showCreateTaskGrid(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop) {
            GridPane createGrid = createGrid();

            Text createTitle = createText("Create task");
            createGrid.add(createTitle, 0, 0, 2, 1);

            Label taskName = new Label("Task name:");
            createGrid.add(taskName, 0, 1);

            TextField taskTextField = new TextField();
            taskTextField.setText("Task " + id++);
            createGrid.add(taskTextField, 1, 1);

            Label taskPrice = new Label("Price");
            createGrid.add(taskPrice, 0, 2);

            TextField taskPriceTextField = new TextField();
            taskPriceTextField.setText("100");
            createGrid.add(taskPriceTextField, 1, 2);

            Label taskDuration = new Label("Duration(min)");
            createGrid.add(taskDuration, 0, 3);

            TextField taskDurationTextField = new TextField();
            taskDurationTextField.setText("40");
            createGrid.add(taskDurationTextField, 1, 3);

            Button okBtn = createButtonWithTitle("Create");
            okBtn.setOnMouseClicked(userConfirm -> {
                TaskFacade.createAndAddTask(taskTextField.getText(), Integer.valueOf(taskPriceTextField.getText()), Long.valueOf(taskDurationTextField.getText()));
                go(previousPane, previousTop, previousGrid, previousBottom);
                listView.setItems(FXCollections.observableArrayList(TaskFacade.retrieveAllTaskText()));
            });

            Button leaveBtn = createButtonWithTitle("Leave");
            leaveBtn.setOnMouseClicked(event -> go(previousPane, previousTop, previousGrid, previousBottom));

            createGrid.add(createHboxAndAppendButtons(okBtn, leaveBtn), 1, 5);

            return createGrid;
    }

    public GridPane showScheduleGrid(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop) {
        GridPane scheduleGrid = createGrid();

        Label scheduleLabel = new Label("My schedule:");
        scheduleGrid.add(scheduleLabel, 0, 1);

        ListView<String> list = createAndFillWithParsedValues(ScheduleFacade.getSchedule());
        scheduleGrid.add(list, 0, 2);

        Button leaveBtn = createButtonWithTitle("Leave");
        leaveBtn.setOnMouseClicked(event -> go(previousPane, previousTop, previousGrid, previousBottom));

        scheduleGrid.add(createHboxAndAppendButtons(leaveBtn), 1, 5);

        return scheduleGrid;
    }


    public void showBarberPanel() {
        GridPane joinGrid = createGrid();

        Text tasksTitle = createText("Existing tasks");
        joinGrid.add(tasksTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");
        Button removeTaskBtn = createButtonWithTitle("Remove task");
        Button createBtn = createButtonWithTitle("Create task");
        Button checkScheduleBtn = createButtonWithTitle("Check schedule");

        HBox hBox = createHboxAndAppendButtons(removeTaskBtn, createBtn, checkScheduleBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFill(TaskFacade.getAllTasks());
        joinGrid.add(list, 0, 1);

        exit.setOnMouseClicked(event1 -> stage.close());

        createBtn.setOnMouseClicked(event -> go(borderPane, new HBox(), showCreateTaskGrid(joinGrid, borderPane, hBoxBtn, list, hBox), new HBox()));

        checkScheduleBtn.setOnMouseClicked(evt -> go(borderPane, new HBox(), showScheduleGrid(joinGrid, borderPane, hBoxBtn, list, hBox), new HBox()));

        removeTaskBtn.setOnMouseClicked(event2 -> {
            String selectedTask = list.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
               TaskFacade.removeTask(selectedTask);
               list.setItems(FXCollections.observableArrayList(TaskFacade.retrieveAllTaskText()));
            }
        });


        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

}
