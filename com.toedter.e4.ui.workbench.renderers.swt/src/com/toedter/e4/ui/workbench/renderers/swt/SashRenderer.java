package com.toedter.e4.ui.workbench.renderers.swt;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;
import com.toedter.e4.ui.workbench.swt.layouts.SimpleTrimLayout;

@SuppressWarnings("restriction")
public class SashRenderer extends GenericRenderer {
	@Inject
	private IEventBroker eventBroker;

	private EventHandler sashOrientationHandler;
	private EventHandler sashWeightHandler;

	private SashForm sashForm;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MPartSashContainer)) {
			return;
		}
		final MPartSashContainer partSashContainer = (MPartSashContainer) element;
		sashForm = new SashForm((Composite) parent.getWidget(), SWT.NONE);

		if (parent.getWidget() instanceof Shell) {
			sashForm.setLayoutData(SimpleTrimLayout.CENTER);
		}

		if (partSashContainer.isHorizontal()) {
			sashForm.setOrientation(SWT.HORIZONTAL);
		} else {
			sashForm.setOrientation(SWT.VERTICAL);
		}
		element.setWidget(sashForm);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> element) {
		if (!((MUIElement) element instanceof MPartSashContainer)) {
			return;
		}
	}

	@Override
	public void hookControllerLogic(final MUIElement element) {
		sashOrientationHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				// Ensure that this event is for a MPartSashContainer
				MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
				if (element.getRenderer() != SashRenderer.this) {
					return;
				}
				forceLayout((MElementContainer<MUIElement>) element);
			}
		};

		eventBroker.subscribe(UIEvents.GenericTile.TOPIC_HORIZONTAL, sashOrientationHandler);

		sashWeightHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				// Ensure that this event is for a MPartSashContainer
				MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
				MElementContainer<MUIElement> parent = element.getParent();
				if (parent.getRenderer() != SashRenderer.this) {
					return;
				}
				forceLayout(parent);
			}
		};

		eventBroker.subscribe(UIEvents.UIElement.TOPIC_CONTAINERDATA, sashWeightHandler);

		final SashForm sashForm = (SashForm) element.getWidget();
		System.out.println("SashRenderer.hookControllerLogic()");
		Control[] children = sashForm.getChildren();
		children[0].addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				int[] weights = sashForm.getWeights();
				String weightsString = "";
				boolean first = true;
				for (int i = 0; i < weights.length; i++) {
					if (!first) {
						weightsString += ",";
					}
					weightsString += weights[i];
					first = false;
				}
				element.setContainerData(weightsString);
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});

		String weightString = element.getContainerData();
		if (weightString != null) {
			String[] results = weightString.split(",");
			int[] weights = new int[results.length];
			for (int i = 0; i < results.length; i++) {
				weights[i] = Integer.parseInt(results[i]);
			}
			sashForm.setWeights(weights);
		}

	}

	@PreDestroy
	void preDestroy() {
		eventBroker.unsubscribe(sashOrientationHandler);
		eventBroker.unsubscribe(sashWeightHandler);
	}

	protected void forceLayout(MElementContainer<MUIElement> pscModel) {
		// layout the containing Composite
		while (!(pscModel.getWidget() instanceof Control)) {
			pscModel = pscModel.getParent();
		}
		Control ctrl = (Control) pscModel.getWidget();
		if (ctrl instanceof Shell) {
			((Shell) ctrl).layout(null, SWT.ALL | SWT.CHANGED | SWT.DEFER);
		} else {
			ctrl.getParent().layout(null, SWT.ALL | SWT.CHANGED | SWT.DEFER);
		}
	}

}
