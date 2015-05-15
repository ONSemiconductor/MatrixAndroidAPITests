package com.onsemi.matrix.api.tests.audio;

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
public class AudioModeTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultAudioMode(){
        Utils.setValue("audiomode", "0");
    }

    @After
    public void setAudiomodeTo0(){
        Utils.setValue("audiomode", "0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=audiomode",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void audiomode_GetDefaultValue_ShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audiomode=0", "default audiomode value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void audiomode_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audiomode", "response contains audiomode");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=0", "audiomode value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void audiomode_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audiomode", "response contains audiomode");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=1", "audiomode value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=2",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3
    )
    public void audiomode_SetTo2_ValueShouldBe2(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audiomode", "response contains audiomode");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=2", "audiomode value is 2");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void audiomode_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audiomode = response.getBody();
        assertFalse("Response should not contain OK", audiomode.contains("OK"));
        assertTrue("Response should contain NG", audiomode.contains("NG"));
        assertTrue("Response should contain audiomode", audiomode.contains("audiomode"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiomode"), "NaN", "audiomode not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=0", "audiomode should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=3",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void audiomode_SetTo3_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audiomode = response.getBody();
        assertFalse("Response should not contain OK", audiomode.contains("OK"));
        assertTrue("Response should contain NG", audiomode.contains("NG"));
        assertTrue("Response should contain audiomode", audiomode.contains("audiomode"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiomode"), "3", "audiomode should not equal 3");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=0", "audiomode should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void audiomode_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audiomode = response.getBody();
        assertFalse("Response should not contain OK", audiomode.contains("OK"));
        assertTrue("Response should contain NG", audiomode.contains("NG"));
        assertTrue("Response should contain audiomode", audiomode.contains("audiomode"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiomode"), "-1", "audiomode not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=0", "audiomode should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiomode=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 6)
    public void audiomode_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audiomode = response.getBody();
        assertFalse("Response should not contain OK", audiomode.contains("OK"));
        assertTrue("Response should contain NG", audiomode.contains("NG"));
        assertTrue("Response should contain audiomode", audiomode.contains("audiomode"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiomode"), "audiomode=0", "audiomode should be 0");
    }
}
