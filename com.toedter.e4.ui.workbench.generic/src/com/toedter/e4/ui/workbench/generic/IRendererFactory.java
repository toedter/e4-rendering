package com.toedter.e4.ui.workbench.generic;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

@SuppressWarnings("restriction")
public interface IRendererFactory {
	public GenericRenderer getRenderer(MUIElement uiElement);
}
