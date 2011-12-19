package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolBar;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}
		CoolBar coolbar = (CoolBar) parent.getWidget();
		final CoolItem item = new CoolItem(coolbar, SWT.DROP_DOWN);
		ToolBar toolBar = new ToolBar(coolbar, SWT.NONE);
		item.setControl(toolBar);
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
	}
}
