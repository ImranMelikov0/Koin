package com.imranmelikov.koinkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.imranmelikov.koinkotlin.adapter.CryptoAdapter
import com.imranmelikov.koinkotlin.api.CryptoApi
import com.imranmelikov.koinkotlin.databinding.FragmentListBinding
import com.imranmelikov.koinkotlin.model.Crypto
import com.imranmelikov.koinkotlin.mvvm.CryptoViewModel
import com.imranmelikov.koinkotlin.util.Status
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.core.scope.Scope

class ListFragment : Fragment(), CryptoAdapter.Listener,AndroidScopeComponent{
    private lateinit var binding:FragmentListBinding
    private var cryptoAdapter= CryptoAdapter(arrayListOf(),this)
    private val viewModel by viewModel<CryptoViewModel>()

    /*
    private val api=get<CryptoApi> ()
    private val apilazy by inject<CryptoApi>()

     */


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())

        viewModel.load()
        observe()

        println(hello)
        println(hi)
    }

    fun observe(){
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    it?.let {
                        binding.recyclerView.visibility=View.VISIBLE
                        binding.cryptoErrorText.visibility=View.GONE
                        binding.cryptoProgressBar.visibility=View.GONE
                        cryptoAdapter= CryptoAdapter(ArrayList(it.data?: arrayListOf()),this@ListFragment)
                        binding.recyclerView.adapter=cryptoAdapter
                    }
                }
                Status.LOADING ->{
                    binding.recyclerView.visibility=View.GONE
                    binding.cryptoProgressBar.visibility=View.VISIBLE
                    binding.cryptoErrorText.visibility=View.GONE
                }
                Status.ERROR ->{
                    binding.recyclerView.visibility=View.GONE
                    binding.cryptoErrorText.visibility=View.VISIBLE
                    binding.cryptoProgressBar.visibility=View.GONE
                }
            }

        })
        /*
            // without Resourse

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.recyclerView.visibility=View.GONE
                binding.cryptoProgressBar.visibility=View.VISIBLE
                binding.cryptoErrorText.visibility=View.GONE
            }else{
                binding.cryptoProgressBar.visibility=View.GONE
            }

        })
        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.recyclerView.visibility=View.GONE
                    binding.cryptoErrorText.visibility=View.VISIBLE
                }else{
                    binding.cryptoErrorText.visibility=View.GONE
                }

            }
        })
         */

    }

    override fun onItemClick(cryptoModel: Crypto) {
        Toast.makeText(requireContext(),"Clicked on: ${cryptoModel.currency}",Toast.LENGTH_SHORT).show()
    }

    override val scope: Scope by fragmentScope()
    private val hi by inject<String>(qualifier= named("Hi"))
    private val hello by inject<String>(qualifier= named("Hello"))

}