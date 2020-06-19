package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.contracts.PlaceContract
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.activities.DetailPlaceViewActivity
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.activities.EditPlaceActivity
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.adapter.PlaceAdapter
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.diff.PlaceDiffItemCallback
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.states.PlaceState
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.viewmodel.PlaceViewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val placeViewModel: PlaceContract.ViewModel by viewModel<PlaceViewModel>()
    private lateinit var placeAdapter: PlaceAdapter
    private var ascending = true

    companion object {
        const val PLACE = "PLACE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListeners()
        initRecycler()
    }

    private fun initObservers() {
        placeViewModel.placeState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        placeViewModel.filter("",true)
    }

    private fun renderState(state: PlaceState) {
        when(state) {
            is PlaceState.Success -> {
                placeAdapter.submitList(state.places)
            }
            is PlaceState.Error -> Toast.makeText(context, "Error happened", Toast.LENGTH_SHORT).show()
            is PlaceState.Delete -> Toast.makeText(activity, "Place deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        et_searchList.doAfterTextChanged {
            placeViewModel.filter(et_searchList.text.toString(), ascending)
        }
        floatingButton.setOnClickListener {
            ascending = !ascending
            placeViewModel.filter(et_searchList.text.toString(), ascending)
            if (ascending)
                it.rotation = 90F
            else
                it.rotation = 270F
            listRv.smoothScrollToPosition(0)
        }
    }

    private val clickOnViewMap: (Place) -> Unit = {
        val intent = Intent(activity, DetailPlaceViewActivity::class.java)
        intent.putExtra(PLACE, it)

        startActivity(intent)
    }

    private val clickOnEdit: (Place) -> Unit = {
        val intent = Intent(activity, EditPlaceActivity::class.java)
        intent.putExtra(PLACE, it)

        startActivity(intent)
    }

    private val clickOnDelete: (Place) -> Unit = {
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        placeViewModel.delete(it.id)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setMessage("Are you sure you want to delete this place?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener)
            .show()
    }

    private fun initRecycler() {
        listRv.layoutManager = LinearLayoutManager(activity)
        placeAdapter = PlaceAdapter(PlaceDiffItemCallback(), clickOnViewMap, clickOnEdit, clickOnDelete)
        listRv.adapter = placeAdapter
    }

    override fun onResume() {
        super.onResume()
        placeViewModel.filter(et_searchList.text.toString(), ascending)
    }

}