/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
https://www.youtube.com/watch?v=5KyYAsYvLjs
https://www.youtube.com/watch?v=57zkzBE0g48 JavaFx Tutorial For 
      Beginners 4 - How to Use Lambda Expressions to Handle Events

*/
package javafx.tuts;

/**
 *
 * @author kpadhikari
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//kp: Looks like we have to always extend the abstract class Application
//    and implement the abstract method start() to define the primary stage of 
//    of the GUI with the 'scene' and various components and their layouts. 
// And, to start the program, one must call the launch(args) method from the main().
public class HelloWorldJavaFX2 extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception { //Basic JavaFX window
        Button btn = new Button("Click me");
        Button bExit = new Button("Exit");
        //Using anonymous class that has only one abstract method, in
        // handling the button action event. Below, we'll use Lambda expression
        // instead of this block.
        btn.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World.");
            }        
        });
        
        //Using Lambda expression (introduced since Java 8) to define the 
        // event handling
        // Lambda expression - if you have a single abstract method inside a
        //    class, you can use Lambda Expression to directly call it.

        //bExit.setOnAction(e -> System.out.println("Hello World, We're exiting"));
        //bExit.setOnAction(e -> System.exit(0));//For single statement in the abstract method
        bExit.setOnAction(e -> { //Lambda exp with multi-statements need them inside {}
            System.out.println("Hello World, We're exiting");
            System.exit(0);
        });
        
        
        //StackPane root = new StackPane(); //One kind of layout
        VBox root = new VBox(); //Another kind of layout
        //root.getChildren().add(btn); //Good for StackPane
        root.getChildren().addAll(btn, bExit); //Good for VBox()
        //Scene scene = new Scene(root);//Works but only a tiny window will show up
        Scene scene = new Scene(root,500,400);
        primaryStage.setScene(scene);
        primaryStage.show(); //kp: Is it like frame.setVisible(true) in Swing
    }
    
}
