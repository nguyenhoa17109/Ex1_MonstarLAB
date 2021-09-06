package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnClickItem{
    private EditText etName, etYear, etPhone, etMajor, etID;
    private Button btAdd, btGetAll, btDelete, btUpdate;
    private Spinner sp1, sp2, spStu;
    private RecyclerView rc;
    private SQLiteSV sqLiteSV;
    private RecyclerViewAdapter adapter;
    private LinearLayout layout;
    private SearchView searchView;

    private ArrayList<SinhVien> list;
    private String[] stu = {"Lọc", "Đại học", "Cao Đẳng"};
    private String[] stu1 = {"Đại học", "Cao Đẳng"};
    private String[] sortBy = {"Sort by Name", "Sort by YearOfBirth", "Sort by Phone number"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        createSpinner();
        setClick();
    }

    private SinhVien getSV(){
        String name = etName.getText().toString();
        String year = etYear.getText().toString();
        String phone = etPhone.getText().toString();
        String major = etMajor.getText().toString();
        String st = "";
        int i = spStu.getSelectedItemPosition();
        if(i == 0)  st = stu[1];
        else        st = stu[2];
        String j = etID.getText().toString();
        if(j.equals(""))
            return new SinhVien(name, year, phone, major, st);
        else {
            int id = Integer.parseInt(j);
            return new SinhVien(id, name, year, phone, major, st);
        }
    }
    private void setClick() {
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinhVien sv = getSV();
                if(!sv.getName().equals("") && !sv.getMajor().equals("")
                        && !sv.getSdt().equals("") && !sv.getDateOfBirth().equals("")){
                    if(sqLiteSV.addSV(sv) > 0){
                        etMajor.setText("");
                        etName.setText("");
                        etPhone.setText("");
                        etYear.setText("");
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Success!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Have empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.GONE);
                int id = Integer.parseInt(etID.getText().toString());
                if(sqLiteSV.deleteSV(id) > 0){
                    etMajor.setText("");
                    etName.setText("");
                    etPhone.setText("");
                    etYear.setText("");
                    Toast.makeText(getApplicationContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.GONE);
                SinhVien sv = getSV();
                if(sqLiteSV.updateSV(sv) > 0){
                    Toast.makeText(getApplicationContext(), "Update Success!", Toast.LENGTH_SHORT).show();
                    etMajor.setText("");
                    etName.setText("");
                    etPhone.setText("");
                    etYear.setText("");
                    update();
                } else {
                    Toast.makeText(getApplicationContext(), "No Success!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        list = (ArrayList<SinhVien>) sqLiteSV.getAllAsYearOfBirth();
                        break;
                    case 2:
                        list = (ArrayList<SinhVien>) sqLiteSV.getAllAsPhoneNumber();
                        break;
                    default:
                        list = (ArrayList<SinhVien>) sqLiteSV.getAllAsName();
                }
                adapter.setSV(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<SinhVien> lst = (ArrayList<SinhVien>) adapter.getList();
                switch (i){
                    case 1:
                        list = filter(stu[1], lst);
                        break;
                    case 2:
                        list = filter(stu[2], lst);
                        break;
                    default:
                        list = filter(stu[0], lst);
                }
                adapter.setSV(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayList<SinhVien> filter(String s, ArrayList<SinhVien> list){
        for(int i=0; i< list.size(); i++){
            if(!list.get(i).getEdu().equals(s) && s != stu[0])
                list.remove(i);
        }
        return list;
    }

    private void update(){
        list = (ArrayList<SinhVien>) sqLiteSV.getAllAsName();
        adapter.setSV(list);
    }

    private void init(){
        etName = findViewById(R.id.etName);
        etYear = findViewById(R.id.etDateOfBirth);
        etPhone = findViewById(R.id.etPhoneNum);
        etMajor = findViewById(R.id.etMajor);
        etID = findViewById(R.id.etID);

        btAdd = findViewById(R.id.btAdd);
        btGetAll = findViewById(R.id.btGetAll);
        btDelete = findViewById(R.id.btDelete);
        btUpdate = findViewById(R.id.btUpdate);

        sp1 = findViewById(R.id.sp1);
        sp2 = findViewById(R.id.sp2);
        spStu = findViewById(R.id.spStu);

        layout = findViewById(R.id.layout);

        rc = findViewById(R.id.recyclerView);
        rc.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, MainActivity.this::ClickItem);
        rc.setAdapter(adapter);

        sqLiteSV = new SQLiteSV(this);
        layout.setVisibility(View.GONE);
    }

    private void createSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, stu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, stu1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStu.setAdapter(adapter2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sortBy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void ClickItem(int position) {
        SinhVien sv = list.get(position);
        etID.setText(String.valueOf(sv.getId()));
        etMajor.setText(sv.getMajor());
        etName.setText(sv.getName());
        etPhone.setText(sv.getSdt());
        etYear.setText(sv.getDateOfBirth());
        layout.setVisibility(View.VISIBLE);
        Log.d("LOLO", sv.getId()+"");

    }


}