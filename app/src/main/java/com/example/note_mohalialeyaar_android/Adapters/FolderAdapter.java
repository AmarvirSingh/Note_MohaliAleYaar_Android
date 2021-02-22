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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mohalialeyaar_android.FolderModelClass;
import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.example.note_mohalialeyaar_android.MainActivity;
import com.example.note_mohalialeyaar_android.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.RVViewHolder> implements Filterable {

   Context context;
    ArrayList<FolderModelClass> arrayList;
    DatabaseHelperClass helper;
    ArrayList<FolderModelClass> filerList;



    // Constructor
    public FolderAdapter(Context context, ArrayList<FolderModelClass> arrayList, DatabaseHelperClass helper) {
        this.context = context;
        this.arrayList = arrayList;
        this.helper = helper;
        this.filerList = arrayList;

    }





    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_row_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {

        final String name = filerList.get(position).getFolderName();
        final int id = filerList.get(position).getFolderId();
        Log.i("tag ", "onBindViewHolder: " + name);

        final FolderModelClass modelClass = filerList.get(position);

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
        return filerList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charcater = constraint.toString();
                if (charcater.isEmpty()){
                    filerList = arrayList ;
                }else {
                    ArrayList<FolderModelClass> newfilterList = new ArrayList<>();
                    for (FolderModelClass row: arrayList){
                        if (row.getFolderName().toLowerCase().contains(charcater.toLowerCase())){
                            newfilterList.add(row);
                        }
                    }

                    filerList = newfilterList ;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filerList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filerList = (ArrayList<FolderModelClass>) results.values ;
                notifyDataSetChanged();
            }
        };



    }

    // ViewHolder for the recycler view
    public static class RVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView rowFolderName, rowFolderCount;
        ImageView deleteImageView;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);

            rowFolderName = itemView.findViewById(R.id.rowFolderTitle);
            rowFolderCount = itemView.findViewById(R.id.rowFolderCount);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // whenever user click on any row of recycler view this function will  be called
            // do intent or any other task

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}
