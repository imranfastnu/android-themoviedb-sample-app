package com.themoviedb.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.themoviedb.app.R;

/**
 * Created by imran.khalid on 7/25/2016.
 */
public class FilterActivity extends AppCompatActivity {

    Context context;

    EditText editTextMinYear;
    EditText editTextMaxYear;
    Button btnApplyFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        editTextMinYear = (EditText) findViewById(R.id.editTextMinYear);
        editTextMaxYear = (EditText) findViewById(R.id.editTextMaxYear);
        btnApplyFilter = (Button) findViewById(R.id.btnApplyFilter);
        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String minYear = editTextMinYear.getText().toString().trim();
                String maxYear = editTextMaxYear.getText().toString().trim();

                if (minYear.length()<4) {
                    editTextMinYear.setError("Enter min year");
                    return;
                }

                if (maxYear.length()<4) {
                    editTextMaxYear.setError("Enter max year");
                    return;
                }

                    Intent resultIntent = getIntent();
                    resultIntent.putExtra("minYear", minYear);
                    resultIntent.putExtra("maxYear", maxYear);
                    setResult(Activity.RESULT_OK, resultIntent);
                    FilterActivity.this.finish();


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }


}
