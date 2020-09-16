package news;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.java.dac.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import data.Configuration;
import data.Manager;
import news.newslist.NewsListFregment;


public class newsFragment extends Fragment {
    private TabLayout newsTabLayout;
    private ViewPager newsViewPaper;
    private newsPaperAdapter mPagerAdapter;
    private String searchKeyWord = "";
    private List<Configuration.Category> newsCategoreis = new ArrayList<Configuration.Category>();

    public newsFragment(){}

    public void setSearchKeyWord(String key)
    {
        this.searchKeyWord = key.trim();
        //改变内容
        //this.mPagerAdapter.notifyDataSetChanged();
        if (!this.searchKeyWord.isEmpty()) {
            this.mPagerAdapter.getCurrentFragment().setKeyword(this.searchKeyWord);
        }
    }

    public static newsFragment newInstance()
    {
        newsFragment fragment = new newsFragment();
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.newsCategoreis = Manager.M.getavailableCategories();
        this.mPagerAdapter = new newsPaperAdapter(getChildFragmentManager(),this.newsCategoreis);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_page, container,false);
        this.newsViewPaper = (ViewPager) view.findViewById(R.id.view_pager);
        this.newsViewPaper.setOffscreenPageLimit(3);

        this.newsTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        this.newsTabLayout.setBackgroundColor(Color.WHITE);
        this.newsTabLayout.setTabTextColors(Color.BLACK,Color.MAGENTA);
        //add Tablayout
        for (int i = 0;i < this.newsCategoreis.size();i++)
        {
            this.newsTabLayout.addTab(newsTabLayout.newTab());
        }

        this.newsViewPaper.setAdapter(this.mPagerAdapter);
        this.newsTabLayout.setupWithViewPager(this.newsViewPaper);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class newsPaperAdapter extends FragmentStatePagerAdapter
    {
        private List<Configuration.Category> mCategories;

        private NewsListFregment currentFragment;
        private int currentFragmentid = -1;

        public newsPaperAdapter(FragmentManager fm,List<Configuration.Category> list)
        {
            super(fm);
            this.mCategories = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).title;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return NewsListFregment.newInstance(this.mCategories.get(position).idx,this.mCategories.get(position).title);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("newFragment",String.valueOf(position) + this.mCategories.get(position).title);
            NewsListFregment f = (NewsListFregment) super.instantiateItem(container, position);
            //f.setKeyword(this.mCategories.get(position).title);
            f.setKeyword(searchKeyWord);
            return f;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //Log.i("newsFragment",String.valueOf(position));
            if (position != this.currentFragmentid){
                if (this.currentFragmentid != -1) this.currentFragment.setKeyword("");
                this.currentFragmentid = position;
                this.currentFragment = (NewsListFregment) object;
            }
            super.setPrimaryItem(container, position, object);
        }

        public NewsListFregment getCurrentFragment()
        {
            return (NewsListFregment) this.currentFragment;
        }

    }

}
