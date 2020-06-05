package example.com.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Joko Wandiro on 20/01/2020.
 */

public class ListViewAdapter extends ArrayAdapter<Event> {

    private List<Event> playerItemList;

    private Context context;

    public ListViewAdapter(List<Event> playerItemList, Context context) {
        super(context, R.layout.list_item, playerItemList);
        this.playerItemList = playerItemList;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);
        TextView label_title = listViewItem.findViewById(R.id.label_title);
        TextView label_date = listViewItem.findViewById(R.id.label_date);
        TextView label_time = listViewItem.findViewById(R.id.label_time);
        ImageView image_event = listViewItem.findViewById(R.id.image_event);
        Event event = playerItemList.get(position);
        Glide.with(context).load(event.getImage()).into(image_event);
        label_title.setText(event.getTitle());
        label_date.setText(event.getStartDate() + " - " + event.getEndDate());
        label_time.setText(event.getStartTime() + " - " + event.getEndTime());
        return listViewItem;
    }
}

