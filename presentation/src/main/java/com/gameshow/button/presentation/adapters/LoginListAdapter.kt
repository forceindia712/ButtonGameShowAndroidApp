package com.gameshow.button.presentation.adapters

import LoginListDiffCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gameshow.button.presentation.databinding.ItemUserLoginBinding
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.presentation.viewmodel.SocketViewModel

class LoginListAdapter(private val viewModel: SocketViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var usersList: List<Profile> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginListAdapter.MyViewHolder {
        val binding =
            ItemUserLoginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        MyList(holder as LoginListAdapter.MyViewHolder, position)
    }

    inner class MyViewHolder(val binding: ItemUserLoginBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private fun MyList(holder: LoginListAdapter.MyViewHolder, position: Int) {
        val item = usersList[position]
        val resourceId = viewModel.getAvatar(item.avatarID)
        if (resourceId != null)
            holder.binding.avatarImageView.setImageResource(resourceId)

        holder.binding.userName.text = item.nickname

        holder.binding.materialCardView.setOnClickListener {
            clickListener?.onItemClick(item, holder, position)
        }

        holder.binding.materialCardView.setOnLongClickListener {
            clickListener?.onLongClick(item, holder, position)
            true
        }
    }

    interface ClickListener {
        fun onLongClick(item: Profile, holder: LoginListAdapter.MyViewHolder, position: Int)
        fun onItemClick(item: Profile, holder: LoginListAdapter.MyViewHolder, position: Int)
    }

    private var clickListener: ClickListener? = null

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    fun updateList(newList: List<Profile>) {
        val diffCallback = LoginListDiffCallback(usersList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        usersList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}