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

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;
import static org.junit.Assert.assertTrue;

@RunWith( HttpJUnitRunner.class )
public class DateTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @BeforeClass
    public static void setDate(){
        Utils.setValue("date", "2015/01/01");
    }

    @After
    public void setDateTo2015_01_01(){
        Utils.setValue("date", "2015/01/01");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?paratest=date",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void date_GetDefaultValue_ShouldBe2015_01_01(){
        Utils.printResponse(response);
        String timesynch_mode = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timesynch_mode.contains("OK"));
        Utils.verifyResponse(response, "date", "response contains date");
        Utils.verifyResponse(response, "date=2015/01/01", "default date is 2015/01/01");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?date=24",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void date_SetTo2015_01_01_ValueShouldBe2015_01_01(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "date", "response contains date");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=date"), "date=2015/01/01", "date is 2015/01/01");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?date=1970/01/01",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin")},
            order = 1
    )
    public void date_SetTo1970_01_01_ValueShouldBe1970_01_01(){
        Utils.printResponse(response);
        String timezone = response.getBody();
        assertOk(response);
        assertTrue("Response should contain OK", timezone.contains("OK"));
        Utils.verifyResponse(response, "date", "response contains date");
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=date"), "date=1970/01/01", "date is 1970/01/01");
    }
}
