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

public class TeacherRVAdapter extends RecyclerView.Adapter<TeacherRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Users> teacherArrayList;
    private Context context;

    // creating constructor for our adapter class
    public TeacherRVAdapter(ArrayList<Users> teacherArrayList, Context context) {
        this.teacherArrayList = teacherArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new TeacherRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.student_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TeacherRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Users users = teacherArrayList.get(position);
        holder.teachNameTV.setText(users.getFullName());
        holder.teachEmailTV.setText(users.getEmail());
        holder.teachPhoneTV.setText(users.getPhone());
        holder.teachRoleTV.setText(users.getRole());
        Glide.with(holder.teachImgTv.getContext()).load(users.getImage()).into(holder.teachImgTv);

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return teacherArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView teachNameTV;
        private final CircleImageView teachImgTv;
        private final TextView teachRoleTV;
        private final TextView teachEmailTV;
        private final TextView teachPhoneTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            teachNameTV = itemView.findViewById(R.id.nameTeacher);
            teachRoleTV = itemView.findViewById(R.id.TeacherRole);
            teachEmailTV = itemView.findViewById(R.id.emailTeacher);
            teachPhoneTV = itemView.findViewById(R.id.phoneSTeacher);
            teachImgTv = (CircleImageView) itemView.findViewById(R.id.ImageTeacher);

        }
    }

}
