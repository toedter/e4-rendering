package com.toedter.e4.ui.workbench.generic;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

@SuppressWarnings("restriction")
public class GenericRendererFactory implements IRendererFactory {
	public static final String ABSTRACT_RENDERER_FACTORY_URI = "platform:/plugin/com.toedter.e4.ui.workbench.generic/"
			+ "com.toedter.e4.ui.workbench.generic.GenericRendererFactory";

	GenericRenderer genericRenderer = new GenericRenderer();

	@Override
	public GenericRenderer getRenderer(MUIElement uiElement) {
		return genericRenderer;
	}

}
