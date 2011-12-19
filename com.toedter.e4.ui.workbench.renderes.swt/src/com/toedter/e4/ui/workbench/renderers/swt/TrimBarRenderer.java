package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class TrimBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MTrimBar)) {
			return;
		}
		CoolBar coolBar = new CoolBar((Shell) parent.getWidget(), SWT.NONE);
		coolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		CoolItem textItem = new CoolItem(coolBar, SWT.NONE);

		Text text = new Text(coolBar, SWT.BORDER | SWT.DROP_DOWN);
		text.setText("TEXT");
		text.pack();
		Point size = text.getSize();
		textItem.setControl(text);
		textItem.setSize(textItem.computeSize(size.x, size.y));

		element.setWidget(coolBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
	}
}
