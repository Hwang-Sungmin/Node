package com.example.taskfirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfirebase.model.Orgs;
import com.example.taskfirebase.R;
import com.example.taskfirebase.databinding.RvItemBinding;

import java.util.ArrayList;

public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder> {
    Context context;
    ArrayList<Orgs> orgs;

    public OrgAdapter(Context context , ArrayList<Orgs> orgs){
        this.context = context;
        this.orgs = orgs;
    }


    private IOrgsSelectListener mSelLsnr = null;

    public interface IOrgsSelectListener {
        void onSelected(Orgs org);
    }

    public OrgAdapter(Context context , ArrayList<Orgs> orgs,IOrgsSelectListener lsnr) {
        this.context = context;
        this.orgs = orgs;
        mSelLsnr = lsnr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orgs org = orgs.get(position);

        holder.binding.tvNumber.setText(String.valueOf(position));
        holder.binding.tvName.setText(org.getOrgName());
        holder.binding.tvId.setText(org.getOwner());
    }

    @Override
    public int getItemCount() {
        if (orgs == null)
            return 0;
        else if ( orgs.size() == 0)
            return 0;

        return orgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RvItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Orgs org = orgs.get(pos);
                        if (mSelLsnr != null) mSelLsnr.onSelected(org);
                    }
                }
            });
        }
    }
}
