/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Holger Staudacher - initial API and implementation
 ******************************************************************************/
package com.onsemi.matrix.api;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.Assert.assertUnauthorized;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.onsemi.matrix.api.Settings;

@RunWith( HttpJUnitRunner.class )
public class AuthenticationTest {

  @Rule
  public Destination destination = new Destination( this, Settings.getHostname() );
  
  @Context
  private Response response;

  @HttpTest( method = Method.GET, path = "/" )
  public void testAuthentication() {
      //assertNotAcceptable(response);
	  assertOk( response );
  }
  
  @HttpTest( 
    method = Method.GET, 
    path = "/",
    authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) } 
  )
  public void testAuthenticationWithInValidCredentials() {
    //assertUnAuthorized( response );
  }
}
