package meiko.application.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import meiko.application.MainWarn;

public class MainController {

	private MainWarn mainWarn;

	public void setMainController(MainWarn mainWarn) {
		// TODO Auto-generated constructor stub
		this.mainWarn = mainWarn;
	}

	@FXML
	private void handleClose() {
		mainWarn.getPrimaryStage().close();
		System.exit(0);
	}

	@FXML
	private void initialize() {

		Text text = new Text();

		text.setFont(new Font(20));
		text.setWrappingWidth(200);
		text.setTextAlignment(TextAlignment.JUSTIFY);
		text.setText("The quick brown fox jumps over the lazy dog");
	}
}
