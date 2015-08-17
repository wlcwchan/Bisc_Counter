package intracode.org.humancounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private static final String STATE_COUNTER_TOTAL = "total-student-counter";
    private static final String STATE_COUNTER_RANDOM = "random-student-counter";
    private static final String STATE_COUNTER_BISC = "bisc-student-counter";
    private int mTotalStudentCounter = 0;
    private int mRandomStudentCounter = 0;
    private int mBiscStudentCounter = 0;
    private Clicker clicker = new Clicker();
    private boolean updateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Always call the superclass first

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        //mSaved.setText(restoredText, TextView.BufferType.EDITABLE);
        mTotalStudentCounter = prefs.getInt(STATE_COUNTER_TOTAL, 0);
        mRandomStudentCounter = prefs.getInt(STATE_COUNTER_RANDOM, 0);
        mBiscStudentCounter = prefs.getInt(STATE_COUNTER_BISC, 0);

        setContentView(R.layout.activity_main);
        registerAsObserver();
        updateLabels(mTotalStudentCounter, mRandomStudentCounter, mBiscStudentCounter);

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putInt(STATE_COUNTER_TOTAL, mTotalStudentCounter);
        editor.putInt(STATE_COUNTER_RANDOM, mRandomStudentCounter);
        editor.putInt(STATE_COUNTER_BISC, mBiscStudentCounter);
        editor.apply();
    }

    public void biscStudentOnClick(View v) {
        TextView biscStudentTotal = (TextView) findViewById(R.id.biscStudentLabel);
        int total = Integer.parseInt(biscStudentTotal.getText().toString());

        mBiscStudentCounter = total + 1;
        biscStudentTotal.setText(String.format("%d", mBiscStudentCounter));

        clicker.clickerClicked(true);
    }

    public void biscStudentSubOnClick(View v) {
        TextView biscStudentTotal = (TextView) findViewById(R.id.biscStudentLabel);

        mBiscStudentCounter = updateSubCounter(mBiscStudentCounter);
        biscStudentTotal.setText(String.format("%d", mBiscStudentCounter));

        clicker.clickerClicked(false);
    }

    public void randomStudentOnClick(View v) {
        TextView randomStudentTotal = (TextView) findViewById(R.id.randomStudentLabel);
        int total = Integer.parseInt(randomStudentTotal.getText().toString());

        mRandomStudentCounter = total + 1;
        randomStudentTotal.setText(String.format("%d", mRandomStudentCounter));

        clicker.clickerClicked(true);
    }

    public void randomStudentSubOnClick(View v) {
        TextView biscStudentTotal = (TextView) findViewById(R.id.randomStudentLabel);

        mRandomStudentCounter = updateSubCounter(mRandomStudentCounter);
        biscStudentTotal.setText(String.format("%d", mRandomStudentCounter));

        clicker.clickerClicked(false);
    }

    private int updateSubCounter(int counter) {
        if (counter == 0) {
            updateFlag = false;
        } else {
            counter -= 1;
            updateFlag = true;
        }
        return counter;
    }

    public void resetOnClick(View v) {
        TextView biscStudentTotal = (TextView) findViewById(R.id.biscStudentLabel);
        TextView randomStudentTotal = (TextView) findViewById(R.id.randomStudentLabel);
        TextView studentTotal = (TextView) findViewById(R.id.totalLabel);
        randomStudentTotal.setText("0");
        biscStudentTotal.setText("0");
        studentTotal.setText("0");
        mTotalStudentCounter = 0;
        mRandomStudentCounter = 0;
        mBiscStudentCounter = 0;
    }

    private void updateLabels(int totalLabel, int randomStudentLabel, int biscStudentLabel) {
        TextView biscStudentTotal = (TextView) findViewById(R.id.biscStudentLabel);
        TextView randomStudentTotal = (TextView) findViewById(R.id.randomStudentLabel);
        TextView studentTotal = (TextView) findViewById(R.id.totalLabel);

        studentTotal.setText(String.format("%d", totalLabel));
        randomStudentTotal.setText(String.format("%d", randomStudentLabel));
        biscStudentTotal.setText(String.format("%d", biscStudentLabel));
    }

    private void updateTotalLabel() {
        TextView totalStudent = (TextView) findViewById(R.id.totalLabel);
        if(clicker.getPositive()){
            mTotalStudentCounter += 1;
        } else {
            if(updateFlag) {
                mTotalStudentCounter -= 1;
            }
        }

        totalStudent.setText(String.format("%d", mTotalStudentCounter));
    }

    private void registerAsObserver() {
        clicker.registerObserver(new ClickerObserver() {
            @Override
            public void stateChanged() {
                updateTotalLabel();
            }
        });
    }
}
