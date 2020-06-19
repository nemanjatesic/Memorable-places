package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.diff.PlaceDiffItemCallback
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.viewholder.PlaceViewHolder

class PlaceAdapter(
    placeDiffItemCallback: PlaceDiffItemCallback,
    private val onViewMapClicked: (Place) -> Unit,
    private val onEditClicked: (Place) -> Unit,
    private val onDeleteClicked: (Place) -> Unit) : ListAdapter<Place, PlaceViewHolder>(placeDiffItemCallback) {

    private val functionViewMap: (Int) -> Unit = {
        if (it >= 0) {
            val place = getItem(it)
            onViewMapClicked.invoke(place)
        }
    }

    private val functionEditPlace: (Int) -> Unit = {
        if (it >= 0) {
            val place = getItem(it)
            onEditClicked.invoke(place)
        }
    }

    private val functionDeletePlace: (Int) -> Unit = {
        if (it >= 0) {
            val place = getItem(it)
            onDeleteClicked.invoke(place)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.layout_place_list_item, parent, false)
        return PlaceViewHolder(containerView, functionViewMap, functionEditPlace, functionDeletePlace)

    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }

}
