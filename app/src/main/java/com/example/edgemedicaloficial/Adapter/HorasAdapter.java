package com.example.edgemedicaloficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edgemedicaloficial.Activity.AddAppointmentActivity;
import com.example.edgemedicaloficial.Model.mHoras.Horas;
import com.example.edgemedicaloficial.R;

import java.util.List;

public class HorasAdapter extends RecyclerView.Adapter<HorasAdapter.ViewHolder> {
    List<Horas> mValues;
    Context mContext;
    protected ItemListener mListener;

    public HorasAdapter(Context context, List<Horas> values, ItemListener itemListener)
    {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView txtHora;
        public Button btnHora;
        Horas item;


        public ViewHolder(@NonNull View v) {
            super(v);

            v.setOnClickListener(this);
            txtHora = v.findViewById(R.id.txtHoraDisp);
        }

        public void setData(Horas item)
        {
            this.item = item;
            txtHora.setText(item.getHora());


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


    public HorasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_hora_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull HorasAdapter.ViewHolder holder, int position) {

        holder.setData(mValues.get(position));
    }


    public int getItemCount()
    {
        return mValues.size();
    }

    public interface ItemListener
    {
        void onItemClick(Horas item);
    }
}
