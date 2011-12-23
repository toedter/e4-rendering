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

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.internal.workbench.Activator;
import org.eclipse.e4.ui.internal.workbench.Policy;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

@SuppressWarnings("restriction")
public class MenuItemRenderer extends ItemRenderer {

	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (parent != null && element instanceof MMenuItem) {
			Menu menu = (Menu) parent.getWidget();
			MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
			setItemText((MMenuItem) element, menuItem);
			element.setWidget(menuItem);
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		// if (container.getWidget() instanceof JMenu) {
		// JMenu menu = (JMenu) container.getWidget();
		// for (MUIElement e : container.getChildren()) {
		// menu.add((JMenuItem) e.getWidget());
		// }
		// }
	}

	protected void setItemText(MMenuItem model, MenuItem menuItem) {
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
		menuItem.setText(text);

		if (model.getIconURI() != null) {
			String iconURI = model.getIconURI();
			ImageDescriptor icon = getImageDescriptor(model);
			LocalResourceManager localResourceManager = new LocalResourceManager(JFaceResources.getResources());

			try {
				menuItem.setImage(icon == null ? null : localResourceManager.createImage(icon));
			} catch (DeviceResourceException e) {
				icon = ImageDescriptor.getMissingImageDescriptor();
				menuItem.setImage(localResourceManager.createImage(icon));
				// as we replaced the failed icon, log the message once.
				Activator.trace(Policy.DEBUG_MENUS, "failed to create image " + iconURI, e); //$NON-NLS-1$
			}
		}
		// item.setAccelerator(new KeyCharacterCombination("k"));
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		if (me instanceof MDirectMenuItem) {
			final MDirectMenuItem item = (MDirectMenuItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			MenuItem menuItem = (MenuItem) item.getWidget();
			menuItem.addListener(SWT.Selection, createEventHandler(item));
		} else if (me instanceof MHandledMenuItem) {
			final MHandledMenuItem item = (MHandledMenuItem) me;
			MenuItem menuItem = (MenuItem) item.getWidget();
			menuItem.addListener(SWT.Selection, createParametrizedCommandEventHandler(item));
		}
	}
}
