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

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;
import static org.junit.Assert.assertTrue;

@RunWith( HttpJUnitRunner.class )
public class UptimeTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=uptime",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void uptime_Get_ShouldBeMatch(){
        Utils.printResponse(response);
        String uptime = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", uptime.contains("OK"));
        Utils.verifyResponse(response, "uptime", "response contains uptime");
        assertTrue("uptime value is match", uptime.matches("OK\\suptime=\\d{1,3}\\sDays,\\s\\d{1,2}\\sHrs,\\s\\d{1,2}\\sMin"));
    }
}
