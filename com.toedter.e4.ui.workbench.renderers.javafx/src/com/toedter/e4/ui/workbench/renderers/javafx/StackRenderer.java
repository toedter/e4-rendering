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

package com.toedter.e4.ui.workbench.renderers.javafx;

import java.net.URL;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.emf.common.util.URI;

import com.toedter.e4.ui.controls.javafx.CTabPane;
import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class StackRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		CTabPane tabPane = new CTabPane();
		element.setWidget(tabPane);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		System.out.println("StackRenderer.processContents()");
		TabPane parentPane = (TabPane) container.getWidget();

		for (MUIElement element : container.getChildren()) {
			final MUIElement theElement = element;
			MUILabel mLabel = (MUILabel) element;
			final Tab tab = new Tab(mLabel.getLocalizedLabel());
			tab.setContent((Node) theElement.getWidget());
			tab.setClosable(false); // TODO We need to read this from tags
			if (mLabel.getIconURI() != null) {
				URL url = Util.convertToOSGiURL(URI.createURI(mLabel.getIconURI()));
				Image img = new Image(url.toExternalForm());
				tab.setGraphic(new ImageView(img));
			}

			tab.setOnSelectionChanged(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					tab.setContent((Node) theElement.getWidget());
				}
			});
			parentPane.getTabs().add(tab);
		}
	}

	@Override
	public void setVisible(MUIElement changedElement, boolean visible) {
		System.out.println("StackRenderer.setVisible(): " + visible);
		TabPane tabPane = (TabPane) changedElement.getWidget();
		tabPane.setVisible(visible);
	}
}
