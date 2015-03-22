package edu.csusb.libraryspace;

import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class PostRequest extends AsyncTask<String, Void, String>
{
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public AsyncResponsePOST listener;
    //public AsyncResponse delegate=null;
    public interface AsyncResponsePOST
    {
        void processPOSTFinish(String output);
    }
    public PostRequest(AsyncResponsePOST listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) // 1st param is url, 2nd is json
    {
        if(params[2].equals("calendarPOST"))
        {
            client.setFollowSslRedirects(true);
            RequestBody body = RequestBody.create(JSON, params[1]);
            Request request = new Request.Builder()
                    .url(params[0])
                    .post(body)
                    .build();
            try
            {
                Log.d("result", "Attempting POST to " + params[0]);

                Response response = client.newCall(request).execute();
                String returnedString = response.body().string();
                //Log.d("result", returnedString);
                return returnedString;
            }
            catch (IOException ie)
            {
                Log.e("result", "POST FAILED to " + params[0], ie);
                return "fail";
            }
        }
        else // BOOKING DETAILS POST
        {
            RequestBody formBody = new FormEncodingBuilder()
                    .add("sid", params[3])
                    .add("tc", "done")
                    .add("gid", params[4])
                    .add("fname", params[5])
                    .add("lname", params[6])
                    .add("email", params[7])
                    .add("nick", params[8])
                    .add("qcount", "0")
                    .add("fid", "0")
                    .build();
            Request request = new Request.Builder()
                    .url(params[0])
                    .header("Host", "csusb.libcal.com")
                    //.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0")
                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("Referer", "http://csusb.libcal.com/booking/" + params[9])
                    .post(formBody)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String returnedString = response.body().string();
                if (!response.isSuccessful())
                    Log.d("result", returnedString);
                return returnedString;
            }
            catch (IOException e)
            {
                return "fail";
            }
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        //Log.d("result", result);
        listener.processPOSTFinish(result);
    }
}
