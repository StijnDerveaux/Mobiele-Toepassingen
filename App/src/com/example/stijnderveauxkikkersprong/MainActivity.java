package com.example.stijnderveauxkikkersprong;

import model.Child;
import model.QrCode;
import model.User;
import service.Facade;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	Facade facade;
	TextView uitvoer;

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			// handle scan result
			String t = scanResult.getContents().toString();
			QrCode qr=splitQRCode(t);
			Child u=(Child)facade.getUser(qr);
		uitvoer.setText(u.getNaam()+u.getVoornaam());
		}
		// else continue with any other code you need in the method

	}

	public QrCode splitQRCode(String text) {
		
		String[] stringParts = text.split("/");
		int number = Integer.parseInt(stringParts[0]);
		QrCode q = null;
		if (stringParts.length > 1) {
			String naam = stringParts[1];
			String voornaam = stringParts[2];
			q=new QrCode(number,naam,voornaam);
		}
		else {
			q=new QrCode(number);
		}
		return q;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		uitvoer = (TextView) findViewById(R.id.uitvoer);
		IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
		integrator.initiateScan();
		facade = Facade.getInstance();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
