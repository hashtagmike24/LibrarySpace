package edu.csusb.libraryspace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupActivity extends ActionBarActivity implements OnItemSelectedListener, PostRequest.AsyncResponsePOST {

    CalendarView myCalendar;
    int _month = 0;
    int _day = 0;
    int _year = 0;

    ArrayList<String> availableIDs = new ArrayList<String>();
    ArrayList<String> availableRooms = new ArrayList<String>();
    ArrayList<String> availableHours = new ArrayList<String>();

    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> adapter_state2;

    String formattedDate;

    Spinner roomSpinner;
    String _room;
    Spinner hourSpinner;
    String _hour;

    PostRequest postRequest = new PostRequest(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

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
        ArrayList<String> rooms = new ArrayList<String>();
        rooms.add("Select a room");
        rooms.add("PL-321");
        rooms.add("PL-323");
        _room = "Select a Room";
        adapter_state = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, rooms);
        adapter_state.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter_state);
        roomSpinner.setOnItemSelectedListener(this);

        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);
        ArrayList<String> hours = new ArrayList<String>();
        hours.add("Select an hour");
        _hour = "Select an hour";
        adapter_state2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, hours);
        adapter_state2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter_state2);
        hourSpinner.setOnItemSelectedListener(this);

        myCalendar = (CalendarView) findViewById(R.id.groupCalendar);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                _month = month + 1;
                _day = dayOfMonth;
                _year = year;
                formattedDate = _year + "-" + _month + "-" + _day;

                // Update Hours
                String json = makeJSON(formattedDate);
                PostRequest pr = new PostRequest(GroupActivity.this);
                pr.execute("http://csusb.libcal.com/process_roombookings.php?m=calscroll&gid=1787&date=" + formattedDate, json);

                adapter_state2.clear();
                adapter_state2.addAll(getHoursBasedOnRoom());
                adapter_state2.notifyDataSetChanged();
            }
        });

        myCalendar.setMinDate(System.currentTimeMillis() - 1000);
        myCalendar.setMaxDate(myCalendar.getDate() + (86400000 * 7)); // dat magic number doe

        if(_day == 0)
        {
            Calendar calendar = Calendar.getInstance();
            _day = calendar.get(Calendar.DAY_OF_MONTH);
            _month = calendar.get(Calendar.MONTH) + 1;
            _year = calendar.get(Calendar.YEAR);
            formattedDate = _year + "-" + _month + "-" + _day;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.roomSpinner)
        {
            roomSpinner.setSelection(position);
            _room = (String) roomSpinner.getSelectedItem();

            adapter_state2.clear();
            adapter_state2.addAll(getHoursBasedOnRoom());
            adapter_state2.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_group, menu);
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
     * OnClick function for conditionsButton. Opens ConditionsActivity and passes date data.
     */
    public void conditionsButtonOnClick(View view)
    {
        Intent myIntent = new Intent(GroupActivity.this, ConditionsActivity.class);

        if(_room.equals(""))
            _room = "PL-321";
        if(_hour.equals(""))
            _hour = "9:00 AM - 10:00 AM";

        if(_day == 0)
        {
            Calendar calendar = Calendar.getInstance();
            _day = calendar.get(Calendar.DAY_OF_MONTH);
            _month = calendar.get(Calendar.MONTH) + 1;
            _year = calendar.get(Calendar.YEAR);
            formattedDate = _year + "-" + _month + "-" + _day;
        }

        myIntent.putExtra("MONTH", _month);
        myIntent.putExtra("DAY", _day);
        myIntent.putExtra("YEAR", _year);
        myIntent.putExtra("TYPE", "Group Study Room");
        myIntent.putExtra("ROOM", _room);
        myIntent.putExtra("HOUR", _hour);

        GroupActivity.this.startActivity(myIntent);
    }

    public void pdfButtonOnClick(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lib.csusb.edu/documents/floorMaps/group_study.pdf")));
    }

    public void descriptionButtonOnClick(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Library has two Group Study Rooms (PL-321 & PL-323) that can be reserved.\n\nBoth have two tables, 8 chairs, and a whiteboard.\n\nThese rooms are intended for use by a small to moderate-sized group.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog emailPopUp = builder.create();
        emailPopUp.show();
    }

    /**
     * Used to handle incoming data from PostRequest
     * @param output
     */
    public void processPOSTFinish(String output)
    {
        // regex setup
        ArrayList<String> availableBookings = new ArrayList<String>();
        int i = 1;

        // black magic
        Pattern pattern = Pattern.compile("(id=\\\"(\\w*)\\\"\\s(\\S*)\\s(\\S*)\\s\\W\\s(\\S*)\\w\\W)");
        Matcher matcher = pattern.matcher(output);
        while (matcher.find())
        {
            availableBookings.add(matcher.group(1));
            //Log.d("bookingTile", availableBookings.get(i-1));
            i++;
        }

        // parse and update hours
        availableIDs = new ArrayList<String>();
        availableRooms = new ArrayList<String>();
        availableHours = new ArrayList<String>();
        for(int x = 0; x < i-1; x++)
        {
            Pattern p = Pattern.compile("\\\"(\\d*)\\\"");
            Matcher m = p.matcher(availableBookings.get(x));
            while (m.find())
            {
                availableIDs.add(m.group(1));
                Log.d("ids", availableIDs.get(x));
            }

            p = Pattern.compile("\\'(\\w*-\\d*)");
            m = p.matcher(availableBookings.get(x));
            while (m.find())
            {
                availableRooms.add(m.group(1));
                Log.d("rooms", availableRooms.get(x));
            }

            p = Pattern.compile("\\'(\\d*:\\d*\\w*\\W*\\d*\\W*\\d*\\w*)");
            m = p.matcher(availableBookings.get(x));
            while (m.find())
            {
                availableHours.add(m.group(1));
                Log.d("hours", availableHours.get(x));
            }
        }
    }

    private String makeJSON(String date)
    {
        return "{ 'm':'calscroll','gid':1787,'date':'" + date + "'}";
    }

    private ArrayList<String> getHoursBasedOnRoom()
    {
        ArrayList<String> temp = new ArrayList<>();

        if(_room.equals("Select a room"))
        {
            temp.add("Select an hour");
            return temp;
        }
        else
        {
            if(availableRooms.size() == 0)
            {
                temp.add("No hours available");
                return temp;
            }

            for(int i = 0; i < availableRooms.size(); i++)
            {
                if(availableRooms.get(i).equals(_room))
                    temp.add(availableHours.get(i));
                Log.d("test: " + _room, Boolean.toString(_room.equals(availableRooms.get(i))));
            }
            return temp;
        }
    }
}

