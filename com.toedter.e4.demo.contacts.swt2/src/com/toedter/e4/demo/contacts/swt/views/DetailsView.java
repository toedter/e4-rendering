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

package com.toedter.e4.demo.contacts.swt.views;

import com.toedter.e4.demo.contacts.generic.model.Contact;
import com.toedter.e4.demo.contacts.swt.handlers.ThemeUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import javax.inject.Inject;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

public class DetailsView {

	@Inject
	private MDirtyable dirtyable;

	private final DetailComposite detailComposite;

	@Inject
	private EHandlerService handlerService;

	@Inject
	private ECommandService commandService;

	@Inject
	@Optional
	private IStylingEngine engine;

	@Inject
	private MUILabel uiItem;

	@Focus
	void setFocus() {
		detailComposite.setFocus();
	}

	public boolean isSaveOnCloseNeeded() {
		return true;
	}

	@Inject
	public DetailsView(Composite parent) {
		parent.setData("org.eclipse.e4.ui.css.id", "DetailsView");

		detailComposite = new DetailComposite(dirtyable, parent);
	}

	@Persist
	public void doSave(@Optional IProgressMonitor monitor) throws IOException, InterruptedException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask("Saving contact details to vCard...", 16);

		Contact originalContact = detailComposite.getOriginalContact();
		Contact modifiedContact = detailComposite.getModifiedContact();
		saveAsVCard(modifiedContact, modifiedContact.getSourceFile());

		originalContact.setCity(modifiedContact.getCity());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setCompany(modifiedContact.getCompany());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setCountry(modifiedContact.getCountry());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setEmail(modifiedContact.getEmail());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setFirstName(modifiedContact.getFirstName());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setJobTitle(modifiedContact.getJobTitle());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setLastName(modifiedContact.getLastName());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setMiddleName(modifiedContact.getMiddleName());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setMobile(modifiedContact.getMobile());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setNote(modifiedContact.getNote());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setPhone(modifiedContact.getPhone());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setState(modifiedContact.getState());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setStreet(modifiedContact.getStreet());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setTitle(modifiedContact.getTitle());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setWebPage(modifiedContact.getWebPage());
		Thread.sleep(50);
		monitor.worked(1);

		originalContact.setZip(modifiedContact.getZip());
		Thread.sleep(50);
		monitor.worked(1);

		updatePartTitle(originalContact);
		monitor.done();

		dirtyable.setDirty(false);
	}

	private String getName(Contact contact, String charSet) {
		StringBuilder builder = new StringBuilder();
		builder.append("N;").append(charSet).append(':'); //$NON-NLS-1$
		builder.append(contact.getLastName()).append(';');
		builder.append(contact.getFirstName()).append(';');
		builder.append(contact.getMiddleName());

		String title = contact.getTitle();
		if (title.length() != 0) {
			builder.append(';').append(title);
		}

		builder.append('\n');
		return builder.toString();
	}

	private void saveAsVCard(Contact contact, String fileName) throws IOException {
		String charSet = "CHARSET=" + Charset.defaultCharset().name();
		String vCard = "BEGIN:VCARD" + "\nVERSION:2.1" + "\n" + getName(contact, charSet) + "FN;" + charSet + ":"
				+ contact.getFirstName() + " " + contact.getLastName() + "\nORG;" + charSet + ":"
				+ contact.getCompany() + "\nTITLE:" + contact.getJobTitle() + "\nNOTE:" + contact.getNote()
				+ "\nTEL;WORK;VOICE:" + contact.getPhone() + "\nTEL;CELL;VOICE:" + contact.getMobile() + "\nADR;WORK;"
				+ charSet + ":" + ";;" + contact.getStreet() + ";" + contact.getCity() + ";" + contact.getState() + ";"
				+ contact.getZip() + ";" + contact.getCountry() + "\nURL;WORK:" + contact.getWebPage()
				+ "\nEMAIL;PREF;INTERNET:" + contact.getEmail() + "\n";

		if (!contact.getJpegString().equals("")) {
			vCard += "PHOTO;TYPE=JPEG;ENCODING=BASE64:\n " + contact.getJpegString() + "\n";
		}

		vCard += "END:VCARD\n";

		PrintWriter out = new PrintWriter(fileName, "Cp1252");
		out.println(vCard);
		out.close();
	}

	private void updatePartTitle(Contact contact) {
		// StringBuffer title = new StringBuffer("Details of ");
		// title.append(contact.getFirstName()).append(' ').append(contact.getLastName());
		// uiItem.setLabel(title.toString());
	}

	@Inject
	public void setSelection(@Optional Contact contact) {
		if (contact != null) {
			if (dirtyable.isDirty()) {
				MessageDialog dialog = new MessageDialog(detailComposite.getShell(), "Save vCard", null,
						"The current vCard has been modified. Save changes?", MessageDialog.CONFIRM, new String[] {
								IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 0);
				dialog.create();
				if (engine != null) {
					ThemeUtil.applyDialogStyles(engine, dialog.getShell());
				}
				if (dialog.open() == Window.OK) {
					ParameterizedCommand saveCommand = commandService.createCommand("contacts.save",
							Collections.EMPTY_MAP);
					handlerService.executeHandler(saveCommand);
				}
			}

			updatePartTitle(contact);
		} else {
			uiItem.setLabel("Details");
		}
		dirtyable.setDirty(false);
		detailComposite.update(contact);
	}
}
