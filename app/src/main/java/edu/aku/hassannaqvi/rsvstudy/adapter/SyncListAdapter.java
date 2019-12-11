package edu.aku.hassannaqvi.rsvstudy.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.aku.hassannaqvi.rsvstudy.R;
import edu.aku.hassannaqvi.rsvstudy.databinding.SyncListAdapterBinding;
import edu.aku.hassannaqvi.rsvstudy.otherClasses.SyncModel;

public class SyncListAdapter extends RecyclerView.Adapter<SyncListAdapter.SyncListViewHolder> {
    List<SyncModel> synclist;
    SyncListViewHolder holder;

    public SyncListAdapter(List<SyncModel> synclist) {
        this.synclist = synclist;
        this.setHasStableIds(true);
    }

    public void updatesyncList(List<SyncModel> synclist) {
        this.synclist = synclist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SyncListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sync_list_adapter, parent, false);
        return new SyncListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SyncListViewHolder holder, int position) {
        this.holder = holder;
        this.holder.bindUser(this.synclist.get(position));
    }

    @Override
    public int getItemCount() {
        return synclist.size() > 0 ? synclist.size() : 0;
    }

    private int checkStatus(int i) {
        switch (i) {
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    public class SyncListViewHolder extends RecyclerView.ViewHolder {
        SyncListAdapterBinding binding;


        public SyncListViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void bindUser(SyncModel model) {
            binding.statusColor.setBackgroundColor(checkStatus(model.getstatusID()));
            binding.tvTableName.setText(model.gettableName());
            binding.tvStatus.setText(model.getstatus());
            binding.tvMsg.setText(model.getmessage());
            if (model.getstatusID() == 1 || model.getstatusID() == 3) {
                binding.pb.setVisibility(View.GONE);
            } else {
                binding.pb.setVisibility(View.VISIBLE);
            }
        }
    }
}
