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
public class FTPPasswordTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultFTPPassword(){
        Utils.setValue("ftppassword", "");
    }

    @After
    public void setFTPPasswordPasswordToBlank(){
        Utils.setValue("ftppassword", "");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=ftppassword",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void ftppassword_GetDefaultValue_PasswordShouldBeBlank(){
        Utils.printResponse(response);
        String ftppassword = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftppassword.contains("OK"));
        Utils.verifyResponse(response, "ftppassword", "response contains ftppassword");
        assertTrue("Response equal", response.getBody().replaceAll("\n|\r\n", "").equals("OK ftppassword="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=Password",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void ftppassword_SetPassword_PasswordShouldBeValid(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppassword", "response contains ftppassword");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppassword"), "ftppassword=Password", "ftppassword value is Password");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=Password12345",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 2
    )
    public void ftppassword_SetPasswordWithNumbers_PasswordShouldBeValidWithNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppassword", "response contains ftppassword");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppassword"), "ftppassword=Password12345", "ftppassword value is Password12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=Password@#$:.",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 3
    )
    public void ftppassword_SetPasswordWithSpecialSymbols_PasswordShouldBePasswordWithSpecialSymbols(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppassword", "response contains ftppassword");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppassword"), "ftppassword=Password@#$:.", "ftppassword value is Password@#$:.");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=Password@#$:._^",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 5
    )
    public void ftppassword_SetPasswordWithSpecialSymbolsAndNumbers_PasswordShouldBePasswordWithSpecialSymbolsAndNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppassword", "response contains ftppassword");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppassword"), "ftppassword=Password@#$:._^", "ftppassword value is Password@#$:._^");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=ABCDEFGHIJKLMNOP",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 6
    )
    public void ftppassword_SetToStringWithLenght16_PasswordShouldBeStringWithLenght16(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppassword", "response contains ftppassword");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppassword"), "ftppassword=ABCDEFGHIJKLMNOP", "ftppassword value is ABCDEFGHIJKLMNOP");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=@#$:._^",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 7
    )
    public void ftppassword_SetToSpecialSymbol_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftppasswordBody = response.getBody();
        String ftppassword = Utils.getResponse("/vb.htm?paratest=ftppassword").getBody();
        assertFalse("Response should not contain OK", ftppasswordBody.contains("OK"));
        assertTrue("Response should contain NG", ftppasswordBody.contains("NG"));
        assertTrue("Response should contain ftppassword", ftppasswordBody.contains("ftppassword"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftppassword"),
                "@#$:._^", "ftppassword not equal @#$:._^");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftppassword").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftppassword="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=ABCDEFGHIJKLMNOPQ",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 8
    )
    public void ftppassword_SetToStringWithLenght17_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftppasswordBody = response.getBody();
        String ftppassword = Utils.getResponse("/vb.htm?paratest=ftppassword").getBody();
        assertFalse("Response should not contain OK", ftppasswordBody.contains("OK"));
        assertTrue("Response should contain NG", ftppasswordBody.contains("NG"));
        assertTrue("Response should contain ftppassword", ftppasswordBody.contains("ftppassword"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftppassword"),
                "ABCDEFGHIJKLMNOPQ", "ftppassword not equal ABCDEFGHIJKLMNOPQ");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftppassword").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftppassword="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppassword=Pass()_-,%^&*+=/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 9
    )
    public void ftppassword_SetToStringWithIncorrectSymbols_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftppasswordBody = response.getBody();
        String ftppassword = Utils.getResponse("/vb.htm?paratest=ftppassword").getBody();
        assertFalse("Response should not contain OK", ftppasswordBody.contains("OK"));
        assertTrue("Response should contain NG", ftppasswordBody.contains("NG"));
        assertTrue("Response should contain ftppassword", ftppasswordBody.contains("ftppassword"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftppassword"),
                "Pass()_-,%^&*+=/", "ftppassword not equal Pass()_-,%^&*+=/");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftppassword").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftppassword="));
    }
}
