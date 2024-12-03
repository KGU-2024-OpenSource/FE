package com.provocation.checkmate.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.provocation.checkmate.FragmentManageActivity
import com.provocation.checkmate.MateDetailInfoFragment
import com.provocation.checkmate.R
import java.time.LocalDate

class UserItemAdapter(
    val fragment: Fragment,
    val itemList: ArrayList<UserItemList>
) :
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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_home, parent, false)

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
        holder.birthYear.text = "#${LocalDate.now().year - item.birthYear}살"
        holder.mbti.text = "#${item.mbti}"
        holder.desire.text = "#${item.desire}"

        holder.itemView.setOnClickListener {
            fragment.requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MateDetailInfoFragment.newInstance(item.myInfoId, item.mateId))
                .addToBackStack(null)
                .commit()
        }
    }
}