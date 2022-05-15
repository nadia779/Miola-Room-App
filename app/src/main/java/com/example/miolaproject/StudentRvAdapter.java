package com.example.miolaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentRvAdapter extends RecyclerView.Adapter<StudentRvAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Users> studentArrayList;
    private Context context;

    // creating constructor for our adapter class
    public StudentRvAdapter(ArrayList<Users> studentArrayList, Context context) {
        this.studentArrayList = studentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.student_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull StudentRvAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Users users = studentArrayList.get(position);
        holder.studentNameTV.setText(users.getFullName());
        holder.studentEmailTV.setText(users.getEmail());
        holder.studentPhoneTV.setText(users.getPhone());
        holder.studentRoleTV.setText(users.getRole());
        Glide.with(holder.studentImgTv.getContext()).load(users.getImage()).into(holder.studentImgTv);

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return studentArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView studentNameTV;
        private final CircleImageView studentImgTv;
        private final TextView studentRoleTV;
        private final TextView studentEmailTV;
        private final TextView studentPhoneTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            studentNameTV = itemView.findViewById(R.id.nameStudent);
            studentRoleTV = itemView.findViewById(R.id.StudentRole);
            studentEmailTV = itemView.findViewById(R.id.emailStudent);
            studentPhoneTV = itemView.findViewById(R.id.phoneStudent);
            studentImgTv = (CircleImageView) itemView.findViewById(R.id.ImageStudent);

        }
    }
}
