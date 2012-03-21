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

package com.toedter.e4.ui.workbench.renderers.swing;

import javax.swing.JToolBar;

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
		JToolBar toolBar = new JToolBar();

		MTrimBar mTrimBar = (MTrimBar) element;
		int orientation = mTrimBar.getSide().getValue();

		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE) {
			toolBar.setOrientation(JToolBar.HORIZONTAL);
		} else {
			toolBar.setOrientation(JToolBar.VERTICAL);
		}
		toolBar.setFloatable(false);
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		IPresentationEngine renderer = (IPresentationEngine) context.get(IPresentationEngine.class.getName());
		JToolBar toolBar = (JToolBar) container.getWidget();

		boolean isFirst = true;
		toolBar.removeAll();
		for (MUIElement element : container.getChildren()) {
			JToolBar subToolbar = (JToolBar) renderer.createGui(element);
			subToolbar.setOrientation(toolBar.getOrientation());
			if (subToolbar != null) {
				if (!isFirst) {
					toolBar.addSeparator();
				}
				toolBar.add(subToolbar);
				isFirst = false;
			}
		}
	}
}
