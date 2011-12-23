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

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.emf.common.util.URI;

@SuppressWarnings("restriction")
public class MenuItemRenderer extends ItemRenderer {

	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (parent != null && element instanceof MMenuItem) {
			JMenuItem menuItem = new JMenuItem();
			setItemText((MMenuItem) element, menuItem);

			element.setWidget(menuItem);
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		if (container.getWidget() instanceof JMenu) {
			JMenu menu = (JMenu) container.getWidget();
			for (MUIElement e : container.getChildren()) {
				menu.add((JMenuItem) e.getWidget());
			}
		}
	}

	protected void setItemText(MMenuItem model, JMenuItem item) {
		String text = model.getLocalizedLabel();
		if (model instanceof MHandledItem) {
			MHandledItem handledItem = (MHandledItem) model;
			generateParameterizedCommand(handledItem, context);
			ParameterizedCommand cmd = handledItem.getWbCommand();
			if (cmd != null && (text == null || text.length() == 0)) {
				try {
					text = cmd.getName();
				} catch (NotDefinedException e) {
					e.printStackTrace();
				}
			}
		}
		int mnemonic = text.indexOf("&");
		if (mnemonic != -1) {
			text = text.substring(0, mnemonic) + text.subSequence(mnemonic + 1, text.length());
			item.setMnemonic(mnemonic);
		}
		item.setText(text);

		if (model.getIconURI() != null) {
			try {
				ImageIcon icon = new ImageIcon(new URL(URI.createURI(model.getIconURI()).toString()));
				item.setIcon(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		// item.setAccelerator(new KeyCharacterCombination("k"));
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		if (me instanceof MDirectMenuItem) {
			final MDirectMenuItem item = (MDirectMenuItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			JMenuItem menuItem = (JMenuItem) item.getWidget();
			menuItem.addActionListener(createEventHandler(item));
		} else if (me instanceof MHandledMenuItem) {
			final MHandledMenuItem item = (MHandledMenuItem) me;
			JMenuItem menuItem = (JMenuItem) item.getWidget();
			menuItem.addActionListener(createParametrizedCommandEventHandler(item));
		}
	}
}
