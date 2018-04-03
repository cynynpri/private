package exrely;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ExrelyGUI extends Application {
	//他クラスから参照されると不都合があるので、private化. ついでにfinal化.
	private static int debuglevel = 0;
	private final String title = "Extend_Result_Analyzer";
	private final String version_String = ".0.0.1.jar";
	private Button screen_shot_btn;

	public void start(Stage stage) {
		stage.setTitle(title + version_String);//->line 30 to 31.
		GridPane gpane = new GridPane();// -> Main Controller Pane.
		//Settings write here.
		screen_shot_btn = new Button("スクショを撮る");
		setButtonEvent(screen_shot_btn);
		gpane.add(screen_shot_btn, 0, 0);
		Scene root_scene = new Scene(gpane);
		stage.setWidth(400);
		stage.setHeight(400);
		stage.setScene(root_scene);
		stage.show();
	}

	private final void setButtonEvent(Button button) {
		//継承させないためprivate final.
		button.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					GraphicsDevice[] all_gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
					for(GraphicsDevice gd : all_gd){
						GraphicsConfiguration[] all_gc = gd.getConfigurations();
						Robot robot = new Robot(gd);
						for(GraphicsConfiguration gc : all_gc){
							BufferedImage image = robot.createScreenCapture(gc.getBounds());
							if(debuglevel >= 1){
								if(image != null){
									System.out.println("SS is getted.");
								}
							}
							//settings.
						}
					}
				}catch(Exception e){
					if(debuglevel >= 1){
						e.printStackTrace();
					}
					System.out.println(e);
					System.out.println("Exception occured.");
				}
			}
		});
	}

	public static void main(String[] args) {
		int argc = args.length;
		if(argc != 0){
			//debug settings.
			try{
				debuglevel = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				System.out.println("args[0] isn't number.");
				System.out.println("args[0] is "+args[0]);
			}
		}
		launch(args);
	}
}
