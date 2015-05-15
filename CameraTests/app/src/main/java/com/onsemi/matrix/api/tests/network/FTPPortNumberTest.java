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
public class FTPPortNumberTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultFTPPort(){
        Utils.getResponse("/vb.htm?ftpport=21");
    }

    @After
    public void setFTPPortTo21(){
        Utils.getResponse("/vb.htm?ftpport=21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=ftpport",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void ftpport_GetDefaultValue_ShouldBe21(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "ftpport", "response contains ftpport");
        Utils.verifyResponse(response, "ftpport=21", "default ftpport value is 21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void ftpport_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpport", "response contains ftpport");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"), "ftpport=0", "ftpport value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=8442",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void ftpport_SetTo8442_ValueShouldBe8442(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpport", "response contains ftpport");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"), "ftpport=8442", "ftpport value is 8442");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=64000",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void ftpport_SetTo64000_ValueShouldBe64000(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpport", "response contains ftpport");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"), "ftpport=64000", "ftpport value is 64000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void ftpport_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpportsBody = response.getBody();
        String ftpport = Utils.getResponse("/vb.htm?paratest=ftpport").getBody();
        assertFalse("Response should not contain OK", ftpportsBody.contains("OK"));
        assertTrue("Response should contain NG", ftpportsBody.contains("NG"));
        assertTrue("Response should contain ftpport", ftpport.contains("ftpport"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "-1", "ftpport not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "ftpport=21", "ftpport should be 21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void ftpport_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpportsBody = response.getBody();
        String ftpport = Utils.getResponse("/vb.htm?paratest=ftpport").getBody();
        assertFalse("Response should not contain OK", ftpportsBody.contains("OK"));
        assertTrue("Response should contain NG", ftpportsBody.contains("NG"));
        assertTrue("Response should contain ftpport", ftpport.contains("ftpport"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "NaN", "ftpport not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "ftpport=21", "ftpport should be 21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void ftpport_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpportsBody = response.getBody();
        String ftpport = Utils.getResponse("/vb.htm?paratest=ftpport").getBody();
        assertFalse("Response should not contain OK", ftpportsBody.contains("OK"));
        assertTrue("Response should contain NG", ftpportsBody.contains("NG"));
        assertTrue("Response should contain ftpport", ftpport.contains("ftpport"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "ftpport=21", "ftpport should be 21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=443",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void ftpport_SetTo443_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpportsBody = response.getBody();
        String ftpport = Utils.getResponse("/vb.htm?paratest=ftpport").getBody();
        assertFalse("Response should not contain OK", ftpportsBody.contains("OK"));
        assertTrue("Response should contain NG", ftpportsBody.contains("NG"));
        assertTrue("Response should contain ftpport", ftpport.contains("ftpport"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "443", "ftpport not equal 443");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "ftpport=21", "ftpport should be 21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=80",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void ftpport_SetTo80_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpportsBody = response.getBody();
        String ftpport = Utils.getResponse("/vb.htm?paratest=ftpport").getBody();
        assertFalse("Response should not contain OK", ftpportsBody.contains("OK"));
        assertTrue("Response should contain NG", ftpportsBody.contains("NG"));
        assertTrue("Response should contain ftpport", ftpport.contains("ftpport"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "80", "ftpport not equal 80");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "ftpport=21", "ftpport should be 21");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpport=64001",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void ftpport_SetTo64001_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpportsBody = response.getBody();
        String ftpport = Utils.getResponse("/vb.htm?paratest=ftpport").getBody();
        assertFalse("Response should not contain OK", ftpportsBody.contains("OK"));
        assertTrue("Response should contain NG", ftpportsBody.contains("NG"));
        assertTrue("Response should contain ftpport", ftpport.contains("ftpport"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "64001", "ftpport not equal 64001");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpport"),
                "ftpport=21", "ftpport should be 21");
    }
}
