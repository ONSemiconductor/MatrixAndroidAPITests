package com.onsemi.matrix.api.tests.video;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;
import static org.junit.Assert.assertFalse;

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
public class ProfileRestartTest {

	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;

	@HttpTest(method = Method.GET, path = "/vb.htm?profile_restart=0", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void profilerestart_SetTo0_ValueShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "profile_restart", "response contains profile_restart");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?profile_restart=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void profilerestart_SetTo1_ValueShouldBe1() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "profile_restart", "response contains profile_restart");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?profile_restart=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void profilerestart_SetToNaN_ShouldThrowException() {
		Utils.printResponse(response);
		String profilerestartSetResponse = response.getBody();
		assertFalse("Response should not contain OK", profilerestartSetResponse.contains("OK"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?profile_restart=3", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void profilerestart_SetTo3_ShouldThrowException() {
		Utils.printResponse(response);
		String profilerestartSetResponse = response.getBody();
		assertFalse("Response should not contain OK", profilerestartSetResponse.contains("OK"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?profile_restart=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void profilerestart_SetToNegativeNumber_ShouldThrowException() {
		Utils.printResponse(response);
		String profilerestartSetResponse = response.getBody();
		assertFalse("Response should not contain OK", profilerestartSetResponse.contains("OK"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?profile_restart=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void profilerestart_SetToEmpty_ShouldThrowException() {
		Utils.printResponse(response);
		String profilerestartSetResponse = response.getBody();
		assertFalse("Response should not contain OK", profilerestartSetResponse.contains("OK"));
	}
}
