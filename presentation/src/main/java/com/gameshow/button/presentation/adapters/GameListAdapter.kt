import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.ItemUserGameBinding
import com.gameshow.button.domain.entities.User
import com.gameshow.button.presentation.viewmodel.SocketViewModel

class GameListAdapter(private val viewModel: SocketViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userList: List<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingNormal = ItemUserGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(bindingNormal)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        MyList(holder as MyViewHolder, position)
    }

    private inner class MyViewHolder(val binding: ItemUserGameBinding) : RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private fun MyList(viewHolder: MyViewHolder, position: Int) {
        val item = userList[position]
        val resourceId = viewModel.getAvatar(item.avatarID)
        if (resourceId != null)
            viewHolder.binding.avatarImageView.setImageResource(resourceId)
        if(item.isClicked) {
            val positionString = (position + 1).toString()
            viewHolder.binding.numberList.isVisible = true
            viewHolder.binding.numberList.text = positionString
            viewHolder.binding.numberList.setTextColor(Color.RED)
            viewHolder.binding.userName.text = item.nickname
            viewHolder.binding.userName.setTextColor(Color.RED)
        } else {
            viewHolder.binding.numberList.isVisible = false
            viewHolder.binding.userName.text = item.nickname
            viewHolder.binding.userName.setTextAppearance(R.style.Name)
        }
        viewHolder.binding.materialCardView.setOnLongClickListener {
            clickListener?.onLongClick(item, viewHolder, position)
            return@setOnLongClickListener true
        }
    }

    interface ClickListener {
        fun onLongClick(item: User, holder: RecyclerView.ViewHolder, position: Int)
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    private var clickListener: ClickListener? = null

    fun updateList(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(GameListDiffCallback(userList, newList))
        userList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}