package com.toedter.e4.ui.workbench.renderers.swing;

import javax.swing.JToolBar;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class TrimBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MTrimBar)) {
			return;
		}
		JToolBar toolBar = new JToolBar();
		// toolBar.setFloatable(false);
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		IPresentationEngine renderer = (IPresentationEngine) context.get(IPresentationEngine.class.getName());
		JToolBar panel = (JToolBar) container.getWidget();

		boolean isFirst = true;
		for (MUIElement element : container.getChildren()) {
			JToolBar subToolbar = (JToolBar) renderer.createGui(element);
			if (subToolbar != null) {
				if (!isFirst) {
					panel.addSeparator();
				}
				panel.add(subToolbar);
				isFirst = false;
			}
		}
	}
}
