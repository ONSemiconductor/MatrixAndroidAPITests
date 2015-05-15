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
public class FTPUserNameTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultFTPUserName(){
        Utils.setValue("ftpuser", "");
    }

    @After
    public void setFTPUserNameToBlank(){
        Utils.setValue("ftpuser", "");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=ftpuser",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void ftpuser_GetDefaultValue_UsernameShouldBeBlank(){
        Utils.printResponse(response);
        String ftpuser = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpuser.contains("OK"));
        Utils.verifyResponse(response, "ftpuser", "response contains ftpuser");
        assertTrue("Response equal", response.getBody().replaceAll("\n|\r\n", "").equals("OK ftpuser="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=ValidName",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void ftpuser_SetValidName_UsernameShouldBeValidName(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpuser", "response contains ftpuser");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpuser"), "ftpuser=ValidName", "ftpuser value is ValidName");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=ValidName12345",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 2
    )
    public void ftpuser_SetValidNameWithNumbers_UsernameShouldBeValidNameWithNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpuser", "response contains ftpuser");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpuser"), "ftpuser=ValidName12345", "ftpuser value is ValidName12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=ValidName@#$:.",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 3
    )
    public void ftpuser_SetValidNameWithSpecialSymbols_UsernameShouldBeValidNameWithSpecialSymbols(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpuser", "response contains ftpuser");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpuser"), "ftpuser=ValidName@#$:.", "ftpuser value is ValidName@#$:.");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=ValidName@#$:.12345",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 5
    )
    public void ftpuser_SetValidNameWithSpecialSymbolsAndNumbers_UsernameShouldBeValidNameWithSpecialSymbolsAndNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpuser", "response contains ftpuser");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpuser"), "ftpuser=ValidName@#$:.12345", "ftpuser value is ValidName@#$:.12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=ABCDEFGHIJKLMNOPQRSTUVWXYZ123456",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 6
    )
    public void ftpuser_SetToStringWithLenght32_UsernameShouldBeStringWithLenght32(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftpuser", "response contains ftpuser");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpuser"), "ftpuser=ValidName@#$:.12345", "ftpuser value is ValidName@#$:.12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=@#$:.",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 7
    )
    public void ftpuser_SetToSpecialSymbol_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpuserBody = response.getBody();
        String ftpuser = Utils.getResponse("/vb.htm?paratest=ftpuser").getBody();
        assertFalse("Response should not contain OK", ftpuserBody.contains("OK"));
        assertTrue("Response should contain NG", ftpuserBody.contains("NG"));
        assertTrue("Response should contain ftpuser", ftpuserBody.contains("ftpuser"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpuser"),
                "@#$:.", "ftpuser not equal @#$:.");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftpuser").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftpuser="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 8
    )
    public void ftpuser_SetToStringWithLenght33_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpuserBody = response.getBody();
        String ftpuser = Utils.getResponse("/vb.htm?paratest=ftpuser").getBody();
        assertFalse("Response should not contain OK", ftpuserBody.contains("OK"));
        assertTrue("Response should contain NG", ftpuserBody.contains("NG"));
        assertTrue("Response should contain ftpuser", ftpuserBody.contains("ftpuser"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpuser"),
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567", "ftpuser not equal ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftpuser").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftpuser="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpuser=InvalidName()_-,%^&*+=/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 9
    )
    public void ftpuser_SetToStringWithIncorrectSymbols_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpuserBody = response.getBody();
        String ftpuser = Utils.getResponse("/vb.htm?paratest=ftpuser").getBody();
        assertFalse("Response should not contain OK", ftpuserBody.contains("OK"));
        assertTrue("Response should contain NG", ftpuserBody.contains("NG"));
        assertTrue("Response should contain ftpuser", ftpuserBody.contains("ftpuser"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpuser"),
                "InvalidName()_-,%^&*+=/", "ftpuser not equal InvalidName()_-,%^&*+=/");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftpuser").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftpuser="));
    }
}
