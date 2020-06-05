package example.com.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventActivity extends AppCompatActivity {
    TextView label_title;
    TextView label_date;
    TextView label_time;
    TextView label_content;
    ImageView image_event;
    EventActivity event_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        event_activity = this;
        loadPost();
    }

    private void loadPost() {
        String url = this.getIntent().getExtras().getString("url");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject record = new JSONObject(response);
                            label_title = findViewById(R.id.label_title);
                            label_date = findViewById(R.id.label_date);
                            label_time = findViewById(R.id.label_time);
                            label_content = findViewById(R.id.label_content);
                            image_event = findViewById(R.id.image_event);
                            Glide.with(event_activity).load(record.getString("image")).into(image_event);
                            label_title.setText(record.getString("title"));
                            label_date.setText(record.getString("start_date") + " - " +
                                    record.getString("end_date"));
                            label_time.setText(record.getString("start_time") + " - " +
                                    record.getString("end_time"));
                            label_content.setText(Html.fromHtml(record.getString("description")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
