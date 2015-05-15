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
public class SNTPServerListTest {
    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=sntp_list",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void sntp_list_GetDefaultValue_ShouldBeDefaultValue(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        System.out.print(response.getBody().replaceAll("\n|\r\n", ""));
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "sntp_list", "response contains sntp_list");
        assertTrue("sntp_list value is default", response.getBody().replaceAll("\n|\r\n", "")
                .equals("OK sntp_list=" +
                        "1:pool.ntp.org," +
                        "2:asia.pool.ntp.org," +
                        "3:europe.pool.ntp.org," +
                        "4:north-america.pool.ntp.org," +
                        "5:oceania.pool.ntp.org," +
                        "6:south-america.pool.ntp.org," +
                        "7:"));
    }
}
