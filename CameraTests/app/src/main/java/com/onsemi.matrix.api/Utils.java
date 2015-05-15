package com.onsemi.matrix.api;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.internal.AuthenticationInfo;
import com.eclipsesource.restfuse.internal.InternalRequest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import com.onsemi.matrix.api.Settings;

public class Utils {

    public static void printResponse(Response response){
        System.out.println("Status=" + response.getStatus());
        if (response.hasBody()) {
            System.out.println("Body=" + response.getBody());
        }
        System.out.println("mediaType=" + response.getType());
        System.out.println("===========");
    }

    public static void verifyResponse(Response response, String verifystr){
        String body = response.getBody();
        assertTrue(body.contains(verifystr));
    }

    public static void verifyResponse(Response response, String verifystr, String assertMessage){
        String body = response.getBody();
        assertTrue(assertMessage, body.contains(verifystr));
    }

    public static void verifyResponseNonContainString(Response response, String verifystr, String assertMessage){
        String body = response.getBody();
        assertFalse(assertMessage, body.contains(verifystr));
    }

    private static String getRequestString(String key, String value){
        return "/vb.htm?" + key + "=" + value;
    }

    private static void getRequest(String requestString) {
        AuthenticationInfo authenticationInfoInfo = new AuthenticationInfo(AuthenticationType.BASIC, "admin", "admin");
        InternalRequest request = new InternalRequest(Settings.getHostname() + requestString);
        request.addAuthenticationInfo(authenticationInfoInfo);
        request.get();
    }

    public static Response getResponse(String requestString) {
        AuthenticationInfo authenticationInfoInfo = new AuthenticationInfo(AuthenticationType.BASIC, "admin", "admin");
        InternalRequest request = new InternalRequest(Settings.getHostname() + requestString);
        request.addAuthenticationInfo(authenticationInfoInfo);
        System.out.print(requestString + "  :  " + request.get().getBody());
        return request.get();
    }

    public static void setValue(String setting, String value){
        try {
            String requestString = getRequestString(setting, value);
            getRequest(requestString);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
