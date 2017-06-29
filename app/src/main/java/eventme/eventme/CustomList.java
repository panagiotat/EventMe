package eventme.eventme;
/**
 * Created by andreas agapitos on 07-Jan-17.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends BaseAdapter {

    private final Activity context;
    ArrayList<Event> events;
    public CustomList(Activity context, ArrayList<Event> events) {
        this.context = context;
        this.events=events;


    }
    @Override
    public int getCount() {
        return events.size();
    }
    @Override
    public Object getItem(int position) {
        return events.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        txtTitle.setText(events.get(position).getDescription());

        //imageView.setImageBitmap(imageId[position]);
        return rowView;
    }
}