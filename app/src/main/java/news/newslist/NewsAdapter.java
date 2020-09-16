package news.newslist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.dac.R;

import java.util.ArrayList;
import java.util.List;

import data.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_NORM = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_END = 2;

    private Context context;
    private List<NewsItem> newsdata = new ArrayList<NewsItem>();
    private boolean isshowend = true;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context){ this.context = context; }

    public NewsItem getnewItemById(int position)
    {
        return newsdata.get(position);
    }

    public void setNewsData(List<NewsItem> data)
    {
        this.newsdata = new ArrayList<NewsItem>(data);
        //TODO 提示信息改变
        this.notifyDataSetChanged();
    }

    public void appendNewsData(List<NewsItem> data)
    {
        int joint = this.newsdata.size();
        this.newsdata.addAll(data);
        //TODO 提示信息改变
        this.notifyItemRangeChanged(joint,getItemCount());
    }

    public void removeItem(int pos)
    {
        this.newsdata.remove(pos);
        //TODO 提示信息改变
        this.notifyItemRemoved(pos);
    }

    public void setHasRead(int pos, boolean has_read)
    {
        NewsItem news = this.getnewItemById(pos);
        news.has_read = has_read;
        this.newsdata.set(pos,news);
    }

    public boolean isShowEnd() {return this.isshowend;}

    public void setEndVisible(boolean visible)
    {
        if (this.isshowend != visible){
            this.isshowend = visible;
            if (this.isshowend) this.notifyItemInserted(this.newsdata.size());
            else this.notifyItemRemoved(this.newsdata.size());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override//创建对象
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.newlist_end,parent,false);
            return new EndViewHolder(view);
        }

    }

    @Override//填充新闻内容
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder)
        {
            NewsItem newsItem = this.newsdata.get(position);
            final ItemViewHolder item = (ItemViewHolder) holder;
            item.Title.setText(newsItem.title);
            item.Author.setText(newsItem.news_Author);
            item.Date.setText(newsItem.time);
            //item.myimageview.setImageBitmap(null);
            int temp = position % 6;
            switch(temp){
                case 0:
                    item.myimageview.setImageResource(R.drawable.icon_qino);
                    break;
                case 1:
                    item.myimageview.setImageResource(R.drawable.icon_qino2);
                    break;
                case 2:
                    item.myimageview.setImageResource(R.drawable.icon_qino3);
                    break;
                case 3:
                    item.myimageview.setImageResource(R.drawable.icon_qino4);
                    break;
                case 4:
                    item.myimageview.setImageResource(R.drawable.icon_qino5);
                    break;
                case 5:
                    item.myimageview.setImageResource(R.drawable.icon_qino6);
                    break;
                default:
                    break;
            }
            item.PositioninList = position;
            if (newsItem.has_read) item.myview.setBackgroundColor(Color.parseColor("#FFE1FF"));
            else item.myview.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        if (this.isshowend) return this.newsdata.size() + 1;
        else return this.newsdata.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == this.newsdata.size() && isshowend == true) return TYPE_END;
        else return TYPE_NORM;
    }

    /*
        新闻单元格的listener
         */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /*
    列表内容
     */

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View myview;
        TextView Title,Author,Date;
        int PositioninList = -1;
        ImageView myimageview;

        public ItemViewHolder(View view) {
            super(view);
            this.myview = view;
            Title = (TextView) view.findViewById(R.id.text_title);
            Author = (TextView) view.findViewById(R.id.text_author);
            Date = (TextView) view.findViewById(R.id.text_date);
            myimageview = (ImageView) view.findViewById(R.id.image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view,this.getLayoutPosition());
            }
        }
    }

    /*
    列表底部
     */
    public class EndViewHolder extends RecyclerView.ViewHolder
    {
        public EndViewHolder(View view) {super(view);}
    }

}
