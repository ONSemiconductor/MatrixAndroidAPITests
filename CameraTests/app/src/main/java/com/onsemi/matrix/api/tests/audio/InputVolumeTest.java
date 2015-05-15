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
public class InputVolumeTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultAudioInVolume(){
        Utils.setValue("audioinvolume", "50");
    }

    @After
    public void setAudioInVolumeTo50(){
        Utils.setValue("audioinvolume", "50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=audioinvolume",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void audioinvolume_GetDefaultValue_ShouldBe50(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audioinvolume=50", "default audioin_enable value is 50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioinvolume=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
     public void audioinvolume_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audioinvolume", "response contains audioinvolume");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioinvolume"),
                "audioinvolume=0", "audioinvolume value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioinvolume=100",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void audioinvolume_SetTo100_ValueShouldBe100(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "audioinvolume", "response contains audioinvolume");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioinvolume"),
                "audioinvolume=100", "audioinvolume value is 100");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioinvolume=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void audioinvolume_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioinvolume = response.getBody();
        String audioinvolumeResponse = Utils.getResponse("/vb.htm?paratest=audioinvolume").getBody();
        assertFalse("Response should not contain OK", audioinvolumeResponse.contains("OK"));
        assertTrue("Response should contain NG", audioinvolumeResponse.contains("NG"));
        assertTrue("Response should contain audioinvolume", audioinvolumeResponse.contains("audioinvolume"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audioinvolume"),
                "NaN", "audioinvolume not equal NaN");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioinvolume"),
                "audioinvolume=0", "audioinvolume should be 50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioinvolume=101",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void audioinvolume_SetTo101_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioinvolume = response.getBody();
        String audioinvolumeResponse = Utils.getResponse("/vb.htm?paratest=audioinvolume").getBody();
        assertFalse("Response should not contain OK", audioinvolumeResponse.contains("OK"));
        assertTrue("Response should contain NG", audioinvolumeResponse.contains("NG"));
        assertTrue("Response should contain audioinvolume", audioinvolumeResponse.contains("audioinvolume"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audioinvolume"), "101", "audioinvolume not equal 101");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioinvolume"), "audioinvolume=0", "audioinvolume should be 50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioinvolume=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void audioinvolume_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioinvolume = response.getBody();
        String audioinvolumeResponse = Utils.getResponse("/vb.htm?paratest=audioinvolume").getBody();
        assertFalse("Response should not contain OK", audioinvolumeResponse.contains("OK"));
        assertTrue("Response should contain NG", audioinvolumeResponse.contains("NG"));
        assertTrue("Response should contain audioinvolume", audioinvolumeResponse.contains("audioinvolume"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audioinvolume"), "-1", "audioinvolume not equal -1");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioinvolume"), "audioinvolume=0", "audioinvolume should be 50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audioinvolume=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 6)
    public void audioinvolume_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String audioinvolume = response.getBody();
        String audioinvolumeResponse = Utils.getResponse("/vb.htm?paratest=audioinvolume").getBody();
        assertFalse("Response should not contain OK", audioinvolumeResponse.contains("OK"));
        assertTrue("Response should contain NG", audioinvolumeResponse.contains("NG"));
        assertTrue("Response should contain audioinvolume", audioinvolumeResponse.contains("audioinvolume"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audioinvolume"), "audioinvolume=0", "audioinvolume should be 50");
    }
}
