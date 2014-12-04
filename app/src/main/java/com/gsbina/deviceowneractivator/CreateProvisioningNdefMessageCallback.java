package com.gsbina.deviceowneractivator;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class CreateProvisioningNdefMessageCallback implements NfcAdapter.CreateNdefMessageCallback {

	private static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME
			= "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME";
	private static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION
			= "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";
	private static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM
			= "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM";
	private static final String MIME_TYPE_PROVISIONING_NFC
			= "application/com.android.managedprovisioning";

	private static final String PACKAGE_DEVICE_OWNER = "";
	private static final String APK_URL = "";
	private static final String APK_CHECKSUM = "";

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		Properties properties = new Properties();
		properties.setProperty(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME, PACKAGE_DEVICE_OWNER);
		properties.setProperty(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION, APK_URL);
		properties.setProperty(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM, APK_CHECKSUM);

		ByteArrayOutputStream bos = null;
		OutputStream out = null;
		try {
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			properties.store(out, "");
			byte[] bytes = bos.toByteArray();
			return new NdefMessage(NdefRecord.createMime(MIME_TYPE_PROVISIONING_NFC, bytes));
		} catch (Exception e) {
			Log.w("DeviceOwnerActivator", e);
		} finally {
			close(out);
			close(bos);
		}

		return null;
	}

	private void close(Closeable closeable) {
		if (closeable == null) {
			return;
		}

		try {
			closeable.close();
		} catch (IOException ignored) {
		}
	}
}
