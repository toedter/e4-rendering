package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Shell;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;
import com.toedter.e4.ui.workbench.swt.layouts.BorderLayout;

@SuppressWarnings("restriction")
public class TrimBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MTrimBar)) {
			return;
		}
		CoolBar coolBar = new CoolBar((Shell) parent.getWidget(), SWT.NONE);
		coolBar.setLayoutData(new BorderLayout.BorderData(BorderLayout.NORTH));

		element.setWidget(coolBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		if (!((MUIElement) container instanceof MTrimBar && container.getWidget() instanceof CoolBar)) {
			return;
		}
		CoolBar coolBar = (CoolBar) container.getWidget();
		coolBar.layout();
		for (CoolItem item : coolBar.getItems()) {
			calcSize(item);
		}

	}

	private void calcSize(CoolItem item) {
		Control control = item.getControl();
		Point pt = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		pt = item.computeSize(pt.x, pt.y);
		item.setSize(pt);
	}

}
