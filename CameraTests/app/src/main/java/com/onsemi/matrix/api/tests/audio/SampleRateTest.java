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
public class SampleRateTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultSampleRate(){
        Utils.setValue("samplerate", "0");
    }

    @After
    public void setSampleRateTo0(){
        Utils.setValue("samplerate", "0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=samplerate",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void samplerate_GetDefaultValue_ShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "samplerate=0", "default samplerate value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?samplerate=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void samplerate_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "samplerate", "response contains samplerate");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=samplerate"), "samplerate=1", "samplerate value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?samplerate=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2)
    public void samplerate_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String samplerate = response.getBody();
        String samplerateResponse = Utils.getResponse("/vb.htm?paratest=samplerate").getBody();
        assertFalse("Response should not contain OK", samplerateResponse.contains("OK"));
        assertTrue("Response should contain NG", samplerateResponse.contains("NG"));
        assertTrue("Response should contain samplerate", samplerateResponse.contains("samplerate"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=samplerate"),
                "NaN", "samplerate not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=samplerate"), "samplerate=0", "samplerate should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?samplerate=3",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void samplerate_SetTo3_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String samplerate = response.getBody();
        String samplerateResponse = Utils.getResponse("/vb.htm?paratest=samplerate").getBody();
        assertFalse("Response should not contain OK", samplerateResponse.contains("OK"));
        assertTrue("Response should contain NG", samplerateResponse.contains("NG"));
        assertTrue("Response should contain samplerate", samplerateResponse.contains("samplerate"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=samplerate"),
                "3", "samplerate not equal 3");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=samplerate"),
                "samplerate=0", "samplerate should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?samplerate=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void samplerate_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String samplerate = response.getBody();
        String samplerateResponse = Utils.getResponse("/vb.htm?paratest=samplerate").getBody();
        assertFalse("Response should not contain OK", samplerateResponse.contains("OK"));
        assertTrue("Response should contain NG", samplerateResponse.contains("NG"));
        assertTrue("Response should contain samplerate", samplerateResponse.contains("samplerate"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=samplerate"),
                "-1", "samplerate not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=samplerate"),
                "samplerate=0", "samplerate should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?samplerate=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void samplerate_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String samplerate = response.getBody();
        String samplerateResponse = Utils.getResponse("/vb.htm?paratest=samplerate").getBody();
        assertFalse("Response should not contain OK", samplerateResponse.contains("OK"));
        assertTrue("Response should contain NG", samplerateResponse.contains("NG"));
        assertTrue("Response should contain samplerate", samplerateResponse.contains("samplerate"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=samplerate"), "samplerate=0", "samplerate should be 0");
    }
}
