package com.gsbina.deviceowneractivator;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		nfcAdapter.setNdefPushMessageCallback(new CreateProvisioningNdefMessageCallback(), this);
	}

}
