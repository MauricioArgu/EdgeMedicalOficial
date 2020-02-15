package com.example.edgemedicaloficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edgemedicaloficial.Model.mEspecialidades.Especialidades;
import com.example.edgemedicaloficial.R;

import java.util.List;

public class EspecialidadAdapter extends RecyclerView.Adapter<EspecialidadAdapter.ViewHolder> {
    List<Especialidades> mValues;
    Context mContext;
    protected ItemListener mListener;

    public EspecialidadAdapter(Context context, List<Especialidades> values, ItemListener itemListener)
    {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView especialidadName;
        Especialidades item;

        public ViewHolder(@NonNull View v) {
            super(v);

            v.setOnClickListener(this);
            especialidadName = v.findViewById(R.id.especialidadName);
        }

        public void setData(Especialidades item)
        {
            this.item = item;

            especialidadName.setText(item.getNombre());
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


    public EspecialidadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_especialidades, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull EspecialidadAdapter.ViewHolder holder, int position) {

        holder.setData(mValues.get(position));
    }


    public int getItemCount()
    {
        return mValues.size();
    }

    public interface ItemListener
    {
        void onItemClick(Especialidades item);
    }
}

