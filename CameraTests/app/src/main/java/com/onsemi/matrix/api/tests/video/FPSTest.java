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
public class FPSTest {

	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;
	
	@BeforeClass
	public static void resetSettingBeforeTests() {
		Utils.setValue("video_fps_pri_1", "30");
	}
	
	@After
	public void resetSettingAfterTest() {
		Utils.setValue("video_fps_pri_1", "30");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?paratest=video_fps_pri_1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void videofpspri1_GetDefaultValue_ShouldBe30() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1=30", "default video_fps_pri_1 value is 30");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=5", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void videofpspri1_SetTo5_ValueShouldBe5() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1", "response contains video_fps_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"), 
				"video_fps_pri_1=5", "video_fps_pri_1 value is 5");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=10", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void videofpspri1_SetTo10_ValueShouldBe10() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1", "response contains video_fps_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"),
				"video_fps_pri_1=10", "video_fps_pri_1 value is 10");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=15", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void videofpspri1_SetTo15_ValueShouldBe15() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1", "response contains video_fps_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"), 
				"video_fps_pri_1=15", "video_fps_pri_1 value is 15");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=20", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void videofpspri1_SetTo20_ValueShouldBe20() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1", "response contains video_fps_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"),
				"video_fps_pri_1=20", "video_fps_pri_1 value is 20");
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=25", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void videofpspri1_SetTo25_ValueShouldBe25() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1", "response contains video_fps_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"), 
				"video_fps_pri_1=25", "video_fps_pri_1 value is 25");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=30", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void videofpspri1_SetTo30_ValueShouldBe30() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_fps_pri_1", "response contains video_fps_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"),
				"video_fps_pri_1=30", "video_fps_pri_1 value is 30");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 7)
	public void videofpspri1_SetToNaN_ShouldThrowException() {
		Utils.printResponse(response);
		String videofpspri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videofpspri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"), "NaN",
				"video_fps_pri_1 not equal NaN");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=120", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 8)
	public void videofpspri1_SetTo120_ShouldThrowException() {
		Utils.printResponse(response);
		String videofpspri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videofpspri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"), "120",
				"video_fps_pri_1 not equal 120");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 9)
	public void videofpspri1_SetToNegativeNumber_ShouldThrowException() {
		Utils.printResponse(response);
		String videofpspri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videofpspri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_fps_pri_1"), "-1",
				"video_fps_pri_1 not equal -1");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_fps_pri_1=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 10)
	public void videofpspri1_SetToEmpty_ShouldThrowException() {
		Utils.printResponse(response);
		String videofpspri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videofpspri1SetResponse.contains("OK"));
		String videofpspri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_fps_pri_1").getBody();		
		assertTrue("video_fps_pri_1 has default value", videofpspri1GetResponse.contains("video_fps_pri_1=30"));
	}
}
