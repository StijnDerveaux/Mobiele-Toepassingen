package com.example.stijnderveauxkikkersprong;

import model.Bedragen;
import model.Child;

import service.Facade;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AdminBedragenActivity extends ActionBarActivity {
	private Facade facade;
	private int number;
	private TableLayout table;
	private Button back;
	private TextView bed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_admin_bedragen);
		initLayoutComponents();

	}

	public void onClick(View v) {

		if (v.equals(back)) {
			back();
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
		back = (Button) findViewById(R.id.btnBack);
		table = (TableLayout) findViewById(R.id.TableLayout);
		bed=(TextView)findViewById(R.id.lblBedragen);
		Intent in = getIntent();
		number = in.getIntExtra("number", 0);
		generateTable();

	}

	private void generateTable() {

		Child c = (Child) facade.getUser(number);

		for (Bedragen b : c.getBedragen()) {
			TableRow tbrow = new TableRow(this);
			TextView t1v = new TextView(this);

			t1v.setText(b.getMaand().toString());

			t1v.setGravity(Gravity.CENTER);
			tbrow.addView(t1v);

			CheckBox check = new CheckBox(this);
			check.setChecked(b.isBetaald());
			check.setEnabled(false);

			tbrow.addView(check);

			TextView t2v = new TextView(this);
			t2v.setText(Integer.toString(b.getBedrag()));
			t2v.setGravity(Gravity.CENTER);
			tbrow.addView(t2v);

			table.addView(tbrow);
		}
		String tekst= bed.getText() + " : " + c.getNaam() + " " + c.getVoornaam();
		bed.setText(tekst);
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		back();
	}

	public void back() {
		Intent intent = new Intent(this, AdminActivity.class);
		startActivity(intent);
		finish();
	}
}
