package meiko.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import meiko.application.view.MainController;
import meiko.application.view.ResizeHelper;


public class MainWarn extends Application {

	private AnchorPane mainLayout;
	private Stage primaryStage;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("±„«©");

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainWarn.class.getResource("view/MainLayout.fxml"));
			mainLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(mainLayout);
//			scene.setFill(Color.TRANSPARENT);

			// The controller
			MainController mainController = loader.getController();
			mainController.setMainController(this);

			this.primaryStage.setScene(scene);
			ResizeHelper.addResizeListener(this.primaryStage);

			this.primaryStage.setAlwaysOnTop(true);
			this.primaryStage.initStyle(StageStyle.TRANSPARENT);

//			this.primaryStage.getIcons().add(new Image(MainLable.class.getResourceAsStream("usagilogo.png")));
			this.primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
