/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 ******************************************************************************/
package com.toedter.e4.tools.modeleditor;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.tools.emf.ui.common.IModelResource;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

@SuppressWarnings("restriction")
public class MemoryModelResource implements IModelResource {
	private final WritableList list = new WritableList();
	private final EditingDomain editingDomain;

	public MemoryModelResource(MApplication application) {
		list.add(application);
		BasicCommandStack commandStack = new BasicCommandStack();
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);
	}

	@Override
	public IObservableList getRoot() {
		return list;
	}

	@Override
	public boolean isSaveable() {
		return false;
	}

	@Override
	public IStatus save() {
		return Status.OK_STATUS;
	}

	@Override
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void addModelListener(ModelListener listener) {

	}

	@Override
	public void removeModelListener(ModelListener listener) {

	}

	public IMarker createMarker() {
		return null;
	}

	public void clearMarkers() {

	}

	@Override
	public void replaceRoot(EObject eobject) {

	}
}