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

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

@SuppressWarnings("restriction")
public class GenericRenderer {

	protected IEclipseContext context;
	protected EModelService modelService;

	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		this.context = context;
		this.modelService = (EModelService) context.get(EModelService.class.getName());
	}

	public Object getParentWidget(MUIElement element) {
		if (element.getParent() != null) {
			return element.getParent().getWidget();
		}
		return null;
	}

	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("GenericRenderer.createWidget(): " + element + ", parent: " + parent);
	}

	public void removeWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("GenericRenderer.removeWidget(): " + element);
	}

	public void bindModelToWidget(MUIElement element) {
		System.out.println("GenericRenderer.bindModelToWidget(): " + element);
	}

	public void processContents(MElementContainer<MUIElement> element) {
		System.out.println("GenericRenderer.processContents(): " + element);
	}

	public void removeChild(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("GenericRenderer.removeChild(): " + element + " from parent: " + parent);
	}

	public void hookControllerLogic(MUIElement element) {
		// System.out.println("GenericRenderer.hookControllerLogic(): " +
		// element);
	}

	public IEclipseContext getContext(MUIElement part) {
		if (part instanceof MContext) {
			return ((MContext) part).getContext();
		}
		return getContextForParent(part);
	}

	protected IEclipseContext getContextForParent(MUIElement element) {
		return modelService.getContainingContext(element);
	}

	public void setVisible(MUIElement changedElement, boolean visible) {
		System.out.println("GenericRenderer.setVisible(): " + visible);
	}
}
