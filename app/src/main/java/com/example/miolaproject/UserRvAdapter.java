package com.example.miolaproject;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserRvAdapter extends RecyclerView.Adapter<UserRvAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Users> usersArrayList;
    private Context context;

    // creating constructor for our adapter class
    public UserRvAdapter(ArrayList<Users> coursesArrayList, Context context) {
        this.usersArrayList = coursesArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public UserRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.users_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull UserRvAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Users users = usersArrayList.get(position);
        holder.userNameTV.setText(users.getFullName());
        holder.userRoleTV.setText(users.getRole());
        holder.userEmailTV.setText(users.getEmail());
        holder.userPhoneTV.setText(users.getPhone());
        Glide.with(holder.userImgTv.getContext()).load(users.getImage()).into(holder.userImgTv);

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return usersArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView userNameTV;
        private final CircleImageView userImgTv;
        private final TextView userRoleTV;
        private final TextView userEmailTV;
        private final TextView userPhoneTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            userNameTV = itemView.findViewById(R.id.nameUser);
            userRoleTV = itemView.findViewById(R.id.UserRole);
            userEmailTV = itemView.findViewById(R.id.emailUser);
            userPhoneTV = itemView.findViewById(R.id.phoneUser);
            userImgTv=(CircleImageView) itemView.findViewById(R.id.ImagePerson);

            // here we are adding on click listener
            // for our item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    Users users = usersArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, UpdateUserActivity.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("user", users);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);
                }
            });

        }
    }
}
