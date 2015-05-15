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
public class CustomSNTPTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultCustomSNTP(){
        Utils.setValue("custom_sntp", " ");
    }

    @After
    public void setCustomSNTPToBlank(){
        Utils.setValue("custom_sntp", " ");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=current_sntp",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void custom_sntp_GetDefaultValue_ShouldBeBlank(){
        Utils.printResponse(response);
        String timesynch_mode = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timesynch_mode.contains("OK"));
        Utils.verifyResponse(response, "custom_sntp", "response contains current_sntp");
        assertTrue("default current_sntp value is blank", response.getBody().replaceAll("\n|\r\n", "OK custom_sntp=").equals(""));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=ValidName",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void custom_sntp_SetValidName_UsernameShouldBeValidName(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "custom_sntp", "response contains custom_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=custom_sntp"), "custom_sntp=ValidName", "custom_sntp value is ValidName");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=ValidName12345",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 2
    )
    public void custom_sntp_SetValidNameWithNumbers_UsernameShouldBeValidNameWithNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "custom_sntp", "response contains custom_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=custom_sntp"), "custom_sntp=ValidName12345", "custom_sntp value is ValidName12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=ValidName@#$:.",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 3
    )
    public void custom_sntp_SetValidNameWithSpecialSymbols_UsernameShouldBeValidNameWithSpecialSymbols(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "custom_sntp", "response contains custom_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=custom_sntp"), "custom_sntp=ValidName@#$:.", "custom_sntp value is ValidName@#$:.");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=ValidName@#$:.12345",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 5
    )
    public void custom_sntp_SetValidNameWithSpecialSymbolsAndNumbers_UsernameShouldBeValidNameWithSpecialSymbolsAndNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "custom_sntp", "response contains custom_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=custom_sntp"), "custom_sntp=ValidName@#$:.12345", "custom_sntp value is ValidName@#$:.12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=ABCDEFGHIJKLMNOPQRSTUVWXYZ123456",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 6
    )
    public void custom_sntp_SetToStringWithLenght32_UsernameShouldBeStringWithLenght32(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "custom_sntp", "response contains custom_sntp");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=custom_sntp"),
                "custom_sntp=ABCDEFGHIJKLMNOPQRSTUVWXYZ123456",
                "custom_sntp value is ABCDEFGHIJKLMNOPQRSTUVWXYZ123456");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=@#$:.",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 7
    )
    public void custom_sntp_SetToSpecialSymbol_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String custom_sntpBody = response.getBody();
        String custom_sntp = Utils.getResponse("/vb.htm?paratest=custom_sntp").getBody();
        assertFalse("Response should not contain OK", custom_sntpBody.contains("OK"));
        assertTrue("Response should contain NG", custom_sntpBody.contains("NG"));
        assertTrue("Response should contain custom_sntp", custom_sntpBody.contains("custom_sntp"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=custom_sntp"),
                "@#$:.", "custom_sntp not equal @#$:.");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=custom_sntp").getBody()
                .replaceAll("\n|\r\n", "").equals("OK custom_sntp="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 8
    )
    public void custom_sntp_SetToStringWithLenght33_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String custom_sntpBody = response.getBody();
        String custom_sntp = Utils.getResponse("/vb.htm?paratest=custom_sntp").getBody();
        assertFalse("Response should not contain OK", custom_sntpBody.contains("OK"));
        assertTrue("Response should contain NG", custom_sntpBody.contains("NG"));
        assertTrue("Response should contain custom_sntp", custom_sntpBody.contains("custom_sntp"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=custom_sntp"),
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567", "custom_sntp not equal ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=custom_sntp").getBody()
                .replaceAll("\n|\r\n", "").equals("OK custom_sntp="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?custom_sntp=InvalidName()_-,%^&*+=/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 9
    )
    public void custom_sntp_SetToStringWithIncorrectSymbols_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String custom_sntpBody = response.getBody();
        String custom_sntp = Utils.getResponse("/vb.htm?paratest=custom_sntp").getBody();
        assertFalse("Response should not contain OK", custom_sntpBody.contains("OK"));
        assertTrue("Response should contain NG", custom_sntpBody.contains("NG"));
        assertTrue("Response should contain custom_sntp", custom_sntpBody.contains("custom_sntp"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=custom_sntp"),
                "InvalidName()_-,%^&*+=/", "custom_sntp not equal InvalidName()_-,%^&*+=/");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=custom_sntp").getBody()
                .replaceAll("\n|\r\n", "").equals("OK custom_sntp="));
    }
}
