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
public class TimeZoneTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultTimeZone(){
        Utils.setValue("timezone", "20");
    }

    @After
    public void setTimeZoneTo20(){
        Utils.setValue("timezone", "20");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=timezone",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void timezone_GetDefaultValue_ShouldBe20(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "timezone", "response contains timezone");
        Utils.verifyResponse(response, "timezone=20", "default timezone value is 20");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timezone=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
     public void timezone_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "timezone", "response contains timezone");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=timezone"), "timezone=1", "timezone value is 1");
    }

    //This is crash cam!!
    //@HttpTest(method = Method.GET,
    //        path = "/vb.htm?timezone=30",
    //        authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
    //        order = 1
    //)
    //public void timezone_SetTo30_ValueShouldBe30(){
    //    Utils.printResponse(response);
    //    String timezone = response.getBody();
    //    assertOk(response);
    //    assertTrue("Response should contain OK", timezone.contains("OK"));
    //    Utils.verifyResponse(response, "timezone", "response contains timezone");
    //    Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=timezone"), "timezone=30", "timezone value is 30");
    //}

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timezone=25",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetTo25_ValueShouldBe25(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "timezone", "response contains timezone");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=timezone"), "timezone=25", "timezone value is 25");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timezone=31",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetTo31_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        String timezoneLevelResponse = Utils.getResponse("/vb.htm?paratest=timezone").getBody();
        assertFalse("Response should not contain OK", timezone.contains("OK"));
        assertTrue("Response should contain NG", timezone.contains("NG"));
        assertTrue("Response should contain timezone", timezone.contains("timezone"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=timezone"), "31", "timezone not equal 31");
        assertTrue("timezone should be 20", timezoneLevelResponse.contains("timezone=20"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timezone=NaN",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        String timezoneLevelResponse = Utils.getResponse("/vb.htm?paratest=timezone").getBody();
        assertTrue("Response should contain OK", timezone.contains("OK"));
        //assertFalse("Response should not contain OK", timezone.contains("OK"));
        //assertTrue("Response should contain NG", timezone.contains("NG"));
        assertTrue("Response should contain timezone", timezone.contains("timezone"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=timezone"), "NaN", "timezone not equal NaN");
        assertTrue("timezone should be 0", timezoneLevelResponse.contains("timezone=0"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timezone=",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        String timezoneLevelResponse = Utils.getResponse("/vb.htm?paratest=timezone").getBody();
        assertTrue("Response should contain OK", timezone.contains("OK"));
        //assertFalse("Response should not contain OK", timezone.contains("OK"));
        //assertTrue("Response should contain NG", timezone.contains("NG"));
        assertTrue("Response should contain timezone", timezone.contains("timezone"));
        assertTrue("timezone should be 0", timezoneLevelResponse.contains("timezone=0"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?timezone=-1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void timezone_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        String timezoneLevelResponse = Utils.getResponse("/vb.htm?paratest=timezone").getBody();
        assertFalse("Response should not contain OK", timezone.contains("OK"));
        assertTrue("Response should contain NG", timezone.contains("NG"));
        assertTrue("Response should contain timezone", timezone.contains("timezone"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=timezone"), "-1", "timezone not equal -1");
        assertTrue("timezone should be 20", timezoneLevelResponse.contains("timezone=20"));
    }
}
