package cs112.ud2;

import javafx.application.Application;  //abstract class used for JavaFX GUI's
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.stage.Stage;              //class for GUI window
import javafx.scene.Scene;              //class for specific view in GUI window
import javafx.scene.layout.StackPane;   //class for layout pane, organized back to front
import javafx.scene.control.Label;      //class for label component
import javafx.scene.control.Button;     //class for button component
import javafx.event.EventHandler;       //interface for handling events
import javafx.event.ActionEvent;        //class for type of event for action (like button or key pressed)
import javafx.geometry.Pos;             //class for getting positions for alignment
import javafx.scene.control.Tooltip;    //class for tooltip attribute for button
import javafx.scene.text.Font;          //class for creating Font objects
import javafx.scene.text.FontPosture;   //class for font italic or non-italic
import javafx.scene.text.FontWeight;    //class for font bold or non-bold
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClickerApplication extends Application { //inheriting core functionality
    /*** GUI COMPONENTS ***/
    private Button statisticsButton; //renamed button
    private Button upgradesButton;
    private Button upgrade1Button;
    private Button pointsButton;
    private Label messageLabel; //moved declaration out of start(), not a local variable now but class-level variable. so scope is all methods here
    private Label pointsLabel;
    private Rectangle clickerWindow;
    private Rectangle workforceWindow;
    private Rectangle statisticsWindow;
    private Rectangle pointsBackground;
    private Rectangle upgradesBackground;
    private Image computer;
    private static final int HEIGHT = 600;
    private static final int LENGTH = 500;
    private int numClicks = 0;
    private int numPoints = 0;

    /*** DRIVER main ***/
    public static void main(String[] args) {
        launch(args); //method from Application class, must be called to setup javafx application
    }

    /*** OVERRIDDEN Application METHODS ***/
    @Override
    public void start(Stage primaryStage) throws Exception{ //Application automatically calls this method to run (our) main javafx code. passes in primary stage (main window)
        //SETUP COMPONENTS

        messageLabel = new Label("");
        StackPane.setAlignment(messageLabel, Pos.TOP_LEFT);
        StackPane.setMargin(messageLabel, new Insets(35,0,0,(int)(2*LENGTH/5-10)));
        messageLabel.setVisible(false);

        pointsLabel = new Label("Points: 0\nPassive Points Per second: 0");
        pointsLabel.setStyle("-fx-font-weight: bold;");
        StackPane.setAlignment(pointsLabel, Pos.TOP_LEFT);
        StackPane.setMargin(pointsLabel, new Insets(10,0,0,20));

        FileInputStream input = null;
        try {
            input = new FileInputStream("./src/Images/computer.jpg");
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.err.println("ERROR: could not open file.");
            System.exit(0);
        }
        computer = new Image(input);
        ImageView computerView = new ImageView(computer);
        computerView.setFitWidth((int)(2*LENGTH/5 - 60));
        computerView.setPreserveRatio(true);


        //"windows"
        pointsBackground = new Rectangle(0, 0, 160, 45);
        pointsBackground.setFill(Color.WHITE);
        StackPane.setAlignment(pointsBackground, Pos.TOP_LEFT);
        StackPane.setMargin(pointsBackground, new Insets(12,0,0,20));

        upgradesBackground = new Rectangle(0, 0, 200, HEIGHT-45);
        upgradesBackground.setFill(Color.WHITE);
        StackPane.setAlignment(upgradesBackground, Pos.TOP_RIGHT);
        StackPane.setMargin(upgradesBackground, new Insets(35,0,0,10));
        upgradesBackground.setVisible(false);

        clickerWindow = new Rectangle(0, 0, (int)(2*LENGTH/5 - 20), HEIGHT-20);
        clickerWindow.setFill(Color.BLUE);
        StackPane.setAlignment(clickerWindow, Pos.TOP_LEFT);
        StackPane.setMargin(clickerWindow, new Insets(10,0,0,10));

        workforceWindow = new Rectangle(0, 0, (int)(3*LENGTH/5), HEIGHT-45);
        workforceWindow.setFill(Color.ORANGE);
        StackPane.setAlignment(workforceWindow, Pos.TOP_LEFT);
        StackPane.setMargin(workforceWindow, new Insets(35,0,0,(int)(2*LENGTH/5-10)));

        statisticsWindow = new Rectangle(0, 0, 200, 20);
        statisticsWindow.setFill(Color.WHITE);
        StackPane.setAlignment(statisticsWindow, Pos.TOP_LEFT);
        StackPane.setMargin(statisticsWindow, new Insets(35,0,0,(int)(2*LENGTH/5-10)));
        statisticsWindow.setVisible(false);
        //

        //Buttons
        statisticsButton = new Button("Statistics"); //or can set text using setText method separately
        StackPane.setAlignment(statisticsButton, Pos.TOP_LEFT);
        StackPane.setMargin(statisticsButton, new Insets(10,0,0,(int)(2*LENGTH/5-10)));
        statisticsButton.setPrefWidth(70);
        Node[] statisticZone = {
                messageLabel,
                statisticsWindow
        };
        /*** NEW ***/ //who the event handler is (which object/class should handle the event) == anonymous class
        statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                messageLabel.setText("Clicks: " + numClicks); //changed message label instead of console output
                messageLabel.setFont(Font.font("Courier New", FontWeight.LIGHT, FontPosture.REGULAR, 20)); //setting font attribute to message label
                //messageLabel.setVisible(!messageLabel.isVisible());
                //statisticsWindow.setVisible(!statisticsWindow.isVisible());
                for (Node i: statisticZone) {
                    i.setVisible(!i.isVisible());
                }
            }
        }); /*** NEW ***/ //end of anonymous class
        statisticsButton.setTooltip(new Tooltip("Click this button to change the text above it!"));/*** NEW ***/ //set tooltip attribute, text shows when hovering over button

        pointsButton = new Button();
        pointsButton.setGraphic(computerView);
        StackPane.setAlignment(pointsButton, Pos.CENTER_LEFT);
        StackPane.setMargin(pointsButton, new Insets(0,0,0,22));
        pointsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                numClicks+=1;
                messageLabel.setText("Clicks: " + numClicks);
                numPoints+=2;
                pointsLabel.setText("Points: "+numPoints+"\nPassive Points Per second: 0");
            }
        });

        upgrade1Button = new Button("Upgrade 1");
        StackPane.setAlignment(upgrade1Button, Pos.TOP_RIGHT);
        StackPane.setMargin(upgrade1Button, new Insets(50,10,0,0));
        upgrade1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Upgrade Purchased");
            }
        });
        upgrade1Button.setVisible(false);

        upgradesButton = new Button("Upgrades &\nBuildings");
        StackPane.setAlignment(upgradesButton, Pos.TOP_RIGHT);
        StackPane.setMargin(upgradesButton, new Insets(0,9,0,0));
        Node[] buildingsZone = {
                //all nodes this should show and hide
                upgrade1Button,
                upgradesBackground
        };
        upgradesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Node i: buildingsZone) {
                    i.setVisible(!i.isVisible());
                }
            }
        });
        //



        //ADD COMPONENTS
        StackPane layout = new StackPane(); //simple layout, components are stacked on top of each other in added order

        layout.getChildren().add(clickerWindow);
        layout.getChildren().add(workforceWindow);
        layout.getChildren().add(statisticsWindow);
        layout.getChildren().add(statisticsButton);
        layout.getChildren().add(pointsBackground);
        layout.getChildren().add(upgradesBackground);
        layout.getChildren().add(pointsButton);
        layout.getChildren().add(upgradesButton);
        layout.getChildren().add(upgrade1Button);
        layout.getChildren().add(messageLabel);
        layout.getChildren().add(pointsLabel);

        //layout.setAlignment(Pos.CENTER); //modify VBox attributes

        //SETUP SCENE AND SHOW
        Scene primaryScene = new Scene(layout, LENGTH, HEIGHT); //layout, dimensions of window
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Click Computer"); //setting title of main window
        primaryStage.setResizable(false); //set resizable attribute to window, fixed now
        primaryStage.show();
    }
}