package utnfrt.sireciuser;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utnfrt.sireciuser.model.Contenedor;

public class MainScreen extends AppCompatActivity {

    private OkHttpClient client;

    private TextView mensajeText;
    private Button btnBuscar;
    private EditText campoX;
    private EditText campoY;
    private Spinner materialSpinner;

    private List<String> contenedores = new ArrayList<>();

    private ArrayList<Integer> idsDeContenedores;
    private Map<Integer, Contenedor> contenedoresMaps;
    private ArrayAdapter<String> adapter;
    private ListView contenedoresListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        mensajeText = (TextView)findViewById(R.id.textMensaje);

        campoX = (EditText)findViewById(R.id.campoX);
        campoY = (EditText)findViewById(R.id.campoY);
        materialSpinner = (Spinner)findViewById(R.id.spinnerMateriales);

        contenedoresListView = (ListView) findViewById(R.id.contenedoresListView);

        Button btnAgregar = (Button)findViewById(R.id.btnBuscar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String materialFromSpinner = materialSpinner.getSelectedItem().toString();
                getContenedoresFromApi(materialFromSpinner,campoX.getText().toString(),campoY.getText().toString());
            }
        });
    }

    public TextView getMensajeText() {
        return mensajeText;
    }

    private void getContenedoresFromApi(String materialFromSpinner,String x, String y) {
        client = new OkHttpClient();
        OkHttpHandler okHttpHandler= new OkHttpHandler(this);
        okHttpHandler.execute("https://sci-utn.herokuapp.com/contenedor?material="
                + materialFromSpinner + "&x=" + x + "&y=" + y);
    }

    public void getUbicacion(View view) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.campoX.setText("-26.816912");
        this.campoY.setText("-65.199361");
    }

    public class OkHttpHandler extends AsyncTask {
        private Context view;



        public OkHttpHandler(Context view) {
            this.view = view;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Request.Builder builder = new Request.Builder();
            builder.url(String.valueOf(params[0]));
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
//                return response.body().string();
                String jsonString = response.body().string();
//                Reader json = response.body().charStream();

                Type eventoListType = new TypeToken<ArrayList<String>>(){}.getType();

//                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                Gson gson = new Gson();
                contenedores = gson.fromJson(jsonString, eventoListType);
                return response.body().charStream();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

//            Log.i("contenedores", String.valueOf(contenedoresListView));

            adapter = new ArrayAdapter<String>(view,android.R.layout.activity_list_item,android.R.id.text1, contenedores);
            contenedoresListView.setAdapter(adapter);
//            String mensaje = String.valueOf(result);
//            mensajeText.setText(mensaje);
        }
    }
}
