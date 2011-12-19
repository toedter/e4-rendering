package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;
import com.toedter.e4.ui.workbench.generic.IPresentationEngine2;
import com.toedter.e4.ui.workbench.swt.layouts.BorderLayout;

@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MWindow) {
			MWindow mWindow = (MWindow) element;
			Shell shell = new Shell(Display.getCurrent());
			shell.setLayout(new BorderLayout());

			mWindow.getContext().set(Shell.class, shell);
			element.setWidget(shell);
		}
	}

	@Override
	public void hookControllerLogic(final MUIElement element) {
		if (element instanceof MWindow) {
			final MWindow mWindow = (MWindow) element;
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element) {
		if (!(((MUIElement) element) instanceof MWindow)) {
			return;
		}
		MWindow mWindow = (MWindow) ((MUIElement) element);
		Shell shell = (Shell) element.getWidget();

		// Populate the main menu
		IPresentationEngine2 renderer = (IPresentationEngine2) context.get(IPresentationEngine.class.getName());
		if (mWindow.getMainMenu() != null) {
			renderer.createGui(mWindow.getMainMenu(), element);
			shell.setMenuBar((Menu) mWindow.getMainMenu().getWidget());
		}

		// create Detached Windows
		for (MWindow dw : mWindow.getWindows()) {
			renderer.createGui(dw, element);
		}

		// Populate the trim (if any)
		if (mWindow instanceof MTrimmedWindow) {
			MTrimmedWindow tWindow = (MTrimmedWindow) mWindow;
			for (MTrimBar trimBar : tWindow.getTrimBars()) {
				renderer.createGui(trimBar, element);
			}
		}

		shell.layout();
		shell.open();

	}

	@Override
	public void removeChild(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("SWT WBW Renderer.removeChild(): " + element + ", parent: " + parent);
	}
}