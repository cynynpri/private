package samples;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.concurrent.*;
import javafx.application.Platform;

public class ServiceTest extends Application{
	public void start(Stage stage){
		stage.setWidth(200);
		stage.setHeight(200);
		Button btn = new Button("ƒ{ƒ^ƒ“");
		Label lbl = new Label();
		String str = new String();
		Task<Boolean> task = new Task<Boolean>(){
			@Override
			protected Boolean call() throws Exception{
				Platform.runLater(() -> lbl.setText("Hello!"));
				return true;
			}
		};
		Thread t = new Thread(task);
		t.setDaemon(true);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				t.start();
			}
		});
		BorderPane bPane = new BorderPane();
		bPane.setCenter(btn);
		bPane.setBottom(lbl);
		Scene scene = new Scene(bPane);
		stage.setScene(scene);
		stage.show();

	}
	public static void main(String[] args){
		launch(args);
	}
}
