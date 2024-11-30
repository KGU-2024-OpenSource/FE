package com.provocation.checkmate.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.provocation.checkmate.R

class UserItemAdapter(val itemList: ArrayList<UserItemList>) :
    RecyclerView.Adapter<UserItemAdapter.UserViewHolder>() {

        inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nickname: TextView = itemView.findViewById(R.id.mateName)
            val profileImageUrl: ImageView = itemView.findViewById(R.id.userImageView)
            val studentId: TextView = itemView.findViewById(R.id.entrance_year)
            val birthYear: TextView = itemView.findViewById(R.id.age)
            val mbti: TextView = itemView.findViewById(R.id.mbti)
            val desire: TextView = itemView.findViewById(R.id.desired)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_home, parent, false)
            return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = itemList[position]
        holder.nickname.text = item.mateNickName

        Glide.with(holder.profileImageUrl.context)
            .load(item.profileImageUrl)
            .placeholder(R.drawable.flower_kgu)
            .into(holder.profileImageUrl)
        holder.studentId.text = "#${item.studentId}학번"
        holder.birthYear.text = "#${2024 - item.birthYear}살"
        holder.mbti.text = "#${item.mbti}"
        holder.desire.text = "#${item.desire}"
    }
}