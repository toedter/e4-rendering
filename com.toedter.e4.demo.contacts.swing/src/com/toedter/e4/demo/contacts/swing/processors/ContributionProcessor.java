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

package com.toedter.e4.demo.contacts.swing.processors;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

@SuppressWarnings("restriction")
public class ContributionProcessor {

	@Inject
	@Named("part:detailsView")
	private MPart detailsView;

	@Inject
	@Named("part:listView")
	private MPart listView;

	@Inject
	@Named("handler:switchTheme")
	private MHandler switchThemeHandler;

	@Inject
	@Named("handler:exit")
	private MHandler exitHandler;

	@Execute
	public void process() {
		listView.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.swing/com.toedter.e4.demo.contacts.swing.views.ListView");
		detailsView
				.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.swing/com.toedter.e4.demo.contacts.swing.views.DetailsView");
		exitHandler
				.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.swing/com.toedter.e4.demo.contacts.swing.handlers.ExitHandler");
		switchThemeHandler
				.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.swing/com.toedter.e4.demo.contacts.swing.handlers.SwitchThemeHandler");
	}
}
