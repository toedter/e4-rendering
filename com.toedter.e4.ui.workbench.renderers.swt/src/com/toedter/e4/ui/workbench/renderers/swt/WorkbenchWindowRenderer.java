package com.toedter.e4.ui.workbench.renderers.swt;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;
import com.toedter.e4.ui.workbench.generic.IPresentationEngine2;
import com.toedter.e4.ui.workbench.swt.layouts.BorderLayout;

@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends GenericRenderer {

	@Inject
	private IEventBroker eventBroker;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MWindow) {
			MWindow mWindow = (MWindow) element;
			Shell shell = new Shell(Display.getCurrent());
			shell.setLayout(new BorderLayout());
			shell.setBounds(mWindow.getX(), mWindow.getY(), mWindow.getWidth(), mWindow.getHeight());
			mWindow.getContext().set(Shell.class, shell);
			element.setWidget(shell);
		}
	}

	@Override
	public void hookControllerLogic(final MUIElement element) {
		if (element instanceof MWindow) {
			final MWindow mWindow = (MWindow) element;
			final Shell shell = (Shell) mWindow.getWidget();

			EventHandler sizeHandler = new EventHandler() {
				@Override
				public void handleEvent(Event event) {

					// Ensure that this event is for a MMenuItem
					Object objElement = event.getProperty(UIEvents.EventTags.ELEMENT);
					if (!(objElement instanceof MWindow)) {
						return;
					}

					// Is this listener interested ?
					final MWindow window = (MWindow) objElement;
					if (window.getRenderer() != WorkbenchWindowRenderer.this) {
						return;
					}

					// No widget == nothing to update
					if (shell == null) {
						return;
					}

					String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);

					if (UIEvents.Window.X.equals(attName) || UIEvents.Window.Y.equals(attName)
							|| UIEvents.Window.WIDTH.equals(attName) || UIEvents.Window.HEIGHT.equals(attName)) {
						shell.getDisplay().asyncExec(new Runnable() {

							@Override
							public void run() {
								shell.setBounds(window.getX(), window.getY(), window.getWidth(), window.getHeight());
							}
						});
					}
				}
			};

			eventBroker.subscribe(UIEvents.Window.TOPIC_ALL, sizeHandler);

			shell.addControlListener(new ControlListener() {
				@Override
				public void controlResized(ControlEvent e) {
					// Don't store the maximized size in the model
					if (shell.getMaximized()) {
						return;
					}

					mWindow.setWidth(shell.getSize().x);
					mWindow.setHeight(shell.getSize().y);
				}

				@Override
				public void controlMoved(ControlEvent e) {
					// Don't store the maximized size in the model
					if (shell.getMaximized()) {
						return;
					}

					mWindow.setX(shell.getLocation().x);
					mWindow.setY(shell.getLocation().y);
				}
			});

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