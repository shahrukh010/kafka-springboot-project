package application.view;

import java.io.IOException;

import application.EmailManager;
import application.controller.BaseController;
import application.controller.ComposeWindowController;
import application.controller.LoginWindowController;
import application.controller.MainWindowControlle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {

	
	
	private EmailManager emailManger;
	public boolean isMainWindowInitialize=false;

	public ViewFactory(EmailManager emailManager) {
		this.emailManger = emailManager;
	}
	
	public void loginWindow() {
		
		BaseController controller = new LoginWindowController(emailManger,this,"LoginWindow.fxml");
		initializeStage(controller);
		
	}
	
	public void mainWindow() {
		
		BaseController controller = new MainWindowControlle(emailManger, this, "MainWindow.fxml");
		initializeStage(controller);
		isMainWindowInitialize=true;
	}
	
	public void composeWindow() {
		
		BaseController controller = new ComposeWindowController(emailManger,this,"ComposeWindow.fxml");
		initializeStage(controller);
	}
	private void initializeStage(BaseController controller) {
		
		FXMLLoader fxlFxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxml()));
		fxlFxmlLoader.setController(controller);
		Parent parent;
		try {
			 parent = fxlFxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	

	public void closeStage(Stage stage) {
		
		stage.close();
	}
}
