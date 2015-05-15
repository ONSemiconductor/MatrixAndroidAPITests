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

@RunWith( HttpJUnitRunner.class )
public class NetmaskTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultLanMask(){
        Utils.setValue("lan_mask", "255.255.255.0");
    }

    @After
    public void setLanMaskTo255_255_255_0(){
        Utils.setValue("lan_mask", "255.255.255.0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=lan_mask",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void lan_mask_GetDefaultValue_ShouldBe255_255_255_0(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "lan_mask", "response contains lan_mask");
        Utils.verifyResponse(response, "lan_mask=255.255.255.000", "default lan_mask value is 255.255.255.000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?lan_mask=255.255.254.000",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void lan_mask_SetTo255_255_254_000_ValueShouldBe255_255_254_000(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "lan_mask", "response contains lan_mask");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.254.000", "lan_mask value is 255.255.254.000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?lan_mask=255.255.254.NaN",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void lan_mask_SetTo255_255_254_NaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        Utils.verifyResponse(response, "lan_mask", "response contains lan_mask");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.NaN", "lan_mask value is not 255.255.254.NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.000", "lan_mask value is not 255.255.254.000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?lan_mask=255.255.254.",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3
    )
    public void lan_mask_SetTo255_255_254_EmptyString_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        Utils.verifyResponse(response, "lan_mask", "response contains lan_mask");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.000", "lan_mask value is 255.255.255.000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?lan_mask=255.255.254.-1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4
    )
    public void lan_mask_SetTo255_255_254_NegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        Utils.verifyResponse(response, "lan_mask", "response contains lan_mask");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.-1", "lan_mask value is 255.255.255.-1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.000", "lan_mask value is 255.255.255.000");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?lan_mask=255.255.255.256",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5
    )
    public void lan_mask_SetTo255_255_255_256_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        Utils.verifyResponse(response, "lan_mask", "response contains lan_mask");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.256", "lan_mask value is 255.255.255.256");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=lan_mask"), "lan_mask=255.255.255.000", "lan_mask value is 255.255.255.000");
    }
}
