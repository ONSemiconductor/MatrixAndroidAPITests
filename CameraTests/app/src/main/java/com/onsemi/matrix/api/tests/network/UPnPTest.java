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
public class UPnPTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultUPnP(){
        Utils.getResponse("/vb.htm?upnp_on=1");
    }

    @After
    public void setUPnPTo1(){
        Utils.getResponse("/vb.htm?upnp_on=1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=upnp_on",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void upnp_on_GetDefaultValue_ShouldBe1(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "upnp_on", "response contains rtsp_enable");
        Utils.verifyResponse(response, "upnp_on=1", "default upnp_on value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?upnp_on=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void upnp_on_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "upnp_on", "response contains upnp_on");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=upnp_on"), "upnp_on=1", "upnp_on value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?upnp_on=2",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2)
    public void upnp_on_SetTo2_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String upnp_onBody = response.getBody();
        String upnp_on = Utils.getResponse("/vb.htm?paratest=upnp_on").getBody();
        assertFalse("Response should not contain OK", upnp_onBody.contains("OK"));
        assertTrue("Response should contain NG", upnp_onBody.contains("NG"));
        assertTrue("Response should contain upnp_on", upnp_on.contains("upnp_on"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "2", "upnp_on not equal 2");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "upnp_on=2", "upnp_on should be 2");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?upnp_on=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void upnp_on_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String upnp_onBody = response.getBody();
        String upnp_on = Utils.getResponse("/vb.htm?paratest=upnp_on").getBody();
        assertFalse("Response should not contain OK", upnp_onBody.contains("OK"));
        assertTrue("Response should contain NG", upnp_onBody.contains("NG"));
        assertTrue("Response should contain upnp_on", upnp_on.contains("upnp_on"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "-1", "upnp_on not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "upnp_on=1", "upnp_on should be 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?upnp_on=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void upnp_on_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String upnp_onBody = response.getBody();
        String upnp_on = Utils.getResponse("/vb.htm?paratest=upnp_on").getBody();
        assertFalse("Response should not contain OK", upnp_onBody.contains("OK"));
        assertTrue("Response should contain NG", upnp_onBody.contains("NG"));
        assertTrue("Response should contain upnp_on", upnp_on.contains("upnp_on"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "NaN", "upnp_on not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "upnp_on=1", "upnp_on should be 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?upnp_on=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void upnp_on_SetToEmptyString_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String upnp_onBody = response.getBody();
        String upnp_on = Utils.getResponse("/vb.htm?paratest=upnp_on").getBody();
        assertFalse("Response should not contain OK", upnp_onBody.contains("OK"));
        assertTrue("Response should contain NG", upnp_onBody.contains("NG"));
        assertTrue("Response should contain upnp_on", upnp_on.contains("upnp_on"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=upnp_on"),
                "upnp_on=1", "upnp_on should be 1");
    }
}
