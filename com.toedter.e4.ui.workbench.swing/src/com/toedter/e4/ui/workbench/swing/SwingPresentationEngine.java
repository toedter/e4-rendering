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

package com.toedter.e4.ui.workbench.swing;

import javax.annotation.PostConstruct;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.equinox.app.IApplication;

import com.toedter.e4.ui.workbench.generic.GenericPresentationEngine;
import com.toedter.e4.ui.workbench.generic.IRendererFactory;

@SuppressWarnings("restriction")
public class SwingPresentationEngine extends GenericPresentationEngine {

	public SwingPresentationEngine() {
		System.out.println("SwingPresentationEngine");
	}

	@Override
	public Object run(final MApplicationElement uiRoot, IEclipseContext appContext) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
				for (int i = 0; i < lnfs.length; i++) {
					if (lnfs[i].getName().equals("Nimbus")) {
						try {
							UIManager.setLookAndFeel(lnfs[i].getClassName());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (UnsupportedLookAndFeelException e) {
							e.printStackTrace();
						}
						break;
					}
				}

				if (uiRoot instanceof MApplication) {
					MApplication theApp = (MApplication) uiRoot;
					for (MWindow window : theApp.getChildren()) {
						createGui(window);
					}
				}
			}
		});

		synchronized (uiRoot) {
			try {
				uiRoot.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("SwingPresentationEngine.run(): Finished");
		return IApplication.EXIT_OK;
	}

	@Override
	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		System.out.println("SwingPresentationEngine.postConstruct()");
		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);

		// TODO use parameter or registry
		IContributionFactory contribFactory = context.get(IContributionFactory.class);
		try {
			rendererFactory = (IRendererFactory) contribFactory.create(
					"platform:/plugin/com.toedter.e4.ui.workbench.renderers.swing/"
							+ "com.toedter.e4.ui.workbench.renderers.swing.SwingRendererFactory", context);
		} catch (Exception e) {
			logger.warn(e, "Could not create rendering factory");
		}
	}
}
