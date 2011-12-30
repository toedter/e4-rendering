/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

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
	private final Button restoreButton;
	private final ImageView maxImageView;

	private final ImageView restoreImageView;

	public CTabPaneSkin(TabPane tabPane) {
		super(tabPane);

		Image maxImage = new Image(getClass().getResourceAsStream("max.png"));
		maxImageView = new ImageView(maxImage);

		maxButton = new Button("", maxImageView);
		maxButton.setVisible(false); // will be enabled by MinMax addon
		maxButton.setStyle("-fx-background-color: transparent; -fx-padding: 4;");

		maxButton.translateXProperty().bind(widthProperty().subtract(24.0));
		maxButton.setLayoutY(6.0);

		Image restoreImage = new Image(getClass().getResourceAsStream("restore.png"));
		restoreImageView = new ImageView(restoreImage);

		restoreButton = new Button("", restoreImageView);
		restoreButton.setVisible(false); // will be enabled by MinMax addon
		restoreButton.setStyle("-fx-background-color: transparent; -fx-padding: 4;");

		restoreButton.translateXProperty().bind(widthProperty().subtract(24.0));
		restoreButton.setLayoutY(6.0);

		Image minImage = new Image(getClass().getResourceAsStream("min.png"));
		minButton = new Button("", new ImageView(minImage));
		minButton.setVisible(false); // will be enabled by MinMax addon
		minButton.setStyle("-fx-background-color: transparent; -fx-padding: 4;");

		minButton.translateXProperty().bind(widthProperty().subtract(44.0));
		minButton.setLayoutY(6.0);

		minMaxGroup.getChildren().add(minButton);
		minMaxGroup.getChildren().add(maxButton);
		minMaxGroup.getChildren().add(restoreButton);

		getChildren().add(minMaxGroup);
	}

	public void setMaximizeHandler(final Runnable handler) {
		System.out.println("CTabPaneSkin.setMaximizeHandler()");
		maxButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("CTabPaneSkin.setMaximizeHandler(...).new EventHandler() {...}.handle()");
				handler.run();
				setMaximizeVisible(false);
				setRestoreVisible(true);
			}
		});
	}

	public void setMinimizeHandler(final Runnable handler) {
		minButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handler.run();
			}
		});
	}

	public void setRestoreHandler(final Runnable handler) {
		restoreButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handler.run();
				setMaximizeVisible(true);
				setRestoreVisible(false);
			}
		});
	}

	public void setMinimizeVisible(boolean isMinimizeVisible) {
		minButton.setVisible(isMinimizeVisible);
	}

	public void setMaximizeVisible(boolean isMaximizeVisible) {
		maxButton.setVisible(isMaximizeVisible);
	}

	public void setRestoreVisible(boolean isRestoreVisible) {
		restoreButton.setVisible(isRestoreVisible);
	}

	public boolean isMinimizeVisible() {
		return minButton.isVisible();
	}

	public boolean isMaximizeVisible() {
		return maxButton.isVisible();
	}

}
