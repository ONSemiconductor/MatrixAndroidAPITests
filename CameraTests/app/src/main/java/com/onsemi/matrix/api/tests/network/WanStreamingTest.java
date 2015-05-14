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
public class WanStreamingTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultDynamicBitrateFps(){
        Utils.getResponse("/vb.htm?dynamic_bitrate_fps=1");
    }

    @After
    public void setDynamicBitrateFpsTo1(){
        Utils.getResponse("/vb.htm?dynamic_bitrate_fps=1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=dynamic_bitrate_fps",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void dynamic_bitrate_fps_GetDefaultValue_ShouldBe1(){
        Utils.printResponse(response);
        String dynamic_bitrate_fps = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", dynamic_bitrate_fps.contains("OK"));
        Utils.verifyResponse(response, "dynamic_bitrate_fps", "response contains dynamic_bitrate_fps");
        Utils.verifyResponse(response, "dynamic_bitrate_fps=1", "default dynamic_bitrate_fps value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?dynamic_bitrate_fps=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void dynamic_bitrate_fps_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "dynamic_bitrate_fps", "response contains dynamic_bitrate_fps");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"), "dynamic_bitrate_fps=1", "dynamic_bitrate_fps value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?dynamic_bitrate_fps=2",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2)
    public void dynamic_bitrate_fps_SetTo2_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String dynamic_bitrate_fpsBody = response.getBody();
        String dynamic_bitrate_fps = Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps").getBody();
        assertFalse("Response should not contain OK", dynamic_bitrate_fpsBody.contains("OK"));
        assertTrue("Response should contain NG", dynamic_bitrate_fpsBody.contains("NG"));
        assertTrue("Response should contain dynamic_bitrate_fps", dynamic_bitrate_fps.contains("dynamic_bitrate_fps"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "2", "dynamic_bitrate_fps not equal 2");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "dynamic_bitrate_fps=2", "dynamic_bitrate_fps should be 2");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?dynamic_bitrate_fps=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void dynamic_bitrate_fps_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String dynamic_bitrate_fpsBody = response.getBody();
        String dynamic_bitrate_fps = Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps").getBody();
        assertFalse("Response should not contain OK", dynamic_bitrate_fpsBody.contains("OK"));
        assertTrue("Response should contain NG", dynamic_bitrate_fpsBody.contains("NG"));
        assertTrue("Response should contain dynamic_bitrate_fps", dynamic_bitrate_fps.contains("dynamic_bitrate_fps"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "-1", "dynamic_bitrate_fps not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "dynamic_bitrate_fps=1", "dynamic_bitrate_fps should be 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?dynamic_bitrate_fps=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void dynamic_bitrate_fps_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String dynamic_bitrate_fpsBody = response.getBody();
        String dynamic_bitrate_fps = Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps").getBody();
        assertFalse("Response should not contain OK", dynamic_bitrate_fpsBody.contains("OK"));
        assertTrue("Response should contain NG", dynamic_bitrate_fpsBody.contains("NG"));
        assertTrue("Response should contain dynamic_bitrate_fps", dynamic_bitrate_fps.contains("dynamic_bitrate_fps"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "NaN", "dynamic_bitrate_fps not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "dynamic_bitrate_fps=1", "dynamic_bitrate_fps should be 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?dynamic_bitrate_fps=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void dynamic_bitrate_fps_SetToEmptyString_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String dynamic_bitrate_fpsBody = response.getBody();
        String dynamic_bitrate_fps = Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps").getBody();
        assertFalse("Response should not contain OK", dynamic_bitrate_fpsBody.contains("OK"));
        assertTrue("Response should contain NG", dynamic_bitrate_fpsBody.contains("NG"));
        assertTrue("Response should contain dynamic_bitrate_fps", dynamic_bitrate_fps.contains("dynamic_bitrate_fps"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=dynamic_bitrate_fps"),
                "dynamic_bitrate_fps=1", "dynamic_bitrate_fps should be 1");
    }
}
