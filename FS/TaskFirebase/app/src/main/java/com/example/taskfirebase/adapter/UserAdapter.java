package com.example.taskfirebase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfirebase.R;
import com.example.taskfirebase.UserActivity;
import com.example.taskfirebase.model.User;
import com.example.taskfirebase.databinding.RvItemBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<User> users;

    public UserAdapter(Context context , ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    private IUserSelectListener mSelLsnr = null;

    public interface IUserSelectListener{
        void onSelected(User user);
    }
    public UserAdapter(Context context , ArrayList<User> users, IUserSelectListener lsnr){
        this.context = context;
        this.users = users;
        this.mSelLsnr = lsnr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);

        holder.binding.tvId.setText(user.getUserId());
        holder.binding.tvName.setText(user.getUserName());
        holder.binding.tvNumber.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        if (users == null)
            return 0;
        else if ( users.size() == 0)
            return 0;

        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RvItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
//                    System.out.println("위치입니다. " + pos);
//                    Intent intent = new Intent(context, UserActivity.class);
//                    intent.putExtra("userKey" ,users.get(pos).getKey());
//                    v.getContext().startActivity(intent);
                    if ( pos != RecyclerView.NO_POSITION){
                        User user = users.get(pos);
                        if(mSelLsnr != null)
                            mSelLsnr.onSelected(user);
                    }
                }
            });
        }
    }
}
