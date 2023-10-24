package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	
	

	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindowView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 1050, 600);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMain(this);
			mainWindowController.setPrimaryStage(primaryStage);
			primaryStage.setMinWidth(100.0);
			primaryStage.setMinHeight(650.0);
			primaryStage.setResizable(true);
            primaryStage.setTitle("     Interface: HomeWork No 3");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		mainWindow();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
