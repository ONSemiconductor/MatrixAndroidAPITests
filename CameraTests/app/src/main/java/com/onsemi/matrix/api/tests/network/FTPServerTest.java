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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith( HttpJUnitRunner.class )
public class FTPServerTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultFTPServer(){
        Utils.getResponse("/vb.htm?ftpserver=192.168.001.001");
    }

    @After
    public void setFTPServerTo192_168_001_001() {
        Utils.getResponse("/vb.htm?ftpserver=192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=ftpserver",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void ftpserver_GetDefaultValue_ShouldBe192_168_001_001(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "ftpserver=192.168.001.001", "default ftpserver value is 192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=192.168.001.002",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void ftpserver_SetTo192_168_001_002_ShouldBe192_168_001_002(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=192.168.001.002", "ftpserver value is 192.168.001.002");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=TestValidName",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 2
    )
    public void ftpserver_SetToTestValidName_ShouldBeTestValidName(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=TestValidName", "ftpserver value is TestValidName");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=TestValidName@#$.:",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 3
    )
    public void ftpserver_SetToTestValidNameWithSpecialSymbols_ShouldBeValidNameWithSpecialSymbols(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=TestValidName@#$.:", "ftpserver value is TestValidName@#$.:");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=TestValidName123456789",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 4
    )
    public void ftpserver_SetToTestValidNameWithNumbers_ShouldBeValidNameWithNumbersWithNumbers(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=TestValidName123456789", "ftpserver value is TestValidName123456789");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=TestValidName123456789@#$.:",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 5
    )
    public void ftpserver_SetToTestValidNameWithNumbersAndSpecialSymbols_ShouldBeValidTestValidNameWithNumbersAndSpecialSymbols(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=TestValidName123456789@#$.:", "ftpserver value is TestValidName123456789@#$.:");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 6
    )
    public void ftpserver_SetToStringWithLenght40_ShouldBeStringWithLenght40(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn",
                "ftpserver value is ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=A",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 7
    )
    public void ftpserver_SetToStringWithLenght1_ShouldBeStringWithLenght1(){
        Utils.printResponse(response);
        String ftpserver = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", ftpserver.contains("OK"));
        Utils.verifyResponse(response, "ftpserver", "response contains ftpserver");
        Utils.verifyResponse(response, "/vb.htm?paratest=ftpserver=A",
                "ftpserver value is A");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 8)
    public void ftpserver_SetToEmptyString_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpserverBody = response.getBody();
        String ftpserver = Utils.getResponse("/vb.htm?paratest=ftpserver").getBody();
        assertFalse("Response should not contain OK", ftpserverBody.contains("OK"));
        assertTrue("Response should contain NG", ftpserverBody.contains("NG"));
        assertTrue("Response should contain ftpserver", ftpserverBody.contains("ftpserver"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "ftpserver=192.168.001.001", "ftpserver should be 192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=123",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 8)
    public void ftpserver_SetToNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpserverBody = response.getBody();
        String ftpserver = Utils.getResponse("/vb.htm?paratest=ftpserver").getBody();
        assertFalse("Response should not contain OK", ftpserverBody.contains("OK"));
        assertTrue("Response should contain NG", ftpserverBody.contains("NG"));
        assertTrue("Response should contain ftpserver", ftpserverBody.contains("ftpserver"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "123", "ftpserver not equal 123");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "ftpserver=192.168.001.001", "ftpserver should be 192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=@#$.:",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 9)
    public void ftpserver_SetToSymbol_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpserverBody = response.getBody();
        String ftpserver = Utils.getResponse("/vb.htm?paratest=ftpserver").getBody();
        assertFalse("Response should not contain OK", ftpserverBody.contains("OK"));
        assertTrue("Response should contain NG", ftpserverBody.contains("NG"));
        assertTrue("Response should contain ftpserver", ftpserverBody.contains("ftpserver"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "@#$.:", "ftpserver not equal @#$.:");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "ftpserver=192.168.001.001", "ftpserver should be 192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=TestName!?,",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 10)
    public void ftpserver_SetToStringWithInvalidSymbol_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpserverBody = response.getBody();
        String ftpserver = Utils.getResponse("/vb.htm?paratest=ftpserver").getBody();
        assertFalse("Response should not contain OK", ftpserverBody.contains("OK"));
        assertTrue("Response should contain NG", ftpserverBody.contains("NG"));
        assertTrue("Response should contain ftpserver", ftpserverBody.contains("ftpserver"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "TestName!?,", "ftpserver not equal TestName!?,");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "ftpserver=192.168.001.001", "ftpserver should be 192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=192.168.001.256",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 11)
    public void ftpserver_SetToInvalidIP192_168_001_256_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpserverBody = response.getBody();
        String ftpserver = Utils.getResponse("/vb.htm?paratest=ftpserver").getBody();
        assertFalse("Response should not contain OK", ftpserverBody.contains("OK"));
        assertTrue("Response should contain NG", ftpserverBody.contains("NG"));
        assertTrue("Response should contain ftpserver", ftpserverBody.contains("ftpserver"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "192.168.1.256", "ftpserver not equal 192.168.001.256");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "ftpserver=192.168.001.001", "ftpserver should be 192.168.001.001");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?ftpserver=192.168.001.A",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 12)
    public void ftpserver_SetToInvalidIP192_168_001_A_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String ftpserverBody = response.getBody();
        String ftpserver = Utils.getResponse("/vb.htm?paratest=ftpserver").getBody();
        assertFalse("Response should not contain OK", ftpserverBody.contains("OK"));
        assertTrue("Response should contain NG", ftpserverBody.contains("NG"));
        assertTrue("Response should contain ftpserver", ftpserverBody.contains("ftpserver"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "192.168.1.A", "ftpserver not equal 192.168.001.A");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=ftpserver"),
                "ftpserver=192.168.001.001", "ftpserver should be 192.168.001.001");
    }
}
