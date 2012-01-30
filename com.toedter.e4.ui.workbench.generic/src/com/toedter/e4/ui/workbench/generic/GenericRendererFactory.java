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

package com.toedter.e4.ui.workbench.generic;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

@SuppressWarnings("restriction")
public class GenericRendererFactory implements IRendererFactory {
	public static final String ABSTRACT_RENDERER_FACTORY_URI = "bundleclass://com.toedter.e4.ui.workbench.generic/"
			+ "com.toedter.e4.ui.workbench.generic.GenericRendererFactory";

	GenericRenderer genericRenderer = new GenericRenderer();

	@Override
	public GenericRenderer getRenderer(MUIElement uiElement) {
		return genericRenderer;
	}

}
