package com.toedter.e4.ui.workbench.generic;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

@SuppressWarnings("restriction")
public interface IPresentationEngine2 extends IPresentationEngine {
	public Object createGui(MUIElement element, MElementContainer<MUIElement> parent);
}
