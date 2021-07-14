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

public class HODAdapter extends RecyclerView.Adapter<HODAdapter.PermissionsHolder> {

    public List<Permissions> permissionsList;


    public HODAdapter(List<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }



    @NonNull
    @Override
    public PermissionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_hod_item, viewGroup, false);
        return new PermissionsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionsHolder permissionsHolder, int i) {
        final int itemPos = i;
        final Permissions permissions = permissionsList.get(itemPos);
        permissionsHolder.title.setText(permissionsList.get(i).getTitle());
        permissionsHolder.description.setText(permissionsList.get(i).getDescription());
        permissionsHolder.council.setText(permissionsList.get(i).getCouncil());
        permissionsHolder.room.setText(permissionsList.get(i).getRoom());
        permissionsHolder.status.setText(permissionsList.get(i).getStatus());
        if(permissionsList.get(i).getStatus().toString().equals("accepted"))
            permissionsHolder.status.setTextColor(Color.parseColor("#4CAF50"));
        else if(permissionsList.get(i).getStatus().toString().equals("rejected"))
            permissionsHolder.status.setTextColor(Color.parseColor("#F44336"));
        else
            permissionsHolder.status.setTextColor(Color.parseColor("#D3D3D3"));
        permissionsHolder.ID.setText(permissionsList.get(i).getDocid());
        permissionsHolder.created.setText(permissionsList.get(i).getCreated());
        permissionsHolder.date.setText(permissionsList.get(i).getDate());
        permissionsHolder.time.setText(permissionsList.get(i).getTime());
        permissionsHolder.hod.setText(permissionsList.get(i).getHod_name());
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
        TextView room;
        TextView status;
        TextView ID;
        TextView created;
        TextView time;
        TextView hod;

        public PermissionsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_permission);
            council = itemView.findViewById(R.id.council);
            date = itemView.findViewById(R.id.date2);
            description = itemView.findViewById(R.id.descr);
            status = itemView.findViewById(R.id.status);
            room = itemView.findViewById(R.id.room);
            ID = itemView.findViewById(R.id.ID);
            created = itemView.findViewById(R.id.created);
            time = itemView.findViewById(R.id.time);
            hod = itemView.findViewById(R.id.hod);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RoomHOD.class);
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(title.getText().toString());
                    data.add(council.getText().toString());
                    data.add(description.getText().toString());
                    data.add(room.getText().toString());
                    data.add(status.getText().toString());
                    data.add(ID.getText().toString());
                    data.add(date.getText().toString());
                    data.add(created.getText().toString());
                    data.add(time.getText().toString());
                    data.add(hod.getText().toString());
                    intent.putStringArrayListExtra("data", data);
                    context.startActivity(intent);
                }
            });
        }

    }

}
