package id.candraibra.bukuwarungtest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import id.candraibra.bukuwarungtest.data.model.User
import id.candraibra.bukuwarungtest.databinding.LayoutItemUserBinding

class HomeAdapter(private val list: List<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskViewHolder(LayoutItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        (holder as TaskViewHolder).bind(data)
    }

    inner class TaskViewHolder(private val view: LayoutItemUserBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(data: User) {
            view.tvUser.text = String.format("%s %s", data.firstName, data.lastName)
            view.ivAvatar.load(data.avatar) { transformations(RoundedCornersTransformation(20f)) }
            view.tvEmail.text = data.email
            view.root.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToDetailUserFragment(data.id)
                view.root.findNavController().navigate(action)
            }
        }
    }

}