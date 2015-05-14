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
public class SelectedSNTPServerTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultCurrentSNTP(){
        Utils.setValue("current_sntp", "1");
    }

    @After
    public void setTimeCurrentSNTPTo1(){
        Utils.setValue("current_sntp", "1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=current_sntp",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void current_sntp_GetDefaultValue_ShouldBe1(){
        Utils.printResponse(response);
        String current_sntp = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", current_sntp.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(response, "current_sntp=1", "default current_sntp value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=1", "current_sntp value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=2",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo2_ValueShouldBe2(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=2", "current_sntp value is 2");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=3",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo3_ValueShouldBe3(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=3", "current_sntp value is 3");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=4",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo4_ValueShouldBe4(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=4", "current_sntp value is 4");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=5",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo5_ValueShouldBe5(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=5", "current_sntp value is 5");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=6",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo6_ValueShouldBe6(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=6", "current_sntp value is 6");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=7",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo7_ValueShouldBe7(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "current_sntp", "response contains current_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=current_sntp"), "current_sntp=7", "current_sntp value is 7");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo0_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String current_sntp = response.getBody();
        String current_sntpLevelResponse = Utils.getResponse("/vb.htm?paratest=current_sntp").getBody();
        assertFalse("Response should not contain OK", current_sntp.contains("OK"));
        assertTrue("Response should contain NG", current_sntp.contains("NG"));
        assertTrue("Response should contain current_sntp", current_sntpLevelResponse.contains("current_sntp"));
        assertFalse("current_sntp should not be 1", current_sntpLevelResponse.contains("current_sntp=0"));
        assertTrue("current_sntp should be 1", current_sntpLevelResponse.contains("current_sntp=1"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=-1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String current_sntp = response.getBody();
        String current_sntpLevelResponse = Utils.getResponse("/vb.htm?paratest=current_sntp").getBody();
        assertFalse("Response should not contain OK", current_sntp.contains("OK"));
        assertTrue("Response should contain NG", current_sntp.contains("NG"));
        assertTrue("Response should contain current_sntp", current_sntpLevelResponse.contains("current_sntp"));
        assertFalse("current_sntp should not be -1", current_sntpLevelResponse.contains("current_sntp=-1"));
        assertFalse("current_sntp should not be 255", current_sntpLevelResponse.contains("current_sntp=255"));
        assertTrue("current_sntp should be 1", current_sntpLevelResponse.contains("current_sntp=1"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=8",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetTo8_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String current_sntp = response.getBody();
        String current_sntpLevelResponse = Utils.getResponse("/vb.htm?paratest=current_sntp").getBody();
        assertFalse("Response should not contain OK", current_sntp.contains("OK"));
        assertTrue("Response should contain NG", current_sntp.contains("NG"));
        assertTrue("Response should contain current_sntp", current_sntpLevelResponse.contains("current_sntp"));
        assertFalse("current_sntp should not be 8", current_sntpLevelResponse.contains("current_sntp=8"));
        assertTrue("current_sntp should be 1", current_sntpLevelResponse.contains("current_sntp=1"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=NaN",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String current_sntp = response.getBody();
        String current_sntpLevelResponse = Utils.getResponse("/vb.htm?paratest=current_sntp").getBody();
        assertFalse("Response should not contain OK", current_sntp.contains("OK"));
        assertTrue("Response should contain NG", current_sntp.contains("NG"));
        assertTrue("Response should contain current_sntp", current_sntpLevelResponse.contains("current_sntp"));
        assertFalse("current_sntp should not be NaN", current_sntpLevelResponse.contains("current_sntp=NaN"));
        assertTrue("current_sntp should be 1", current_sntpLevelResponse.contains("current_sntp=1"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?current_sntp=",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void current_sntp_SetToEmptyString_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String current_sntp = response.getBody();
        String current_sntpLevelResponse = Utils.getResponse("/vb.htm?paratest=current_sntp").getBody();
        assertFalse("Response should not contain OK", current_sntp.contains("OK"));
        assertTrue("Response should contain NG", current_sntp.contains("NG"));
        assertTrue("Response should contain current_sntp", current_sntpLevelResponse.contains("current_sntp"));
        assertTrue("current_sntp should be 1", current_sntpLevelResponse.contains("current_sntp=1"));
    }
}
