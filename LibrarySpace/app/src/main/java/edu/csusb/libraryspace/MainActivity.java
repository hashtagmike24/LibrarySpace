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
import android.view.Window;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Font path
        String fontPath = "fonts/dosis-regular.ttf";
        String thickPath = "fonts/dosis-medium.ttf";
        // text view label
        TextView txtHeaderText = (TextView) findViewById(R.id.headerText);
        TextView txtGroupText = (TextView) findViewById(R.id.groupButton);
        TextView txtIndividualText = (TextView) findViewById(R.id.individualButton);
        TextView txtMultimediaText = (TextView) findViewById(R.id.multimediaButton);
        TextView txtMapText = (TextView) findViewById(R.id.mapButton);
        TextView txtCSUSBText = (TextView) findViewById(R.id.csusbButton);
        TextView txtHelpText = (TextView) findViewById(R.id.helpButton);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(),fontPath);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), thickPath);
        // Applying font
        txtHeaderText.setTypeface(tf2);
        txtGroupText.setTypeface(tf);
        txtIndividualText.setTypeface(tf);
        txtMultimediaText.setTypeface(tf);
        txtMapText.setTypeface(tf2);
        txtCSUSBText.setTypeface(tf2);
        txtHelpText.setTypeface(tf2);
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
     * OnClick function for individualButton. Opens IndividualActivity.
     */
    public void individualButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, IndividualActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    /**
     * OnClick function for multimediaButton. Opens MultimediaActivity
     */
    public void multimediaButtonOnClick(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, MultimediaActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void pdfButtonOnClick(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lib.csusb.edu/documents/floorMaps/group_study.pdf")));
    }

    public void csusbButtonOnClick(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.csusb.edu/")));
    }

    public void helpButtonOnClick(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("CSUSB Library Study Space lets you reserve a study room (Group Study Room, Individual Study Carrel, Multimedia Collaboration Room) from CSUSB's Pfau Library! Here's the process:\n\n1) Select a study room.\n\n2) Select a day and hour slot from the calendar.\n\n3) Submit your booking details (name, school email, public booking label).\n\n4) Within 15 minutes of making your reservation, open the confirmation email that was sent to your inbox.\n\n5) Your reservation is complete! Keep your final confirmation email as your proof of booking and bring it with you.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog helpPopUp = builder.create();
        helpPopUp.show();
    }
}
