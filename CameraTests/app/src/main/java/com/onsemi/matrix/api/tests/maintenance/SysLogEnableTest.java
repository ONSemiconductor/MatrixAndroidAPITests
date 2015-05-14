package com.onsemi.matrix.api.tests.maintenance;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.BeforeClass;
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
public class SysLogEnableTest extends TestCase {

	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;
	
	@BeforeClass
	public static void resetSettingBeforeTests() {
		Utils.setValue("log_enable", "0");
	}
	
	@After
	public void resetSettingAfterTest() {
		Utils.setValue("log_enable", "0");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?paratest=log_enable", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void syslogenable_GetDefaultValue_ShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "log_enable=0", "default log_enable value is 0");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?log_enable=0", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void syslogenable_SetParameterValueTo0_ValueShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "log_enable", "response contains log_enable");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=log_enable"), 
				"log_enable=0", "log_enable value is 0");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?log_enable=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void syslogenable_SetParameterValueTo1_ValueShouldBe1() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "log_enable", "response contains log_enable");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=log_enable"),
				"log_enable=1", "log_enable value is 1");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?log_enable=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void syslogenable_SetParameterValueToNaN_ShouldThrowException() {
		Utils.printResponse(response);
		String syslogenableSetResponse = response.getBody();
		assertFalse("Response should not contain OK", syslogenableSetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=log_enable"), "NaN",
				"log_enable not equal NaN");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?log_enable=3", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void syslogenable_SetParameterValueTo3_ShouldThrowException() {
		Utils.printResponse(response);
		String syslogenableSetResponse = response.getBody();
		assertFalse("Response should not contain OK", syslogenableSetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=log_enable"), "3",
				"log_enable not equal 3");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?log_enable=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void syslogenable_SetParameterValueToNegative_ShouldThrowException() {
		Utils.printResponse(response);
		String syslogenableSetResponse = response.getBody();
		assertFalse("Response should not contain OK", syslogenableSetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=log_enable"), "-1",
				"log_enable not equal -1");
	}

	@HttpTest(method = Method.GET, path = "vb.htm?log_enable=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void syslogenable_SetParameterValueToEmpty_ShouldThrowException() {
		Utils.printResponse(response);
		String syslogenableSetResponse = response.getBody();
		assertFalse("Response should not contain OK", syslogenableSetResponse.contains("OK"));
		String syslogenableGetResponse = Utils.getResponse("/vb.htm?paratest=log_enable").getBody();
		assertTrue("log_enable has default value", syslogenableGetResponse.contains("log_enable=0"));
	}

}
