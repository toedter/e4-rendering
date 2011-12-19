/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 *     Kai Tödter - Adoption to contacts demo
 ******************************************************************************/
package com.toedter.e4.demo.contacts.javafx.handlers;

import at.bestsolution.efxclipse.runtime.services.theme.ThemeManager;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;

@SuppressWarnings("restriction")
public class SwitchThemeHandler {
	@Execute
	public void switchTheme(@Named("contacts.commands.switchtheme.themeid") String themeId,
			@Optional ThemeManager themeManager) {
		if (themeManager != null) {
			themeManager.setCurrentThemeId(themeId);
		}
	}
}