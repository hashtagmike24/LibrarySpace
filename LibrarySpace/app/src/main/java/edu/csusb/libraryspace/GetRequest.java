package edu.csusb.libraryspace;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

public class GetRequest extends AsyncTask<String, Void, String>
{
    OkHttpClient client = new OkHttpClient();

    public AsyncResponseGET listener;
    //public AsyncResponse delegate=null;
    public interface AsyncResponseGET
    {
        void processFinish(String output);
    }

    public GetRequest(AsyncResponseGET listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... url_link)
    {
        Request request = new Request.Builder()
                .url(url_link[0])
                .build();

        try
        {
            Response response = client.newCall(request).execute();
            Log.d("result", "Attempting GET from " + url_link[0]);
            return response.body().string();
        }
        catch (IOException ie)
        {
            Log.e("result", "Failed to GET from " + url_link[0], ie);
            return "FAIL";
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        Log.d("YES", result);
        listener.processFinish(result);
        //super.onPostExecute(result);
    }
}
