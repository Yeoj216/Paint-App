package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.image.Image;
import sample.Eraser;

public class Main extends Application {
    Stack<WritableImage> undoStack;

// zedet comment bas la yaamol push

    @Override
    public void start(Stage primaryStage) throws Exception{
        undoStack = new Stack<WritableImage>();

        BorderPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setStyle("-fx-background-color: #FFFFFF");
        Scene scene = new Scene(root);


//
//        Pane pane = new Pane();
//        pane.setStyle("-fx-background-color: #FFFFFF");
//        pane.setPrefHeight(scene.getHeight());
//        pane.setPrefWidth(scene.getWidth());
//
//
//        canvas = new Canvas(pane.getPrefWidth(),pane.getPrefHeight());
//        gc = canvas.getGraphicsContext2D();
//        gc.setStroke(Color.BLACK);
//
//        //        Screen normal drawing
//        pane.setOnMousePressed(a-> {
//            pushUndo();
//            gc.beginPath();
//            gc.lineTo(a.getSceneX(), a.getSceneY());
//            gc.stroke();
//        });
//
//
//        pane.setOnMouseDragged(a->{
//            gc.lineTo(a.getSceneX(), a.getSceneY());
//            gc.stroke();
//        });
//
//
//        HBox hbox = new HBox(30);
//
//
//        ComboBox<String> thickness =  new ComboBox<>();
//        thickness.setPrefWidth(80);
//        thickness.getItems().add("1.0");
//        thickness.getItems().add("1.5");
//        thickness.getItems().add("2.0");
//        thickness.getItems().add("2.5");
//        thickness.getItems().add("3.0");
//        thickness.getSelectionModel().select(0);
//
//        hbox.getChildren().add(thickness);
//
//        thickness.setOnAction(e->{
//            String value = thickness.getValue();
//
//            if(value.equalsIgnoreCase("1.0")){
//                gc.setLineWidth(1.0);
//            }
//            else if(value.equalsIgnoreCase("1.5")){
//                gc.setLineWidth(4.0);
//            }
//            else if(value.equalsIgnoreCase("2.0")){
//                gc.setLineWidth(7.0);
//            }
//            else if(value.equalsIgnoreCase("2.5")){
//                gc.setLineWidth(10.0);
//            }
//            else if(value.equalsIgnoreCase("3.0")){
//                gc.setLineWidth(14.0);
//            }
//        });
//
//
//
//        ComboBox<String> colors =  new ComboBox<>();
//        colors.getItems().add("Color");
//        colors.getItems().add("Red");
//        colors.getItems().add("Blue");
//        colors.getSelectionModel().select(0);
////        colors.set
//        Label cname = new Label("Color Picker");
//        Group redGroup = new Group();
//        Label redLabel = new Label("Red");
//        TextField txtField = new TextField("Red");
//        Slider R =new Slider(0, 255,0);
//        Slider G =new Slider(0, 255,0);
//        Slider B =new Slider(0, 255,0);
//        VBox color = new VBox(5,R,G,B);
//
//
//
//
//        Button chooseColorBtn = new Button("Select Color");
//        chooseColorBtn.setOnAction(e ->{
//                    DoubleProperty Rproperty = R.valueProperty();
//                    DoubleProperty Gproperty = G.valueProperty();
//                    DoubleProperty Bproperty = B.valueProperty();
//                    Integer intR = Rproperty.intValue();
//                    Integer intG = Gproperty.intValue();
//                    Integer intB = Bproperty.intValue();
//                    gc.setStroke(Color.rgb(intR,intG,intB));
//                }
//
//        );
//        Button resetColorBtn = new Button("Reset");
//        resetColorBtn.setOnAction(e ->{
//            R.adjustValue(0);
//            G.adjustValue(0);
//            B.adjustValue(0);
//            DoubleProperty Rproperty = R.valueProperty();
//            DoubleProperty Gproperty = G.valueProperty();
//            DoubleProperty Bproperty = B.valueProperty();
//            Integer intR = Rproperty.intValue();
//            Integer intG = Gproperty.intValue();
//            Integer intB = Bproperty.intValue();
//            gc.setStroke(Color.rgb(intR,intG,intB));
//        });
//        color.getChildren().add(chooseColorBtn);
//        color.getChildren().add(resetColorBtn);
//
//
//
//
//
//        hbox.getChildren().add(color);
//
//
//        hbox.setStyle("-fx-background-color: #000000");
//
//        hbox.setPrefHeight(52);
//        hbox.setPrefWidth(1450);
//        hbox.setPadding(new Insets(10,10,10,35));
//
//
////
////        pane.setLayoutY(52);
////        canvas.setLayoutY(52);
//
//        Button addTextBtn = new Button("Add Text");
//        TextInputDialog td = new TextInputDialog();
//
//        addTextBtn.setOnAction(e->{
//            td.show();
//            td.setTitle("Text Input ");
//            td.setHeaderText("Enter text: ");
//            td.setContentText("Text");
//            TextField text  = td.getEditor();
//            pane.setOnMousePressed(a->{
//                gc.fillText(text.getText(), a.getX(), a.getY());
//                pushUndo();
//                pane.setOnMouseReleased(z->{
//                    pane.setOnMousePressed(p-> {
//                        pushUndo();
//                        gc.beginPath();
//                        gc.lineTo(p.getSceneX(), p.getSceneY());
//                        gc.stroke();
//                    });
//
//                    pane.setOnMouseDragged(p->{
//                        gc.lineTo(p.getSceneX(), p.getSceneY());
//                        gc.stroke();
//                    });
//
//                });
//            });
////            System.out.println(text.getText());
////            gc.strokeText(text.getText(), 200, 200);
//
//        });
//        hbox.getChildren().add(addTextBtn);
//
//
//        Button clearBtn = new Button("Clear");
//        hbox.getChildren().add(clearBtn);
//        clearBtn.setOnAction(e->{
//            gc.clearRect(0,0,1450,1000);
//        });
//
//        Button undoBtn = new Button("Undo");
//
//        undoBtn.setOnAction(e->{
//            undo();
//        });
//
//        hbox.getChildren().add(undoBtn);
//
//
//        Button saveBtn = new Button("Save");
//        saveBtn.setOnAction((e)->{
//            FileChooser savefile = new FileChooser();
//            savefile.setTitle("Save File");
//            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files","*.JPG");
//            savefile.getExtensionFilters().add(extFilter);
//
//            File file = savefile.showSaveDialog(primaryStage);
//            System.out.println("is file null ? "+ file);
//            if (file != null) {
//                try {
//                    WritableImage writableImage = new WritableImage(1450, 950);
//                    canvas.snapshot(null, writableImage);
//                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
//                    ImageIO.write(renderedImage, "png", file);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    System.out.println("Error!");
//                }
//            }
//        });
//
//        Button uploadBtn = new Button("Upload");
//        uploadBtn.setOnAction(e->{
//            FileChooser savefile = new FileChooser();
//            File file = savefile.showOpenDialog(primaryStage);
////            if(file.canRead()){
//            Image image = new Image(file.getAbsolutePath());
//            canvas.getGraphicsContext2D().drawImage(image,0,0,1450,1000);
////            canvas.getGraphicsContext2D().mage
//
////            else {
////                Alert fileCannotRead = new Alert(Alert.AlertType.ERROR);
////                fileCannotRead.setHeaderText("File Chosen");
////                fileCannotRead.setContentText("Unable to read File "+file.getName());
////                fileCannotRead.showAndWait();
////            }
//        });
//
//
//        hbox.getChildren().add(uploadBtn);
//        hbox.getChildren().add(saveBtn);
//
//
//
//        Group shapeDrawingGroup = new Group();
//        Label shapesText = new Label("Shapes");
//        ComboBox<String> shapesDropDown = new ComboBox<>();
//        shapeDrawingGroup.getChildren().add(new javafx.scene.control.Label("Shapes"));
//        shapeDrawingGroup.getChildren().add(shapesDropDown);
//        hbox.getChildren().add(shapeDrawingGroup);
//
//        shapesDropDown.getItems().add("None");
//        shapesDropDown.getItems().add("Circle");
//        shapesDropDown.getItems().add("Ovale");
//        shapesDropDown.getItems().add("Triangle");
//        shapesDropDown.getItems().add("Rectangle");
//        shapesDropDown.getItems().add("Square");
//        shapesDropDown.getSelectionModel().selectFirst();
//
//        AtomicInteger drawShapesCounter = new AtomicInteger();
//        shapesDropDown.setOnAction(e->{
//
//            if(shapesDropDown.getValue().equalsIgnoreCase("none")){
//                //        Screen normal drawing
//                pane.setOnMousePressed(a-> {
//                    pushUndo();
//                    gc.beginPath();
//                    gc.lineTo(a.getSceneX(), a.getSceneY());
//                    gc.stroke();
//                });
//
//                pane.setOnMouseClicked(z->{
//                    pushUndo();
//                    gc.beginPath();
//                    gc.lineTo(z.getSceneX(), z.getSceneY());
//                    gc.stroke();
//                });
//
//                pane.setOnMouseDragged(a->{
//                    gc.lineTo(a.getSceneX(), a.getSceneY());
//                    gc.stroke();
//                });
//            }
//            else if(shapesDropDown.getValue().equalsIgnoreCase("rectangle")){
//                drawRectangle(gc,pane);
//                pane.setOnMouseDragged(a->{
//                    pushUndo();
//                });
//                pane.setOnMousePressed(a-> {
//                    pushUndo();
//                });
//            }
//
//            else if(shapesDropDown.getValue().equalsIgnoreCase("square")){
//                drawSquare(gc,pane);
//                pane.setOnMouseDragged(a->{
//                    pushUndo();
//                });
//                pane.setOnMousePressed(a-> {
//                    pushUndo();
//                });
//            }
//            else if(shapesDropDown.getValue().equalsIgnoreCase("circle")){
//                drawSquare(gc,pane);
//                pane.setOnMouseDragged(a->{
//                    pushUndo();
//                });
//                pane.setOnMousePressed(a-> {
//                    pushUndo();
//                });
//            }
//        });
//
//        ToggleButton eraserButton = new ToggleButton("Eraser");
//        Eraser eraser = new Eraser();
//        ToggleGroup tglGroup = new ToggleGroup();
//        eraserButton.setToggleGroup(tglGroup);
//        eraserButton.setOnAction(e->{
//            if(eraserButton.isSelected()){
//                pane.setOnMousePressed(a->{
//                    eraser.onPress(a,gc);
//                });
//                pane.setOnMouseDragged(a->{
//                    eraser.onDrag(a,gc);
//                });
//                pane.setOnMouseReleased(a->{
//                    eraser.onRelease(a,gc);
//                });
//
//            }
//            else{
//                pane.setOnMousePressed(a-> {
//                    pushUndo();
//                    gc.beginPath();
//                    gc.lineTo(a.getSceneX(), a.getSceneY());
//                    gc.stroke();
//                });
//
//
//                pane.setOnMouseDragged(a->{
//                    gc.lineTo(a.getSceneX(), a.getSceneY());
//                    gc.stroke();
//                });
//
//            }
//        });
//
//
//        hbox.getChildren().add(eraserButton);
//
//        pane.getChildren().add(canvas);
//
//
//        root.getChildren().add(0,pane);
//        root.getChildren().add(1,hbox);

//        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    Canvas canvas = new Canvas(903,855);
//    GraphicsContext gc;
//
//
//    private void pushUndo() {
//        // Restore the canvas scale to 1 so I can get the original scale image
//        canvas.setScaleX(1);
//        canvas.setScaleY(1);
//
//        // Get the image with the snapshot method and store it on the undo stack
//        WritableImage snapshot = canvas.snapshot(null, null);
//        undoStack.push(snapshot);

//        // Set the canvas scale to the value it was before the method
//        canvas.setScaleX(canvasScale);
//        canvas.setScaleY(canvasScale);
//    }
//
//    private void undo() {
//        if (!undoStack.empty()) {
//            WritableImage undoImage = undoStack.pop();
//            canvas.getGraphicsContext2D().drawImage(undoImage, 0, 0);
//        }
//    }

    private void drawShapes(GraphicsContext gc,Pane pane) {
        gc.setFill(Color.GREEN);

//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(5);
//        gc.strokeLine(40, 10, 10, 40);
        pane.setOnMouseClicked(e->{
            gc.fillOval(e.getSceneX(), e.getSceneY(), 30, 30);
        });

//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                new double[]{210, 210, 240, 240}, 4);
    }

    private void drawRectangle(GraphicsContext gc,Pane pane) {
        Rectangle rect1 = new Rectangle(10, 10, 350, 200);

        pane.setOnMouseClicked(e->{
            gc.fillRoundRect(e.getSceneX(), e.getSceneY(), 60, 40,0,0);
        });

    }

    private void drawSquare(GraphicsContext gc,Pane pane) {
        Rectangle rect1 = new Rectangle(10, 10, 200, 200);

        pane.setOnMouseClicked(e->{
            gc.fillRoundRect(e.getSceneX(), e.getSceneY(), 60, 60,0,0);
        });

    }






    public static void main(String[] args) {
        launch(args);
    }
}
