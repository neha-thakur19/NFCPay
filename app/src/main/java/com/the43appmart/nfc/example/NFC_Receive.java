package com.the43appmart.nfc.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NFC_Receive extends AppCompatActivity implements
        NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {
    TextView textInfo;
    EditText textOut;
    NfcAdapter nfcAdapter;
    private SharedPreferences saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nfc__receive );
        getSupportActionBar().setTitle( "NfC Receiver" );
        textInfo = (TextView) findViewById( R.id.info );
        textOut = (EditText) findViewById( R.id.textout );

        nfcAdapter = NfcAdapter.getDefaultAdapter( this );
        if (nfcAdapter == null) {
            Toast.makeText( NFC_Receive.this, "NFC Not Supported", Toast.LENGTH_LONG ).show();

            saveUser = PreferenceManager.getDefaultSharedPreferences( this );
            String Email = saveUser.getString( "strEmail", "" );

        } else {
            Toast.makeText( NFC_Receive.this, "Set Callback(s)", Toast.LENGTH_LONG ).show();
            nfcAdapter.setNdefPushMessageCallback( this, this );
            nfcAdapter.setOnNdefPushCompleteCallback( this, this );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if (action == null) {
            Toast.makeText( this, "NFC device not found", Toast.LENGTH_SHORT ).show();
        } else if (action.equals( NfcAdapter.ACTION_NDEF_DISCOVERED )) {
            Parcelable[] parcelables =
                    intent.getParcelableArrayExtra(
                            NfcAdapter.EXTRA_NDEF_MESSAGES );
            NdefMessage inNdefMessage = (NdefMessage) parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord NdefRecord_0 = inNdefRecords[0];
            String inMsg = new String( NdefRecord_0.getPayload() );
            textInfo.setText( inMsg );

            Intent i = new Intent( NFC_Receive.this, NFCSendPreview.class );
            i.putExtra( "strEmail", inMsg );
            startActivity( i );

        } else {
            Toast.makeText( this, "No Data Found", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent( intent );
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread( new Runnable() {

            @Override
            public void run() {
                Toast.makeText( getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG ).show();
            }
        } );
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        saveUser = PreferenceManager.getDefaultSharedPreferences( this );
        String Email = saveUser.getString( "strEmail", "" );
        String stringOut = Email;
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[]{},
                bytesOut );

        NdefMessage ndefMessageout = new NdefMessage( ndefRecordOut );
        return ndefMessageout;
    }
}