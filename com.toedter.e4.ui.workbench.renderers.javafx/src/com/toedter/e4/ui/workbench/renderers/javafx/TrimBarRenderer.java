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

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class TrimBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MTrimBar)) {
			return;
		}
		final MTrimBar trimBar = (MTrimBar) element;

		ToolBar toolBar = new ToolBar();

		switch (trimBar.getSide().getValue()) {
		case SideValue.TOP_VALUE:
		case SideValue.BOTTOM_VALUE:
			toolBar.setOrientation(Orientation.HORIZONTAL);
			break;
		case SideValue.LEFT_VALUE:
		case SideValue.RIGHT_VALUE:
			toolBar.setOrientation(Orientation.VERTICAL);
			break;
		}

		element.setWidget(toolBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		System.out.println("TrimBarRenderer.processContents(): " + container.getElementId());
		IPresentationEngine renderer = (IPresentationEngine) context.get(IPresentationEngine.class.getName());
		ToolBar toolbar = (ToolBar) container.getWidget();

		boolean isFirst = true;
		toolbar.getItems().removeAll(toolbar.getItems());
		for (MUIElement element : container.getChildren()) {
			Node node = (Node) element.getWidget();
			if (node != null) {
				if (!isFirst) {
					toolbar.getItems().add(new Separator());
				}
				try {
					toolbar.getItems().add(node);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isFirst = false;
			}
		}
	}

	@Override
	public void setVisible(MUIElement changedElement, boolean visible) {
		ToolBar toolBar = (ToolBar) changedElement.getWidget();
		toolBar.setVisible(visible);
	};
}
