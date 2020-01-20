package edu.aku.hassannaqvi.uen_sosas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.FormsContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.databinding.ItemChildListBinding;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder> {


    OnItemClicked itemClicked;
    private Context mContext;
    private List<FamilyMembersContract> mList;
    DatabaseHelper db;
    FormsContract ChildData;
    boolean isMother;

    private ItemChildListBinding viewHolder;

    public ChildListAdapter(Context mContext, List<FamilyMembersContract> mList, boolean isMother) {
        this.mContext = mContext;
        this.mList = mList;
        this.isMother = isMother;
        db = new DatabaseHelper(mContext);

    }

    public void setItemClicked(OnItemClicked itemClicked) {
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        ItemChildListBinding bi = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_child_list, viewGroup, false);
        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.bi.id.setText("Serial No: " + mList.get(i).getSerialno());
        holder.bi.name.setText(isMother ? mList.get(i).getName() : mList.get(i).getName() + "/" + mList.get(i).getMotherName());
        holder.bi.dob.setText("Age: " + mList.get(i).getAge() + " Year(s)");
        holder.bi.index.setText(String.valueOf(i + 1));
        holder.bi.genderImage.setImageResource(isMother ? R.drawable.mother : R.drawable.boy);
        holder.bi.parentLayout.setOnClickListener(v -> {
                    itemClicked.onItemClick(mList.get(i), i, isMother);
                }
        );

        viewHolder = holder.bi;

    }

    public ItemChildListBinding getHolder() {
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClicked {
        void onItemClick(FamilyMembersContract item, int position, boolean isMother);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemChildListBinding bi;

        ViewHolder(@NonNull ItemChildListBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }
}
