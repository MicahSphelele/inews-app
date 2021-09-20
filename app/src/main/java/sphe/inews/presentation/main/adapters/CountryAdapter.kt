package sphe.inews.presentation.main.adapters

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import sphe.inews.R
import sphe.inews.databinding.ItemCountryBinding
import sphe.inews.domain.models.Country
import sphe.inews.util.viewHolderItemBinding

class CountryAdapter(private var list: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private lateinit var listener: CountryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewHolderItemBinding(R.layout.item_country) as ItemCountryBinding)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val country = list[position]

        holder.viewBinder.country = country

        holder.itemView.setOnClickListener {
            listener.onItemCountryClick(country)
        }
    }

    fun setCountryClickListener(listener: CountryListener) {
        this.listener = listener
    }

    class ViewHolder(@NonNull val viewBinder: ItemCountryBinding) :
        RecyclerView.ViewHolder(viewBinder.root)

    interface CountryListener {
        fun onItemCountryClick(country: Country)
    }
}