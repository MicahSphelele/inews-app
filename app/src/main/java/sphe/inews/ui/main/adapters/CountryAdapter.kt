package sphe.inews.ui.main.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import sphe.inews.R
import sphe.inews.models.Country

class CountryAdapter(private var list: List<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){

    private lateinit var listener: CountryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = list[position]

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            holder.image.setImageDrawable(holder.itemView.context.resources.getDrawable(country.image,null))

        }else{
            @Suppress("DEPRECATION")
            holder.image.setImageDrawable(holder.itemView.context.resources.getDrawable(country.image))
        }
        holder.title.text = country.countryName

        holder.itemView.setOnClickListener {
            listener.onCountryClicked(country)
        }

    }

    fun setCountryClickListener(listener: CountryListener){
        this.listener = listener
    }

    inner class ViewHolder(@NonNull v: View): RecyclerView.ViewHolder(v){
        var image : ImageView = v.findViewById(R.id.image)
        var title :TextView  = v.findViewById(R.id.title)
    }

    interface CountryListener{
        fun onCountryClicked(country: Country)
    }
}