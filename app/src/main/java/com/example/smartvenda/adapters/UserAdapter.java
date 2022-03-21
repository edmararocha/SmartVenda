package com.example.smartvenda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartvenda.R;
import com.example.smartvenda.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;

    public UserAdapter (List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemUser = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new UserAdapter.MyViewHolder(itemUser);
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {
        User user = userList.get(position);

        holder.user.setText(user.getName());

        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() { return this.userList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView user;
        private TextView email;

        public MyViewHolder(View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.user_text_recycler);
            email = itemView.findViewById(R.id.email_text_recycler);
        }
    }
}

