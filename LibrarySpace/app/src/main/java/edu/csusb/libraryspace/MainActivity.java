package edu.csusb.libraryspace;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * OnClick function for groupButton. Opens GroupActivity.
     */
    public void groupButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, GroupActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
	
	/**
     * OnClick function for bookingButton. Opens BookingActivity.
     */
	public void bookingButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, BookingActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
	
	/**
     * OnClick function for conditionsButton. Opens CondtionsActivity.
     */
	public void conditionsButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, ConditionsActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
	
	/**
     * OnClick function for individualButton. Opens IndividualActivity.
     */
	public void individualButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, IndividualActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
	
	/**
     * OnClick function for multimediaButton. Opens MultimediaActivity.
     */
	public void multimediaButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, MultimediaActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
