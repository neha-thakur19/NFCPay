package com.the43appmart.nfc.example;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.the43appmart.nfc.example.Domain.DOMAIN_URL;

/**
 * Created by Aniruddha on 19/12/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL =DOMAIN_URL+"/NFCPay/LoginRequest.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
