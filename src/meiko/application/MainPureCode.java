package meiko.application;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import meiko.application.view.ResizeHelper;

public class MainPureCode extends Application {
	private List<Text> textList = new ArrayList<Text>();
	private List<Rectangle> retangleList = new ArrayList<Rectangle>();
	private String[] settingContent;

	@Override
	public void start(Stage primaryStage) throws Exception {

		AnchorPane gp = new AnchorPane();

		// getDate("2016-12-26 23:59:59");

		settingContent = readFile();
		String inputContent = settingContent[0] + getDate(settingContent[1] + " 23:59:59") + "天";

		getList(inputContent);

		gp.getChildren().addAll(retangleList);
		gp.getChildren().addAll(textList);

		Button button = setCloseBtn();
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.close();
				System.exit(0);
			}
		});
		gp.getChildren().add(button);

		gp.setId("anchorPane-id");
		Scene scene = new Scene(gp, 1800, 300);
		scene.getStylesheets().add(MainPureCode.class.getResource("view/application.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(settingContent[3].contains("true"));
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setIconified(true);
		ResizeHelper.addResizeListener(primaryStage);

		primaryStage.show();
	}

	private String[] readFile() {
		String result[] = new String[4];
		BufferedReader bReader;
		try {
			bReader = new BufferedReader(
					new UnicodeReader(new FileInputStream("settings.txt"), Charset.defaultCharset().name()));
			String line;
			int i = 0;
			while (i < 4 && (line = bReader.readLine()) != null) {
				result[i++] = line;
				System.out.println(result[i - 1]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private int flagHeight, flagY;

	private void getList(String inputContent) {
		// TODO Auto-generated method stub
		int length = inputContent.length();
		double recodeTextWidth = 0;
		for (int i = 0; i < length; i++) {
			flagHeight = (int) (Double.parseDouble((settingContent[2])) * 0.2);
			flagY = (int) (Double.parseDouble((settingContent[2])) * 0.2);
			String s = inputContent.substring(i, i + 1);
			Text text = setContentText(s, 30 + recodeTextWidth, 100);
			recodeTextWidth += text.prefWidth(0);
			textList.add(text);
			Rectangle rect = setContentRetangle(text);
			retangleList.add(rect);
		}
	}

	private Button setCloseBtn() {
		Button button = new Button();
		button.setLayoutY(100);
		button.setPrefWidth(32);
		button.setPrefHeight(32);
		button.setLayoutX(0);
		button.setId("button-decorate");

		return button;
	}

	private Text setContentText(String inputText, double x, double y) {
		int fontSize = new Random().nextInt(31) + (int) Double.parseDouble((settingContent[2]));
		int rotateNum = new Random().nextInt(31) - 20;
		String dontFamily = "叶根友唐楷飞墨";

		Text text = new Text();
		text.setLayoutX(x);
		text.setFill(Color.WHITE);
		text.setLayoutY(y);
		text.setText(inputText);
		text.setRotate(rotateNum);

		Pattern pattern = Pattern.compile("[0-9]*");
		if (pattern.matcher(inputText).matches()) {
			fontSize = new Random().nextInt(11) + (int) Double.parseDouble((settingContent[2])) + 90;
			dontFamily = "Jokerman";
			text.setFill(Color.RED);
			text.setLayoutY(y + fontSize - 90);
			flagY = flagY + 60;
			flagHeight = (int) (Double.parseDouble((settingContent[2])) * 0.2) - 80;
		}
		text.setFont(Font.font(dontFamily, fontSize));

		return text;
	}

	private Rectangle setContentRetangle(Text text) {
		double height = text.prefHeight(0);
		double width = text.prefWidth(0);
		double startX = text.getLayoutX();
		double startY = text.getLayoutY();
		double rotate = text.getRotate();

		Rectangle rect = new Rectangle();
		rect.setHeight(height + (int) Double.parseDouble((settingContent[2])) * 0.1 + flagHeight);
		rect.setWidth(width + (int) Double.parseDouble((settingContent[2])) * 0.4);
		rect.setFill(Color.BLACK);
		rect.setLayoutX(startX - 10);
		rect.setLayoutY(startY - height + flagY);
		rect.setRotate(rotate);

		return rect;
	}

	private static String getDate(String inputDate) {
		String resultDate = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date_early = format.parse(format.format(new Date()));
			Date date_late = format.parse(inputDate);

			if (date_early.after(date_late)) {
				System.out.println("error!");
				return "";
			}

			resultDate = Integer.toString(differentDaysByMillisecond(date_early, date_late));
			System.out.println("两个日期的差距：" + differentDaysByMillisecond(date_early, date_late));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date_early, Date date_late) {
		int days = (int) ((date_late.getTime() - date_early.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	public static void main(String[] args) {
		launch(args);
	}

}