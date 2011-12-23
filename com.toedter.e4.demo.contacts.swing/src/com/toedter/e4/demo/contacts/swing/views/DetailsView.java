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

package com.toedter.e4.demo.contacts.swing.views;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.e4.demo.contacts.generic.model.Contact;
import java.awt.BorderLayout;
import javax.inject.Inject;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.osgi.internal.signedcontent.Base64;

@SuppressWarnings("restriction")
public class DetailsView {
	private final DefaultFormBuilder builder;
	private JTextField titleField;
	private JTextField nameField;
	private JTextField companyField;
	private JTextField jobTitleField;
	private JTextField noteField;
	private JTextField streetField;
	private JTextField cityField;
	private JTextField zipField;
	private JTextField countryField;
	private JTextField phoneField;
	private JTextField mobileField;
	private JTextField emailField;
	private JTextField webSiteField;
	private JLabel imageLabel;

	@Inject
	public DetailsView(JPanel parent) {

		FormLayout layout = new FormLayout(
				"right:max(50dlu;p), 4dlu, pref:grow, 3dlu, 40dlu",
				"p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 4dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 4dlu, p, 1dlu, p, 1dlu, p, 4dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p");
		// "");
		builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		builder.addSeparator("General", cc.xyw(1, 1, 5));
		builder.addLabel("Title:", cc.xy(1, 3));
		builder.add(titleField = new JTextField(), cc.xy(3, 3));
		builder.addLabel("Name:", cc.xy(1, 5));
		builder.add(nameField = new JTextField(), cc.xy(3, 5));
		builder.addLabel("Company:", cc.xy(1, 7));
		builder.add(companyField = new JTextField(), cc.xy(3, 7));

		builder.addLabel("Job Title:", cc.xy(1, 9));
		builder.add(jobTitleField = new JTextField(), cc.xyw(3, 9, 3));

		builder.add(imageLabel = new JLabel(), cc.xywh(5, 3, 1, 5));

		builder.addLabel("Note:", cc.xy(1, 11));
		builder.add(noteField = new JTextField(), cc.xyw(3, 11, 3));

		builder.addSeparator("Business Address", cc.xyw(1, 13, 5));
		builder.addLabel("Street:", cc.xy(1, 15));
		builder.add(streetField = new JTextField(), cc.xyw(3, 15, 3));
		builder.addLabel("City:", cc.xy(1, 17));
		builder.add(cityField = new JTextField(), cc.xyw(3, 17, 3));
		builder.addLabel("Zip:", cc.xy(1, 19));
		builder.add(zipField = new JTextField(), cc.xyw(3, 19, 3));
		builder.addLabel("Country:", cc.xy(1, 21));
		builder.add(countryField = new JTextField(), cc.xyw(3, 21, 3));

		builder.addSeparator("Business Phones", cc.xyw(1, 23, 5));
		builder.addLabel("Phone:", cc.xy(1, 25));
		builder.add(phoneField = new JTextField(), cc.xyw(3, 25, 3));
		builder.addLabel("Mobile:", cc.xy(1, 27));
		builder.add(mobileField = new JTextField(), cc.xyw(3, 27, 3));

		builder.addSeparator("Business Internet", cc.xyw(1, 29, 5));
		builder.addLabel("E-Mail:", cc.xy(1, 31));
		builder.add(emailField = new JTextField(), cc.xyw(3, 31, 3));
		builder.addLabel("Web Site:", cc.xy(1, 33));
		builder.add(webSiteField = new JTextField(), cc.xyw(3, 33, 3));

		JPanel panel = builder.getPanel();
		parent.add(panel, BorderLayout.CENTER);
	}

	@Inject
	public void setSelection(@Optional final Contact contact) {
		if (contact != null) {
			titleField.setText(contact.getTitle());
			nameField.setText(contact.getFirstName() + " " + contact.getLastName());
			companyField.setText(contact.getCompany());
			jobTitleField.setText(contact.getJobTitle());
			noteField.setText(contact.getNote());
			streetField.setText(contact.getStreet());
			cityField.setText(contact.getCity());
			zipField.setText(contact.getZip());
			countryField.setText(contact.getCountry());
			phoneField.setText(contact.getPhone());
			mobileField.setText(contact.getMobile());
			emailField.setText(contact.getEmail());
			webSiteField.setText(contact.getWebPage());

			String jpegString = contact.getJpegString();
			byte[] imageBytes = Base64.decode(jpegString.getBytes());
			ImageIcon icon = new ImageIcon(imageBytes);
			imageLabel.setIcon(icon);
		}
	}

}
