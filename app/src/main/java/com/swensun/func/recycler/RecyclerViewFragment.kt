package com.swensun.func.recycler

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.ItemRecyclerViewBinding
import com.swensun.potato.databinding.RecyclerViewFragmentBinding
import com.swensun.swutils.multitype.AnyCallback
import com.swensun.swutils.multitype.ViewBindingDelegate
import com.swensun.swutils.multitype.ViewBindingViewHolder
import com.swensun.swutils.multitype.submitList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecyclerViewFragment : BaseFragment<RecyclerViewFragmentBinding>() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private var count = 0

    private lateinit var viewModel: RecyclerViewViewModel

    private fun initView() {
//        recycler_view.closeDefaultAnimator()
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        /**
         * MultiTypeAdapter
         */
        val adapter = MultiTypeAdapter()
        adapter.register(RViewHolderDelegate())
        binding.recyclerView.adapter = adapter
        val items = (0 until 10).map { RInt(it) }
        adapter.submitList(RIntCallback(adapter.items, items))
        binding.btnRefresh.setOnClickListener {
            count += 1
            if (count % 2 == 0) {
            } else {
            }
            lifecycleScope.launch {
                (0..10).forEach {
                    delay(200)
                    val newItems = adapter.items.toMutableList()
                    newItems.add(1, RInt(newItems.size + 1))
                    withContext(Dispatchers.Main) {
                        adapter.submitList(RIntCallback(adapter.items, newItems))
                    }
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        initView()
    }
}

class RInt(val id: Int) {
    var count = id

    fun clone(): RInt {
        val rint = RInt(id)
        rint.count = count
        return rint
    }
}

class RViewHolderDelegate : ViewBindingDelegate<RInt, ItemRecyclerViewBinding>() {

    var loadMore: (() -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewBindingViewHolder<ItemRecyclerViewBinding>,
        item: RInt
    ) {
        if (holder.adapterPosition == adapter.itemCount - 1) {
            loadMore?.invoke()
        }
        holder.binding.tvId.text = item.id.toString()
    }
}

class RIntCallback(oldItems: List<Any>, newItems: List<Any>) : AnyCallback(oldItems, newItems) {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        when {
            oldItem is RInt && newItem is RInt -> {
                return oldItem.id == newItem.id
            }
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        when {
            oldItem is RInt && newItem is RInt -> {
                return oldItem.count == newItem.count
            }
        }
        return false
    }

}

fun RecyclerView.closeDefaultAnimator() {
    if (itemAnimator is SimpleItemAnimator) {
        (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }
}

