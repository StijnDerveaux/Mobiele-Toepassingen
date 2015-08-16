package com.example.stijnderveauxkikkersprong;

import service.Facade;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.apache.http.client.ClientProtocolException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import domain.DbConnector;
import model.Aanwezigheden;
import model.Child;
import android.view.View.OnClickListener;

public class ChildActivity extends ActionBarActivity {
	Facade facade;
	private int number;
	private Button aankomst;
	private Button vertrek;
	private Button aanwezigheden;
	private Button bedragen;
	private TextView text;
	private static Aanwezigheden aan;
	private TextView file;
	private String vnaam;

	InputStream is = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_child);

		initLayoutComponents();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(aankomst)) {
			registerAankomst();
			openDialog("Aankomst");

		}
		if (v.equals(vertrek)) {

			registerVertrek();
			openDialog("Vertrek");
			try {
				if (haveNetworkConnection()) {
					aanwezigheden.setEnabled(true);
					bedragen.setEnabled(true);
					uploadAan();
				}

			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (v.equals(aanwezigheden)) {

			Intent i = new Intent(this, AanwezighedenActivity.class);

			i.putExtra("number", number);
			startActivity(i);

		}
		if (v.equals(bedragen)) {

			Intent i = new Intent(this, BedragenActivity.class);

			i.putExtra("number", number);
			startActivity(i);

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

	private void openDialog(String tekst) {
		String naam = "";
		if (haveNetworkConnection()) {
			naam = facade.getVoornaam(facade.getUser(number));
		} else {
			naam = vnaam;
		}

		CustomDialogClass cdd = new CustomDialogClass(ChildActivity.this, naam, tekst);
		cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		cdd.show();
	}

	private void initLayoutComponents() {
		// TODO Auto-generated method stub
		facade = Facade.getInstance();
		aankomst = (Button) findViewById(R.id.btnAankomst);
		vertrek = (Button) findViewById(R.id.btnVertrek);
		aanwezigheden = (Button) findViewById(R.id.btnAanwezigheden);
		bedragen = (Button) findViewById(R.id.btnBedragen);

		Intent in = getIntent();
		number = in.getIntExtra("number", 0);
		vnaam = in.getStringExtra("voornaam");

		// file = (TextView) findViewById(R.id.lblFile);
		// file.setText(Integer.toString(facade.getAantalKeer()));
		if (!haveNetworkConnection()) {
			aanwezigheden.setEnabled(false);
			bedragen.setEnabled(false);
		}

	}

	private void registerAankomst() {
		Date d1 = new Date(Calendar.getInstance().getTimeInMillis());
		aan = new Aanwezigheden(d1);
		try {
			createFile(number, aan);

		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registerVertrek() {

		Date date1 = new Date(Calendar.getInstance().getTimeInMillis());

		aan.setVertrek(date1);
		try {

			createFile(number, aan);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aan = null;
	}

	public void createFile(int number, Aanwezigheden aan) throws JSONException, IOException {
		JSONArray data = new JSONArray();
		JSONObject aanwezigheid;
		boolean komtVoor = false;
		String[] files = fileList();
		File f = new File("myfile");
		if (files.length > 0) {
			for (String file : files) {
				if (file.equals("myfile")) {
					// file exits

					FileInputStream fis = openFileInput("myfile");
					BufferedInputStream bis = new BufferedInputStream(fis);
					StringBuffer b = new StringBuffer();
					while (bis.available() != 0) {
						char c = (char) bis.read();
						b.append(c);
					}
					bis.close();
					fis.close();
					// ergens tonen...
					StringBuffer buf = new StringBuffer();
					JSONArray datas = new JSONArray(b.toString());
					for (int i = 0; i < datas.length(); i++) {
						String m = datas.getJSONObject(i).getString("month");
						int num = datas.getJSONObject(i).getInt("number");
						int dag = datas.getJSONObject(i).getInt("dag");
						int aankomst = datas.getJSONObject(i).getInt("aankomst");
						int vertrek = datas.getJSONObject(i).getInt("vertrek");
						if (num == number && aan.getDag() == dag) {
							aanwezigheid = fillAanwezigheidJsonObject(number, aan.getMaand().toString(), aan.getDag(),
									aan.getAankomstUur(), aan.getVertrekUur());
							data.put(aanwezigheid);
							komtVoor = true;

						} else {
							aanwezigheid = new JSONObject();
							aanwezigheid = fillAanwezigheidJsonObject(num, m, dag, aankomst, vertrek);
							data.put(aanwezigheid);
						}
					}
					if (komtVoor == false) {
						aanwezigheid = fillAanwezigheidJsonObject(number, aan.getMaand().toString(), aan.getDag(),
								aan.getAankomstUur(), aan.getVertrekUur());
						data.put(aanwezigheid);
					}
				}
			}
		} else {
			aanwezigheid = fillAanwezigheidJsonObject(number, aan.getMaand().toString(), aan.getDag(),
					aan.getAankomstUur(), aan.getVertrekUur());
			data.put(aanwezigheid);
		}
		String text = data.toString();
		FileOutputStream fos = openFileOutput("myfile", MODE_PRIVATE);
		fos.write(text.getBytes());
		fos.flush();
		fos.close();
	}

	private JSONObject fillAanwezigheidJsonObject(int n, String m, int d, int a, int v) {
		JSONObject aanwezigheid = new JSONObject();
		try {
			aanwezigheid.put("month", m);
			aanwezigheid.put("number", n);
			aanwezigheid.put("dag", d);
			aanwezigheid.put("aankomst", a);
			aanwezigheid.put("vertrek", v);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return aanwezigheid;
	}

	public Boolean readFileAanwezig(int num) throws IOException, JSONException {

		boolean aanvuld = true;
		File f = new File("myfile");

		if (f.length() > 0) {
			FileInputStream fis = openFileInput("myfile");
			BufferedInputStream bis = new BufferedInputStream(fis);
			StringBuffer b = new StringBuffer();

			while (bis.available() != 0) {
				char c = (char) bis.read();
				b.append(c);
			}
			bis.close();
			fis.close();
			// ergens tonen...
			StringBuffer buf = new StringBuffer();
			JSONArray data = new JSONArray(b.toString());
			Calendar cal = Calendar.getInstance();
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			for (int i = 0; i < data.length(); i++) {
				if ((data.getJSONObject(i).getInt("number") == num)
						&& (data.getJSONObject(i).getInt("dag") == dayOfMonth)
						&& (month.toUpperCase().equals(data.getJSONObject(i).getString("month")))) {

					if (data.getJSONObject(i).getInt("aankomst") > data.getJSONObject(i).getInt("vertrek")) {
						aanvuld = false;
					}

				}
			}
		}
		return aanvuld;
	}

	private void uploadAan() throws IOException, JSONException {

		JSONArray data = new JSONArray();
		JSONObject aanwezigheid;
		boolean leeg = true;
		String[] files = fileList();
		File f = new File("myfile");
		if (files.length > 0) {
			for (String file : files) {
				if (file.equals("myfile")) {
					FileInputStream fis = openFileInput("myfile");
					BufferedInputStream bis = new BufferedInputStream(fis);
					StringBuffer b = new StringBuffer();
					while (bis.available() != 0) {
						char c = (char) bis.read();
						b.append(c);
					}
					bis.close();
					fis.close();

					StringBuffer buf = new StringBuffer();
					JSONArray datas = new JSONArray(b.toString());
					for (int i = 0; i < datas.length(); i++) {
						String m = datas.getJSONObject(i).getString("month");
						int num = datas.getJSONObject(i).getInt("number");
						int dag = datas.getJSONObject(i).getInt("dag");
						int aankomst = datas.getJSONObject(i).getInt("aankomst");
						int vertrek = datas.getJSONObject(i).getInt("vertrek");

						if (m != null && num > 0 && dag > 0 && vertrek >= aankomst) {

							Child cj = (Child) facade.getUser(num);
							if (!cj.getAanwezigheden().contains(new Aanwezigheden(dag, m, aankomst, vertrek))) {
								cj.addAanwezigheid(new Aanwezigheden(dag, m, aankomst, vertrek));
								facade.updateUser(cj);
								final List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
								nameValuePairList.add(new BasicNameValuePair("nummer", Integer.toString(num)));
								nameValuePairList.add(new BasicNameValuePair("dag", Integer.toString(dag)));
								nameValuePairList.add(new BasicNameValuePair("maand", m.toString()));
								nameValuePairList.add(new BasicNameValuePair("aan", Integer.toString(aankomst)));
								nameValuePairList.add(new BasicNameValuePair("ver", Integer.toString(vertrek)));

								new AsyncTask<DbConnector, Long, Boolean>() {
									@Override
									protected Boolean doInBackground(DbConnector... dbconnectors) {
										// TODO Auto-generated method stub
										return dbconnectors[0].uploadAan(nameValuePairList);
									}
								}.execute(new DbConnector());
							}
						} else {

							aanwezigheid = fillAanwezigheidJsonObject(number, aan.getMaand().toString(), aan.getDag(),
									aan.getAankomstUur(), aan.getVertrekUur());
							data.put(aanwezigheid);
							leeg = false;
						}

					}

					if (leeg) {
						// File f = new File("myfile");
						deleteFile("myfile");
					} else {
						String text = data.toString();
						FileOutputStream fos = openFileOutput("myfile", MODE_PRIVATE);
						fos.write(text.getBytes());
						fos.flush();
						fos.close();
					}

				}

			}

		}

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
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

}
