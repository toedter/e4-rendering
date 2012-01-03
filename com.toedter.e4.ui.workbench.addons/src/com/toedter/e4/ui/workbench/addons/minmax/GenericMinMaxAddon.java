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

package com.toedter.e4.ui.workbench.addons.minmax;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class GenericMinMaxAddon {
	// tags representing the min/max state
	public static String MINIMIZED = IPresentationEngine.MINIMIZED;
	public static String MAXIMIZED = IPresentationEngine.MAXIMIZED;
	public static String MINIMIZED_BY_ZOOM = IPresentationEngine.MINIMIZED_BY_ZOOM;

	private final IMinMaxAddon uiMinMaxAddon;
	private final IEventBroker eventBroker;
	private final EModelService modelService;
	protected boolean ignoreTagChanges;

	@Inject
	public GenericMinMaxAddon(IMinMaxAddon uiMinMaxAddon, IEventBroker eventBroker, EModelService modelService) {
		System.out.println("Generic GenericMinMaxAddon()");
		this.uiMinMaxAddon = uiMinMaxAddon;
		this.eventBroker = eventBroker;
		this.uiMinMaxAddon.setGenericMinMaxAddon(this);
		this.modelService = modelService;
	}

	@PostConstruct
	void hookListeners() {
		System.out.println("GenericMinMaxAddon.hookListeners()");
		System.out.println("EventBroker: " + eventBroker);

		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, widgetListener);
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
	}

	private final EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (ignoreTagChanges) {
				return;
			}

			Object changedObj = event.getProperty(EventTags.ELEMENT);
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			String oldVal = (String) event.getProperty(UIEvents.EventTags.OLD_VALUE);

			if (!(changedObj instanceof MUIElement)) {
				return;
			}

			final MUIElement changedElement = (MUIElement) changedObj;

			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				if (MINIMIZED.equals(tag)) {
					minimize(changedElement);
				} else if (MAXIMIZED.equals(tag)) {
					maximize(changedElement);
				}
			} else if (UIEvents.EventTypes.REMOVE.equals(eventType)) {
				if (MINIMIZED.equals(oldVal)) {
					restore(changedElement);
				} else if (MAXIMIZED.equals(oldVal)) {
					unzoom(changedElement);
				}
			}
		}
	};

	private final EventHandler widgetListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			final MUIElement element = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!(element instanceof MPartStack) && !(element instanceof MArea)) {
				return;
			}
			final Runnable maximizeRunnable = new Runnable() {

				@Override
				public void run() {
					maximize(element);
				}
			};
			uiMinMaxAddon.setMaximizeHandler(element, maximizeRunnable);
			final Runnable minimizeRunnable = new Runnable() {

				@Override
				public void run() {
					minimize(element);
				}
			};
			uiMinMaxAddon.setMinimizeHandler(element, minimizeRunnable);
			final Runnable restoreRunnable = new Runnable() {

				@Override
				public void run() {
					restore(element);
				}
			};
			uiMinMaxAddon.setRestoreHandler(element, restoreRunnable);
		}

		private void maximize(MUIElement element) {
			setState(element, MAXIMIZED);
		}

		private void minimize(MUIElement element) {
			setState(element, MINIMIZED);
		}

		public void restore(MUIElement element) {
			setState(element, null);
		}
	};

	private void setState(MUIElement element, String state) {
		element.getTags().remove(MINIMIZED_BY_ZOOM);
		if (MINIMIZED.equals(state)) {
			element.getTags().remove(MAXIMIZED);
			element.getTags().add(MINIMIZED);
		} else if (MAXIMIZED.equals(state)) {
			element.getTags().remove(MINIMIZED);
			element.getTags().add(MAXIMIZED);
		} else {
			element.getTags().remove(MINIMIZED);
			element.getTags().remove(MAXIMIZED);
		}
	}

	protected void minimize(MUIElement element) {
		if (!element.isToBeRendered()) {
			return;
		}

		element.setVisible(false);
	}

	protected void unzoom(MUIElement element) {
		MWindow win = modelService.getTopLevelWindowFor(element);

		List<MPartStack> stacks = modelService.findElements(win, null, MPartStack.class, null,
				EModelService.PRESENTATION);
		for (MPartStack theStack : stacks) {
			if (theStack.getWidget() != null && theStack.getTags().contains(MINIMIZED)
					&& theStack.getTags().contains(MINIMIZED_BY_ZOOM)) {
				theStack.getTags().remove(MINIMIZED);
			}
		}
	}

	public void resetWindows(MUIElement element) {
		ignoreTagChanges = true;
		MWindow win = modelService.getTopLevelWindowFor(element);

		List<MPartStack> stacks = modelService.findElements(win, null, MPartStack.class, null,
				EModelService.PRESENTATION);
		for (MPartStack partStack : stacks) {
			if (partStack.getWidget() != null) {
				if (partStack.getTags().contains(MINIMIZED)) {
					partStack.getTags().remove(MINIMIZED);
				}
				if (partStack.getTags().contains(MAXIMIZED)) {
					partStack.getTags().remove(MAXIMIZED);
				}
				if (partStack.getTags().contains(MINIMIZED_BY_ZOOM)) {
					partStack.getTags().remove(MINIMIZED_BY_ZOOM);
				}
			}
		}
		ignoreTagChanges = false;
	}

	protected void restore(MUIElement element) {
		element.getTags().remove(MINIMIZED_BY_ZOOM);
		element.setVisible(true);
	}

	protected void maximize(MUIElement element) {
		if (!element.isToBeRendered()) {
			return;
		}

		MWindow mWindow = getWindowFor(element);
		MPerspective persp = null; // TODO handle perspectives?

		List<String> maxTag = new ArrayList<String>();
		maxTag.add(MAXIMIZED);
		List<MUIElement> curMax = modelService.findElements(persp == null ? mWindow : persp, null, MUIElement.class,
				maxTag);
		if (curMax.size() > 0) {
			for (MUIElement maxElement : curMax) {
				if (maxElement == element) {
					continue;
				}
				ignoreTagChanges = true;
				try {
					maxElement.getTags().remove(MAXIMIZED);
				} finally {
					ignoreTagChanges = false;
				}
			}
		}

		List<MPartStack> stacks = modelService.findElements(persp == null ? mWindow : persp, null, MPartStack.class,
				null, EModelService.PRESENTATION);
		for (MPartStack theStack : stacks) {
			if (theStack == element || !theStack.isToBeRendered()) {
				continue;
			}

			// Exclude stacks in DW's
			if (getWindowFor(theStack) != mWindow) {
				continue;
			}

			int location = modelService.getElementLocation(theStack);
			if (location != EModelService.IN_SHARED_AREA && theStack.getWidget() != null
					&& !theStack.getTags().contains(MINIMIZED)) {
				theStack.getTags().add(MINIMIZED_BY_ZOOM);
				theStack.getTags().add(MINIMIZED);
			}
		}

		// now let the parent check if the children are visible
		GenericRenderer parentRenderer = (GenericRenderer) element.getParent().getRenderer();
		System.out.println("GenericMinMaxAddon.maximize(): " + parentRenderer);
		if (parentRenderer != null) {
			parentRenderer.processContents(element.getParent());
		}

	}

	private MWindow getWindowFor(MUIElement element) {
		MUIElement parent = element.getParent();

		// We rely here on the fact that a DW's 'getParent' will return
		// null since it's not in the 'children' hierarchy
		while (parent != null && !(parent instanceof MWindow)) {
			parent = parent.getParent();
		}

		// A detached window will end up with getParent() == null
		return (MWindow) parent;
	}

}
