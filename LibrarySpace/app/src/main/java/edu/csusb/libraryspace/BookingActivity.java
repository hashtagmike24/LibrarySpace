package edu.csusb.libraryspace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class BookingActivity extends ActionBarActivity {

    int _month;
    int _day;
    int _year;
    String _type;

    TextView detailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        detailsText = (TextView) findViewById(R.id.detailsText);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            _month = extras.getInt("MONTH");
            _day = extras.getInt("DAY");
            _year = extras.getInt("YEAR");
            _type = extras.getString("TYPE");

            detailsText.setText( _type + ": " + _month + "/" + _day + "/" + _year);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking, menu);
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
    public void mainButtonOnClick(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("A confirmation email has been sent to your inbox! Please check it to confirm your booking details.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(BookingActivity.this, MainActivity.class);
                        BookingActivity.this.startActivity(myIntent);
                    }
                });
        AlertDialog emailPopUp = builder.create();
        emailPopUp.show();
    }
}
