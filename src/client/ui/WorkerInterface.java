package client.ui;

import client.TaskFacade;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Task;
import shared.TaskStatus;

import static client.ui.FormUtils.*;
import static client.ui.FormUtils.createHboxAndAppendButtons;

public class WorkerInterface {
    private Stage stage;
    private double width = 550;
    private double height = 550;

    public WorkerInterface(Stage stage) {
        this.stage = stage;
        showWorkerPanel();
    }

    public GridPane showCompleteTaskGrid(Task selectedTask) {
        GridPane createGrid = createGrid();

        Text createTitle = createText("Complete task");
        createGrid.add(createTitle, 0, 0, 2, 1);

        Label taskName = new Label("Report:");
        createGrid.add(taskName, 0, 1);

        TextArea taskTextField = new TextArea("");
        createGrid.add(taskTextField, 1, 1);

        Button okBtn = createButtonWithTitle("Complete");
        okBtn.setOnMouseClicked(userConfirm -> {
            if(!taskTextField.getText().isEmpty()) {
                TaskFacade.completeTask(selectedTask.getText(), taskTextField.getText());
                showWorkerPanel();
            }
        });

        Button leaveBtn = createButtonWithTitle("Leave");
        leaveBtn.setOnMouseClicked(event -> showWorkerPanel());

        createGrid.add(createHboxAndAppendButtons(okBtn, leaveBtn), 1, 5);

        return createGrid;
    }


    public void showWorkerPanel() {
        GridPane joinGrid = createGrid();

        Text tasksTitle = createText("Existing tasks");
        joinGrid.add(tasksTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");
        Button startProgressBtn = createButtonWithTitle("Start progress");
        Button completeBtn = createButtonWithTitle("Complete");

        HBox hBox = createHboxAndAppendButtons(startProgressBtn, completeBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFill(TaskFacade.getAllAppropriateTasks());
        joinGrid.add(list, 0, 1);

        exit.setOnMouseClicked(event1 -> stage.close());

        completeBtn.setOnMouseClicked(event -> {
            String selectedTaskName = list.getSelectionModel().getSelectedItem();
            if (selectedTaskName != null) {
                Task selectedTask = TaskFacade.getTaskByName(selectedTaskName);
                if(selectedTask.getStatus() == TaskStatus.IN_PROGRESS) {
                    go(borderPane, createHBox(), showCompleteTaskGrid(selectedTask), createHBox());
                }
            }
        });

        startProgressBtn.setOnMouseClicked(event2 -> {
            String selectedTaskName = list.getSelectionModel().getSelectedItem();
            if (selectedTaskName != null) {
                Task selectedTask = TaskFacade.getTaskByName(selectedTaskName);
                if(selectedTask.getStatus() == TaskStatus.OPEN) {
                    TaskFacade.setTaskToInProgress(selectedTask.getText());
                }
                fillList(list, TaskFacade.getAllAppropriateTasks());
            }
        });


        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

}
