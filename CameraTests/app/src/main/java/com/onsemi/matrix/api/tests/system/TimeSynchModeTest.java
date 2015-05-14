package com.onsemi.matrix.api.tests.system;

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
public class TimeSynchModeTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultTimeSynchMode(){
        Utils.setValue("timesynch_mode", "0");
    }

    @After
    public void setTimeSynchModeTo0(){
        Utils.setValue("timesynch_mode", "0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=timesynch_mode",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void timesynch_mode_GetDefaultValue_ShouldBe0(){
        Utils.printResponse(response);
        String timesynch_mode = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timesynch_mode.contains("OK"));
        Utils.verifyResponse(response, "timesynch_mode", "response contains timesynch_mode");
        Utils.verifyResponse(response, "timesynch_mode=0", "default timesynch_mode value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timesynch_mode=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetTo0_ValueShouldBe0(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "timesynch_mode", "response contains timesynch_mode");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=timesynch_mode"), "timesynch_mode=0", "timesynch_mode value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timesynch_mode=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetTo1_ValueShouldBe1(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "timesynch_mode", "response contains timesynch_mode");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=timesynch_mode"), "timesynch_mode=1", "timesynch_mode value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timesynch_mode=2",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetTo2_ValueShouldBe2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "timesynch_mode", "response contains timesynch_mode");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=timesynch_mode"), "timesynch_mode=2", "timesynch_mode value is 2");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timesynch_mode=3",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timesynch_mode_SetTo3_ResponseShouldContainNG(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.printResponse(response);
        String timesynch_mode = response.getBody();
        String timesynch_modeLevelResponse = Utils.getResponse("/vb.htm?paratest=timesynch_mode").getBody();
        assertFalse("Response should not contain OK", timesynch_mode.contains("OK"));
        assertTrue("Response should contain NG", timesynch_mode.contains("NG"));
        assertTrue("Response should contain timesynch_mode", timesynch_mode.contains("timesynch_mode"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=timesynch_mode"), "3", "timesynch_mode not equal 3");
        assertTrue("timesynch_mode should be 0", timesynch_modeLevelResponse.contains("timesynch_mode=0"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timesynch_mode=",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timesynch_mode_SetToEmpty_ResponseShouldContainNG(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.printResponse(response);
        String timesynch_mode = response.getBody();
        String timesynch_modeLevelResponse = Utils.getResponse("/vb.htm?paratest=timesynch_mode").getBody();
        assertFalse("Response should not contain OK", timesynch_mode.contains("OK"));
        assertTrue("Response should contain NG", timesynch_mode.contains("NG"));
        assertTrue("Response should contain timesynch_mode", timesynch_mode.contains("timesynch_mode"));
        assertTrue("timesynch_mode should be 0", timesynch_modeLevelResponse.contains("timesynch_mode=0"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timesynch_mode=-1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timesynch_mode_SetToNegativeNumber_ResponseShouldContainNG(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.printResponse(response);
        String timesynch_mode = response.getBody();
        String timesynch_modeLevelResponse = Utils.getResponse("/vb.htm?paratest=timesynch_mode").getBody();
        assertFalse("Response should not contain OK", timesynch_mode.contains("OK"));
        assertTrue("Response should contain NG", timesynch_mode.contains("NG"));
        assertTrue("Response should contain timesynch_mode", timesynch_mode.contains("timesynch_mode"));
        assertTrue("timesynch_mode should be 0", timesynch_modeLevelResponse.contains("timesynch_mode=0"));
    }
}
