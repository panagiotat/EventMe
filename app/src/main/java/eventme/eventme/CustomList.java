package eventme.eventme;
/**
 * Created by andreas agapitos on 07-Jan-17.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Bitmap[] imageId;
    public CustomList(Activity context,
                      String[] web,Bitmap[] imageId) {
        super(context, R.layout.list_row, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        txtTitle.setText(web[position]);

        imageView.setImageBitmap(imageId[position]);
        return rowView;
    }
}