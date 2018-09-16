package cynynpri.java.sif.combose.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private final String APPLICATION_TITLE = "SIF_ComboSE.java";
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
			BorderPane root = (BorderPane)loader.load();

			GUIControler controller = (GUIControler) loader.getController();
			controller.callimshow(primaryStage);
			controller.initializeComponents();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle(APPLICATION_TITLE);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
