package com.example.appclinica.ui.registry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.CitaMemory;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.dao.HorarioMedico;
import com.example.appclinica.ui.dao.Medico;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.helpers.JsonArrayCustomRequest;
import com.example.appclinica.ui.report.DetailFragment;
import com.example.appclinica.ui.report.ReportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.MyViewHolder> {

    private List<Medico> medicoList;
    private LayoutInflater inflater;
    private Fragment fragment1;
    private Context mContext;
    private NavController navController;
    private static int lastClickedPosition = -1;
    private int selectedItem;

    public static List<HorarioMedico> medicoHorarioList = new ArrayList<>();
    private CalendarView calendario2;
    private RadioGroup radioGroup2;
    private View root;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView gridIcon;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            title = (TextView) view.findViewById(R.id.txtMedico);
            gridIcon = (ImageView) view.findViewById(R.id.imgMedico);
        }
    }

    public MedicoAdapter(List<Medico> medicoList) {
        this.medicoList = medicoList;
        selectedItem = 0;
    }
    public MedicoAdapter(Context ctx, List<Medico> medicoList, RegistryPaso2Fragment fragment1) {
        this.medicoList = medicoList;
        this.inflater = LayoutInflater.from(ctx);
        this.fragment1 = fragment1;
        this.mContext = ctx;
        selectedItem = 0;
    }

    public MedicoAdapter(Context ctx, List<Medico> medicoList, RegistryPaso2Fragment fragment1, View root) {
        this.medicoList = medicoList;
        this.inflater = LayoutInflater.from(ctx);
        this.fragment1 = fragment1;
        this.mContext = ctx;
        selectedItem = 0;
        this.root = root;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medico_fila, parent, false);

        navController = NavHostFragment.findNavController(fragment1);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Medico medico = medicoList.get(position);

        holder.title.setText(medico.getNombre());
        holder.gridIcon.setImageResource(medico.getImagen());

        holder.cardView.setCardBackgroundColor ( inflater.getContext().getResources().getColor(R.color.colorCard));

        if (selectedItem == position) {
            holder.cardView.setCardBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorCardSelected));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);

                Toast.makeText(view.getContext(),medico.getNombre(),Toast.LENGTH_SHORT).show();

                if (root != null) {
                    final TabHost pestaña = (TabHost) root.findViewById(R.id.tabHost);
                    calendario2 = (CalendarView) root.findViewById(R.id.calendario2);
                    radioGroup2 = (RadioGroup) root.findViewById(R.id.radioGroupHorario2);
                    int tab = pestaña.getCurrentTab();
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //String selectedDate = sdf.format(new Date(calendario2.getDate()));
                    if (tab == 1) {
                        //prepareMedicosHorario2(tab, root, selectedItem, selectedDate);
                        prepareMedicosHorario2(tab, root, selectedItem, RegistryPaso2Fragment.fechaSeleccionada);
                    }
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return medicoList.size();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void prepareMedicosHorario2(final int tab, View root, int selectedItem, String selectedDate1) {
        medicoHorarioList.clear();

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        JSONObject jsonobject = new JSONObject();

        try {
            String medico = "";
            if (medicoList.size() > 0 ) medico = medicoList.get(selectedItem).getCodigo();
            Log.i("======>", selectedItem + "" );
            Log.i("======>", selectedDate1 );
            jsonobject.put("fecha", selectedDate1);
            jsonobject.put("medico", medico );
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("======>", e.getMessage());
        }

        JsonArrayCustomRequest stringRequest= new JsonArrayCustomRequest(Request.Method.POST, Constants.WSUrlGetProgramacion, jsonobject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            Log.i("======>", jsonArray.toString());

                            if (jsonArray.length() == 0){
                                Toast.makeText(mContext, R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }else {
                                HorarioMedico horarioMedico;
                                //List<String> items = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    horarioMedico = new HorarioMedico(object.getString("IdProgramacionAtencion"),
                                            object.getString("Fecha"),
                                            object.getString("Hora"),
                                            object.getString("IdMedico"),
                                            object.getString("nombreMedico"),
                                            object.getString("IdEspecialidad"),
                                            object.getString("especialidad"),
                                            object.getString("IdConsultorio"),
                                            object.getString("consultorio"),
                                            object.getString("IdSede"));

                                    medicoHorarioList.add(horarioMedico);
                                    RadioButton nuevoRadio = crearRadioButton(horarioMedico.getHora(), i);
                                    radioGroup2.addView(nuevoRadio);
                                }

                                RadioButton primerRadio = (RadioButton) radioGroup2.getChildAt(0);
                                primerRadio.setChecked(true);

                                progress.dismiss();

                            }
                        } catch (JSONException e) {
                            Log.i("======>", e.getMessage());
                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(fragment1.getActivity());
        requestQueue.add(stringRequest);

    }

    private RadioButton crearRadioButton(String marca, int i) {
        int id = i + 1;
        RadioButton nuevoRadio = new RadioButton(mContext);
        LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        nuevoRadio.setLayoutParams(params);
        nuevoRadio.setText(marca);
        nuevoRadio.setTag(marca);
        nuevoRadio.setId(id);
        return nuevoRadio;
    }

}