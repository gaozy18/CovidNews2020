package Entity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.dac.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import data.CoronaEntity;
import data.ImageLoader;


public class EntityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<CoronaEntity> coronaEntities = new ArrayList<CoronaEntity>();
    private List<Integer> visible;
    private OnItemClickListener onItemClickListener;
    private Context context;
    private DecimalFormat df = new DecimalFormat("0.000");

    public EntityAdapter(Context context){ this.context = context; }

    public CoronaEntity getEntityById(int position){ return coronaEntities.get(position); }

    public void setEntitiesData(List<CoronaEntity> data){
        this.coronaEntities = data;
        this.visible = new ArrayList<Integer>();
        visible.add(1);
        for (int i = 1;i < data.size();i++){
            visible.add(0);
        }
        System.out.println(data.size() + " " + visible.size());
        this.notifyDataSetChanged();
    }

    public void setVisibleNum(int last,int now){
        visible.set(last,0);
        visible.set(now,1);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entity,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            CoronaEntity coronaEntityitem = this.coronaEntities.get(position);
            final ItemViewHolder item = (ItemViewHolder) holder;
            item.textView_name_and_hot.setText(coronaEntityitem.label + "     热度: " + df.format(coronaEntityitem.hot));
            item.textView_description.setText(coronaEntityitem.description);
            item.secondAdpater = new EntitySecondAdpater(context);
            item.secondAdpater.setData(coronaEntityitem.label,coronaEntityitem.fellows,coronaEntityitem.properties);
            item.secondlinearlayourmanager = new LinearLayoutManager(context);
            item.relationshiprecyclerview.setLayoutManager(item.secondlinearlayourmanager);
            item.relationshiprecyclerview.setItemAnimator(new DefaultItemAnimator());
            item.relationshiprecyclerview.setHasFixedSize(false);
            item.relationshiprecyclerview.setAdapter(item.secondAdpater);
            if (!coronaEntityitem.imgUrl.isEmpty()) ImageLoader.displayImage(coronaEntityitem.imgUrl,item.imageView_entity);
            /*if (!coronaEntityitem.imgUrl.isEmpty()){
                Bitmap map = Manager.M.loadPic(coronaEntityitem.imgUrl);
                if (map != null) item.imageView_entity.setImageBitmap(map);
            }*/
            /*if (!coronaEntityitem.imgUrl.isEmpty()) {
                Single<Bitmap> bitmapSingle = Manager.M.getPicFromURL(coronaEntityitem.imgUrl);
                bitmapSingle.subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        System.out.println(bitmap.toString());
                        if (bitmap != null) item.imageView_entity.setImageBitmap(bitmap);
                    }
                });
            }*/
            if (!this.visible.get(position).equals(1)) item.linearLayout_proinfo.setVisibility(View.GONE);
            else item.linearLayout_proinfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return coronaEntities.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View myview;
        private TextView textView_name_and_hot,textView_description;
        private LinearLayout linearLayout_proinfo;
        private ImageView imageView_entity;
        private RecyclerView relationshiprecyclerview;
        private EntitySecondAdpater secondAdpater;
        private LinearLayoutManager secondlinearlayourmanager;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myview = itemView;
            this.textView_name_and_hot = (TextView) myview.findViewById(R.id.entity_name_hot);
            this.textView_description = (TextView) myview.findViewById(R.id.entity_discripition);
            this.linearLayout_proinfo = (LinearLayout) myview.findViewById(R.id.entity_pro_message);
            this.imageView_entity = (ImageView) myview.findViewById(R.id.entity_image_view);
            this.relationshiprecyclerview = (RecyclerView) myview.findViewById(R.id.entity_rationship_and_attributes);
            this.myview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view,this.getLayoutPosition());
            }
        }
    }
}
