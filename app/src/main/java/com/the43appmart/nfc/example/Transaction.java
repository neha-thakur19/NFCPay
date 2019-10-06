package com.the43appmart.nfc.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Aniruddha on 24/02/2018.
 */

public class Transaction extends Fragment {
    Button btnSendMoney, btnReceiveMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.transaction, container, false );
        btnSendMoney = (Button) view.findViewById( R.id.btnSendMoney );
        btnReceiveMoney = (Button) view.findViewById( R.id.btnReceiveMoney );

        btnSendMoney.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent( getContext(), FingerprintActivity.class );
//                I.setAction("android.nfc.action.NDEF_DISCOVERED");
                startActivity( I );
            }
        } );
        btnReceiveMoney.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent( getContext(), NFC_Receive.class );
//                I.setAction("android.nfc.action.NDEF_DISCOVERED");
                startActivity( I );
            }
        } );
        return view;
    }
}
