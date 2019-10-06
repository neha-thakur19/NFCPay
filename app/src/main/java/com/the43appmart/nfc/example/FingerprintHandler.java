package com.the43appmart.nfc.example;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

/**
 * Created by whit3hawks on 11/16/16.
 */
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private SharedPreferences saveUser;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission( context, Manifest.permission.USE_FINGERPRINT ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate( cryptoObject, cancellationSignal, 0, this, null );
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update( "Fingerprint Authentication error\n" + errString );
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update( "Fingerprint Authentication help\n" + helpString );
    }

    @Override
    public void onAuthenticationFailed() {
        this.update( "Fingerprint Authentication failed." );
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        ((Activity) context).finish();
        Intent intent = new Intent( context, NFC_Send.class );
        context.startActivity( intent );

    }

//    private void SendMoney() {
//        saveUser = PreferenceManager.getDefaultSharedPreferences( context);
//        final String sender_email = saveUser.getString( "strEmail", "" );
//        final String rec_email = saveUser.getString( "strSendRec_Email", "" );;
//        final String rec_amt =saveUser.getString( "strSendRec_amt", "" );
//
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonResponse = new JSONObject( response );
//                    String success = jsonResponse.getString( "success" );
//
//                    if (success.equals( "TRANSFERED" )) {
//                        Intent intent = new Intent( context, SendSuccess.class );
//                      context.startActivity( intent );
//                    } else if (success.equals( "NOBAL" )) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder( context );
//                        builder.setMessage( "Insufficient Balance in your Wallete" )
//                                .setNegativeButton( "Try Again..!", null )
//                                .create()
//                                .show();
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder( context );
//                        builder.setMessage( "Something goes wrong" )
//                                .setNegativeButton( "Try Again..!", null )
//                                .create()
//                                .show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        SendMoneyRequest sendMoneyRequest = new SendMoneyRequest( sender_email, rec_email, rec_amt, responseListener );
//        RequestQueue queue = Volley.newRequestQueue( context );
//        queue.add( sendMoneyRequest );
//
//    }

    private void update(String e) {
        TextView textView = (TextView) ((Activity) context).findViewById( R.id.errorText );
        textView.setText( e );
    }

}
