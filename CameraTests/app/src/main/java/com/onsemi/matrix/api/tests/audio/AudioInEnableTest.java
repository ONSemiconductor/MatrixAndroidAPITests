package com.onsemi.matrix.api.tests.audio;

import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.onsemi.matrix.api.Settings;
import com.onsemi.matrix.api.Utils;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.Method;

import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;

@RunWith( HttpJUnitRunner.class )
public class AudioInEnableTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultAudioEnable(){
        Utils.setValue("audioenable", "1");
    }

    @After
    public void setAudioenableTo1(){
        Utils.setValue("audioenable", "1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=audioenable",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void audioenable_GetDefaultValue_ShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audioenable=1", "default audioenable value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioenable=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void audioenable_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audioenable", "response contains audioenable");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioenable"), "audioenable=0", "audioenable value is 0");
    }


    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioenable=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void audioenable_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audioenable", "response contains audioenable");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioenable"), "audioenable=1", "audioenable value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioenable=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void audioenable_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioin_enable = response.getBody();
        assertFalse("Response should not contain OK", audioin_enable.contains("OK"));
        assertTrue("Response should contain NG", audioin_enable.contains("NG"));
        assertTrue("Response should contain audioenable", audioin_enable.contains("audioenable"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audioenable"), "NaN", "audioenable not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioenable"), "audioenable=0", "audioin_enable should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioenable=3",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void audioenable_SetTo3_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioin_enable = response.getBody();
        assertFalse("Response should not contain OK", audioin_enable.contains("OK"));
        assertTrue("Response should contain NG", audioin_enable.contains("NG"));
        assertTrue("Response should contain audioenable", audioin_enable.contains("audioenable"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audioenable"), "3", "audioin_enable not equal 3");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioenable"), "audioenable=1", "audioin_enable should be 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioenable=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
     public void audioenable_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioin_enable = response.getBody();
        assertFalse("Response should not contain OK", audioin_enable.contains("OK"));
        assertTrue("Response should contain NG", audioin_enable.contains("NG"));
        assertTrue("Response should contain audioenable", audioin_enable.contains("audioenable"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audioenable"), "-1", "audioin_enable not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioenable"), "audioenable=1", "audioin_enable should be 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioenable=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 6)
    public void audioenable_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioin_enable = response.getBody();
        assertFalse("Response should not contain OK", audioin_enable.contains("OK"));
        assertTrue("Response should contain NG", audioin_enable.contains("NG"));
        assertTrue("Response should contain audioenable", audioin_enable.contains("audioenable"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioenable"), "audioenable=1", "audioin_enable should be 1");
    }
}
