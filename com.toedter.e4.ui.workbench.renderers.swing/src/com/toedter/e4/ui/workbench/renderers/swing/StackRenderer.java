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

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.emf.common.util.URI;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class StackRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		JTabbedPane tabPane = new JTabbedPane();
		element.setWidget(tabPane);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		JTabbedPane parentPane = (JTabbedPane) container.getWidget();

		for (MUIElement element : container.getChildren()) {
			MUILabel mLabel = (MUILabel) element;
			ImageIcon icon = null;
			if (mLabel.getIconURI() != null) {
				try {
					icon = new ImageIcon(new URL(URI.createURI(mLabel.getIconURI()).toString()));
				} catch (MalformedURLException e) {
					// ignore, icon will be null
				}
			}

			// the following code makes sure, that even in Nimbus L&F the icon
			// is on the left side
			JLabel label = new JLabel(mLabel.getLocalizedLabel());
			label.setIcon(icon);
			label.setIconTextGap(5);
			label.setHorizontalTextPosition(SwingConstants.RIGHT);
			parentPane.add((Component) element.getWidget());
			parentPane.setTabComponentAt(parentPane.getTabCount() - 1, label);
		}
	}
}
