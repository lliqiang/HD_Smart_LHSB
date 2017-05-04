package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.fragment.LocationFragment;
import com.hengda.linhai.m.tools.FragmentUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationActivity extends BaseActivity {

    @Bind(R.id.container_location)
    FrameLayout containerLocation;
    @Bind(R.id.activity_location)
    LinearLayout activityLocation;
    @Bind(R.id.back_loaction)
    ImageView backLoaction;
    @Bind(R.id.tilte_location)
    TextView tilteLocation;
    private Exhibition exhibition;
    private LocationFragment locationFragment;
    private String exhibit_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        exhibit_id =getIntent().getStringExtra("exhibit_id");
        locationFragment = LocationFragment.newInstance(exhibition);
        tilteLocation.setText(exhibition.getUnitName());
        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_location,
                locationFragment, true);

        backLoaction.setOnClickListener(view -> {
            finish();
        });
    }

}
