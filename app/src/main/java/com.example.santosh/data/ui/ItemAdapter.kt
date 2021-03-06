package com.example.santosh.data.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.santosh.data.entities.Item
import com.example.santosh.databinding.ItemAdapterBinding

class ItemAdapter(private val listener: itemsItemListener) : RecyclerView.Adapter<itemsViewHolder>() {

    interface itemsItemListener {
        fun onClickeditems(itemsId: Int)
    }

    private val items = ArrayList<Item>()

    fun setItems(items: ArrayList<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemsViewHolder {
        val binding: ItemAdapterBinding = ItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemsViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: itemsViewHolder, position: Int) = holder.bind(items[position])
}

class itemsViewHolder(private val itemBinding: ItemAdapterBinding, private val listener: ItemAdapter.itemsItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var item: Item

    init {
        itemBinding.root.setOnClickListener(this)
    }


    fun bind(item: Item) {
        this.item = item
        itemBinding.name.text = item.name
        itemBinding.fullName.text = "${item.full_name}"
        Glide.with(itemBinding.root)
            .load(item.owner?.avatar_url)
            .transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickeditems(item.id)
    }
}

