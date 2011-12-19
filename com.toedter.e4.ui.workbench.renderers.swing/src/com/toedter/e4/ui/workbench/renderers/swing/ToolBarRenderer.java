package com.toedter.e4.ui.workbench.renderers.swing;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarSeparator;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		JToolBar toolBar = (JToolBar) container.getWidget();
		for (MUIElement element : container.getChildren()) {
			if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
				toolBar.add((JButton) element.getWidget());
			} else if (element instanceof MToolBarSeparator) {
				System.out.println("separator");
				toolBar.addSeparator();
			}
		}
	}
}
