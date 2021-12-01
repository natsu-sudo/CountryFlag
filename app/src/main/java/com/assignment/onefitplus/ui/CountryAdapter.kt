package com.assignment.onefitplus.ui

import android.R.attr.name
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.onefitplus.R
import com.assignment.onefitplus.pojo.CountryDetail
import com.bumptech.glide.Glide
import java.util.*


class CountryAdapter(private val listener: (CountryDetail) -> Unit) :
    ListAdapter<CountryDetail, CountryAdapter.ViewHolder>(DiffCallBack()){
    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {


        private val countryFlag: ImageView =itemView.findViewById(R.id.country_flag)
        private val countryCode:TextView=itemView.findViewById(R.id.country_code)
        private val townName:TextView=itemView.findViewById(R.id.town_name)
        private val latitude:TextView=itemView.findViewById(R.id.lat)
        private val logitude:TextView=itemView.findViewById(R.id.log)
        init {
                itemView.setOnClickListener {
                    listener.invoke(getItem(adapterPosition))
                }
        }

        fun onBind(item: CountryDetail) {
            val sf1 = String.format("https://flagcdn.com/256x192/%s.png",
                item.country.toLowerCase(Locale.ROOT))
            Glide.with(view)
                .load(sf1)
                .error(R.drawable.ic_launcher_background)
                .into(countryFlag)
            countryCode.text=itemView.context.getString(R.string.country_code,item.country)
            townName.text=itemView.context.getString(R.string.town_name,item.name)
            latitude.text=itemView.context.getString(R.string.lat,item.coordinate.lat.toString())
            logitude.text=itemView.context.getString(R.string.logi,item.coordinate.lon.toString())
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=
            LayoutInflater.from(parent.context).inflate(
                R.layout.country_detail_layout,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


}

class DiffCallBack: DiffUtil.ItemCallback<CountryDetail>(){
    override fun areItemsTheSame(oldItem: CountryDetail, newItem: CountryDetail): Boolean {
        return oldItem.id==oldItem.id
    }

    override fun areContentsTheSame(oldItem: CountryDetail, newItem: CountryDetail): Boolean {
        return oldItem==newItem
    }

}