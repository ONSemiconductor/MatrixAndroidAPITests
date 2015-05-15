package com.onsemi.matrix.api.tests.video;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class VideoResolutionTest {
	
	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;
	
	@BeforeClass
	public static void resetSettingBeforeTests() {
		Utils.setValue("video_resolution_pri_1", "0");
	}
	
	@After
	public void resetSettingAfterTest() {
		Utils.setValue("video_resolution_pri_1", "0");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?paratest=video_resolution_pri_1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void video_resolution_pri_1_GetDefaultValue_ShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1=0", "default video_resolution_pri_1 value is 0");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=0", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void videoresolutionpri1_SetTo0_ValueShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"), 
				"video_resolution_pri_1=0", "video_resolution_pri_1 value is 0");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void videoresolutionpri1_SetTo1_ValueShouldBe1() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"),
				"video_resolution_pri_1=1", "video_resolution_pri_1 value is 1");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=2", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void videoresolutionpri1_SetTo2_ValueShouldBe2() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"), 
				"video_resolution_pri_1=2", "video_resolution_pri_1 value is 2");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=3", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void videoresolutionpri1_SetTo3_ValueShouldBe3() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"),
				"video_resolution_pri_1=3", "video_resolution_pri_1 value is 3");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=4", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void videoresolutionpri1_SetTo4_ValueShouldBe4() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"), 
				"video_resolution_pri_1=4", "video_resolution_pri_1 value is 4");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=5", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void videoresolutionpri1_SetTo5_ValueShouldBe5() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"),
				"video_resolution_pri_1=5", "video_resolution_pri_1 value is 5");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=6", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void videoresolutionpri1_SetTo6_ValueShouldBe6() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"),
				"video_resolution_pri_1=6", "video_resolution_pri_1 value is 6");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=7", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void videoresolutionpri1_SetTo7_ValueShouldBe7() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_resolution_pri_1", "response contains video_resolution_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"),
				"video_resolution_pri_1=7", "video_resolution_pri_1 value is 7");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 7)
	public void videoresolutionpri1_SetToNaN_ShouldThrowException() {
		Utils.printResponse(response);
		String videoresolutionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videoresolutionpri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"), "NaN",
				"video_resolution_pri_1 not equal NaN");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=10", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 8)
	public void videoresolutionpri1_SetTo10_ShouldThrowException() {
		Utils.printResponse(response);
		String videoresolutionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videoresolutionpri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"), "10",
				"video_resolution_pri_1 not equal 10");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 9)
	public void videoresolutionpri1_SetToNegativeNumber_ShouldThrowException() {
		Utils.printResponse(response);
		String videoresolutionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videoresolutionpri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1"), "-1",
				"video_resolution_pri_1 not equal -1");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_resolution_pri_1=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 10)
	public void videoresolutionpri1_SetToEmpty_ShouldThrowException() {
		Utils.printResponse(response);
		String videoresolutionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videoresolutionpri1SetResponse.contains("OK"));
		String videoresolutionpri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_resolution_pri_1").getBody();
		assertTrue("video_resolution_pri_1 has default value", videoresolutionpri1GetResponse.contains("video_resolution_pri_1=0"));
	}

}
