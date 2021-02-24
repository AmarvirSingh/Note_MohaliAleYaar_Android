package com.example.note_mohalialeyaar_android.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mohalialeyaar_android.AddNoteActivity;
import com.example.note_mohalialeyaar_android.FolderModelClass;
import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.example.note_mohalialeyaar_android.MapsActivity;
import com.example.note_mohalialeyaar_android.NotesModelClass;
import com.example.note_mohalialeyaar_android.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.RVNotesViewHolder> implements Filterable {

    public Context context;
    ArrayList<NotesModelClass> arrayList;
    DatabaseHelperClass helper;
    ArrayList<NotesModelClass> filerList;



    // Constructor
    public NotesAdapter(Context context, ArrayList<NotesModelClass> arrayList, DatabaseHelperClass helper) {
        this.context = context;
        this.arrayList = arrayList;
        this.helper = helper;
        this.filerList = arrayList;

    }





    @NonNull
    @Override
    public RVNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVNotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVNotesViewHolder holder, int position) {

        String name = filerList.get(position).getNoteTitle();
        final int id = filerList.get(position).getNoteID();
        final String location = filerList.get(position).getNoteLocation();
        Log.i("tag ", "onBindViewHolder: " + name);

        if(location!=null)
        {
            if(!location.equals(""))
            {
                String[] loc = location.split(",");
                holder.btnMap.setTag(loc);
            }
        }

        final NotesModelClass modelClass = filerList.get(position);
        holder.rowTitle.setText(modelClass.getNoteTitle().toString());
        holder.rowDesc.setText(modelClass.getNoteDescription().toString());


//        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("Folder activity ", "onClick: "+ String.valueOf(modelClass.getFolderId()));
//                deleteFolder(modelClass,position);
//            }
//        });




    }

    private void deleteFolder(FolderModelClass modelClass, int position) {

        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("Are You Sure ??");
        builder.setMessage("Do you want to delete "+ modelClass.getFolderName());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                long result = helper.deleteFolder(modelClass.getFolderId());

                if (result != -1) {
                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                    //notifyDataSetChanged();
                    notifyItemRemoved(position);
                    filerList.remove(position);
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
                    ArrayList<NotesModelClass> newfilterList = new ArrayList<>();
                    for (NotesModelClass row: arrayList){
                        if (row.getNoteTitle().toLowerCase().contains(charcater.toLowerCase())){
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
                filerList = (ArrayList<NotesModelClass>) results.values ;
                notifyDataSetChanged();
            }
        };



    }

    // ViewHolder for the recycler view
    class RVNotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView rowTitle, rowDesc;
        TextView btnMap;
        ImageView deleteImageView;

        public RVNotesViewHolder(@NonNull View itemView) {
            super(itemView);

            rowTitle = itemView.findViewById(R.id.row_note_title);
            rowDesc = itemView.findViewById(R.id.row_note_description);
            btnMap = itemView.findViewById(R.id.row_btn_map);
            btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] c =    (String[]) view.getTag();
                    String lat = "";
                    String longi = "";
                    if(c.length>1)
                    {
                        lat = c[0];
                        longi =c[1];
                    }
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("lat",lat);
                    intent.putExtra("long",longi);
                    context.startActivity(intent);
                }
            });


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // whenever user click on any row of recycler view this function will  be called
            // do intent or any other task
            Intent intent = new Intent(context, AddNoteActivity.class);

            // passing folder id so that all the notes should added to that folder only in notes tableView
//                intent.putExtra("folderId", filerList.get(getAdapterPosition()).getNoteID());
//                context.startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}
