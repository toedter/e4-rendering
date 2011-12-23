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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class StackRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {

		Composite marginComposite = new Composite((Composite) parent.getWidget(), SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 3;
		layout.marginWidth = 3;
		marginComposite.setLayout(layout);

		CTabFolder tabFolder = new CTabFolder(marginComposite, SWT.NONE);
		// tabFolder.setSimple(false);
		tabFolder.setMaximizeVisible(true);
		tabFolder.setBorderVisible(true);

		// tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
		// false));
		element.setWidget(tabFolder);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		CTabFolder tabFolder = (CTabFolder) container.getWidget();

		for (MUIElement element : container.getChildren()) {
			MUILabel mLabel = (MUILabel) element;

			CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE, 0);
			tabItem.setText(mLabel.getLocalizedLabel());
			tabItem.setControl((Control) element.getWidget());
			tabFolder.setSelection(tabItem);
		}
	}
}
