package news.newsdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.java.dac.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import data.Constant;
import data.NewsContent;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract_interface.View {

    private NewsDetailContract_interface.Presenter Presenter;
    private NewsContent newsContent;
    private NestedScrollView nestedScrollView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView author,content;
    private ImageView imageView;
    private boolean Error;//是否发生错误

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        String news_id = getIntent().getStringExtra(Constant.News_ID);
        String news_title = getIntent().getStringExtra(Constant.News_TITLE);
        String news_urlofpicture = getIntent().getStringExtra(Constant.News_URLofPICTURE);
        this.Presenter = new NewsDetailPresenter(this,news_id);
        this.Error = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        this.setSupportActionBar(toolbar);

        ActionBar actionBar  = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        this.nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        this.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });

        this.collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.collapsingToolbarLayout.setTitle(news_title);
        //this.collapsingToolbarLayout.setTitle("详细");
        this.author = (TextView) findViewById(R.id.text_author_detail);
        this.content = (TextView) findViewById(R.id.text_content_detail);
        this.imageView = (ImageView) findViewById(R.id.image_view_detail);

        /*if (news_urlofpicture != null){
            //TODO loadpicture
        }*/
        this.findViewById(R.id.button_reload_detail).setOnClickListener((View view) -> this.Presenter.subscribe());
        this.Presenter.subscribe();
    }

    @Override
    protected void onResume() {
        findViewById(R.id.progress_bar_detail).setVisibility(newsContent != null || Error ? View.INVISIBLE : View.VISIBLE);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_news_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                onShare();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void onShare() {
        Log.i("NewsDetailActivity","share");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, newsContent.source +": "+ newsContent.title);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share to..."));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void setNewsContent(NewsContent newsContent) {
        this.newsContent = newsContent;
        this.Error = false;
        this.author.setText((this.newsContent.author == null ? this.newsContent.source : this.newsContent.author) + this.newsContent.date);
        this.content.setText(this.newsContent.content);
        findViewById(R.id.progress_bar_detail).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout_error).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout_content).setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowToast(String title) {
        Toast.makeText(this,title,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartLoading() {
        findViewById(R.id.layout_error).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout_content).setVisibility(View.INVISIBLE);
        findViewById(R.id.progress_bar_detail).setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        findViewById(R.id.layout_error).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_content).setVisibility(View.INVISIBLE);
        findViewById(R.id.progress_bar_detail).setVisibility(View.INVISIBLE);
    }

    @Override
    public void setImageVisible(boolean visible) {
        findViewById(R.id.image_layout_detail).setVisibility(visible && !Error ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPresenter(NewsDetailContract_interface.Presenter presenter) {
        this.Presenter = presenter;
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void startAc(Intent intent, Bundle bundle) {
        startActivity(intent,bundle);
    }
}
