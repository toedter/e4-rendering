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

import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

@SuppressWarnings("restriction")
public class ToolItemRenderer extends ItemRenderer {
	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
			ToolItem toolItem = new ToolItem((ToolBar) parent.getWidget(), SWT.PUSH);

			MToolItem item = (MToolItem) element;
			if (item.getIconURI() != null) {
				if (item.getIconURI() != null) {
					Image image = getImage(item);
					toolItem.setImage(image);
				}
			}
			if (item.getTooltip() != null) {
				toolItem.setToolTipText(item.getLocalizedTooltip());
			}
			element.setWidget(toolItem);
		}
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		if (me instanceof MDirectToolItem) {
			final MDirectToolItem item = (MDirectToolItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			ToolItem toolItem = (ToolItem) item.getWidget();
			toolItem.addListener(SWT.Selection, createEventHandler(item));
		} else if (me instanceof MHandledToolItem) {
			final MHandledItem item = (MHandledToolItem) me;

			ToolItem toolItem = (ToolItem) item.getWidget();
			toolItem.addListener(SWT.Selection, createParametrizedCommandEventHandler(item));
		}
	}
}
