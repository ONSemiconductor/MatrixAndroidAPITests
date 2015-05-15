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
public class SysLogDeleteTest {
	
	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;

	@HttpTest(method = Method.GET, path = "/vb.htm?log_delete=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void logdelete_DeleteSysLogFile_ShouldReturnOK() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "OK log_delete", "response contains 'OK log_delete'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?log_delete", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void logdelete_DeleteSysLogFileWithoutParameterValue_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG log_delete", "response contains 'NG log_delete'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?log_delete=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void logdelete_DeleteSysLogFileWithEmptyParameterValue_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG log_delete", "response contains 'NG log_delete'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?log_delete=2", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void logdelete_DeleteSysLogFileWithIncorrectParameterValue_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG log_delete", "response contains 'NG log_delete'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?log_delete=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void logdelete_DeleteSysLogFileWithNegativeParameterValue_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG log_delete", "response contains 'NG log_delete'");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?log_delete=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void logdelete_DeleteSysLogFileWithNaNParameterValue_ShouldReturnNG() {
		Utils.printResponse(response);
		Utils.verifyResponse(response, "NG log_delete", "response contains 'NG log_delete'");
	}
}
