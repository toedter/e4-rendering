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

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;

import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.emf.common.util.URI;

@SuppressWarnings("restriction")
public class ToolItemRenderer extends ItemRenderer {
	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
			Button button = new Button();

			MToolItem item = (MToolItem) element;
			button.setText(item.getLabel());
			if (item.getIconURI() != null) {
				URL url = Util.convertToOSGiURL(URI.createURI(item.getIconURI()));
				Image img = new Image(url.toExternalForm());
				button.setGraphic(new ImageView(img));
			}
			if (item.getTooltip() != null) {
				button.setTooltip(new Tooltip(item.getLocalizedTooltip()));
			}
			element.setWidget(button);
		}
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		if (me instanceof MDirectToolItem) {
			final MDirectToolItem item = (MDirectToolItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			Button button = (Button) item.getWidget();
			button.setOnAction(createEventHandler(item));
		} else if (me instanceof MHandledToolItem) {
			final MHandledItem item = (MHandledToolItem) me;

			Button button = (Button) item.getWidget();
			button.setOnAction(createParametrizedCommandEventHandler(item));
		}
	}
}
