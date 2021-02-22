package com.example.note_mohalialeyaar_android.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mohalialeyaar_android.FolderModelClass;
import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.example.note_mohalialeyaar_android.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.RVViewHolder> {

   Context context;
    ArrayList<FolderModelClass> arrayList;
    DatabaseHelperClass helper;



    // Constructor
    public FolderAdapter(Context context, ArrayList<FolderModelClass> arrayList, DatabaseHelperClass helper) {
        this.context = context;
        this.arrayList = arrayList;
        this.helper = helper;
    }





    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_row_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {

        final String name = arrayList.get(position).getFolderName();
        final int id = arrayList.get(position).getFolderId();
        Log.i("tag ", "onBindViewHolder: " + name);

        final FolderModelClass modelClass = arrayList.get(position);

        holder.rowFolderName.setText(name);

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Folder activity ", "onClick: "+ String.valueOf(modelClass.getFolderId()));
                deleteFolder(modelClass);
            }
        });




    }

    private void deleteFolder(FolderModelClass modelClass) {

        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("Are You Sure ??");
        builder.setMessage("Do you want to delete "+ modelClass.getFolderName());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                long result = helper.deleteFolder(modelClass.getFolderId());

                if (result != -1) {
                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "not deleted", Toast.LENGTH_SHORT).show();
                }




            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // ViewHolder for the recycler view
    public static class RVViewHolder extends RecyclerView.ViewHolder{

        TextView rowFolderName, rowFolderCount;
        ImageView deleteImageView;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);

            rowFolderName = itemView.findViewById(R.id.rowFolderTitle);
            rowFolderCount = itemView.findViewById(R.id.rowFolderCount);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);

        }
    }


}
