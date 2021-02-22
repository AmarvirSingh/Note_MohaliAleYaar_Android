package com.example.note_mohalialeyaar_android.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    ArrayList<String> arrayList;
    DatabaseHelperClass helper;



    // Constructor
    public FolderAdapter(Context context, ArrayList<String> arrayList, DatabaseHelperClass helper) {
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

        final String name = arrayList.get(position);
        Log.i("tag ", "onBindViewHolder: " + name);
        holder.rowFolderName.setText(name);




    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // ViewHolder for the recycler view
    public static class RVViewHolder extends RecyclerView.ViewHolder{

        TextView rowFolderName, rowFolderCount;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);

            rowFolderName = itemView.findViewById(R.id.rowFolderTitle);
            rowFolderCount = itemView.findViewById(R.id.rowFolderCount);


        }
    }


}
