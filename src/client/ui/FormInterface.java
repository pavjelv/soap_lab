package client.ui;

import client.TestFacade;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Question;
import server.entity.Student;
import server.entity.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FormInterface {
    private Stage stage;
    private double width = 400;
    private double height = 275;

    private Test[] tests = new Test[0];
    private GameBoard gameBoard;

    private JPanel panel;

    public FormInterface(Stage stage ){
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

        setWindowSize(width, height);
        stage.setTitle("Testing system");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scene_title = new Text("Welcome");
        scene_title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scene_title, 0, 0, 2, 1);

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

    public GridPane showCreateGrid (GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView) {
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
            previousPane.setCenter(previousGrid);
            previousPane.setBottom(previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });
        hBox.getChildren().addAll(okBtn);

        Button leaveBtn = new Button("Leave");
        leaveBtn.setOnMouseClicked(event -> stage.setScene(stage.getScene()));
        leaveBtn.setPrefSize(90, 20);
        hBox.getChildren().add(leaveBtn);
        createGrid.add(hBox, 1, 5);
        return createGrid;
    }

    public void showTeacherPanel() {
        Button modifyTestBtn = new Button("Modify test");
        Button createBtn = new Button("Create test");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 5, 5, 10));
        hBox.getChildren().add(modifyTestBtn);
        hBox.getChildren().add(createBtn);

        GridPane joinGrid = new GridPane();
        joinGrid.setAlignment(Pos.CENTER);
        joinGrid.setHgap(10);
        joinGrid.setVgap(10);
        joinGrid.setPadding(new Insets(20, 20, 10, 10));

        Text joinTitle = new Text("Join room");
        joinTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        joinGrid.add(joinTitle, 0, 0, 2, 1);

        Button okBtn = new Button("OK");
        okBtn.setPrefSize(50, 20);
        HBox hBoxBtn = new HBox();
        hBoxBtn.setPadding(new Insets(20, 20, 30, 20));
        hBoxBtn.setAlignment(Pos.CENTER);
        hBoxBtn.getChildren().add(okBtn);

        Button exit = new Button("exit");
        exit.setPrefSize(50, 20);
        hBoxBtn.getChildren().add(exit);

        BorderPane borderPane = new BorderPane();

        borderPane.setTop(hBox);
        borderPane.setCenter(joinGrid);
        borderPane.setBottom(hBoxBtn);

        ListView<String> list = new ListView<>();
        list.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));

        exit.setOnMouseClicked(event1 -> stage.close());

        createBtn.setOnMouseClicked(event -> {
            borderPane.setCenter(showCreateGrid(joinGrid, borderPane, hBoxBtn, list));
            borderPane.setBottom(new HBox());
        });

        modifyTestBtn.setOnMouseClicked( event2 ->  {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if(selectedTest != null) {
                Test test = TestFacade.getTestByName(selectedTest);
                test.addQuestion(new Question("NAPAS"));
                TestFacade.updateTest(test);
            }
        });

        joinGrid.add(list, 0, 1);

        okBtn.setOnMouseClicked( event -> {
                    stage.hide();
                    gameBoard = new GameBoard();
                    gameBoard.setVisible(true);
        });

        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }


    public class GameBoard extends JFrame {
        public GameBoard(){
            super("Passeng_Korid");
            initUI();
        }
        private void initUI() {
            setSize(540, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            panel = new GPanel();
            add(panel);
        }

        public class GPanel extends JPanel {
            public GPanel() {
                setPreferredSize(new Dimension(540, 600));
                MouseAdapter mouseHandler;
                mouseHandler = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        repaint();
                    }
                };
                addMouseListener(mouseHandler);
            }

            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
            }
        }
    }
}
