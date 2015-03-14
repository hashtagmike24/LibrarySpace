package edu.csusb.libraryspace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MultimediaActivity extends ActionBarActivity implements OnItemSelectedListener{

    CalendarView myCalendar;
    int _month;
    int _day;
    int _year;

    Spinner roomSpinner;
    String _room;
    Spinner hourSpinner;
    String _hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);

        // Font path
        String fontPath = "fonts/dosis-regular.ttf";
        String thickPath = "fonts/dosis-medium.ttf";
        // text view label
        TextView txtRoomText = (TextView) findViewById(R.id.roomText);
        TextView txtHourText = (TextView) findViewById(R.id.hourText);
        TextView txtPDFText = (TextView) findViewById(R.id.pdfButton);
        TextView txtDescriptionText = (TextView) findViewById(R.id.descriptionButton);
        TextView txtNextText = (TextView) findViewById(R.id.nextButton);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), thickPath);
        // Applying font
        txtRoomText.setTypeface(tf);
        txtHourText.setTypeface(tf);
        txtPDFText.setTypeface(tf2);
        txtDescriptionText.setTypeface(tf2);
        txtNextText.setTypeface(tf2);

        roomSpinner = (Spinner) findViewById(R.id.roomSpinner);
        String[] rooms = {"PL-5005F", "PL-5005J"};
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, rooms);
        adapter_state.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter_state);
        roomSpinner.setOnItemSelectedListener(this);

        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);
        String[] hours = {"9:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "12:00 PM - 1:00 PM", "1:00 PM - 2:00 PM", "2:00 PM - 3:00 PM", "3:00 PM - 4:00 PM", "4:00 PM - 5:00 PM", "5:00 PM - 6:00 PM", "6:00 PM - 7:00 PM", "7:00 PM - 8:00 PM", "8:00 PM - 9:00 PM"};
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, hours);
        adapter_state2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter_state2);
        hourSpinner.setOnItemSelectedListener(this);

        myCalendar = (CalendarView) findViewById(R.id.multimediaCalendar);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                _month = month + 1;
                _day = dayOfMonth;
                _year = year;
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.roomSpinner)
        {
            roomSpinner.setSelection(position);
            _room = (String) roomSpinner.getSelectedItem();
        }
        else if(spinner.getId() == R.id.hourSpinner)
        {
            hourSpinner.setSelection(position);
            _hour = (String) hourSpinner.getSelectedItem();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multimedia, menu);
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
     * OnClick function for conditionsButton. Opens ConditionsActivity.
     */
    public void conditionsButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MultimediaActivity.this, ConditionsActivity.class);

        if(_room.equals(""))
            _room = "PL-5005F";
        if(_hour.equals(""))
            _hour = "9:00 AM - 10:00 AM";

        if(_day == 0)
        {
            Calendar calendar = Calendar.getInstance();
            _day = calendar.get(Calendar.DAY_OF_MONTH);
            _month = calendar.get(Calendar.MONTH) + 1;
            _year = calendar.get(Calendar.YEAR);
        }

        myIntent.putExtra("MONTH", _month);
        myIntent.putExtra("DAY", _day);
        myIntent.putExtra("YEAR", _year);
        myIntent.putExtra("TYPE", "Multimedia Collaboration Room");
        myIntent.putExtra("ROOM", _room);
        myIntent.putExtra("HOUR", _hour);

        MultimediaActivity.this.startActivity(myIntent);
    }

    public void pdfButtonOnClick(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lib.csusb.edu/documents/floorMaps/group_study.pdf")));
    }

    public void descriptionButtonOnClick(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Library Multimedia Center has two Multimedia Collaboration Rooms (PL-5005 F & J) that can be reserved.\n\nBoth have Mac Pros with advanced media editing software.\n\nThese rooms are intended for use by individuals or small groups working on audio-visual projects.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog emailPopUp = builder.create();
        emailPopUp.show();
    }
}
