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

package com.toedter.e4.ui.workbench.renderers.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;
import com.toedter.e4.ui.workbench.generic.PresentationEngine;

@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MWindow) {
			MWindow mWindow = (MWindow) element;
			JFrame jFrame = new JFrame();
			jFrame.getContentPane().setLayout(new BorderLayout());
			jFrame.setBounds(mWindow.getX(), mWindow.getY(), mWindow.getWidth(), mWindow.getHeight());
			jFrame.setTitle(mWindow.getLocalizedLabel());
			element.setWidget(jFrame);
			((MWindow) element).getContext().set(JFrame.class, jFrame);
		}
	}

	@Override
	public void hookControllerLogic(final MUIElement element) {
		if (element instanceof MWindow) {
			final MWindow mWindow = (MWindow) element;
			JFrame jFrame = (JFrame) mWindow.getWidget();
			jFrame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent evt) {
					if ((MUIElement) element.getParent() instanceof MApplication) {
						MApplication application = (MApplication) (MUIElement) element.getParent();
						synchronized (application) {
							application.notifyAll();
						}
					}
				}
			});

			jFrame.getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener() {

				@Override
				public void ancestorMoved(HierarchyEvent e) {
					mWindow.setX(e.getChanged().getX());
					mWindow.setY(e.getChanged().getY());
				}

				@Override
				public void ancestorResized(HierarchyEvent e) {
					mWindow.setWidth(e.getChanged().getWidth());
					mWindow.setHeight(e.getChanged().getHeight());
				}
			});
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element) {
		if ((MUIElement) element instanceof MWindow) {
			JFrame jFrame = (JFrame) element.getWidget();
			for (MUIElement e : element.getChildren()) {
				if (e.getWidget() != null) {
					jFrame.getContentPane().add((Component) e.getWidget(), BorderLayout.CENTER);
				}
			}

			PresentationEngine engine = (PresentationEngine) context.get(IPresentationEngine.class.getName());
			MWindow window = (MWindow) ((MUIElement) element);

			if (window.getMainMenu() != null) {
				engine.createGui(window.getMainMenu(), element);
				jFrame.setJMenuBar((JMenuBar) window.getMainMenu().getWidget());
			}

			Container root = (Container) jFrame.getContentPane();
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars()) {
					Component n = (Component) engine.createGui(trim);
					if (n != null) {
						switch (trim.getSide()) {
						case BOTTOM:
							root.add(n, BorderLayout.SOUTH);
							break;
						case LEFT:
							root.add(n, BorderLayout.WEST);
							break;
						case RIGHT:
							root.add(n, BorderLayout.EAST);
							break;
						case TOP:
							root.add(n, BorderLayout.NORTH);
							break;
						}
					}
				}
			}

			jFrame.invalidate();
			jFrame.doLayout();
			jFrame.setVisible(true);
		}
	}

	@Override
	public void doLayout(MElementContainer<?> element) {
		if ((MUIElement) element instanceof MWindow) {
			JFrame jFrame = (JFrame) element.getWidget();

			PresentationEngine engine = (PresentationEngine) context.get(IPresentationEngine.class.getName());
			MWindow window = (MWindow) ((MUIElement) element);

			Container root = (Container) jFrame.getContentPane();
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars()) {
					Component n = (Component) trim.getWidget();
					if (n != null) {
						boolean isVisible = trim.isVisible();
						if (!isVisible) {
							root.remove(n);
						} else {
							switch (trim.getSide()) {
							case BOTTOM:
								root.add(n, BorderLayout.SOUTH);
								break;
							case LEFT:
								root.add(n, BorderLayout.WEST);
								break;
							case RIGHT:
								root.add(n, BorderLayout.EAST);
								break;
							case TOP:
								root.add(n, BorderLayout.NORTH);
								break;
							}
						}
					}
				}
			}

			jFrame.invalidate();
			jFrame.doLayout();
			jFrame.setVisible(true);
		}
	}

	@Override
	public void removeChild(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("Swing WBW Renderer.removeChild(): " + element + ", parent: " + parent);
	}
}