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
import com.example.note_mohalialeyaar_android.UpdateActivity;

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
        holder.rowTitle.setText(modelClass.getNoteTitle());
        holder.rowDesc.setText(modelClass.getNoteDescription());

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Note Added on - " + modelClass.getNoteDateTime() + "\n" + modelClass.getNoteAddress() , Toast.LENGTH_SHORT).show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("noteid",modelClass.getNoteID());
                context.startActivity(intent);

            }
        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                if (modelClass.getNoteLocation() != null) {
                    String[] array = modelClass.getNoteLocation().split(",");

                    intent.putExtra("array", array);
                }
                context.startActivity(intent);


            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                deleteNote(modelClass,position );

                return true;
            }
        });




    }

    private void deleteNote( NotesModelClass model, int position) {

        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("Are You Sure ??");
        builder.setMessage("Do you want to delete "+ model.getNoteTitle());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
    try {

    long result = helper.deleteNote(model.getNoteID());

    if (result != -1) {
        Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
        //notifyDataSetChanged();
        notifyItemRemoved(position);
        filerList.remove(position);
    } else {
        Toast.makeText(context, "not deleted", Toast.LENGTH_SHORT).show();
    }

    }catch(Exception e){
    Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
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
    class RVNotesViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener {

        TextView rowTitle, rowDesc;
        TextView btnMap,  info;
        ImageView deleteImageView;

        public RVNotesViewHolder(@NonNull View itemView) {
            super(itemView);

            rowTitle = itemView.findViewById(R.id.row_note_title);
            rowDesc = itemView.findViewById(R.id.row_note_description);
            info = itemView.findViewById(R.id.row_btn_info);
            btnMap = itemView.findViewById(R.id.row_btn_map);
          /*  btnMap.setOnClickListener(new View.OnClickListener() {
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
*/

            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}
