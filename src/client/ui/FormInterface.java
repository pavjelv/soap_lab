package client.ui;

import client.TestFacade;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Question;
import server.entity.Test;

import javax.swing.*;
import java.util.ArrayList;

public class FormInterface {
    private Stage stage;
    private double width = 400;
    private double height = 275;

    private Test[] tests = new Test[0];

    private JPanel panel;

    public FormInterface(Stage stage) {
        this.stage = stage;
        start();
    }

    public void setWindowSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void start() {
        registration();
    }

    public void registration() {
        setWindowSize(400, 500);
        stage.setTitle("Testing system");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Role:");
        grid.add(userName, 0, 1);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Teacher", "Student");
        grid.add(comboBox, 1, 1);

        Button btn = new Button("Sign in");
        btn.setDefaultButton(true);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        btn.setOnAction(event -> showTeacherPanel());

        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Scene scene = new Scene(grid, width, height);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane showCreateGrid(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop) {
        GridPane createGrid = new GridPane();
        createGrid.setAlignment(Pos.CENTER);
        createGrid.setHgap(10);
        createGrid.setVgap(10);
        Text createTitle = new Text("Create test");
        createTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        createGrid.add(createTitle, 0, 0, 2, 1);
        Label testName = new Label("Test name:");
        createGrid.add(testName, 0, 1);

        TextField testTextField = new TextField();
        testTextField.setText("Test 1");
        createGrid.add(testTextField, 1, 1);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20, 20, 30, 20));
        hBox.setAlignment(Pos.CENTER);

        Button okBtn = new Button("Create");
        okBtn.setPrefSize(90, 20);
        okBtn.setOnMouseClicked(userConfirm -> {
            TestFacade.createAndAddTest(testTextField.getText());
            previousPane.setTop(previousTop);
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });
        hBox.getChildren().addAll(okBtn);

