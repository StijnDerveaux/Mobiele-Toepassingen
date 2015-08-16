package com.example.stijnderveauxkikkersprong;

import model.Aanwezigheden;
import model.Bedragen;
import model.Child;
import model.QrCode;
import model.User;
import service.Facade;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import domain.DbConnector;

public class MainActivity extends ActionBarActivity {
	private Facade facade;
	private Child c;
	private boolean network;
	private final Map<Integer, Child> users = new HashMap<>();
	private List<QrCode> codes = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		// voor offline
		//initQrCodes();
		IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
		integrator.initiateScan();
	
		facade = Facade.getInstance();
		network = haveNetworkConnection();
		if (!facade.isFetched()) {
			if (haveNetworkConnection()) {
				getTasks();
			}
		}
	}

	private void initQrCodes() {
		QrCode q = new QrCode(1, "Olive", "Nikki");
		QrCode q1 = new QrCode(2, "Derveaux", "Stijn");
		QrCode q2 = new QrCode(3, "De Held", "Steve");
		QrCode q3 = new QrCode(4, "Boonen", "Tom");
		QrCode q4 = new QrCode(5, "Boonen", "Rudy");
		QrCode q5 = new QrCode(6, "Peeters", "Hans");
		QrCode q6 = new QrCode(7, "Suarez", "Matias");
		codes.add(q);
		codes.add(q2);
		codes.add(q3);
		codes.add(q4);
		codes.add(q5);
		codes.add(q6);

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

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult.getContents() != null) {
			// handle scan result
			String t = scanResult.getContents().toString();
			QrCode qr = splitQRCode(t);
			if (qr != null) {
				if (qr.getCode().equals("-1")) {
					
					toAdmin(-1);
					
				} else {
					
					if(network){
					c = (Child) facade.getUser(qr);
					String[] stringParts = qr.getCode().split("/");

					int nummer = Integer.parseInt(stringParts[0]);
					// c = users.get(nummer);

					toChild(c.getNumber());
					}
					else{
						String[] stringParts = qr.getCode().split("/");
						int nummer = Integer.parseInt(stringParts[0]);
						String naam = stringParts[1];
						String voornaam = stringParts[2];
						Intent intents = new Intent(this, ChildActivity.class);
						intents.putExtra("number", nummer);
						intents.putExtra("naam", naam);
						intents.putExtra("voornaam", voornaam);
						startActivity(intents);
					}
					
					
				}
			}
			// else continue with any other code you need in the method

		}
		else{
			finish();
		}
		
	}

	public void toChild(int number) {
		Intent intent = new Intent(this, ChildActivity.class);
		intent.putExtra("number", number);
		startActivity(intent);
	}

	public void toAdmin(int number) {
		Intent intent = new Intent(this, AdminActivity.class);

		startActivity(intent);
	}

	public QrCode splitQRCode(String text) {

		String[] stringParts = text.split("/");
		int number = Integer.parseInt(stringParts[0]);
		QrCode q = null;
		if (stringParts.length > 1) {
			String naam = stringParts[1];
			String voornaam = stringParts[2];
			q = new QrCode(number, naam, voornaam);
		} else {
			q = new QrCode(number);
		}
		return q;
	}

	private void setAan(JSONArray arrayAanwezigheden) {

		for (int i = 0; i < arrayAanwezigheden.length(); i++) {

			JSONObject json = null;
			try {
				json = arrayAanwezigheden.getJSONObject(i);
				int GbN = json.getInt("GbNummer");
				Child child = (Child) facade.getUser(GbN);
				Aanwezigheden aan = new Aanwezigheden(json.getInt("Dag"), json.getString("Maand"),
						json.getInt("AankomstUur"), json.getInt("VertrekUur"));
				child.addAanwezigheid(aan);
				facade.updateUser(child);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	private void setBedragen(JSONArray arrayBedragen) {
		for (int i = 0; i < arrayBedragen.length(); i++) {

			JSONObject json = null;
			try {
				json = arrayBedragen.getJSONObject(i);
				int GbN = json.getInt("GbNummer");
				Child child = (Child) facade.getUser(GbN);

				int be = json.getInt("Betaald");
				boolean betaald = false;
				if (be == 1) {
					betaald = true;
				}
				String month = json.getString("Maand");
				int bedrag = json.getInt("Bedrag");
				Bedragen b = new Bedragen(month, bedrag, betaald);

				child.addBedrag2(b);
				facade.updateUser(child);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	public void setChilds(JSONArray jsonArray) {

		for (int i = 0; i < jsonArray.length(); i++) {
			Child ch = new Child("", "");
			JSONObject json = null;
			try {
				json = jsonArray.getJSONObject(i);

				ch.setNaam(json.getString("Naam"));
				ch.setVoornaam(json.getString("Voornaam"));
				ch.setNumber(json.getInt("GbNummer"));

				// setAan(c);
				// setBedragen(c);
				// users.put(c.getNumber(), c);
				facade.addUser(ch);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
	


	private void getTasks() {
		new GetAllUsersTask().execute(new DbConnector());
		new GetAllBedragenTask().execute(new DbConnector());
		new GetAllAanwezighedenTask().execute(new DbConnector());
		facade.setFetched(true);
	}

	private class GetAllUsersTask extends AsyncTask<DbConnector, Long, JSONArray> {

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			// setTextToTextView(jsonArray);
			setChilds(jsonArray);

		}

		@Override
		protected JSONArray doInBackground(DbConnector... params) {
			// TODO Auto-generated method stub
			return params[0].GetAllUsers();
		}
	}

	private class GetAllAanwezighedenTask extends AsyncTask<DbConnector, Long, JSONArray> {

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			// setTextToTextView(jsonArray);
			setAan(jsonArray);
			;

		}

		@Override
		protected JSONArray doInBackground(DbConnector... params) {
			// TODO Auto-generated method stub
			return params[0].GetAllAanwezigheden();
		}
	}

	private class GetAllBedragenTask extends AsyncTask<DbConnector, Long, JSONArray> {

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			// setTextToTextView(jsonArray);
			setBedragen(jsonArray);

		}

		@Override
		protected JSONArray doInBackground(DbConnector... params) {
			// TODO Auto-generated method stub
			return params[0].GetAllBedragen();
		}
	}
	

}
