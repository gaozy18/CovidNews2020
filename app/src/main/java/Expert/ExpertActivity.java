package Expert;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.java.dac.R;

import data.ImageLoader;

public class ExpertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_expert);
        if (getIntent().getBooleanExtra("isPassedAway",false))
            toolbar.setTitle("详细信息    " + getIntent().getStringExtra("name") + "(已故)");
        else
            toolbar.setTitle("详细信息    " + getIntent().getStringExtra("name"));
        this.setSupportActionBar(toolbar);

        ActionBar actionBar  = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        if (!getIntent().getStringExtra("avater").isEmpty())
            ImageLoader.displayImage(getIntent().getStringExtra("avater"),findViewById(R.id.expert_detail_image_view));

        ( (TextView) findViewById(R.id.expert_detail_name) ).setText(getIntent().getStringExtra("name") + " " + getIntent().getStringExtra("name_zh"));
        ( (TextView) findViewById(R.id.expert_detail_affiliation) ).setText(getIntent().getStringExtra("affiliation"));
        ( (TextView) findViewById(R.id.expert_detail_position) ).setText(getIntent().getStringExtra("position"));
        ( (TextView) findViewById(R.id.expert_detail_homepage) ).setText("主页:" + getIntent().getStringExtra("homepage"));
        ( (TextView) findViewById(R.id.expert_detail_activity) ).setText( "Activity:" + String.valueOf(getIntent().getDoubleExtra("activity",0.0) ));
        ( (TextView) findViewById(R.id.expert_detail_citations) ).setText( "Citations:" + String.valueOf(getIntent().getIntExtra("citations",0)));
        ( (TextView) findViewById(R.id.expert_detail_diversity) ).setText( "Diversity:" + String.valueOf(getIntent().getDoubleExtra("diversity",0.0) ));
        ( (TextView) findViewById(R.id.expert_detail_gindex) ).setText( "Gindex:" + String.valueOf(getIntent().getDoubleExtra("gindex",0.0) ));
        ( (TextView) findViewById(R.id.expert_detail_hindex) ).setText( "Hindex:" + String.valueOf(getIntent().getDoubleExtra("hindex",0.0) ));
        ( (TextView) findViewById(R.id.expert_detail_bio) ).setText(getIntent().getStringExtra("bio"));
        ( (TextView) findViewById(R.id.expert_detail_edu) ).setText(getIntent().getStringExtra("edu"));
        ( (TextView) findViewById(R.id.expert_detail_work) ).setText(getIntent().getStringExtra("work"));


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
