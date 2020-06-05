package example.com.events;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String URL_EVENTS = "http://192.168.1.5/events/public/api/events";

    ListView events_listview;
    private List<Event> eventList;
    long id = 0;
    Event event;
    int page = 1;
    int total_page;
    Button btn_prev;
    Button btn_next;
    Boolean request_status = false;
    TabLayout tablayout;
    Map<String, String> categories = new HashMap<String, String>();
    int id_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);
        events_listview = findViewById(R.id.events_listview);
        events_listview.setClickable(true);
        events_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id_selection) {
                id = id_selection;
                event = (Event) events_listview.getAdapter().getItem(position);
                Log.i("record", event.getId());
                display_event(event);
            }
        });
        loadPosts(page);
    }

    private void loadPosts(int page) {
        String URL = URL_EVENTS + "?page=" + page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject output = new JSONObject(response);
                            total_page = output.getInt("total_page");
                            JSONArray records = output.getJSONArray("records");
                            eventList = new ArrayList<>();
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject record = records.getJSONObject(i);
                                Event event = new Event(record.getString("events.id"),
                                        record.getString("events.title"),
                                        record.getString("events.image"),
                                        record.getString("events.start_date"),
                                        record.getString("events.end_date"),
                                        record.getString("events.start_time"),
                                        record.getString("events.end_time"),
                                        record.getString("url")
                                );
                                eventList.add(event);
                            }
                            ListViewAdapter adapter = new ListViewAdapter(eventList, getApplicationContext());
                            events_listview.setAdapter(adapter);
                            setButtonEnabled();
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

    public void display_event(Event event) {
        String url = event.getUrl();
        Intent intent = new Intent(MainActivity.this, EventActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
        Log.i("action", "Event");
    }

    public void setButtonEnabled() {
        if (page == 1) {
            btn_prev.setEnabled(false);
        } else {
            btn_prev.setEnabled(true);
        }
        if (page == total_page) {
            btn_next.setEnabled(false);
        } else {
            btn_next.setEnabled(true);
        }
    }

    public void load(View view) {
        loadPosts(1);
    }

    public void prev(View view) {
        if (page > 1) {
            page--;
            loadPosts(page);
        }
    }

    public void next(View view) {
        if (page < total_page) {
            page++;
            loadPosts(page);
        }
    }
}

