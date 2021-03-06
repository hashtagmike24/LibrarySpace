package edu.csusb.libraryspace;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.locks.Condition;

public class ConditionsActivity extends ActionBarActivity {

    int _month;
    int _day;
    int _year;
    String _type;
    String _room;
    String _hour;
    String _sid;
    String _gid;

    TextView bodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);

        // Font path
        String fontPath = "fonts/dosis-regular.ttf";
        String thickPath = "fonts/dosis-medium.ttf";
        // text view label
        TextView txtConditionsHeaderText = (TextView) findViewById(R.id.conditionsHeaderText);
        TextView txtBodyText = (TextView) findViewById(R.id.bodyText);
        TextView txtNextText = (TextView) findViewById(R.id.nextButton);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(),fontPath);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), thickPath);
        // Applying font
        txtConditionsHeaderText.setTypeface(tf2);
        txtBodyText.setTypeface(tf);
        txtNextText.setTypeface(tf2);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            _month = extras.getInt("MONTH");
            _day = extras.getInt("DAY");
            _year = extras.getInt("YEAR");
            _type = extras.getString("TYPE");
            _room = extras.getString("ROOM");
            _hour = extras.getString("HOUR");
            _sid = extras.getString("SID");
            _gid = extras.getString("GID");

            Toast.makeText(getApplicationContext(), _room + " " + _hour + " on " + _month + "/" + _day + "/" + _year, Toast.LENGTH_LONG).show();
        }

        bodyText = (TextView) findViewById(R.id.bodyText);

        switch(_type)
        {
            case "Group Study Room": bodyText.setText(getResources().getString(R.string.conditions_group_bodyText));
                break;
            case "Individual Study Carrel": bodyText.setText(getResources().getString(R.string.conditions_individual_bodyText));
                break;
            case "Multimedia Collaboration Room": bodyText.setText(getResources().getString(R.string.conditions_multimedia_bodyText));
                break;
            default: bodyText.setText("ERROR: No room type specified");
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conditions, menu);
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
     * OnClick function for bookingButton. Opens BookingActivity.
     */
    public void bookingButtonOnClick(View view)
    {
        Intent myIntent = new Intent(ConditionsActivity.this, BookingActivity.class);

        myIntent.putExtra("MONTH", _month);
        myIntent.putExtra("DAY", _day);
        myIntent.putExtra("YEAR", _year);
        myIntent.putExtra("TYPE", _type);
        myIntent.putExtra("ROOM", _room);
        myIntent.putExtra("HOUR", _hour);
        myIntent.putExtra("SID", _sid);
        myIntent.putExtra("GID", _gid);

        ConditionsActivity.this.startActivity(myIntent);
    }
}
