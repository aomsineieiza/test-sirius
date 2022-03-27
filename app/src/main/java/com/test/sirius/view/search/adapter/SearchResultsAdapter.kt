package com.test.sirius.view.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.test.sirius.databinding.ItemSearchCityBinding
import com.test.sirius.databinding.ItemSearchNotFoundResultBinding
import com.test.sirius.model.CityDataModel

class SearchResultsAdapter(private val data: MutableList<CityDataModel>) :
    RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    var onItemClick: ((CityDataModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchResultsAdapter.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FOUND_DATA -> {
                ViewHolder(
                    ItemSearchCityBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_NOT_FOUND_DATA -> {
                ViewHolder(
                    ItemSearchNotFoundResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ViewHolder(
                    ItemSearchNotFoundResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: SearchResultsAdapter.ViewHolder, position: Int) {
        if (data.size > 0) {
            holder.bindView(data[position])
        } else {
            holder.bindViewNotFoundData()
        }
    }

    override fun getItemCount(): Int = if (data.size > 0) {
        data.size
    } else {
        1
    }

    inner class ViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: CityDataModel) {
            with(binding as ItemSearchCityBinding) {
                tvName.text = "${item.name}, ${item.country}"
                tvPosition.text = "${item.coord?.lat}, ${item.coord?.lon}"
//                tvSearchResult.text = item.keyword
                cvCity.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }

        fun bindViewNotFoundData() {}
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.size != 0) {
            VIEW_TYPE_FOUND_DATA
        } else {
            VIEW_TYPE_NOT_FOUND_DATA
        }
    }

    companion object {
        const val VIEW_TYPE_FOUND_DATA = 0
        const val VIEW_TYPE_NOT_FOUND_DATA = 1
    }
}