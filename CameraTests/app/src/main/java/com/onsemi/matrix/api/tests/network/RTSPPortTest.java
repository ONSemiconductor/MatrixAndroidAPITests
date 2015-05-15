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
public class RTSPPortTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultPortRTSP(){
        Utils.getResponse("/vb.htm?rtspports=8551");
    }

    @After
    public void setRTSPPortTo0(){
        Utils.getResponse("/vb.htm?rtspports=8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=rtspports",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void rtspports_GetDefaultValue_ShouldBe8551(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "rtspports", "response contains rtspports");
        Utils.verifyResponse(response, "rtspports=8551", "default rtspports value is 8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void rtspports_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "rtspports", "response contains rtspports");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"), "rtspports=0", "rtspports value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=8442",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void rtspports_SetTo8442_ValueShouldBe8442(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "rtspports", "response contains rtspports");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"), "rtspports=8442", "rtspports value is 8442");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=64000",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void rtspports_SetTo64000_ValueShouldBe64000(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "rtspports", "response contains rtspports");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"), "rtspports=64000", "rtspports value is 64000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtspports_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsportsBody = response.getBody();
        String rtspports = Utils.getResponse("/vb.htm?paratest=rtspports").getBody();
        assertFalse("Response should not contain OK", rtsportsBody.contains("OK"));
        assertTrue("Response should contain NG", rtsportsBody.contains("NG"));
        assertTrue("Response should contain rtspports", rtspports.contains("rtspports"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "-1", "rtspports not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "rtspports=8551", "rtspports should be 8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtspports_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsportsBody = response.getBody();
        String rtspports = Utils.getResponse("/vb.htm?paratest=rtspports").getBody();
        assertFalse("Response should not contain OK", rtsportsBody.contains("OK"));
        assertTrue("Response should contain NG", rtsportsBody.contains("NG"));
        assertTrue("Response should contain rtspports", rtspports.contains("rtspports"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "NaN", "rtspports not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "rtspports=8551", "rtspports should be 8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtspports_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsportsBody = response.getBody();
        String rtspports = Utils.getResponse("/vb.htm?paratest=rtspports").getBody();
        assertFalse("Response should not contain OK", rtsportsBody.contains("OK"));
        assertTrue("Response should contain NG", rtsportsBody.contains("NG"));
        assertTrue("Response should contain rtspports", rtspports.contains("rtspports"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "rtspports=8551", "rtspports should be 8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=443",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtspports_SetTo443_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsportsBody = response.getBody();
        String rtspports = Utils.getResponse("/vb.htm?paratest=rtspports").getBody();
        assertFalse("Response should not contain OK", rtsportsBody.contains("OK"));
        assertTrue("Response should contain NG", rtsportsBody.contains("NG"));
        assertTrue("Response should contain rtspports", rtspports.contains("rtspports"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "443", "rtspports not equal 443");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "rtspports=8551", "rtspports should be 8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=80",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtspports_SetTo80_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsportsBody = response.getBody();
        String rtspports = Utils.getResponse("/vb.htm?paratest=rtspports").getBody();
        assertFalse("Response should not contain OK", rtsportsBody.contains("OK"));
        assertTrue("Response should contain NG", rtsportsBody.contains("NG"));
        assertTrue("Response should contain rtspports", rtspports.contains("rtspports"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "80", "rtspports not equal 80");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "rtspports=8551", "rtspports should be 8551");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?rtspports=64001",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void rtspports_SetTo64001_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String rtsportsBody = response.getBody();
        String rtspports = Utils.getResponse("/vb.htm?paratest=rtspports").getBody();
        assertFalse("Response should not contain OK", rtsportsBody.contains("OK"));
        assertTrue("Response should contain NG", rtsportsBody.contains("NG"));
        assertTrue("Response should contain rtspports", rtspports.contains("rtspports"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "64001", "rtspports not equal 64001");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=rtspports"),
                "rtspports=8551", "rtspports should be 8551");
    }
}
