package com.example.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.VeViewHolder> implements Filterable {
    private Context context;
    private List<SinhVien> list, oldList;
    private OnClickItem  onClickItem;

    public RecyclerViewAdapter(Context context, OnClickItem onClickItem) {
        this.context = context;
        this.onClickItem = onClickItem;
    }

    public void setSV(List<SinhVien> list){
        this.list=list;
        this.oldList = list;
        notifyDataSetChanged();
    }

    public SinhVien getSV(int position){
        return list.get(position);
    }

    public interface OnClickItem{
        void ClickItem(int position);
    }

    @NonNull
    @Override
    public VeViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.item,parent,false);
        return new VeViewHolder(v);
    }
    public void onBindViewHolder(@NonNull VeViewHolder holder, int position) {
        SinhVien s=list.get(position);
        holder.tvName.setText("Name Student: "+s.getName());
        holder.tvYear.setText("Year Of Birht: " + s.getDateOfBirth());
        holder.tvPhone.setText("Phone number: "+s.getSdt());
        holder.tvMajor.setText("Major: "+s.getMajor());
        holder.tvStu.setText(s.getEdu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.ClickItem(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        else
            return 0;
    }

    public List<SinhVien> getList() {
        return list;
    }

    class VeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvYear, tvPhone, tvMajor, tvStu;

        public VeViewHolder(@NonNull View v) {
            super(v);
            tvYear = v.findViewById(R.id.tvYear);
            tvName=v.findViewById(R.id.tvName);
            tvMajor=v.findViewById(R.id.tvMajor);
            tvPhone=v.findViewById(R.id.tvPhone);
            tvStu = v.findViewById(R.id.tvStu);
        }

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()) {
                    list = oldList;
                } else{
                    ArrayList<SinhVien> list1 = new ArrayList<>();
                    for(SinhVien sv:list){
                        if(sv.getName().toLowerCase().contains(strSearch.toLowerCase()) ||
                            sv.getEdu().toLowerCase().contains(strSearch.toLowerCase()) ||
                            sv.getDateOfBirth().contains(strSearch) ||
                            sv.getSdt().contains(strSearch) ||
                            sv.getMajor().toLowerCase().contains(strSearch.toLowerCase()) ){
                            list1.add(sv);
                        }
                    }
                    list = list1;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<SinhVien>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
