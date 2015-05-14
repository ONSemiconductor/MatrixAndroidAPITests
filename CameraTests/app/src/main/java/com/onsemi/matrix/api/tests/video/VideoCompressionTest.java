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
public class VideoCompressionTest {
	
	@Rule
	public Destination restfuse = new Destination(this, Settings.getHostname());
	
	@Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

	@Context
	private Response response;
	
	@BeforeClass
	public static void resetSettingBeforeTests() {
		Utils.setValue("video_compression_pri_1", "0");
	}
	
	@After
	public void resetSettingAfterTest() {
		Utils.setValue("video_compression_pri_1", "0");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?paratest=video_compression_pri_1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 0)
	public void videocompressionpri1_GetDefaultValue_ShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_compression_pri_1=0", "default video_compression_pri_1 value is 0");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_compression_pri_1=0", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 1)
	public void videocompressionpri1_SetTo0_ValueShouldBe0() {
		Utils.printResponse(response);
		assertOk(response);
		Utils.verifyResponse(response, "video_compression_pri_1", "response contains video_compression_pri_1");
		Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=video_compression_pri_1"), 
				"video_compression_pri_1=0", "video_compression_pri_1 value is 0");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_compression_pri_1=NaN", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 2)
	public void videocompressionpri1_SetToNaN_ShouldThrowException() {
		Utils.printResponse(response);
		String videocompressionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videocompressionpri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_compression_pri_1"), "NaN",
				"video_compression_pri_1 not equal NaN");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_compression_pri_1=1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 3)
	public void videocompressionpri1_SetTo1_ShouldThrowException() {
		Utils.printResponse(response);
		String videocompressionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videocompressionpri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_compression_pri_1"), "1",
				"video_compression_pri_1 not equal 1");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_compression_pri_1=-1", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 4)
	public void videocompressionpri1_SetToNegativeNumber_ShouldThrowException() {
		Utils.printResponse(response);
		String videocompressionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videocompressionpri1SetResponse.contains("OK"));
		Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=video_compression_pri_1"), "-1",
				"video_compression_pri_1 not equal -1");
	}

	@HttpTest(method = Method.GET, path = "/vb.htm?video_compression_pri_1=", 
			authentications = { @Authentication(type = BASIC, user = "admin", password = "admin") }, order = 5)
	public void videocompressionpri1_SetToEmpty_ShouldThrowException() {
		Utils.printResponse(response);
		String videocompressionpri1SetResponse = response.getBody();
		assertFalse("Response should not contain OK", videocompressionpri1SetResponse.contains("OK"));
		String videocompressionpri1GetResponse = Utils.getResponse("/vb.htm?paratest=video_compression_pri_1").getBody();
		assertTrue("video_compression_pri_1 has default value", videocompressionpri1GetResponse.contains("video_compression_pri_1=0"));
	}

}
