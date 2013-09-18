/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.form;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.PatientIdentifierType;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.kenyacore.metadata.MetadataUtils;
import org.openmrs.module.kenyacore.test.TestUtils;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link EmrVelocityFunctions}
 */
public class EmrVelocityFunctionsTest extends BaseModuleContextSensitiveTest {

	private EmrVelocityFunctions functionsForSession1, functionsForSession2;

	/**
	 * Setup each test
	 */
	@Before
	public void setup() throws Exception {
		executeDataSet("dataset/test-concepts.xml");
		executeDataSet("dataset/test-metadata.xml");

		HttpSession httpSession = new MockHttpSession();
		String formXml = "<htmlform></htmlform>";

		// Create a session for dummy form with patient #6
		FormEntrySession formSession1 = new FormEntrySession(TestUtils.getPatient(6), formXml, httpSession);
		functionsForSession1 = new EmrVelocityFunctions(formSession1);

		// Create a session for dummy form with patient #7
		FormEntrySession formSession2 = new FormEntrySession(TestUtils.getPatient(7), formXml, httpSession);
		functionsForSession2 = new EmrVelocityFunctions(formSession2);
	}

	/**
	 * @see EmrVelocityFunctions#hasHivUniquePatientNumber()
	 */
	@Test
	public void hasHivUniquePatientNumber() {
		// Give patient #7 a UPN
		PatientIdentifierType upn = MetadataUtils.getPatientIdentifierType(Metadata.PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
		TestUtils.savePatientIdentifier(TestUtils.getPatient(7), upn, "1234567890");

		// Patient #7 now has a UPN
		Assert.assertThat(functionsForSession2.hasHivUniquePatientNumber(), is(true));

		// Patient #6 doesn't have a UPN
		Assert.assertThat(functionsForSession1.hasHivUniquePatientNumber(), is(false));
	}
}