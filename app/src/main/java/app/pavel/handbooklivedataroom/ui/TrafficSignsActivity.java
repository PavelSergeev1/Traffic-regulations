package app.pavel.handbooklivedataroom.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.pavel.handbooklivedataroom.R;

public class TrafficSignsActivity extends AppCompatActivity
    implements TrafficSignsAdapter.OnSignClickListener {

    private TrafficSignsAdapter trafficSignsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traffic_signs_activity);

        trafficSignsAdapter = new TrafficSignsAdapter(this, this);

        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.getAllTrafficSigns()
                .observe(this, trafficSigns -> trafficSignsAdapter.setData(trafficSigns));


        RecyclerView recyclerView = findViewById(R.id.recyclerViewSigns);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(trafficSignsAdapter);
    }

    @Override
    public void onSignClickListener(String trafficSignTitle) {
        // start SelectedSignActivity
        Intent intent = new Intent(this, SelectedSignActivity.class);
        intent.putExtra("Title", trafficSignTitle);
        startActivity(intent);
    }
}