        Button leaveBtn = new Button("Leave");
        leaveBtn.setOnMouseClicked(event -> {
            previousPane.setTop(previousTop);
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
        });
        leaveBtn.setPrefSize(90, 20);
        hBox.getChildren().add(leaveBtn);
        createGrid.add(hBox, 1, 5);
        return createGrid;
    }

    public GridPane showAddQuestion(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop, Test selectedTest) {
        GridPane createGrid = new GridPane();
        createGrid.setAlignment(Pos.CENTER);
        createGrid.setHgap(10);
        createGrid.setPadding(new Insets(10, 20, 10, 20));
        createGrid.setVgap(20);
        int gridRowIndex = 0;

        Text createTitle = new Text("Create question for " + selectedTest.getName());
        createTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);

        Label testName = new Label("Question name:");
        createGrid.add(testName, 0, gridRowIndex++);

        TextField questionNameField = new TextField();
        questionNameField.setText("Question 1");
        createGrid.add(questionNameField, 0, gridRowIndex++);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20, 20, 30, 20));
        hBox.setAlignment(Pos.CENTER);

        Label createdQuestionsLabel = new Label("Created questions:");
        createGrid.add(createdQuestionsLabel, 0, gridRowIndex++);

        ListView<String> list = new ListView<>();
        Question[] existingQuestions = selectedTest.getAllQuestions();
        ArrayList<String> qNames = new ArrayList<>();
        for (Question existingQuestion : existingQuestions) {
            if (existingQuestion != null && existingQuestion.getQuestionText() != null) {
                qNames.add(existingQuestion.getQuestionText());
            }
        }
        list.setItems(FXCollections.observableArrayList(qNames));
        createGrid.add(list, 0, gridRowIndex++);


        Button okBtn = new Button("Create");
        okBtn.setPrefSize(90, 20);
        okBtn.setOnMouseClicked(userConfirm -> {
            TestFacade.addQuestion(selectedTest, new Question(questionNameField.getText()));
            previousPane.setTop(previousTop);
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });
        hBox.getChildren().addAll(okBtn);

        Button backBtn = new Button("Back");
        backBtn.setOnMouseClicked(event -> {
            previousPane.setTop(previousTop);
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
        });
        backBtn.setPrefSize(90, 20);
        hBox.getChildren().add(backBtn);
        createGrid.add(hBox, 1, gridRowIndex);
        return createGrid;
    }

    public GridPane showPreviewTest(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop, Test selectedTest) {
        GridPane createGrid = new GridPane();
        createGrid.setAlignment(Pos.CENTER);
        createGrid.setHgap(10);
        createGrid.setPadding(new Insets(10, 20, 10, 20));
        createGrid.setVgap(20);
        int gridRowIndex = 0;

        Text createTitle = new Text("Test info: " + selectedTest.getName());
        createTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20, 20, 30, 20));
        hBox.setAlignment(Pos.CENTER);

        Label createdQuestionsLabel = new Label("Created questions:");
        createGrid.add(createdQuestionsLabel, 0, gridRowIndex++);

        ListView<String> list = new ListView<>();
        Question[] existingQuestions = selectedTest.getAllQuestions();
        ArrayList<String> qNames = new ArrayList<>();
        for (Question existingQuestion : existingQuestions) {
            if (existingQuestion != null && existingQuestion.getQuestionText() != null) {
                qNames.add(existingQuestion.getQuestionText());
            }
        }
        list.setItems(FXCollections.observableArrayList(qNames));
        createGrid.add(list, 0, gridRowIndex);


        Button modifyQuestionButton = new Button("Modify Question");
        createGrid.add(modifyQuestionButton, 1, gridRowIndex++);
        modifyQuestionButton.setOnMouseClicked(onSelect -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0) {
                Question selectedQuestion = selectedTest.getAllQuestions()[selectedIndex];
                System.out.println(selectedQuestion);
            }
        });


        Button okBtn = new Button("Create");
        okBtn.setPrefSize(90, 20);
        okBtn.setOnMouseClicked(userConfirm -> {
            previousPane.setTop(previousTop);
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });
        hBox.getChildren().addAll(okBtn);

        Button backBtn = new Button("Back");
        backBtn.setOnMouseClicked(event -> {
            previousPane.setTop(previousTop);
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
        });
        backBtn.setPrefSize(90, 20);
        hBox.getChildren().add(backBtn);
        createGrid.add(hBox, 1, gridRowIndex);
        return createGrid;
    }

    public void showTeacherPanel() {
        Button modifyTestBtn = new Button("Modify test");
        Button createBtn = new Button("Create test");
        Button previewTestBtn = new Button("Preview Test");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 5, 5, 10));
        hBox.getChildren().add(modifyTestBtn);
        hBox.getChildren().add(createBtn);
        hBox.getChildren().add(previewTestBtn);

        GridPane joinGrid = new GridPane();
        joinGrid.setAlignment(Pos.CENTER);
        joinGrid.setHgap(10);
        joinGrid.setVgap(10);
        joinGrid.setPadding(new Insets(20, 20, 10, 10));

        Text joinTitle = new Text("Existing tests");
        joinTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        joinGrid.add(joinTitle, 0, 0, 2, 1);

        HBox hBoxBtn = new HBox();
        hBoxBtn.setPadding(new Insets(20, 20, 30, 20));
        hBoxBtn.setAlignment(Pos.CENTER);

        Button exit = new Button("exit");
        exit.setPrefSize(50, 20);
        hBoxBtn.getChildren().add(exit);

        BorderPane borderPane = new BorderPane();

        borderPane.setTop(hBox);
        borderPane.setCenter(joinGrid);
        borderPane.setBottom(hBoxBtn);

        ListView<String> list = new ListView<>();
        list.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        joinGrid.add(list, 0, 1);

        exit.setOnMouseClicked(event1 -> stage.close());

        createBtn.setOnMouseClicked(event -> {
            borderPane.setTop(new HBox());
            borderPane.setCenter(showCreateGrid(joinGrid, borderPane, hBoxBtn, list, hBox));
            borderPane.setBottom(new HBox());
        });

        modifyTestBtn.setOnMouseClicked(event2 -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Test test = TestFacade.getTestByName(selectedTest);
                borderPane.setTop(new HBox());
                borderPane.setCenter(showAddQuestion(joinGrid, borderPane, hBoxBtn, list, hBox, test));
                borderPane.setBottom(new HBox());
            }
        });

        previewTestBtn.setOnMouseClicked(event -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Test test = TestFacade.getTestByName(selectedTest);
                borderPane.setTop(new HBox());
                borderPane.setCenter(showPreviewTest(joinGrid, borderPane, hBoxBtn, list, hBox, test));
                borderPane.setBottom(new HBox());
            }
        });

        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }
}
