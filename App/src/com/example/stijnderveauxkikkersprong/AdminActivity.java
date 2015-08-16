package com.example.stijnderveauxkikkersprong;

import model.Bedragen;
import model.Child;
import model.User;
import service.Facade;

import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import domain.UploadFile;

public class AdminActivity extends ActionBarActivity implements View.OnClickListener {

	private Facade facade;

	private TableLayout table;
	private Button factuur;
	private Button aanmaning;
	private Button login;
	private static final String APP_KEY = "dsdwkslphr3p0wj";
	private static final String APP_SECRET = "3wl2xtz5q9dwvnd";
	private DropboxAPI<AndroidAuthSession> mDBApi;
	private final static String DROPBOX_DIR = "/files/";
	private final static String DROPBOX_NAME = "dropbox_prefs";
	private final static AccessType ACCESS_TYPE = AccessType.DROPBOX;
	private boolean isLoggedIn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_admin);
		// We create a new AuthSession so that we can use the Dropbox API.
		if (haveNetworkConnection()) {
			initLayoutComponents();
		} else {
			Toast.makeText(getApplicationContext(), "Een admin heeft netwerk connectie nodig! Log in op een netwerk",
					Toast.LENGTH_LONG).show();
			onBackPressed();
		}

	}

	@SuppressWarnings("deprecation")
	public void onClick(View v) {

		if (v.equals(login)) {
			if (isLoggedIn) {
				mDBApi.getSession().unlink();
				setLoggedIn(false);
			} else {
				((AndroidAuthSession) mDBApi.getSession()).startAuthentication(AdminActivity.this);
			}

		}
		if (v.equals(factuur)) {
			UploadFile fac = new UploadFile(this, mDBApi, DROPBOX_DIR, "factuur");
			fac.execute();

		}
		if (v.equals(aanmaning)) {
			UploadFile fac = new UploadFile(this, mDBApi, DROPBOX_DIR, "aanmaning");
			fac.execute();

		}

		if (v.getId() > 0) {
			Button b = (Button) v;
			if (b.getText().equals("Aanwezigheden")) {
				Intent i = new Intent(this, AdminAanwezighedenActivity.class);

				i.putExtra("number", v.getId());
				startActivity(i);
			}
			if (b.getText().equals("Bedragen")) {
				Intent i = new Intent(this, AdminBedragenActivity.class);

				i.putExtra("number", v.getId());
				startActivity(i);
			}

		}
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

	private void initLayoutComponents() {
		// TODO Auto-generated method stub
		facade = Facade.getInstance();
		factuur = (Button) findViewById(R.id.btnFacturen);
		aanmaning = (Button) findViewById(R.id.btnAanmaningen);
		table = (TableLayout) findViewById(R.id.TableLayout);
		login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(this);
		aanmaning.setOnClickListener(this);
		factuur.setOnClickListener(this);
		generateTable();
		setLoggedIn(false);
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session;
		SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
		String key = prefs.getString(APP_KEY, null);
		String secret = prefs.getString(APP_SECRET, null);
		if (key != null && secret != null) {
			AccessTokenPair token = new AccessTokenPair(key, secret);
			session = new AndroidAuthSession(appKeys, ACCESS_TYPE, token);
		} else {
			session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		}

		mDBApi = new DropboxAPI(session);
		// setLoggedIn(isLoggedIn);

		setLoggedIn(mDBApi.getSession().isLinked());
	}

	private void generateTable() {

		for (User u : facade.getUsers()) {
			Child c = (Child) u;

			TableRow tbrow = new TableRow(this);

			TextView t1v = new TextView(this);
			t1v.setText(c.getNaam() + " " + c.getVoornaam());
			t1v.setGravity(Gravity.CENTER);
			tbrow.addView(t1v);

			CheckBox check = new CheckBox(this);

			check.setChecked(openstaande(c));
			check.setEnabled(false);
			check.setGravity(Gravity.CENTER);
			tbrow.addView(check);
			Button btnAanwezigheden = new Button(this);

			btnAanwezigheden.setText("Aanwezigheden");
			btnAanwezigheden.setId(c.getNumber());

			btnAanwezigheden.setOnClickListener(this);
			tbrow.addView(btnAanwezigheden);

			Button btnBedragen = new Button(this);

			btnBedragen.setText("Bedragen");
			btnBedragen.setId(c.getNumber());

			btnBedragen.setOnClickListener(this);
			tbrow.addView(btnBedragen);

			table.addView(tbrow);
		}

	}

	private boolean openstaande(Child c) {
		boolean waar = false;
		for (Bedragen b : c.getBedragen()) {
			if (!b.isBetaald()) {
				waar = true;
			}
		}

		return waar;

	}

	private void setLoggedIn(boolean loggedIn) {
		isLoggedIn = loggedIn;

		factuur.setEnabled(isLoggedIn);
		aanmaning.setEnabled(isLoggedIn);
		login.setText(isLoggedIn ? "Logout" : "Log in ");

	}

	@Override
	public void onResume() {
		super.onResume();

		AndroidAuthSession session = mDBApi.getSession();
		if (session.authenticationSuccessful()) {
			try {
				// Required to complete auth, sets the access token on the
				// session
				session.finishAuthentication();
				TokenPair tokens = session.getAccessTokenPair();
				SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
				Editor editor = prefs.edit();
				editor.putString(APP_KEY, tokens.key);
				editor.putString(APP_SECRET, tokens.secret);
				editor.commit();
				setLoggedIn(true);

			} catch (IllegalStateException e) {
				Toast.makeText(this, "Error during dropbox auth", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}