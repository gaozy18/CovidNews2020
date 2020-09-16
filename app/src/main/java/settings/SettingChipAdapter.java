package settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.dac.R;
import com.pchmn.materialchips.ChipView;
import java.util.List;

import data.Configuration;

public class SettingChipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Configuration.Category> chips;
    private OnRemoveChipListener onRemoveChipListener;
    private OnCountofChipsChangeListener onCountofChipsChangeListener;
    private int chip_index = -1;

    interface OnRemoveChipListener{
        void onRemoveChip(View view, int position);
    }

    interface OnCountofChipsChangeListener{
        void onChipsCountChange(int count);
    }

    public void setOnRemoveChipListener(OnRemoveChipListener listener){
        this.onRemoveChipListener = listener;
    }

    public void setOnCountofChipsChangeListener(OnCountofChipsChangeListener listener){
        this.onCountofChipsChangeListener = listener;
    }

    public SettingChipAdapter(List<Configuration.Category> list)
    {
        this.chips = list;
    }

    public SettingChipAdapter(List<Configuration.Category> list,int index)
    {
        this.chips = list;
        this.chip_index = index;
    }

    public Configuration.Category getChip(int position){ return this.chips.get(position); }

    public void addChip(Configuration.Category chip){
        this.chips.add(chip);
        this.notifyItemInserted(this.chips.size() - 1);
        if (onCountofChipsChangeListener != null) onCountofChipsChangeListener.onChipsCountChange(getItemCount());
    }

    public void removeChip(int position){
        this.chips.remove(position);
        this.notifyItemRemoved(position);
        if (onCountofChipsChangeListener != null) onCountofChipsChangeListener.onChipsCountChange(getItemCount());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CategoryHolder categoryHolder = (CategoryHolder) holder;
        categoryHolder.category.setLabel(getChip(position).title);
        categoryHolder.category.setDeletable(position > this.chip_index);
    }

    @Override
    public int getItemCount() {
        return this.chips.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ChipView category;

        public CategoryHolder(View view)
        {
            super(view);
            category = (ChipView) view.findViewById(R.id.category);
            category.setOnDeleteClicked((View v) -> {
                if (onRemoveChipListener != null) {
                    onRemoveChipListener.onRemoveChip(v,this.getLayoutPosition());
                }
            });
        }

    }
}
