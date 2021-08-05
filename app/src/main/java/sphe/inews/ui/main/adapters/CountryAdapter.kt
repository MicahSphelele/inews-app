package sphe.inews.ui.main.adapters

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sphe.inews.R
import sphe.inews.databinding.ItemCountryBinding
import sphe.inews.models.Country
import sphe.inews.util.viewHolderItemBinding

class CountryAdapter(private var list: List<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){

    private lateinit var listener: CountryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(parent.viewHolderItemBinding(R.layout.item_country) as ItemCountryBinding)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = list[position]

        holder.image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,country.image))

        holder.title.text = country.countryName

        holder.itemView.setOnClickListener {
            listener.onItemCountryClick(country)
        }
    }

    fun setCountryClickListener(listener: CountryListener){
        this.listener = listener
    }

     class ViewHolder(@NonNull viewBinder: ItemCountryBinding): RecyclerView.ViewHolder(viewBinder.root){
        var image : ImageView = viewBinder.image
        var title :TextView  = viewBinder.title
    }

    interface CountryListener{
        fun onItemCountryClick(country: Country)
    }
}