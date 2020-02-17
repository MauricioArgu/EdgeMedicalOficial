package com.example.edgemedicaloficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edgemedicaloficial.Model.mAppointment.Appointment;
import com.example.edgemedicaloficial.Model.mDoctor.Doctor;
import com.example.edgemedicaloficial.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder>
{
    List<Appointment> mValues;
    Context mContext;
    protected AppointmentAdapter.ItemListener mListener;

    public AppointmentAdapter(Context context, List<Appointment> values, AppointmentAdapter.ItemListener itemListener)
    {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView titulo;
        public TextView fecha;
        Appointment item;

        public ViewHolder(@NonNull View v) {
            super(v);

            v.setOnClickListener(this);
            titulo = v.findViewById(R.id.titulo);
            fecha = v.findViewById(R.id.fecha);
        }

        public void setData(Appointment item)
        {
            this.item = item;

            titulo.setText("Dr: " + item.getDoctor());
            fecha .setText(item.getFecha());
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


    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {

        holder.setData(mValues.get(position));
    }


    public int getItemCount()
    {
        return mValues.size();
    }

    public interface ItemListener
    {
        void onItemClick(Appointment item);
    }
}


