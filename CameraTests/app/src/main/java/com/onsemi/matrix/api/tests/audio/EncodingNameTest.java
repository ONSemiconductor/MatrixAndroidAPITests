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
public class EncodingNameTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;


    @HttpTest(method = Method.GET,
            path = "/vb.htm?encodingname=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void encodingname_GetValueWhereBitRateIsDefault_ShouldBeG711(){
        Utils.printResponse(response);
        assertOk(Utils.getResponse("/vb.htm?paratest=encodingname"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encodingname"), "G711", "encodingname value is G711");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=encodingname"), "AAC-LC", "encodingname value not contain AAC-LC");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?encodingname=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void encodingname_GetValueWhereBitRateIs1_ShouldBeAAC_LC(){
        Utils.printResponse(response);
        assertOk(Utils.getResponse("/vb.htm?paratest=encodingname"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=encodingname"), "AAC-LC", "encodingname value is AAC-LC");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=encodingname"), "G711", "encodingname value not contain AAC-LC");
    }
}
