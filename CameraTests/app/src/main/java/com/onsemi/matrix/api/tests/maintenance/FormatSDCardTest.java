package com.onsemi.matrix.api.tests.maintenance;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.onsemi.matrix.api.Settings;
import com.onsemi.matrix.api.Utils;

@RunWith( HttpJUnitRunner.class )
public class FormatSDCardTest {
	
	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;

	@HttpTest(method = Method.GET, path = "/vb.htm?sdformat=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void sdformat_FormatSDCard_ShouldReturnOK() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "OK sdformat", "response contains 'OK sdformat'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?sdformat", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void sdformat_DoesNotSetParameterValue_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG sdformat", "response contains 'NG sdformat'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?sdformat=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void sdformat_SetParameterValueToEmpty_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG sdformat", "response contains 'NG sdformat'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?sdformat=2", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void sdformat_SetParameterValueToIncorrect_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG sdformat", "response contains 'NG sdformat'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?sdformat=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void sdformat_SetParameterValueToNegative_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG sdformat", "response contains 'NG sdformat'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?sdformat=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void sdformat_SetParameterValueToNaN_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG sdformat", "response contains 'NG sdformat'");
	}
}
