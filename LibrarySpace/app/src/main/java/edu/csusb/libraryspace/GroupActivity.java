package edu.csusb.libraryspace;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.security.acl.Group;

public class GroupActivity extends ActionBarActivity {

    CalendarView myCalendar;
    int _month;
    int _day;
    int _year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        myCalendar = (CalendarView) findViewById(R.id.groupCalendar);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                _month = month + 1;
                _day = dayOfMonth;
                _year = year;
                Toast.makeText(getApplicationContext(), _month + "/" + _day + "/" + _year, Toast.LENGTH_LONG).show();
            }
        });
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

        myIntent.putExtra("MONTH", _month);
        myIntent.putExtra("DAY", _day);
        myIntent.putExtra("YEAR", _year);
        myIntent.putExtra("TYPE", "Group Study Room");

        GroupActivity.this.startActivity(myIntent);
    }
}
