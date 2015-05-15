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
public class AlarmLevelTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDefaultAlarmLevel(){
        Utils.getResponse("/vb.htm?alarmlevel=50");
    }

    @After
    public void setAlarmLevelTo50(){
        Utils.getResponse("/vb.htm?alarmlevel=50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=alarmlevel",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void alarmlevel_GetDefaultValue_ShouldBe50(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "alarmlevel", "response contains alarmlevel");
        Utils.verifyResponse(response, "alarmlevel=50", "default audioin_enable value is 50");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?alarmlevel=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void alarmlevel_SetTo0_ValueShouldBe0(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "alarmlevel", "response contains alarmlevel");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=alarmlevel"), "alarmlevel=0", "alarmlevel value is 0");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?alarmlevel=100",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 2
    )
    public void alarmlevel_SetTo100_ValueShouldBe100(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", alarmlevel.contains("OK"));
        Utils.verifyResponse(response, "alarmlevel", "response contains alarmlevel");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=alarmlevel"), "alarmlevel=100", "alarmlevel value is 100");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?alarmlevel=NaN",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 3)
    public void alarmlevel_SetToNaN_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        String alarmLevelResponse = Utils.getResponse("/vb.htm?paratest=alarmlevel").getBody();
        assertFalse("Response should not contain OK", alarmlevel.contains("OK"));
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        assertTrue("Response should contain alarmlevel", alarmlevel.contains("alarmlevel"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=alarmlevel"), "NaN", "alarmlevel not equal NaN");
        assertTrue("alarmlevel should be 50", alarmLevelResponse.contains("alarmlevel=50"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?alarmlevel=101",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 4)
    public void alarmlevel_SetTo101_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        String alarmLevelResponse = Utils.getResponse("/vb.htm?paratest=alarmlevel").getBody();
        assertFalse("Response should not contain OK", alarmlevel.contains("OK"));
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        assertTrue("Response should contain alarmlevel", alarmlevel.contains("alarmlevel"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=alarmlevel"), "101", "alarmlevel not equal 101");
        assertTrue("alarmlevel should be 50", alarmLevelResponse.contains("alarmlevel=50"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?alarmlevel=-1",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 5)
    public void alarmlevel_SetToNegativeNumber_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        String alarmLevelResponse = Utils.getResponse("/vb.htm?paratest=alarmlevel").getBody();
        assertFalse("Response should not contain OK", alarmlevel.contains("OK"));
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        assertTrue("Response should contain alarmlevel", alarmlevel.contains("alarmlevel"));
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=alarmlevel"), "-1", "alarmlevel not equal -1");
        assertTrue("alarmlevel should be 50", alarmLevelResponse.contains("alarmlevel=50"));
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?alarmlevel=",
            authentications = {@Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 6)
    public void alarmlevel_SetToEmpty_ResponseShouldContainNG(){
        Utils.printResponse(response);
        String alarmlevel = response.getBody();
        String alarmLevelResponse = Utils.getResponse("/vb.htm?paratest=alarmlevel").getBody();
        assertFalse("Response should not contain OK", alarmlevel.contains("OK"));
        assertTrue("Response should contain NG", alarmlevel.contains("NG"));
        assertTrue("Response should contain alarmlevel", alarmlevel.contains("alarmlevel"));
        assertTrue("alarmlevel should be 50", alarmLevelResponse.contains("alarmlevel=50"));
    }
}
