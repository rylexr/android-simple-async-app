package com.tinbytes.simpleasyncapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SimpleAsyncParallelActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_async_parallel);

    CountingTask ct1 = new CountingTask();
    ct1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, R.id.tvCounter1);

    CountingTask ct2 = new CountingTask();
    ct2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, R.id.tvCounter2);
  }

  private class CountingTask extends AsyncTask<Integer, Integer, Integer> {
    private int tvId;

    @Override
    protected Integer doInBackground(Integer... ids) {
      tvId = ids[0];
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
      ((TextView) findViewById(tvId)).setText(progress[0] + "% Complete!");
    }

    protected void onPostExecute(Integer result) {
      ((TextView) findViewById(tvId)).setText("Count Complete! Counted to " + result.toString());
    }
  }
}
