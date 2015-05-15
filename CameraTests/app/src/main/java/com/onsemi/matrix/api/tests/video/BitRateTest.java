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
public class BitRateTest {
	
	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;
	
	@BeforeClass
	public static void resetSettingBeforeTests() {
		Utils.setValue("video_bitrate_pri_1", "256");
	}
	
	@After
	public void resetSettingAfterTest() {
		Utils.setValue("video_bitrate_pri_1", "256");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?paratest=video_bitrate_pri_1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void videobitratepri1_GetDefaultValue_ShouldBe256() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1=256\n", "default video_bitrate_pri_1 value is 256");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void videobitratepri1_SetTo1_ValueShouldBe1() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=1\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=2", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void videobitratepri1_SetTo2_ValueShouldBe2() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=2\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=3", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void videobitratepri1_SetTo3_ValueShouldBe3() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=3\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=4", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void videobitratepri1_SetTo4_ValueShouldBe4() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=4\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=5", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void videobitratepri1_SetTo5_ValueShouldBe5() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=5\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=64", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 6)
	public void videobitratepri1_SetTo64_ValueShouldBe64() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=64\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=128", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 7)
	public void videobitratepri1_SetTo128_ValueShouldBe128() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=128\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=192", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 8)
	public void videobitratepri1_SetTo192_ValueShouldBe192() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=192\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=256", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 9)
	public void videobitratepri1_SetTo256_ValueShouldBe256() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=256\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}
	
	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=512", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 10)
	public void videobitratepri1_SetTo512_ValueShouldBe512() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_bitrate_pri_1", "response contains video_bitrate_pri_1");
		
		String expectedResult = "OK video_bitrate_pri_1=512\n";
		Response videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1");
		Utils.verifyResponse(videobitratepri1GetResponse,
				expectedResult, String.format("Expected: '%s' Actual: '%s'", 
						expectedResult, videobitratepri1GetResponse.getBody()).replace("\n", "\\n"));
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 11)
	public void videobitratepri1_SetToNaN_ShouldThrowException() {
		Utils.printResponse(response);
		String videobitratepri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videobitratepri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1"), "NaN",
				"video_bitrate_pri_1 not equal NaN");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=1024", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 12)
	public void videobitratepri1_SetTo1024_ShouldThrowException() {
		Utils.printResponse(response);
		String videobitratepri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videobitratepri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1"), "1024",
				"video_bitrate_pri_1 not equal 1024");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 13)
	public void videobitratepri1_SetToNegativeNumber_ShouldThrowException() {
		Utils.printResponse(response);
		String videobitratepri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videobitratepri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1"), "-1",
				"video_bitrate_pri_1 not equal -1");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_bitrate_pri_1=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 14)
	public void videobitratepri1_SetToEmpty_ShouldThrowException() {
		Utils.printResponse(response);
		String videobitratepri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videobitratepri1SetResponse.contains("OK"));
		String videobitratepri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_bitrate_pri_1").getBody();
		assertTrue("video_bitrate_pri_1 has default value", videobitratepri1GetResponse.contains("video_bitrate_pri_1=256"));
	}
}
