package com.toedter.e4.ui.controls.javafx.skin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.sun.javafx.scene.control.skin.TabPaneSkin;

public class CTabPaneSkin extends TabPaneSkin {

	Group minMaxGroup = new Group();
	private final Button maxButton;
	private final Button minButton;

	public CTabPaneSkin(TabPane tabPane) {
		super(tabPane);

		Image maxImage = new Image(getClass().getResourceAsStream("max.png"));
		maxButton = new Button("", new ImageView(maxImage));
		maxButton.setVisible(false); // will be enabled by MinMax addon
		maxButton
				.setStyle("-fx-background-color: transparent; -fx-padding: 4;");

		maxButton.translateXProperty().bind(widthProperty().subtract(24.0));
		maxButton.setLayoutY(6.0);

		Image minImage = new Image(getClass().getResourceAsStream("min.png"));
		minButton = new Button("", new ImageView(minImage));
		minButton.setVisible(false); // will be enabled by MinMax addon
		minButton
				.setStyle("-fx-background-color: transparent; -fx-padding: 4;");

		minButton.translateXProperty().bind(widthProperty().subtract(44.0));
		minButton.setLayoutY(6.0);

		minMaxGroup.getChildren().add(minButton);
		minMaxGroup.getChildren().add(maxButton);

		getChildren().add(minMaxGroup);
	}

	public void setMaximizedHandler(final Runnable handler) {
		maxButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handler.run();
			}
		});
	}

	public void setMinimizedHandler(final Runnable handler) {
		minButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handler.run();
			}
		});
	}

	public void setMinimizeVisible(boolean isMinimizeVisible) {
		minButton.setVisible(isMinimizeVisible);
	}

	public void setMaximizeVisible(boolean isMaximizeVisible) {
		maxButton.setVisible(isMaximizeVisible);
	}

	public boolean isMinimizeVisible() {
		return minButton.isVisible();
	}

	public boolean isMaximizeVisible() {
		return maxButton.isVisible();
	}
}
