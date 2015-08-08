package com.example.stijnderveauxkikkersprong;

import model.Aanwezigheden;
import model.Child;
import service.Facade;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AanwezighedenActivity extends ActionBarActivity {
	Facade facade;
	private TableLayout table;
	private Button back;
	private int number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_aanwezigheden);
		facade = Facade.getInstance();
		initLayoutComponents();

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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(back)) {
			back();
		}

	}

	private void initLayoutComponents() {
		// TODO Auto-generated method stub
		table = (TableLayout) findViewById(R.id.TableLayout);
		back = (Button) findViewById(R.id.Back);
		Intent in = getIntent();
		number = in.getIntExtra("number", 0);
		generateTable();

	}

	private void generateTable() {

		Child c=(Child)facade.getUser(number);
		
		for (Aanwezigheden aan:c.getAanwezigheden()) {
			TableRow tbrow = new TableRow(this);
			TextView t1v = new TextView(this);
			
			t1v.setText(aan.getDag()+ " " + aan.getMaand());

			t1v.setGravity(Gravity.CENTER);
			tbrow.addView(t1v);

			TextView t2v = new TextView(this);
			
			t2v.setText(Integer.toString(aan.getUren()));
			t2v.setGravity(Gravity.CENTER);
			tbrow.addView(t2v);

			table.addView(tbrow);
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		back();
	}
	public void back(){
		Intent intent = new Intent(this, ChildActivity.class);
		intent.putExtra("number", number);
		startActivity(intent);
		finish();
	}

}
