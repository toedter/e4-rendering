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

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.tools.emf.ui.common.IModelResource;
import org.eclipse.e4.tools.emf.ui.internal.wbm.ApplicationModelEditor;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class OpenModelEditorHandler {

	@Execute
	public void run(final MApplication application) {
		Realm.runWithDefault(SWTObservables.getRealm(Display.getDefault()), new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell(SWT.SHELL_TRIM);
				shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
				shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
				FillLayout layout = new FillLayout();
				layout.marginHeight = 5;
				layout.marginWidth = 5;
				shell.setLayout(layout);

				final IEclipseContext childContext = application.getContext().createChild("EditorContext");
				MemoryModelResource resource = new MemoryModelResource(application);
				childContext.set(IModelResource.class, resource);
				childContext.set(Composite.class.getCanonicalName(), shell);

				ContextInjectionFactory.make(ApplicationModelEditor.class, childContext);

				shell.open();
				Display display = shell.getDisplay();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				childContext.dispose();
				shell = null;
			}
		});
	}
}
