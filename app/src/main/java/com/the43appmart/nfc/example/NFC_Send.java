package com.the43appmart.nfc.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NFC_Send extends AppCompatActivity implements
        NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    TextView textInfo;
    EditText textOut;
    NfcAdapter nfcAdapter;
    private SharedPreferences saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nfc__send );
        getSupportActionBar().setTitle( "NfC Sender" );
        textInfo = (TextView) findViewById( R.id.info );
        textOut = (EditText) findViewById( R.id.textout );

        Intent i=new Intent( NFC_Send.this,NFCSendPreview.class );
        startActivity( i );

        nfcAdapter = NfcAdapter.getDefaultAdapter( this );
        if (nfcAdapter == null) {
            Toast.makeText( NFC_Send.this, "NFC not Supported", Toast.LENGTH_LONG ).show();

        } else {
            Toast.makeText( NFC_Send.this, "Set Callback(s)", Toast.LENGTH_LONG ).show();
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

            Intent i=new Intent( NFC_Send.this,NFCSendPreview.class );
            i.putExtra( "strEmail",inMsg );
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
        String stringOut = "null";
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