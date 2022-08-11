package com.app.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.app.base.BaseFragment
import com.app.base.BaseFragmentStateAdapter
import com.app.databinding.FragmentWalkThroughBinding
import com.app.presentation.extension.*
import com.app.presentation.ui.onBoarding.SignInActivity
import com.app.presentation.vm.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class WalkThroughFragment : BaseFragment(AppLayout.fragment_walk_through) {

    private var _binding: FragmentWalkThroughBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OnBoardingViewModel>()


    private val adapter by lazy {
        BaseFragmentStateAdapter(activityCompat)
    }

    private val fragmentList by lazy {
        val list = mutableListOf<Fragment>()
        for (i in 0..3) {
            val frag = WalkThroughContentFragment.newInstance(i)
            list.add(frag)
        }
        list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCompat.onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = _binding ?: FragmentWalkThroughBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(view: View) {
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initialize() {

        initViewPager()
        binding.btnSkip.click {
            viewModel.setFirstTimeUser(true)
        }

        binding.btnNext.click {
            if (binding.vpWalkThrough.currentItem == 3) {
                viewModel.setFirstTimeUser(true)
            } else {
                binding.vpWalkThrough.currentItem = binding.vpWalkThrough.currentItem + 1
            }
        }

    }

    private fun initViewPager() {
        binding.vpWalkThrough.adapter = adapter
        adapter.submitList(fragmentList)
        binding.indicator.setViewPager(binding.vpWalkThrough)
        binding.vpWalkThrough.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 3) {
                    binding.btnSkip.visibility = GONE
                    binding.btnNext.text = getString(AppString.label_finish)
                } else {
                    binding.btnSkip.visibility = VISIBLE
                    binding.btnNext.text = getString(AppString.label_next)
                }
            }
        })
    }

    override fun observeLiveData() {

        viewModel.observeSetUserFirstTimeLaunchLive.observe(viewLifecycleOwner) {
            Timber.d("Value :: $it")
            navigate(it)
        }
    }

    private fun navigate(data: Boolean) {
        if (data) {
            startActivity<SignInActivity>()
            finish()
        } else {
//            startActivity<SignInActivity>()
//            finish()
        }
    }

}