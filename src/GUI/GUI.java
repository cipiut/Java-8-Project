package GUI;

import Program.MonitoredData;
import Program.Task;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Collectors;



public class GUI extends Application implements EventHandler<ActionEvent> {

    private TextArea write;
    private Button task1;
    private Button task2;
    private Button task3;
    private Button task4;
    private Button task5;
    private Task task;
    private LinkedList<MonitoredData> list;

    private void initValues(){//pentru initializarea variabilelor
        task=new Task();
        task1 = new Button("Task1");
        task2= new Button("Task2");
        task3 = new Button("Task3");
        task4 = new Button("Task4");
        task5 = new Button("Task5");
        task1.setOnAction(this);
        task2.setOnAction(this);
        task3.setOnAction(this);
        task4.setOnAction(this);
        task5.setOnAction(this);
        write= new TextArea();
        write.setEditable(false);
        write.setBackground(Background.EMPTY);
        write.setPrefColumnCount(50);
        write.setPrefRowCount(50);
        try {
            list=task.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        initValues();//initializam variabilele de clasa
        stage.setTitle("Tema 5 Lambda");
        stage.setResizable(false);
        HBox h =new HBox();
        VBox v = new VBox();
        v.setPrefSize(200,520);
        v.setPadding(new Insets(10, 50, 50, 50));
        v.setSpacing(10);
        v.setAlignment(Pos.BASELINE_LEFT);
        v.getChildren().addAll(task1,task2,task3,task4,task5);
        h.getChildren().addAll(v,write);
        Scene scene = new Scene(h,890,520);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()==task1){
            write.setText(String.valueOf(task.count(list)));
        }
        if(event.getSource()==task2){
            try {
                task.writeTask2(list);
                write.setText(Files.lines(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask2.txt")).collect(Collectors.joining("\n")));
            }catch (Exception e) {
                write.setText("Task2 Failed ...");
            }
        }
        if(event.getSource()==task3){
            try {
                task.writeTask3(list);
                write.setText(Files.lines(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask3.txt")).collect(Collectors.joining("\n")));
            }catch (Exception e) {
                write.setText("Task3 Failed ...");
            }
        }
        if(event.getSource()==task4){
            try {
                task.writeTask4(list);
                write.setText(Files.lines(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask4.txt")).collect(Collectors.joining("\n")));
            }catch (Exception e) {
                write.setText("Task4 Failed ...");
            }
        }
        if(event.getSource()==task5){
            try {
                task.writeTask5(list);
                write.setText(Files.lines(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask5.txt")).collect(Collectors.joining("\n")));
            }catch (Exception e) {
                write.setText("Task5 Failed ...");
            }
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}