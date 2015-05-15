/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Holger Staudacher - initial API and implementation
 ******************************************************************************/
package com.onsemi.matrix.api;

import com.onsemi.matrix.api.tests.system.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses({
	CustomSNTPTest.class,
	DateTest.class,
	DisplayLanguageTest.class,
	MacAddressTest.class,
	SelectedSNTPServerTest.class,
	SerialNoTest.class,
	SNTPServerListTest.class,
	SynchronisationIntervalTest.class,
	TimeSynchModeTest.class,
	TimeTest.class,
	TimeZoneTest.class,
	TitleTest.class,
	UptimeTest.class
})
public class SystemTest {
}	
