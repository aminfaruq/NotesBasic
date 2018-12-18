package id.co.aminfaruq.notesbasic;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    public static ArrayList<DataFilter> dataList;
    public static ArrayList<DataFilter> mFilterList;


    public RecycleAdapter(ArrayList<DataFilter> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder viewHolder, final int i) {
        final String judul = dataList.get(i).getJudul();
        final String isi = dataList.get(i).getIsi();
        final String id = String.valueOf(dataList.get(i).getId());

        viewHolder.judul.setText(judul);
        viewHolder.isi.setText(isi);

        //onClick for content
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateActivity.class);
                intent.putExtra(UpdateActivity.EXTRA_ID, id);
                intent.putExtra(UpdateActivity.EXTRA_JUDUL, judul);
                intent.putExtra(UpdateActivity.EXTRA_ISI, isi);
                context.startActivity(intent);
            }
        });

       viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View v) {
               PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
               popupMenu.inflate(R.menu.popup_menu);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()) {
                           case R.id.delete:
                               DBCatatan getDbCatatan = new DBCatatan(v.getContext());
                               SQLiteDatabase DeleteData = getDbCatatan.getWritableDatabase();

                               // Membuat query untuk mencari id_judul
                               String selection = DBCatatan.MyColums.id_judul + " LIKE ?";

                               // mengambil data ID
                               String[] selectionArgs = {id};
                               // Aksi Delete
                               DeleteData.delete(DBCatatan.MyColums.namaTabel, selection, selectionArgs);

                               // Menghapus data pada list recycleview
                               String position = String.valueOf(id.indexOf(id));
                               dataList.remove(i);

                               notifyItemRemoved(i);
                               notifyItemRangeChanged(Integer.parseInt(position), dataList.size());
                               Log.i("isi data dihapus", dataList.toString());
                               Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                               break;
                       }
                       return true;
                   }
               });
               popupMenu.show();
           }
       });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton overflow;
        private TextView judul,isi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            judul = itemView.findViewById(R.id.tvJudul);
            isi = itemView.findViewById(R.id.tvIsi);
            overflow = itemView.findViewById(R.id.overflow);
        }
    }

    public void setFilter(ArrayList<DataFilter> filterList){
        Log.e("Masuk setFilter", "yes");
        dataList = new ArrayList<>();
        dataList.addAll(filterList);
        Log.e("Isi data setFilter", dataList.toString());
        notifyDataSetChanged();
    }
}
