package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}

		ToolBar toolBar = new ToolBar((Composite) parent.getWidget(), SWT.FLAT);
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		// JToolBar toolBar = (JToolBar) container.getWidget();
		// for (MUIElement element : container.getChildren()) {
		// if (element instanceof MHandledToolItem || element instanceof
		// MDirectToolItem) {
		// toolBar.add((JButton) element.getWidget());
		// } else if (element instanceof MToolBarSeparator) {
		// System.out.println("separator");
		// toolBar.addSeparator();
		// }
		// }
	}
}
