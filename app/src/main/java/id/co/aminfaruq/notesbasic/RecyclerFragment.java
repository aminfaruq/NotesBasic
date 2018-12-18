package id.co.aminfaruq.notesbasic;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment {

    public static DBCatatan dbCatatan;
    public static ArrayList<DataFilter> dataList;
    public static ArrayList<DataFilter> mFilterList;
    public static RecycleAdapter adapter;


    public RecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        RecyclerView rvCatatan = view.findViewById(R.id.rvCatatan);

        dbCatatan = new DBCatatan(getContext());

        dataList = new ArrayList<>();
        getData();

        //Create adapter
        adapter = new RecycleAdapter(dataList);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvCatatan.setLayoutManager(layoutManager);
        rvCatatan.setHasFixedSize(true);
        rvCatatan.setAdapter(adapter);

        return view;
    }

    public static void getData(){
        //create object database
        SQLiteDatabase readData = dbCatatan.getReadableDatabase();
        String query = " SELECT * FROM " + DBCatatan.MyColums.namaTabel + " ORDER BY ID_JUDUL DESC " ;
        Cursor cursor = readData.rawQuery(query,null);

        cursor.moveToFirst();

        for (int count = 0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            dataList.add(new DataFilter(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));

        }
    }

    public static void doFilter(String s){

        doUpdate();

        mFilterList = new ArrayList<>();

        s = s.toLowerCase();
        Log.e("judul sebelum", s);
        Log.e("Isi dataList sebelum", dataList.toString());
        for (DataFilter data : dataList){
            String nama = data.getJudul().toLowerCase();
            if (nama.contains(s)){
                Log.e("Isi data loop", data.getJudul());
                mFilterList.add(data);
            }
        }
        Log.e("Isi mFiltered for", mFilterList.toString());
        adapter.setFilter(mFilterList);
    }

    public static void doUpdate(){
        Log.e("Do update sebelum", dataList.toString());

        dataList.clear();
        getData();
        adapter.notifyDataSetChanged();
        Log.e("Masuk", "Do update");
        Log.e("DoUpdate sesudah", dataList.toString());
    }

}
