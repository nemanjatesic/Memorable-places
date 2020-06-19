package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place

class PlaceDiffItemCallback : DiffUtil.ItemCallback<Place>() {

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.dateCreated == newItem.dateCreated &&
                oldItem.latitude == newItem.latitude &&
                oldItem.longitude == newItem.longitude &&
                oldItem.note == newItem.note &&
                oldItem.title == newItem.title
    }

}