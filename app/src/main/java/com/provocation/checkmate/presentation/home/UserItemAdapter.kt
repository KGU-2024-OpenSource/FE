package com.provocation.checkmate.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.provocation.checkmate.R

class UserItemAdapter(val itemList: ArrayList<UserItemList>) :
    RecyclerView.Adapter<UserItemAdapter.UserViewHolder>() {

        inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val profile: ImageView = itemView.findViewById(R.id.userImageView)
            val mateName: TextView = itemView.findViewById(R.id.mateName)
            val smoke: TextView = itemView.findViewById(R.id.smoke)
            val mbti: TextView = itemView.findViewById(R.id.mbti)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_home, parent, false)
            return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = itemList[position]
        holder.profile.setImageResource(item.profileLocal)
        holder.mateName.text = item.mateNickName
        holder.smoke.text = item.smoke
        holder.mbti.text = item.mbti
    }
}