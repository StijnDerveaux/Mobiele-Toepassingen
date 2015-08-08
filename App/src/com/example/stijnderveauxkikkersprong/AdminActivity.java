package com.example.stijnderveauxkikkersprong;

import model.Aanwezigheden;
import model.Bedragen;
import model.Child;
import model.User;
import service.Facade;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AdminActivity extends ActionBarActivity implements
		View.OnClickListener {

	private Facade facade;
	private int number;
	private TableLayout table;
	private Button factuur;
	private Button aanmaning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_admin);
		initLayoutComponents();

	}

	public void onClick(View v) {

		if (v.equals(factuur)) {

		}
		if (v.equals(aanmaning)) {

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

		generateTable();

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

}
