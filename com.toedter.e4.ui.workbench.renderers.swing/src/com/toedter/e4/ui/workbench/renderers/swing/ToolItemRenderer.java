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
import javax.swing.JButton;

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
			JButton button = new JButton();

			MToolItem item = (MToolItem) element;
			if (item.getIconURI() != null) {
				try {
					ImageIcon icon = new ImageIcon(new URL(URI.createURI(item.getIconURI()).toString()));
					button.setIcon(icon);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			if (item.getTooltip() != null) {
				button.setToolTipText(item.getLocalizedTooltip());
			}
			element.setWidget(button);
		}
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		if (me instanceof MDirectToolItem) {
			final MDirectToolItem item = (MDirectToolItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			JButton button = (JButton) item.getWidget();
			button.addActionListener(createEventHandler(item));
		} else if (me instanceof MHandledToolItem) {
			final MHandledItem item = (MHandledToolItem) me;

			JButton button = (JButton) item.getWidget();
			button.addActionListener(createParametrizedCommandEventHandler(item));
		}
	}
}
