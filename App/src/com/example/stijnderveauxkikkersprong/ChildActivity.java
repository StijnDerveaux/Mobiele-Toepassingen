package com.example.stijnderveauxkikkersprong;



import model.Child;
import service.Facade;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ChildActivity extends ActionBarActivity {
	Facade facade;
private int number;
	private Button aankomst;
	private Button vertrek;
	private Button aanwezigheden;
	private Button bedragen;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_child);
		
		initLayoutComponents();
		

	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*if (v.equals(aankomst)) {
		

		}
		if (v.equals(vertrek)) {
		
		}*/
		if(v.equals(aanwezigheden)){
			
			Intent i = new Intent(this,
					AanwezighedenActivity.class);
			
			i.putExtra("number", number);
			startActivity(i);
			
		}
		if(v.equals(bedragen)){
			
			Intent i = new Intent(this,
					BedragenActivity.class);
			
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
	
	
	
	private void initLayoutComponents() {
		// TODO Auto-generated method stub
		facade = Facade.getInstance();
		aankomst = (Button) findViewById(R.id.btnAankomst);
		vertrek = (Button) findViewById(R.id.btnVertrek);
		aanwezigheden = (Button) findViewById(R.id.btnAanwezigheden);
		bedragen = (Button) findViewById(R.id.btnBedragen);
	
		
		Intent in=getIntent();
		 number=in.getIntExtra("number", 0);
		

	}
	 @Override
	    public void onResume(){
	        super.onResume();
	        
	    }

	    @Override
	    public void onBackPressed(){
	        super.onBackPressed();
	        Intent intent = new Intent(this, MainActivity.class);
	        startActivity(intent);
	        finish();
	    }
}
