package com.example.stijnderveauxkikkersprong;





import model.Child;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogClass extends Dialog implements
		android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public GifView gifView;
	public Button btnBack;
	private String naam;
	private String tekst;
	
	
	public TextView lblText;

	public CustomDialogClass(Activity a,String naam,String tekst) {
		super(a);
		this.naam=naam;
		this.tekst=tekst;
		// TODO Auto-generated constructor stub
		this.c = a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dialog);
	     getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		btnBack = (Button) findViewById(R.id.btnBackDialog);
		lblText = (TextView) findViewById(R.id.lblText);
		
		if(tekst.equals("Aankomst")){
			lblText.setText("Welkom " + " " +   naam + ", amuseer je vandaag!");
			
		}
		if(tekst.equals("Vertrek")){
			lblText.setText("Bye" + " " + naam + ", tot snel!" );
			
		}
		
		
		btnBack.setOnClickListener(this);
		gifView = (GifView) findViewById(R.id.gif_view);
	

	}

	@Override
	public void onClick(View v) {
		if(v.equals(btnBack)){
			dismiss();
		}
	}

}
