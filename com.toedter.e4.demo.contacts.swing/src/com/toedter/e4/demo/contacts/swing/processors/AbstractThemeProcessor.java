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

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.commands.MParameter;

@SuppressWarnings("restriction")
public abstract class AbstractThemeProcessor {

	@Execute
	public void process() {
		if (!check()) {
			return;
		}

		LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();

		if (lnfs.length > 0) {
			MApplication application = getApplication();

			MCommand switchThemeCommand = null;
			for (MCommand cmd : application.getCommands()) {
				if ("contacts.switchTheme".equals(cmd.getElementId())) { //$NON-NLS-1$
					switchThemeCommand = cmd;
					break;
				}
			}

			if (switchThemeCommand != null) {

				preprocess();

				for (LookAndFeelInfo lookAndFeelInfo : lnfs) {
					MParameter parameter = MCommandsFactory.INSTANCE.createParameter();
					parameter.setName("contacts.commands.switchtheme.themeid"); //$NON-NLS-1$
					parameter.setValue(lookAndFeelInfo.getClassName());
					String iconURI = "platform:/plugin/com.toedter.e4.demo.contacts.swing/icons/";
					String name = lookAndFeelInfo.getName();
					String icon = "dark.png";
					if (name.contains("Nimbus")) {
						icon = "nimbus.png";
					} else if (name.contains("Windows")) {
						icon = "windows.png";
					} else if (name.contains("Metal")) {
						icon = "metal.png";
					} else if (name.contains("Motif")) {
						icon = "motif.png";
					} else if (name.contains("Napkin")) {
						icon = "napkin.png";
					}

					processTheme(lookAndFeelInfo.getName(), switchThemeCommand, parameter, iconURI + icon);
				}

				postprocess();
			}
		}
	}

	abstract protected boolean check();

	abstract protected void preprocess();

	abstract protected void processTheme(String name, MCommand switchCommand, MParameter themeId, String iconURI);

	abstract protected void postprocess();

	abstract protected MApplication getApplication();
}
