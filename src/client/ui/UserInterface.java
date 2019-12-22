package client.ui;

import client.TaskFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Task;
import shared.TaskStatus;

import java.time.LocalDate;

import static client.ui.FormUtils.*;

public class UserInterface {
    private Stage stage;
    private double width = 550;
    private double height = 550;
    LocalDate currentSelectedDate = null;

    public UserInterface(Stage stage) {
        this.stage = stage;
        showClientPanel();
    }

    public void showClientPanel() {
        GridPane joinGrid = createGrid();

        Text tasksTitle = createText("All tasks");
        joinGrid.add(tasksTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");
        Button createTaskBtn = createButtonWithTitle("Create new task");
        Button checkStatusBtn = createButtonWithTitle("Preview task");

        HBox hBox = createHboxAndAppendButtons(createTaskBtn, checkStatusBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFillWithTrimmedValues(TaskFacade.getAllTasks());

        joinGrid.add(list, 0, 2);

        exit.setOnMouseClicked(event1 -> stage.close());

        createTaskBtn.setOnMouseClicked(event2 -> go(borderPane, createHBox(), createTask(borderPane ,joinGrid), createHBox()));
        checkStatusBtn.setOnMouseClicked(onSelected -> {
            if(list.getSelectionModel().getSelectedItem() != null) {
                Task selectedTask = TaskFacade.getTaskByName(list.getSelectionModel().getSelectedItem());
                go(borderPane, createHBox(), previewTask(selectedTask), createHBox());
            }
        });


        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane createTask(BorderPane previousPane, GridPane previousGrid) {
        GridPane grid = createGrid();
        int gridRowIndex = 0;

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, gridRowIndex, 2, 1);
        TextArea nameInput = new TextArea("");
        grid.add(nameInput, 2, gridRowIndex++);

        Label emailLabel = new Label("E-mail:");
        grid.add(emailLabel, 0, gridRowIndex, 2, 1);
        TextArea emailInput = new TextArea("");
        grid.add(emailInput, 2, gridRowIndex++);

        Label descriptionLabel = new Label("Description:");
        grid.add(descriptionLabel, 0, gridRowIndex, 2, 1);
        TextArea descriptionInput = new TextArea("");
        grid.add(descriptionInput, 2, gridRowIndex++);

        Label dateLabel = new Label("Date:");
        grid.add(dateLabel, 0, gridRowIndex, 2, 1);

        DatePicker datePicker = new DatePicker();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentSelectedDate = datePicker.getValue();
            }
        };

        datePicker.setOnAction(event);

        grid.add(datePicker, 2, gridRowIndex++);

        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(onClicked ->{
            if(currentSelectedDate != null) {
                TaskFacade.createAndAddTask(nameInput.getText(), descriptionInput.getText(), emailInput.getText(), currentSelectedDate.toEpochDay());
            }
        });
        grid.add(createHboxAndAppendButtons(okBtn), 1, gridRowIndex++);

        Button finishBtn = createButtonWithTitle("Close");
        finishBtn.setOnMouseClicked(onFinish -> showClientPanel());
        grid.add(createHboxAndAppendButtons(finishBtn), 1, gridRowIndex);

        return grid;
    }

    public GridPane previewTask(Task selectedTask) {
        GridPane grid = createGrid();
        int gridRowIndex = 0;

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, gridRowIndex, 2, 1);
        Label nameValue = new Label(selectedTask.getReporterName());
        grid.add(nameValue, 2, gridRowIndex++);

        Label emailLabel = new Label("E-mail:");
        grid.add(emailLabel, 0, gridRowIndex, 2, 1);
        Label emailValue = new Label(selectedTask.getReporterEmail());
        grid.add(emailValue, 2, gridRowIndex++);

        Label dateLabel = new Label("Date:");
        grid.add(dateLabel, 0, gridRowIndex, 2, 1);
        Label dateValueLabel = new Label(LocalDate.ofEpochDay(selectedTask.getDateOfCreation()).toString());
        grid.add(dateValueLabel, 2, gridRowIndex++);

        Label statusLabel = new Label("Status:");
        grid.add(statusLabel, 0, gridRowIndex, 2, 1);
        Label statusValue = new Label(selectedTask.getStatus().getText());
        grid.add(statusValue, 2, gridRowIndex++);

        if(selectedTask.getStatus() != TaskStatus.COMPLETED) {
            Label descriptionLabel = new Label("Description:");
            grid.add(descriptionLabel, 0, gridRowIndex, 2, 1);
            Label descriptionValueLabel = new Label(selectedTask.getDescription());
            grid.add(descriptionValueLabel, 2 ,gridRowIndex++);
        } else {
            Label reportLabel = new Label("Report:");
            grid.add(reportLabel, 0, gridRowIndex, 2, 1);
            Label reportValue = new Label(selectedTask.getReport());
            grid.add(reportValue, 2, gridRowIndex++);

            Label descriptionLabel = new Label("Description:");
            grid.add(descriptionLabel, 0, gridRowIndex, 2, 1);
            TextArea descriptionInput = new TextArea(selectedTask.getDescription());
            grid.add(descriptionInput, 2, gridRowIndex++);

            Button okBtn = createButtonWithTitle("Reopen");
            okBtn.setOnMouseClicked(onClicked -> {
                if (!descriptionInput.getText().equals(selectedTask.getDescription())) {
                    TaskFacade.reopenTask(selectedTask.getText(), descriptionInput.getText());
                    showClientPanel();
                }
            });
            Button closeBtn = createButtonWithTitle("Close");
            closeBtn.setOnMouseClicked(onClicked -> {
                TaskFacade.closeTask(selectedTask.getText());
                showClientPanel();
            });
            grid.add(createHboxAndAppendButtons(okBtn, closeBtn), 2, gridRowIndex++);
        }

        Button finishBtn = createButtonWithTitle("Exit");
        finishBtn.setOnMouseClicked(onFinish -> showClientPanel());
        grid.add(createHboxAndAppendButtons(finishBtn), 1, gridRowIndex);

        return grid;

    }

}
