package com.tinbytes.simpleasyncapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SimpleAsyncActivity extends AppCompatActivity {
  private TextView tvCounter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_async);

    tvCounter = (TextView) findViewById(R.id.tvCounter);

    CountingTask ct = new CountingTask();
    ct.execute();
  }

  private class CountingTask extends AsyncTask<Void, Integer, Integer> {
    @Override
    protected Integer doInBackground(Void... unused) {
      int i = 0;
      while (i < 100) {
        SystemClock.sleep(250);
        i++;
        if (i % 5 == 0) { // update UI with progress every 5%
          publishProgress(i);
        }
      }
      return i;
    }

    protected void onProgressUpdate(Integer... progress) {
      tvCounter.setText(progress[0] + "% Complete!");
    }

    protected void onPostExecute(Integer result) {
      tvCounter.setText("Count Complete! Counted to " + result.toString());
    }
  }
}
