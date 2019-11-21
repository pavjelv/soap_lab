package client.ui;

import client.TestFacade;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Printable;
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

        GridPane grid = createGrid();

        Text sceneTitle = createText("Welcome");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Role:");
        grid.add(userName, 0, 1);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Teacher", "Student");
        grid.add(comboBox, 1, 1);

        Button btn = createButtonWithTitle("Sign in");
        btn.setOnAction(event -> showTeacherPanel());

        grid.add(createHboxAndAppendButtons(btn), 1, 4);

        Scene scene = new Scene(grid, width, height);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createGrid () {
        GridPane createGrid = new GridPane();
        createGrid.setAlignment(Pos.CENTER);
        createGrid.setHgap(10);
        createGrid.setPadding(new Insets(10, 20, 10, 20));
        createGrid.setVgap(20);
        return createGrid;
    }

    private HBox createHboxAndAppendButtons (Button... buttons) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20, 20, 30, 20));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(buttons);
        return hBox;
    }

    private Button createButtonWithTitle (String title) {
        Button btn = new Button(title);
        btn.setPrefSize(90, 20);
        return btn;
    }

    private Text createText (String content) {
        Text txt = new Text(content);
        txt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        return txt;
    }

    private void go(BorderPane previousPane, Node previousTop, Node previousGrid, Node previousBottom) {
        previousPane.setTop(previousTop);
        previousPane.setCenter(previousGrid);
        previousPane.setBottom(previousBottom);
    }

    private ListView<String> createListAndFill(Printable... items) {
        ListView<String> list = new ListView<>();
        ArrayList<String> qNames = new ArrayList<>();
        for (Printable item : items) {
            if (item != null && item.getText() != null) {
                qNames.add(item.getText());
            }
        }
        list.setItems(FXCollections.observableArrayList(qNames));
        return list;
    }

    public GridPane showCreateGrid(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop) {
        GridPane createGrid = createGrid();

        Text createTitle = createText("Create test");
        createGrid.add(createTitle, 0, 0, 2, 1);

        Label testName = new Label("Test name:");
        createGrid.add(testName, 0, 1);

        TextField testTextField = new TextField();
        testTextField.setText("Test 1");
        createGrid.add(testTextField, 1, 1);

        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(userConfirm -> {
            TestFacade.createAndAddTest(testTextField.getText());
            go(previousPane, previousTop, previousGrid, previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });

        Button leaveBtn = createButtonWithTitle("Leave");
        leaveBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });

        createGrid.add(createHboxAndAppendButtons(okBtn, leaveBtn), 1, 5);

        return createGrid;
    }

    public GridPane showAddQuestion(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop, Test selectedTest) {
        GridPane createGrid = createGrid();
        int gridRowIndex = 0;

        Text createTitle = createText("Create question for " + selectedTest.getName());
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);

        Label testName = new Label("Question name:");
        createGrid.add(testName, 0, gridRowIndex++);

        TextField questionNameField = new TextField();
        questionNameField.setText("Question 1");
        createGrid.add(questionNameField, 0, gridRowIndex++);

        Label createdQuestionsLabel = new Label("Created questions:");
        createGrid.add(createdQuestionsLabel, 0, gridRowIndex++);

        ListView<String> list = createListAndFill(selectedTest.getAllQuestions());
        createGrid.add(list, 0, gridRowIndex++);


        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(userConfirm -> {
            TestFacade.addQuestion(selectedTest, new Question(questionNameField.getText()));
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
            go(previousPane, previousTop, previousGrid, previousBottom);
        });

        Button backBtn = createButtonWithTitle("Back");
        backBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });

        createGrid.add(createHboxAndAppendButtons(okBtn, backBtn), 1, gridRowIndex);
        return createGrid;
    }

    public GridPane showPreviewTest(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop, Test selectedTest) {
        GridPane createGrid = createGrid();
        int gridRowIndex = 0;

        Text createTitle = createText("Test info: " + selectedTest.getName());
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);


        Label createdQuestionsLabel = new Label("Created questions:");
        createGrid.add(createdQuestionsLabel, 0, gridRowIndex++);

        ListView<String> list = createListAndFill(selectedTest.getAllQuestions());
        createGrid.add(list, 0, gridRowIndex);


        Button modifyQuestionButton = createButtonWithTitle("Modify Question");
        createGrid.add(modifyQuestionButton, 1, gridRowIndex++);
        modifyQuestionButton.setOnMouseClicked(onSelect -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0) {
                Question selectedQuestion = selectedTest.getAllQuestions()[selectedIndex];
                System.out.println(selectedQuestion);
            }
        });

        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(userConfirm -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });

        Button backBtn = createButtonWithTitle("Back");
        backBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });
        createGrid.add(createHboxAndAppendButtons(okBtn, backBtn), 1, gridRowIndex);
        return createGrid;
    }

    public void showTeacherPanel() {
        GridPane joinGrid = createGrid();

        Text testsTitle = new Text("Existing tests");
        testsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        joinGrid.add(testsTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");
        Button modifyTestBtn = createButtonWithTitle("Modify test");
        Button createBtn = createButtonWithTitle("Create test");
        Button previewTestBtn = createButtonWithTitle("Preview Test");

        HBox hBox = createHboxAndAppendButtons(modifyTestBtn, createBtn, previewTestBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFill(TestFacade.getAllTests());
        joinGrid.add(list, 0, 1);

        exit.setOnMouseClicked(event1 -> stage.close());

        createBtn.setOnMouseClicked(event -> {
            go(borderPane, new HBox(), showCreateGrid(joinGrid, borderPane, hBoxBtn, list, hBox), new HBox());
        });

        modifyTestBtn.setOnMouseClicked(event2 -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Test test = TestFacade.getTestByName(selectedTest);
                go(borderPane, new HBox(), showAddQuestion(joinGrid, borderPane, hBoxBtn, list, hBox, test), new HBox());
            }
        });

        previewTestBtn.setOnMouseClicked(event -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Test test = TestFacade.getTestByName(selectedTest);
                go(borderPane, new HBox(), showPreviewTest(joinGrid, borderPane, hBoxBtn, list, hBox, test), new HBox());
            }
        });

        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }
}
