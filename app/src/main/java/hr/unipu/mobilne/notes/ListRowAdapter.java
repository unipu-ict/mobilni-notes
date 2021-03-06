package hr.unipu.mobilne.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListRowAdapter extends BaseAdapter {
    public static final int NASLOV_MAX_LENGTH = 30;
    public static final int TEKST_MAX_LENGTH = 40;

    private DeleteBiljeskaAdapterCallback callback;
    private ArrayList<Biljeska> biljeske = new ArrayList<>();
    private Context context;


    public ListRowAdapter(ArrayList<Biljeska> biljeske, Context context) {
        this.biljeske = biljeske;
        this.context = context;
    }

    public void setCallback(DeleteBiljeskaAdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return biljeske.size();
    }

    @Override
    public Object getItem(int pos) {
        return biljeske.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return biljeske.get(pos).id;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_row, null);
        }

        //handle row data
        Biljeska biljeska = biljeske.get(position);

        TextView biljeskaNaslov = (TextView) row.findViewById(R.id.list_biljeska_naslov);
        String naslov = biljeska.getNaslov().length() > NASLOV_MAX_LENGTH ? biljeska.getNaslov().substring(0, NASLOV_MAX_LENGTH) : biljeska.getNaslov();
        biljeskaNaslov.setText(naslov);

        TextView biljeskaTekst = (TextView) row.findViewById(R.id.list_biljeska_tekst);
        String tekst = biljeska.getTekst().length() > TEKST_MAX_LENGTH ? biljeska.getTekst().substring(0, TEKST_MAX_LENGTH) + "..." : biljeska.getTekst();
        biljeskaTekst.setText(tekst);

        Button deleteBtn = (Button) row.findViewById(R.id.list_biljeska_delete_btn);

        // handle delete button behaviour
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.deletePressed(biljeske.get(position).getId());

                biljeske.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return row;
    }
}
