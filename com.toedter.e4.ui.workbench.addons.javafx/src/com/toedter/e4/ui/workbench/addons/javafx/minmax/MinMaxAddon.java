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

package com.toedter.e4.ui.workbench.addons.javafx.minmax;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import com.toedter.e4.ui.controls.javafx.CTabPane;
import com.toedter.e4.ui.workbench.addons.minmax.GenericMinMaxAddon;
import com.toedter.e4.ui.workbench.addons.minmax.IMinMaxAddon;

@SuppressWarnings("restriction")
public class MinMaxAddon implements IMinMaxAddon {

	public MinMaxAddon() {
		System.out.println("JavaFX MinMaxAddon");
	}

	@Override
	public void setGenericMinMaxAddon(GenericMinMaxAddon genericMinMaxAddon) {
		System.out.println("JavaFX MinMaxAddon.setGenericMinMaxAddon()");
	}

	@Override
	public void setMaximizedHandler(MUIElement element, Runnable maximizedHandler) {
		System.out.println("JavaFX MinMaxAddon.setMaximizedHandler()");
		if (element.getWidget() instanceof CTabPane) {
			((CTabPane) element.getWidget()).setMaximizedHandler(maximizedHandler);
		}
	}
}
