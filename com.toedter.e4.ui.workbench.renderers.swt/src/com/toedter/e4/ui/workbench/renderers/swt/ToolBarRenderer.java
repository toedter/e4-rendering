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

package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}
		Composite composite = (Composite) parent.getWidget();
		Layout layout = composite.getLayout();
		int orientation = SWT.HORIZONTAL;
		if (layout instanceof RowLayout) {
			RowLayout rowLayout = (RowLayout) layout;
			orientation = rowLayout.type;
		}

		// final CoolItem item = new CoolItem(coolbar, SWT.ALL);
		// final CoolItem item = coolbar.getItem(0);
		// ToolBar toolBar = null;
		// if (item.getControl() == null) {
		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | orientation);
		// item.setControl(toolBar);
		// } else {
		// toolBar = (ToolBar) item.getControl();
		if (composite.getChildren().length > 1) {
			new ToolItem(toolBar, SWT.SEPARATOR);
		}
		// }
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
	}
}
