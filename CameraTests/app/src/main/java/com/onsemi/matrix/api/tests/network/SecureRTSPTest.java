package com.onsemi.matrix.api.tests.network;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.onsemi.matrix.api.Settings;
import com.onsemi.matrix.api.Utils;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith( HttpJUnitRunner.class )
public class SecureRTSPTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultSecureRTSP(){
        Utils.getResponse("/vb.htm?rtsp_enable=0");
    }

    @After
    public void setSecureRTSPTo0(){
        Utils.getResponse("/vb.htm?rtsp_enable=0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=rtsp_enable",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void rtsp_enable_GetDefaultValue_ShouldBe0(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "rtsp_enable", "response contains rtsp_enable");
        Utils.verifyResponse(response, "rtsp_enable=0", "default rtsp_enable value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtsp_enable=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void rtsp_enable_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "rtsp_enable", "response contains rtsp_enable");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "rtsp_enable=0", "rtsp_enable value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtsp_enable=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void rtsp_enable_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "rtsp_enable", "response contains rtsp_enable");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "rtsp_enable=1", "rtsp_enable value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtsp_enable=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtsp_enable_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsp_enable = response.getBody();
        assertFalse("Response should not contain OK", rtsp_enable.contains("OK"));
        assertTrue("Response should contain NG", rtsp_enable.contains("NG"));
        assertTrue("Response should contain rtsp_enable", rtsp_enable.contains("rtsp_enable"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "NaN", "rtsp_enable not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "rtsp_enable=0", "rtsp_enable should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtsp_enable=3",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void rtsp_enable_SetTo3_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsp_enable = response.getBody();
        assertFalse("Response should not contain OK", rtsp_enable.contains("OK"));
        assertTrue("Response should contain NG", rtsp_enable.contains("NG"));
        assertTrue("Response should contain rtsp_enable", rtsp_enable.contains("rtsp_enable"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "3", "rtsp_enable not equal 3");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "rtsp_enable=0", "rtsp_enable should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtsp_enable=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void rtsp_enable_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsp_enable = response.getBody();
        assertFalse("Response should not contain OK", rtsp_enable.contains("OK"));
        assertTrue("Response should contain NG", rtsp_enable.contains("NG"));
        assertTrue("Response should contain rtsp_enable", rtsp_enable.contains("rtsp_enable"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "-1", "rtsp_enable not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "rtsp_enable=0", "rtsp_enable should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtsp_enable=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 6)
    public void rtsp_enable_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsp_enable = response.getBody();
        String rtsp_enableResponse = Utils.getResponse("/vb.htm?paratest=rtsp_enable").getBody();
        assertFalse("Response should not contain OK", rtsp_enable.contains("OK"));
        assertTrue("Response should contain NG", rtsp_enable.contains("NG"));
        assertTrue("Response should contain rtsp_enable", rtsp_enable.contains("rtsp_enable"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtsp_enable"), "rtsp_enable=0", "rtsp_enable should be 0");
    }
}
