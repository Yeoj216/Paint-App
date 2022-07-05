package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
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
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.image.Image;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.stage.Window;

import static com.sun.javafx.util.Utils.clamp;

public class Controller {

    private static final double MIN_SCALE = .1d;
    private static final double MAX_SCALE = 10.0d;
    @FXML
    public Button chooseColorBtn;
    @FXML
    public Slider red;
    @FXML
    public Slider green;
    @FXML
    public Slider blue;
    @FXML
    public Slider opacity;

    @FXML
    public ToggleButton eraser;

    @FXML
    public ComboBox<String> thickness;

    @FXML
    private Canvas canvas;
    @FXML
    public ComboBox shapesDropDown;
    @FXML
    public Button uploadBtn;
    @FXML
    public Button saveBtn;
    @FXML
    public Button undoBtn;
    @FXML
    public Button clearBtn;
    @FXML
    public Button fill;
    public Button RotateRBtn;
    public Button RotateLBtn;
    public Pane colorViewer;





    public Stack<WritableImage> undoStack;
    private Stage primaryStage;

    public void initialize(){

        undoStack = new Stack<WritableImage>();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);

//        Thickness ComboBox items
        thickness.getItems().add("1.0");
        thickness.getItems().add("5.0");
        thickness.getItems().add("10.0");
        thickness.getSelectionModel().select(0);

//        Eraser
        Eraser erase = new Eraser();

//        Shapes DropDown Initiation
        shapesDropDown.getItems().add("Select shape");
        shapesDropDown.getItems().add("Rectangle");
        shapesDropDown.getItems().add("Square");
        shapesDropDown.getItems().add("Triangle");
        shapesDropDown.getItems().add("Circle");
        shapesDropDown.getSelectionModel().selectFirst();

        //        Screen normal drawing
        canvas.setOnMousePressed(a-> {
            double size = Double.parseDouble(thickness.getValue());
            double x = a.getX() - size/2;
            double y = a.getY() - size/2;

            if(eraser.isSelected()){
                erase.onPress(a,gc);
            }

            pushUndo();
            gc.beginPath();
            gc.lineTo(a.getX(), a.getY());
            gc.setLineWidth(size);
            gc.stroke();
        });


        canvas.setOnMouseDragged(a->{
            double size = Double.parseDouble(thickness.getValue());
            double x = a.getX() - size/2;
            double y = a.getY() - size/2;

            if(eraser.isSelected()){
                erase.onDrag(a,gc);
//                gc.clearRect(x,y,size,size);
            }
            else {
//                pushUndo();
                gc.lineTo(x,y);
                gc.setLineWidth(size);
                gc.stroke();
            }

        });

        canvas.setOnMouseReleased(a->{
            erase.onRelease(a,gc);
        });


