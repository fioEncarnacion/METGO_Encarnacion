package empresa.android.metgo_encarnacion.ui.reclamo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import empresa.android.metgo_encarnacion.BusAdaptador;
import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentReclamoBinding;
import empresa.android.metgo_encarnacion.databinding.FragmentSugerenciaBinding;
import empresa.android.metgo_encarnacion.ui.reclamo.ReclamoViewModel;

public class ReclamoFragment extends Fragment {

    private EditText txtNombre, txtEmail, txtCodigo, txtDescripcion;
    private Button btnEnviar;
    private FragmentReclamoBinding binding;

    // Lista de códigos válidos (puedes personalizarla o cargarla desde un servidor)
    private List<String> codigosValidos = Arrays.asList("101", "102", "103", "200", "300");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReclamoViewModel reclamoViewModel =
                new ViewModelProvider(this).get(ReclamoViewModel.class);

        // Inflar el diseño del fragmento y obtener la vista
        View r = inflater.inflate(R.layout.fragment_reclamo, container, false);

        // Vincular las variables con los elementos del XML
        txtNombre = r.findViewById(R.id.txtnombre_r);
        txtEmail = r.findViewById(R.id.txtemail_r);
        txtCodigo = r.findViewById(R.id.txtcodigo_r);
        txtDescripcion = r.findViewById(R.id.txtdescripcion_r);
        btnEnviar = r.findViewById(R.id.btnenviar_r);

        // Configurar eventos, como el clic del botón
        btnEnviar.setOnClickListener(v -> {
            // Obtener los valores de los campos
            String nombre = txtNombre.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String codigo = txtCodigo.getText().toString().trim();
            String descripcion = txtDescripcion.getText().toString().trim();

            // Validar que todos los campos estén llenos
            if (nombre.isEmpty() || email.isEmpty() || codigo.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar si el código de la parada o bus es válido
            if (!codigosValidos.contains(codigo)) {
                Toast.makeText(getContext(), "Código no reconocido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Enviar los datos si todo es válido
            new EnviarDatos().execute(nombre, email, codigo, descripcion);
        });

        return r;
    }

    private class EnviarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                // URL del archivo PHP
                URL url = new URL("http://192.168.1.2:8012/crud_metgo/insert_reclamo.php"); // Usa "10.0.2.2" para el emulador
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                // Construir los datos a enviar
                String data = "nombre=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&email=" + URLEncoder.encode(params[1], "UTF-8") +
                        "&codigo=" + URLEncoder.encode(params[2], "UTF-8") +
                        "&descripcion=" + URLEncoder.encode(params[3], "UTF-8");

                // Enviar los datos
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Verificar la respuesta del servidor
                int responseCode = conn.getResponseCode();
                return (responseCode == HttpURLConnection.HTTP_OK) ? "Enviado exitosamente" : "Error al enviar reclamo";

            } catch (Exception e) {
                e.printStackTrace();
                return "Excepción: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

            // Limpiar los campos si el mensaje es "Enviado exitosamente"
            if (result.equals("Enviado exitosamente")) {
                txtNombre.setText("");
                txtEmail.setText("");
                txtCodigo.setText("");
                txtDescripcion.setText("");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
