package com.example.stijnderveauxkikkersprong;

import service.Facade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import model.Aanwezigheden;
import android.view.View.OnClickListener;

public class ChildActivity extends ActionBarActivity {
	Facade facade;
	private int number;
	private Button aankomst;
	private Button vertrek;
	private Button aanwezigheden;
	private Button bedragen;
	private TextView text;
	private Aanwezigheden aan;
	private TextView file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		String naam = facade.getVoornaam(facade.getUser(number));
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

		file=(TextView)findViewById(R.id.lblFile);
		try {
			file.setText(readFile());
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// File f = getFilesDir();
		// String path = f.getAbsolutePath();


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

	private void registerAankomst() {
		Date d1 = new Date(Calendar.getInstance().getTimeInMillis());
		aan = new Aanwezigheden(d1);
		try {
			createFile(2, aan);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registerVertrek() {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
		Date date1 = null;
		try {
			date1 = simpleDateFormat.parse("14/08/2015 21:5:10");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aan.setVertrek(date1);
		try {
			createFile(2, aan);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String readFile() throws IOException, JSONException {
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

		for (int i = 0; i < data.length(); i++) {

			String month = data.getJSONObject(i).getString("month");
			buf.append(month + ":");
			String number = data.getJSONObject(i).getString("number");
			buf.append(number + ":");
			String dag = data.getJSONObject(i).getString("dag");
			buf.append(dag + ":");
			String aankomst = data.getJSONObject(i).getString("aankomst");
			buf.append(aankomst + ":");
			String vertrek = data.getJSONObject(i).getString("vertrek");
			buf.append(vertrek + "\n");

		}
		String t = buf.toString();
		return t;
	}

}
