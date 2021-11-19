package com.project.lastsubmission.ui.content.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.project.lastsubmission.R
import com.project.lastsubmission.adapter.SectionPagerAdapter
import com.project.lastsubmission.databinding.FragmentFavoriteBinding
import com.project.lastsubmission.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // init
        viewModel = ViewModelProvider(activity as FragmentActivity, factory)[FavoriteViewModel::class.java]

        setUpViewPager()
    }

    private fun setUpViewPager() {
        binding.apply {
            viewPager.adapter = SectionPagerAdapter(activity as AppCompatActivity)
            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_title_movie, R.string.tab_title_tvshow)
    }
}