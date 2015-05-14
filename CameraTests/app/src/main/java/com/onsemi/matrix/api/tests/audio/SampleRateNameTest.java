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

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;

@RunWith( HttpJUnitRunner.class )
public class SampleRateNameTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @HttpTest(method = Method.GET,
            path = "/vb.htm?sampleratename=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void sampleratename_GetValueWhereBitRateIsDefault_ShouldBe8Khz(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=sampleratename"), "8Khz", "sampleratename value is 8Khz");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=sampleratename"), "16Khz", "sampleratename value is not 16Khz");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?sampleratename=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void sampleratename_GetValueWhereBitRateIs1_ShouldBe16Khz(){
        Utils.printResponse(response);
        assertOk(response);
        Utils.verifyResponse(response, "16Khz", "sampleratename value is 16Khz");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=sampleratename"), "8Khz", "sampleratename value is not 8Khz");
    }
}
