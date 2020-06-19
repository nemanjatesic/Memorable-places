package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_place_list_item.*
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place

class PlaceViewHolder (
    override val containerView: View,
    onViewMapClicked: (Int) -> Unit,
    onEditClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        lookPlaceBtn.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                onViewMapClicked.invoke(adapterPosition)
        }
        editPlaceBtn.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                onEditClicked.invoke(adapterPosition)
        }
        deletePlaceBtn.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                onDeleteClicked.invoke(adapterPosition)
        }
    }

    fun bind(place: Place) {
        placeTitleTV.text = place.title
        placeContentTV.text = place.note
        placeDateTV.text = place.dateCreated
    }
}
