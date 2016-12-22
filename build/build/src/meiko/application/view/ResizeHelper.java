package meiko.application.view;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeHelper {

	public static void addResizeListener(Stage stage) {
		ResizeListener resizeListener = new ResizeListener(stage);
		stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
		stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
		stage.getScene().addEventHandler(MouseEvent.MOUSE_RELEASED, resizeListener);
		ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
		for (Node child : children) {
			addListenerDeeply(child, resizeListener);
		}
	}

	public static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, listener);
		if (node instanceof Parent) {
			Parent parent = (Parent) node;
			ObservableList<Node> children = parent.getChildrenUnmodifiable();
			for (Node child : children) {
				addListenerDeeply(child, listener);
			}
		}
	}

	static class ResizeListener implements EventHandler<MouseEvent> {
		private Stage stage;

		private double xOffset = 0;
		private double yOffset = 0;

		public ResizeListener(Stage stage) {
			this.stage = stage;
		}

		@Override
		public void handle(MouseEvent mouseEvent) {
			EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();

			if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType) == true) {
				// drag window setting
				xOffset = stage.getX() - mouseEvent.getScreenX();
				yOffset = stage.getY() - mouseEvent.getScreenY();
			} else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) == true) {
					stage.setX(mouseEvent.getScreenX() + xOffset);
					stage.setY(mouseEvent.getScreenY() + yOffset);
			} else if (MouseEvent.MOUSE_RELEASED.equals(mouseEventType)) {
				if (stage.getY() < 0) {
					stage.setY(0);
				}
			}
		}

	}
}
