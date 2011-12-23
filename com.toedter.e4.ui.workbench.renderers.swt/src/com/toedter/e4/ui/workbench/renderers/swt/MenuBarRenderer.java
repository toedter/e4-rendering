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
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class MenuBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MMenu)) {
			return;
		}

		if (parent != null && parent.getWidget() instanceof Menu) {
			String label = ((MUILabel) element).getLocalizedLabel();
			MenuItem menuItem = new MenuItem((Menu) parent.getWidget(), SWT.CASCADE);
			menuItem.setText(label);
			Menu menu = new Menu(menuItem);
			menuItem.setMenu(menu);
			element.setWidget(menu);
			return;
		}

		Shell shell = (Shell) parent.getWidget();
		Menu menuBar = new Menu(shell, SWT.BAR);
		element.setWidget(menuBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
	}
}
