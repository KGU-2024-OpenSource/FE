package com.provocation.checkmate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.provocation.checkmate.presentation.home.HomeFragment

class FragmentManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_manage)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()) // fragment_container는 레이아웃의 프래그먼트 컨테이너 ID
                .commit()
        }
        // 툴바 설정 (공통 기능)
        val toolbar = findViewById<ConstraintLayout>(R.id.main_toolbar)

        toolbar.findViewById<View>(R.id.touch_zone_1).setOnClickListener {
            // 해당 버튼이 클릭되었을 때 처리
            replaceFragment(HomeFragment())
        }

        toolbar.findViewById<View>(R.id.touch_zone_2).setOnClickListener {
            // 해당 버튼이 클릭되었을 때 처리
            //replaceFragment(ChatListFragment())
        }

        toolbar.findViewById<View>(R.id.touch_zone_3).setOnClickListener {
            // 다른 버튼 클릭 처리
            val intent = Intent(this, IAmYouAreActivity::class.java)
            startActivity(intent)
        }

        // 기본 Fragment 로드
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