        // trying to add zoom option
       /*primaryStage.addEventHandler(ScrollEvent.SCROLL, event->{
           double x = canvas.getScaleX();
           double y = canvas.getScaleY();
           if (x <= 0.8 && y <= 0.8) { x = 0.8; y = 0.8; }
           if (x >= 2 && y >= 2) { x = 2; y = 2; }
           canvas.setScaleX(x + event.getDeltaY() / 800);
           canvas.setScaleY(y + event.getDeltaY() / 800); });
*/
    }



    public void onUndo(){
        undo();
    }

    private void pushUndo() {

        // Get the image with the snapshot method and store it on the undo stack
        Image snapshot = canvas.snapshot(null, null);
        undoStack.push((WritableImage) snapshot);
    }

    private void undo() {
        if (!undoStack.empty()) {
            Image undoImage = undoStack.pop();
            canvas.getGraphicsContext2D().drawImage(undoImage, 0, 0);
        }
    }
    public void determineColor(){
        DoubleProperty Rproperty = red.valueProperty();
        DoubleProperty Gproperty = green.valueProperty();
        DoubleProperty Bproperty = blue.valueProperty();
        Integer intR = Rproperty.intValue();
        Integer intG = Gproperty.intValue();
        Integer intB = Bproperty.intValue();
        //gc.setStroke(Color.rgb(intR,intG,intB));
    }
    public void ResetColor(){

    }

    public void onSave(){

        try{
            Image snapshot = canvas.snapshot(null,null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png",new File("paint.png"));
        }catch (Exception e){
            System.out.println("Failed to save file "+e);
        }

    }
    // aam zid ha bas la jareb
    public void setStage(Stage primaryStage){
        this.primaryStage=primaryStage;
    }
    public void onUpload(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
            FileChooser openFile = new FileChooser();
            openFile.setTitle("Open File");

        File file = openFile.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    gc.drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        };


    public void onExit(){
        Platform.exit();
    }

    public void onClear(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,2000,2000);
    }

    public void chooseColor(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DoubleProperty Rproperty = red.valueProperty();
        DoubleProperty Gproperty = green.valueProperty();
        DoubleProperty Bproperty = blue.valueProperty();
        Integer intR = Rproperty.intValue();
        Integer intG = Gproperty.intValue();
        Integer intB = Bproperty.intValue();
        gc.setStroke(Color.rgb(intR,intG,intB));
        DoubleProperty Oproperty = opacity.valueProperty();
        gc.setGlobalAlpha(Oproperty.doubleValue());
        colorViewer.setBackground(new Background(new BackgroundFill(Color.rgb(intR,intG,intB), CornerRadii.EMPTY, Insets.EMPTY)));

    }

    public void resetColor(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        red.adjustValue(0);
        green.adjustValue(0);
        blue.adjustValue(0);
        DoubleProperty Rproperty = red.valueProperty();
        DoubleProperty Gproperty = green.valueProperty();
        DoubleProperty Bproperty = blue.valueProperty();
        Integer intR = Rproperty.intValue();
        Integer intG = Gproperty.intValue();
        Integer intB = Bproperty.intValue();
        opacity.adjustValue(1.0);
        DoubleProperty Oproperty = opacity.valueProperty();
        gc.setGlobalAlpha(Oproperty.doubleValue());


    }
    public void rotateRight(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.rotate(90);
    }
    public void rotateLeft(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.rotate(-90);

    }
    public void paintCan(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DoubleProperty Rproperty = red.valueProperty();
        DoubleProperty Gproperty = green.valueProperty();
        DoubleProperty Bproperty = blue.valueProperty();
        Integer intR = Rproperty.intValue();
        Integer intG = Gproperty.intValue();
        Integer intB = Bproperty.intValue();
        gc.setFill(Color.rgb(intR,intG,intB));

    }
    public void setOpacity(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DoubleProperty Oproperty = opacity.valueProperty();
        gc.setGlobalAlpha(Oproperty.doubleValue());

    }


    public void onAddText(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        TextInputDialog td = new TextInputDialog();
        td.show();
        td.setTitle("Text Input ");
        td.setHeaderText("Enter text: ");
        td.setContentText("Text");
        TextField text  = td.getEditor();
        canvas.setOnMousePressed(a->{
            gc.fillText(text.getText(), a.getX(), a.getY());
            pushUndo();
            canvas.setOnMouseReleased(z->{
                canvas.setOnMousePressed(p-> {
                    pushUndo();
                    gc.beginPath();
                    gc.lineTo(p.getX(), p.getY());
                    gc.stroke();
                });

                canvas.setOnMouseDragged(p->{
                    gc.lineTo(p.getX(), p.getY());
                    gc.stroke();
                });

            });
        });
//            System.out.println(text.getText());
        gc.strokeText(text.getText(), 200, 200);


    }

    public void drawShape(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(shapesDropDown.getValue().equals("Select Shape")){
            //        Screen normal drawing
            canvas.setOnMousePressed(a-> {
                pushUndo();
                gc.beginPath();
                gc.lineTo(a.getX(), a.getY());
                gc.stroke();
            });

            canvas.setOnMouseClicked(z->{
                pushUndo();
                gc.beginPath();
                gc.lineTo(z.getX(), z.getY());
                gc.stroke();
            });

            canvas.setOnMouseDragged(a->{
                gc.lineTo(a.getX(), a.getY());
                gc.stroke();
            });
        }
        else if(shapesDropDown.getValue().equals("Rectangle")){
            drawRectangle(gc);
            canvas.setOnMouseDragged(a->{
                pushUndo();
            });
            canvas.setOnMousePressed(a-> {
                pushUndo();
            });
        }

        else if(shapesDropDown.getValue().equals("Square")){
            drawSquare(gc);
            canvas.setOnMouseDragged(a->{
//                pushUndo();
            });
            canvas.setOnMousePressed(a-> {
//                pushUndo();
            });
        }
        else if(shapesDropDown.getValue().equals("circle")){
            drawSquare(gc);
            canvas.setOnMouseDragged(a->{
//                pushUndo();
            });
            canvas.setOnMousePressed(a-> {
//                pushUndo();
            });
        }

    }


    private void drawRectangle(GraphicsContext gc) {
        Rectangle rect1 = new Rectangle(10, 10, 350, 200);

        canvas.setOnMouseClicked(e->{
            gc.fillRoundRect(e.getX(), e.getY(), 60, 40,0,0);
        });

    }

    private void drawSquare(GraphicsContext gc) {
        Rectangle rect1 = new Rectangle(10, 10, 200, 200);

        canvas.setOnMouseClicked(e->{
            gc.fillRoundRect(e.getX(), e.getY(), 60, 60,0,0);
        });

    }





//    public void SaveImg(){
//        FileChooser savefile = new FileChooser();
//        savefile.setTitle("Save File");
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files","*.JPG");
//        savefile.getExtensionFilters().add(extFilter);
//
//        File file = savefile.showSaveDialog(primaryStage);
//        System.out.println("is file null ? "+ file);
//        if (file != null) {
//            try {
//                WritableImage writableImage = new WritableImage(1450, 950);
//                canvas.snapshot(null, writableImage);
//                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
//                ImageIO.write(renderedImage, "png", file);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                System.out.println("Error!");
//            }
//        }
//    }







}


