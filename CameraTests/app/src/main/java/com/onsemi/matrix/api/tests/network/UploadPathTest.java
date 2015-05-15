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
public class UploadPathTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultFTPPath(){
        Utils.setValue("ftppath", "");
    }

    @After
    public void setFTPPathToBlank(){
        Utils.setValue("ftppath", "");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=ftppath",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void ftppath_GetDefaultValue_PathShouldBeBlank(){
        Utils.printResponse(response);
        String ftppath = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftppath.contains("OK"));
        Utils.verifyResponse(response, "ftppath", "response contains ftppath");
        assertTrue("Response equal", response.getBody().replaceAll("\n|\r\n", "").equals("OK ftppath="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=Path",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void ftppath_SetPath_PathShouldBeValid(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppath", "response contains ftppath");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppath"), "ftppath=Path", "ftppath value is Path");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=Path12345",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 2
    )
    public void ftppath_SetPathWithNumbers_PathShouldBeValidWithNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppath", "response contains ftppath");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppath"), "ftppath=Path12345", "ftppath value is Path12345");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=Path._/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 3
    )
    public void ftppath_SetPathWithSpecialSymbols_PathShouldBePathWithSpecialSymbols(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppath", "response contains ftppath");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppath"), "ftppath=Path._/", "ftppath value is Path._/");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=Path._/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 5
    )
    public void ftppath_SetPathWithSpecialSymbolsAndNumbers_PathShouldBePathWithSpecialSymbolsAndNumbers(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppath", "response contains ftppath");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppath"), "ftppath=Path._/", "ftppath value is Path._/");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ.1234567890abcdefghijkl",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 6
    )
    public void ftppath_SetToStringWithLenght128_PathShouldBeStringWithLenght128(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "ftppath", "response contains ftppath");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftppath"),
                "ftppath=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ.1234567890abcdefghijkl",
                "ftppath value is ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ.1234567890abcdefghijkl");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=._/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 7
    )
    public void ftppath_SetToSpecialSymbol_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftppathBody = response.getBody();
        String ftppath = Utils.getResponse("/vb.htm?paratest=ftppath").getBody();
        assertFalse("Response should not contain OK", ftppathBody.contains("OK"));
        assertTrue("Response should contain NG", ftppathBody.contains("NG"));
        assertTrue("Response should contain ftppath", ftppathBody.contains("ftppath"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftppath"),
                "._/", "ftppath not equal ._/");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftppath").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftppath="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ.1234567890abcdefghijklm",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 8
    )
    public void ftppath_SetToStringWithLenght129_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftppathBody = response.getBody();
        String ftppath = Utils.getResponse("/vb.htm?paratest=ftppath").getBody();
        assertFalse("Response should not contain OK", ftppathBody.contains("OK"));
        assertTrue("Response should contain NG", ftppathBody.contains("NG"));
        assertTrue("Response should contain ftppath", ftppathBody.contains("ftppath"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftppath"),
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ.1234567890abcdefghijklm",
                "ftppath not equal ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ.1234567890abcdefghijklm");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftppath").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftppath="));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftppath=Pass()-,%^&*+=/",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 9
    )
    public void ftppath_SetToStringWithIncorrectSymbols_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftppathBody = response.getBody();
        String ftppath = Utils.getResponse("/vb.htm?paratest=ftppath").getBody();
        assertFalse("Response should not contain OK", ftppathBody.contains("OK"));
        assertTrue("Response should contain NG", ftppathBody.contains("NG"));
        assertTrue("Response should contain ftppath", ftppathBody.contains("ftppath"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftppath"),
                "Pass()_-,%^&*+=/", "ftppath not equal Pass()_-,%^&*+=/");
        assertTrue("Response equal", Utils.getResponse("/vb.htm?paratest=ftppath").getBody()
                .replaceAll("\n|\r\n", "").equals("OK ftppath="));
    }
}
