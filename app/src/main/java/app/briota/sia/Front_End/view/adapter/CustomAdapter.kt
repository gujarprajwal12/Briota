package app.briota.sia.Front_End.view.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.findair.findairble.entities.FindAirDevice

class CustomAdapter
/**
 * Initialize the dataset of the Adapter.
 *
 * @param findAirDeviceList String[] containing the data to populate views to be used
 * by RecyclerView.
 */(
    private val findAirDeviceList: List<FindAirDevice>,
    private val clickListener: View.OnClickListener
) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.simple_list_item_1, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = findAirDeviceList[position].mac
        viewHolder.textView.tag = findAirDeviceList[position]
        viewHolder.textView.setOnClickListener(clickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return findAirDeviceList.size
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.text1)
        }
    }
}