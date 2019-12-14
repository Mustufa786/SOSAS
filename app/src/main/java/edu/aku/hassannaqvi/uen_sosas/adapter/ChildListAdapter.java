package edu.aku.hassannaqvi.uen_sosas.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildList;
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

    public ChildListAdapter(Context mContext, List<FamilyMembersContract> mList) {
        this.mContext = mContext;
        this.mList = mList;
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

        holder.bi.motherId.setText(mList.get(i).getMotherid());
        holder.bi.motherName.setText(mList.get(i).getMotherName());
        holder.bi.dob.setText(mList.get(i).getAge());
        holder.bi.index.setText(String.valueOf(i + 1));
//        holder.bi.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FormsContract ChildData;
//                ChildData = db.isDataExists(mList.get(i).getDssid());
//                if (ChildData != null) {
//                    if (!ChildData.getStatus().contains("1") && !ChildData.getStatus().contains("2")) {
//                        itemClicked.onItemClick(mList.get(i), i);
//                    } else {
//                        Toast.makeText(mContext, "Completed form for this child already exist!", Toast.LENGTH_LONG).show();
//                    }
//
//                } else {
//                    Toast.makeText(mContext, "Completed form for this child already exist!", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClicked {

        void onItemClick(ChildList item, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ItemChildListBinding bi;

        public ViewHolder(@NonNull ItemChildListBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }
}
