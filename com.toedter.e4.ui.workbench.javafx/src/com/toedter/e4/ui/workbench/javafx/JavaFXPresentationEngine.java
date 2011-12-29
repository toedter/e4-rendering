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

package com.toedter.e4.ui.workbench.javafx;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.equinox.app.IApplication;

import at.bestsolution.efxclipse.runtime.databinding.JFXRealm;
import at.bestsolution.efxclipse.runtime.services.theme.ThemeManager;

import com.toedter.e4.ui.workbench.generic.GenericPresentationEngine;
import com.toedter.e4.ui.workbench.generic.IRendererFactory;

@SuppressWarnings("restriction")
public class JavaFXPresentationEngine extends GenericPresentationEngine {

	@Inject
	@Optional
	ThemeManager themeManager;

	public JavaFXPresentationEngine() {
		System.out.println("JavaFXPresentationEngine.JavaFXPresentationEngine()");
	}

	public static class JavaFXApp extends Application {
		public static JavaFXPresentationEngine engine;
		public static MApplicationElement uiRoot;
		public static IEclipseContext appContext;

		@Override
		public void start(final Stage primaryStage) throws Exception {
			JFXRealm.createDefault();
			if (uiRoot instanceof MApplication) {
				MApplication theApp = (MApplication) uiRoot;
				for (MWindow window : theApp.getChildren()) {
					engine.createGui(window);
				}
			}
		}

		@Override
		public void stop() throws Exception {
			System.out.println("JavaFXPresentationEngine.JavaFXApp.stop()");
		}
	}

	@Override
	public Object run(MApplicationElement uiRoot, IEclipseContext appContext) {
		JavaFXApp.engine = this;
		JavaFXApp.uiRoot = uiRoot;
		JavaFXApp.appContext = appContext;

		String cssTheme = (String) appContext.get("cssTheme");
		if (themeManager != null && cssTheme != null) {
			themeManager.setCurrentThemeId(cssTheme);
		}

		Application.launch(JavaFXApp.class, null);
		System.out.println("JavaFXPresentationEngine.run(): Finished");
		return IApplication.EXIT_OK;
	}

	@Override
	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		super.postConstruct(context);

		System.out.println("JavaFXPresentationEngine.postConstruct()");
		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);

		// TODO use parameter or registry
		IContributionFactory contribFactory = context.get(IContributionFactory.class);
		try {
			rendererFactory = (IRendererFactory) contribFactory.create(
					"platform:/plugin/com.toedter.e4.ui.workbench.renderers.javafx/"
							+ "com.toedter.e4.ui.workbench.renderers.javafx.JavaFXRendererFactory", context);
		} catch (Exception e) {
			logger.warn(e, "Could not create rendering factory");
		}
	}
}
