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
public class AudioBitRateNameTest {

    @Rule
    public Destination restfuse = new Destination( this, Settings.getHostname() );
    
    @Rule
	public Timeout timeout = new Timeout(Settings.getDefaultTimeout());

    @Context
    private Response response;

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiobitrate=0",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 0
    )
    public void audiobitratename_GetValueWhereAudioBitRateIsDefault_ShouldBe24Kbps(){
        Utils.printResponse(Utils.getResponse("/vb.htm?paratest=audiobitratename"));
        assertOk(Utils.getResponse("/vb.htm?paratest=audiobitratename"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "audiobitratename=24Kbps", "audiobitratename value is 24Kbps");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "36Kbps", "audiobitratename value is not 36Kbps");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "48Kbps", "audiobitratename value is not 48Kbps");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiobitrate=1",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 1
    )
    public void audiobitratename_GetValueWhereAudioBitRateIs1_ShouldBe36Kbps(){
        Utils.printResponse(Utils.getResponse("/vb.htm?paratest=audiobitratename"));
        assertOk(Utils.getResponse("/vb.htm?paratest=audiobitratename"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "audiobitratename=36Kbps", "audiobitratename value is 36Kbps");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "24Kbps", "audiobitratename value is not 24Kbps");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "48Kbps", "audiobitratename value is not 48Kbps");
    }

    @HttpTest(method = Method.GET,
            path = "/vb.htm?audiobitrate=2",
            authentications = { @Authentication( type = BASIC, user = "admin", password = "admin" ) },
            order = 2
    )
    public void audiobitratename_GetValueWhereAudioBitRateIs2_ShouldBe48Kbps(){
        Utils.printResponse(Utils.getResponse("/vb.htm?paratest=audiobitratename"));
        assertOk(Utils.getResponse("/vb.htm?paratest=audiobitratename"));
        Utils.verifyResponse(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "audiobitratename=48Kbps", "audiobitratename value is 48Kbps");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "36Kbps", "audiobitratename value is not 36Kbps");
        Utils.verifyResponseNonContainString(Utils.getResponse("/vb.htm?paratest=audiobitratename"), "24Kbps", "audiobitratename value is not 24Kbps");
    }
}
