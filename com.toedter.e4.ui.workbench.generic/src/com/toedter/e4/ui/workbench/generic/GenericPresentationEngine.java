/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

package com.toedter.e4.ui.workbench.generic;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class GenericPresentationEngine implements IPresentationEngine2 {

	@Inject
	protected Logger logger;

	protected IRendererFactory rendererFactory;

	@Inject
	EModelService modelService;

	@Inject
	EventBroker eventBroker;

	private final EventHandler childrenHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (!(changedObj instanceof MElementContainer<?>)) {
				return;
			}

			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> changedElement = (MElementContainer<MUIElement>) changedObj;

			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				System.out.println("ADD " + changedElement);
			} else if (UIEvents.EventTypes.REMOVE.equals(eventType)) {
				System.out.println("REMOVE " + changedElement);
				MUIElement removed = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);

				removeGui(removed);
			}

			System.out.println(event);
		}
	};

	private final EventHandler visibilityHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			System.out.println("visibilityHandler: " + changedElement);

			GenericRenderer renderer = (GenericRenderer) changedElement.getRenderer();
			if (renderer == null) {
				return;
			}

			renderer.setVisible(changedElement, changedElement.isVisible());

			MElementContainer<MUIElement> parent = changedElement.getParent();
			if (parent == null) {
				parent = (MElementContainer<MUIElement>) ((EObject) changedElement).eContainer();
			}

			GenericRenderer parentRenderer = (GenericRenderer) parent.getRenderer();
			if (parentRenderer != null) {
				parentRenderer.doLayout(parent);
			}
		}
	};

	@Override
	public Object createGui(MUIElement element, Object parentWidget, IEclipseContext parentContext) {
		System.out.println("GenericPresentationEngine.createGui(): This method should not be used.");
		return null;
	}

	@Override
	public Object createGui(MUIElement element, MElementContainer<MUIElement> parent) {
		// System.out.println("GenericPresentationEngine.createGui(): " +
		// element);

		if (element instanceof MContext) {

			MContext ctxt = (MContext) element;

			// Assert.isTrue(ctxt.getContext() == null,
			// "Before rendering Context should be null");
			if (ctxt.getContext() == null) {
				IEclipseContext eclipseContext = getContext(parent).createChild(getContextName(element));
				populateModelInterfaces(ctxt, eclipseContext, element.getClass().getInterfaces());
				ctxt.setContext(eclipseContext);

				// System.out.println("create context: " +
				// eclipseContext.toString() + " parent context: "
				// + parentContext.toString());

				// make sure the context knows about these variables that have
				// been defined in the model
				for (String variable : ctxt.getVariables()) {
					eclipseContext.declareModifiable(variable);
				}

				Map<String, String> props = ctxt.getProperties();
				for (String key : props.keySet()) {
					eclipseContext.set(key, props.get(key));
				}

				E4Workbench.processHierarchy(element);
			}
		}

		GenericRenderer renderer = rendererFactory.getRenderer(element);
		element.setRenderer(renderer);

		// TODO check if parents are needed
		if (parent == null) {
			System.out.println("GenericPresentationEngine.createGui(): no parent: " + element + " parent: " + parent);
		}
		renderer.createWidget(element, parent);

		// Does not work: why?
		// if (parent != null) {
		// element.setParent(parent);
		// }

		// TODO set visible here?
		element.setVisible(true);

		if (element instanceof MElementContainer) {

			// first create the GUI for the children
			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
			for (MUIElement child : container.getChildren()) {
				createGui(child);
			}

			// then let the renderer process them
			renderer.processContents(container);
		}
		renderer.hookControllerLogic(element);

		return element.getWidget();
	}

	private String getContextName(MUIElement element) {
		StringBuilder builder = new StringBuilder(element.getClass().getSimpleName());
		String elementId = element.getElementId();
		if (elementId != null && elementId.length() != 0) {
			builder.append(" (").append(elementId).append(") ");
		}
		builder.append("Context");
		return builder.toString();
	}

	private static void populateModelInterfaces(MContext contextModel, IEclipseContext context, Class<?>[] interfaces) {
		for (Class<?> intf : interfaces) {
			context.set(intf.getName(), contextModel);
			populateModelInterfaces(contextModel, context, intf.getInterfaces());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object createGui(final MUIElement element) {
		// Obtain the necessary parent widget
		MElementContainer<MUIElement> parent = element.getParent();
		if (parent == null) {
			parent = (MElementContainer<MUIElement>) ((EObject) element).eContainer();
		}

		return createGui(element, parent);
	}

	private IEclipseContext getContext(MUIElement parent) {
		if (parent instanceof MContext) {
			return ((MContext) parent).getContext();
		}
		return modelService.getContainingContext(parent);
	}

	@Override
	public void removeGui(MUIElement element) {
		System.out.println("GenericPresentationEngine.removeGui(): " + element);

		((GenericRenderer) element.getRenderer()).removeWidget(element, null);
	}

	@Override
	public Object run(MApplicationElement uiRoot, IEclipseContext appContext) {
		System.out.println("GenericPresentationEngine.run(): " + uiRoot + ":" + appContext);
		if (uiRoot instanceof MApplication) {
			MApplication theApp = (MApplication) uiRoot;
			for (MWindow window : theApp.getChildren()) {
				createGui(window);
			}
		}
		System.out.println("GenericPresentationEngine.run(): Finished");
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		System.out.println("GenericPresentationEngine.stop()");
	}

	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		System.out.println("GenericPresentationEngine.postConstruct()");
		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);

		// TODO use parameter or registry
		IContributionFactory contribFactory = context.get(IContributionFactory.class);
		try {
			rendererFactory = (IRendererFactory) contribFactory.create(
					"bundleclass://com.toedter.e4.ui.workbench.generic/"
							+ "com.toedter.e4.ui.workbench.generic.GenericRendererFactory", context);
		} catch (Exception e) {
			logger.warn(e, "Could not create rendering factory");
		}

		eventBroker.subscribe(UIEvents.ElementContainer.CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityHandler);
	}

	@Override
	public void refreshGui(MUIElement element) {
		GenericRenderer renderer = rendererFactory.getRenderer(element);
		renderer.doLayout((MElementContainer<MUIElement>) element);
	}
}
