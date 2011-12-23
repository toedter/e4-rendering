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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class MenuBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MMenu)) {
			return;
		}

		if (parent != null && parent.getWidget() instanceof JMenuBar) {
			String label = ((MUILabel) element).getLocalizedLabel();
			label = label.replaceAll("&", ""); // JavaFX and SWT Mnemonics
			JMenu menu = new JMenu(label);
			element.setWidget(menu);
			return;
		}

		// No parent means the main menu bar
		JMenuBar menuBar = new JMenuBar();
		element.setWidget(menuBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		if (container.getWidget() instanceof JMenuBar) {
			JMenuBar menuBar = (JMenuBar) container.getWidget();
			for (MUIElement e : container.getChildren()) {
				menuBar.add((JMenu) e.getWidget());
			}
		} else if (container.getWidget() instanceof JMenu) {
			JMenu menu = (JMenu) container.getWidget();
			for (MUIElement e : container.getChildren()) {
				menu.add((JMenuItem) e.getWidget());
			}
		}
	}
}
