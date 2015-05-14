package com.onsemi.matrix.api.tests.audio;

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
public class EncodingTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultEncoding(){
        Utils.setValue("encoding", "0");
    }

    @After
    public void setEncodingTo0(){
        Utils.setValue("encoding", "0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=encoding",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void encoding_GetDefaultValue_ShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "encoding=0", "default encoding value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encoding=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void encoding_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "encoding", "response contains encoding");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encoding"), "encoding=0", "encoding value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encoding=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void encoding_SetTo1_ValueShouldBe1(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "encoding", "response contains encoding");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encoding"), "encoding=1", "encoding value is 1");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encoding=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void encoding_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String encoding = response.getBody();
        assertFalse("Response should not contain OK", encoding.contains("OK"));
        assertTrue("Response should contain NG", encoding.contains("NG"));
        assertTrue("Response should contain encoding", encoding.contains("encoding"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=encoding"), "NaN", "encoding not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encoding"), "encoding=0", "Encoding should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encoding=3",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void encoding_SetTo3_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String encoding = response.getBody();
        assertFalse("Response should not contain OK", encoding.contains("OK"));
        assertTrue("Response should contain NG", encoding.contains("NG"));
        assertTrue("Response should contain encoding", encoding.contains("encoding"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=encoding"), "3", "encoding not equal 3");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encoding"), "encoding=0", "Encoding should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encoding=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void encoding_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String encoding = response.getBody();
        assertFalse("Response should not contain OK", encoding.contains("OK"));
        assertTrue("Response should contain NG", encoding.contains("NG"));
        assertTrue("Response should contain encoding", encoding.contains("encoding"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=encoding"), "-1", "encoding not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encoding"), "encoding=0", "Encoding should be 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encoding=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 6)
    public void encoding_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String encoding = response.getBody();
        String encodingResponse = Utils.getResponse("/vb.htm?paratest=encoding").getBody();
        assertFalse("Response should not contain OK", encoding.contains("OK"));
        assertTrue("Response should contain NG", encoding.contains("NG"));
        assertTrue("Response should contain encoding", encoding.contains("encoding"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encoding"), "encoding=0", "Encoding should be 0");
    }
}
