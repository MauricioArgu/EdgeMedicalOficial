package com.example.edgemedicaloficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edgemedicaloficial.Model.mDoctor.Doctor;
import com.example.edgemedicaloficial.R;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    List<Doctor> mValues;
    Context mContext;
    protected ItemListener mListener;

    public DoctorAdapter(Context context, List<Doctor> values, ItemListener itemListener)
    {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView nombre;
        Doctor item;

        public ViewHolder(@NonNull View v) {
            super(v);

            v.setOnClickListener(this);
            nombre = v.findViewById(R.id.nombre);
        }

        public void setData(Doctor item)
        {
            this.item = item;

            nombre.setText("Dr. " + item.getNombres());
        }

        @Override
        public void onClick(View v)
        {
            if (mListener != null)
            {
                mListener.onItemClick(item);
            }
        }
    }


    public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_doc, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {

        holder.setData(mValues.get(position));
    }


    public int getItemCount()
    {
        return mValues.size();
    }

    public interface ItemListener
    {
        void onItemClick(Doctor item);
    }
}

