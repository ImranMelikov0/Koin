package com.imranmelikov.koinkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imranmelikov.koinkotlin.databinding.RecyclerRowBinding
import com.imranmelikov.koinkotlin.model.Crypto

class CryptoAdapter(var cryptoArrayList: ArrayList<Crypto>, var listener: Listener):RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {
    interface Listener {
        fun onItemClick(cryptoModel: Crypto)
    }
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class CryptoViewHolder(var binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        var binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoArrayList.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoArrayList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.cryptoNameText.text=cryptoArrayList.get(position).currency
        holder.binding.cryptoPriceText.text=cryptoArrayList.get(position).price
    }
}