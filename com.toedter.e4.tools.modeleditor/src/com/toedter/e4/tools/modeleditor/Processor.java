/*******************************************************************************
 * Copyright (c) 2011 BestSolution.at, Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 *     Kai Toedter - additions for more general use
 ******************************************************************************/

package com.toedter.e4.tools.modeleditor;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;

@SuppressWarnings("restriction")
public class Processor {

	private final static String DESCRIPTION = "Show a live Workbench model";

	@Execute
	public void process(MApplication application,
			@Optional @Named("toolbar:org.eclipse.ui.main.toolbar") MTrimBar topBar) {
		System.out.println("Initializing Live Model Editor...");
		MCommand command = MCommandsFactory.INSTANCE.createCommand();
		command.setElementId("com.toedter.e4.tools.modeleditor");
		command.setCommandName(DESCRIPTION);
		command.setDescription(DESCRIPTION);
		application.getCommands().add(command);

		MHandler handler = MCommandsFactory.INSTANCE.createHandler();
		handler.setContributionURI("platform:/plugin/com.toedter.e4.tools.modeleditor/com.toedter.e4.tools.modeleditor.OpenModelEditorHandler");
		handler.setCommand(command);
		application.getHandlers().add(handler);

		MToolBar toolBar = MMenuFactory.INSTANCE.createToolBar();
		if (topBar == null) {
			MTrimmedWindow mWindow = (MTrimmedWindow) application.getChildren().get(0);
			topBar = mWindow.getTrimBars().get(0);
			System.out.println("TopBar ID: " + topBar.getElementId());
		}
		topBar.getChildren().add(toolBar);

		MHandledToolItem toolItem = MMenuFactory.INSTANCE.createHandledToolItem();
		toolItem.setTooltip(DESCRIPTION);
		toolItem.setCommand(command);
		toolItem.setIconURI("platform:/plugin/com.toedter.e4.tools.modeleditor/icons/editor.png");

		toolBar.getChildren().add(toolItem);
	}

}
