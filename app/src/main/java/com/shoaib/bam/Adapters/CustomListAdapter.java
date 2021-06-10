package com.shoaib.bam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.bam.Interfaces.CustomListItemClick;
import com.shoaib.bam.R;
import com.shoaib.bam.utilities.ConstantValues;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder>  {

public ArrayList<HashMap<String, String>> dataArray;
private Context mContext;

private static final int ITEM = 0;
private static final int LOADING = 1;
private boolean isLoadingAdded = false;
public static final int REQUEST_PERMISSION_CODE = 300;
    CustomListItemClick customListItemClick;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView tv_category_name, tv_key, tv_parentId;
    private RelativeLayout rl_head;

    public MyViewHolder(final View view) {
        super(view);

        tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);
        tv_key = (TextView) view.findViewById(R.id.tv_key);
        tv_parentId = (TextView) view.findViewById(R.id.tv_parentId);
        rl_head = (RelativeLayout) view.findViewById(R.id.rl_head);


    }
}


    public CustomListAdapter(Context context , ArrayList<HashMap<String, String>> appealList, CustomListItemClick customListItemClick) {
        this.mContext = context;
        this.dataArray = appealList;
        this.customListItemClick = customListItemClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        MyViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_category_list_adapter_layout, parent, false);
        viewHolder = new MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            int pp = position+1;

            String key = dataArray.get(position).get(ConstantValues.officeId).toString();
            String categoryName = dataArray.get(position).get(ConstantValues.name).toString();

            holder.tv_category_name.setText(categoryName);
            holder.tv_key.setText(key);


            holder.rl_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customListItemClick.onSingleItemClick(view, position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataArray.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


}