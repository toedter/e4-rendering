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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class PartRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		Composite parentWidget = (Composite) parent.getWidget();
		final MPart part = (MPart) element;
		Composite composite = new Composite(parentWidget, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		composite.setLayout(fillLayout);

		// Create a context for this part
		IEclipseContext localContext = part.getContext();
		localContext.set(Composite.class, composite);

		IContributionFactory contributionFactory = (IContributionFactory) localContext.get(IContributionFactory.class
				.getName());
		Object newPart = contributionFactory.create(part.getContributionURI(), localContext);
		part.setObject(newPart);

		element.setWidget(composite);
	}
}
