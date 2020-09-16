package Expert;

import android.content.Context;
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

import data.ImageLoader;
import data.Scholar;

public class ExpertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Scholar> myscholars = new ArrayList<Scholar>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ExpertAdapter(Context context){ this.context = context; }

    public Scholar getScholarById(int id){ return this.myscholars.get(id); }

    public void setExpertData(List<Scholar> list){
        myscholars = new ArrayList<Scholar>();
        myscholars = list;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expert,parent,false);
        return new ExpertItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExpertItemViewHolder){
            Scholar scholar = this.myscholars.get(position);
            final ExpertItemViewHolder item = (ExpertItemViewHolder) holder;
            item.expert_name.setText(scholar.name + " " + scholar.name_zh);
            item.expert_factor.setText("Activity:" + scholar.activity + "\n" +
                                        "Citations:" + String.valueOf(scholar.citations));
            item.expert_position.setText(scholar.position);
            if (!scholar.avatar.isEmpty()) ImageLoader.displayImage(scholar.avatar,item.expertimageview);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return this.myscholars.size();
    }

    private class ExpertItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View myview;
        ImageView expertimageview;
        TextView expert_name,expert_factor,expert_position;

        public ExpertItemViewHolder(View view) {
            super(view);
            this.myview = view;
            expertimageview = (ImageView) view.findViewById(R.id.expert_image_view);
            expert_name = (TextView) view.findViewById(R.id.expert_name);
            expert_factor = (TextView) view.findViewById(R.id.expert_factor);
            expert_position = (TextView) view.findViewById(R.id.expert_position);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view,this.getLayoutPosition());
            }
        }
    }
}
