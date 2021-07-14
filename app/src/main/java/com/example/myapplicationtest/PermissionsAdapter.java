package com.example.myapplicationtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PermissionsAdapter extends RecyclerView.Adapter<PermissionsAdapter.PermissionsHolder> {

    public List<Permissions> permissionsList;

    public PermissionsAdapter(List<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }


    @NonNull
    @Override
    public PermissionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new PermissionsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionsHolder permissionsHolder, int i) {
        permissionsHolder.title.setText(permissionsList.get(i).getTitle());
        permissionsHolder.description.setText(permissionsList.get(i).getDescription());
        permissionsHolder.council.setText(permissionsList.get(i).getCouncil());
        permissionsHolder.room.setText(permissionsList.get(i).getRoom());
        permissionsHolder.date.setText(permissionsList.get(i).getDate());
        permissionsHolder.status.setText(permissionsList.get(i).getStatus());
        if(permissionsList.get(i).getStatus().toString().equals("accepted"))
            permissionsHolder.status.setTextColor(Color.parseColor("#4CAF50"));
        else if(permissionsList.get(i).getStatus().toString().equals("rejected"))
            permissionsHolder.status.setTextColor(Color.parseColor("#F44336"));
        else
            permissionsHolder.status.setTextColor(Color.parseColor("#D3D3D3"));
        permissionsHolder.time.setText(permissionsList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return permissionsList.size();
    }

    public class PermissionsHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView council;
        TextView date;
        TextView description;
        TextView status;
        TextView room;
        TextView time;

        public PermissionsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_permission);
            council = itemView.findViewById(R.id.council);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            description = itemView.findViewById(R.id.descr);
            room = itemView.findViewById(R.id.room);
            time = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RoomDetail.class);
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(title.getText().toString());
                    data.add(council.getText().toString());
                    data.add(description.getText().toString());
                    data.add(room.getText().toString());
                    data.add(date.getText().toString());
                    data.add(time.getText().toString());
                    intent.putStringArrayListExtra("data", data);
                    context.startActivity(intent);
                }
            });
        }

    }

}
