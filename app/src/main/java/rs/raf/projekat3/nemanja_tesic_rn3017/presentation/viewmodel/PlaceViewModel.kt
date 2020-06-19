package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Filter
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.data.repositories.PlaceRepository
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.contracts.PlaceContract
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.states.PlaceState
import timber.log.Timber

class PlaceViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel(), PlaceContract.ViewModel {

    override val placeState: MutableLiveData<PlaceState> = MutableLiveData()

    private val publishSubjectFilter: PublishSubject<Filter> = PublishSubject.create()

    private val publishSubjectAll: PublishSubject<String> = PublishSubject.create()

    private val subscriptions = CompositeDisposable()

    init {
        val subscriptionFilter = publishSubjectFilter
            .switchMap {filter ->
                placeRepository
                    .filter(filter.text, filter.ascending)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    placeState.value = PlaceState.Success(it)
                },
                {
                    placeState.value = PlaceState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        val subscriptionAll = publishSubjectAll
            .switchMap {
                placeRepository
                    .getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    placeState.value = PlaceState.Success(it)
                },
                {
                    placeState.value = PlaceState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscriptionFilter)
        subscriptions.add(subscriptionAll)
    }

    override fun insert(place: Place) {
        val subscription = placeRepository
            .insert(place)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    placeState.value = PlaceState.Add("Added")
                },
                {
                    placeState.value = PlaceState.Error("Error happened while adding place")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun update(place: Place) {
        val subscription = placeRepository
            .update(place)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    placeState.value = PlaceState.Edit("Edited")
                },
                {
                    placeState.value = PlaceState.Error("Error happened while updating place")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun delete(id: Long) {
        val subscription = placeRepository
            .delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    placeState.value = PlaceState.Delete("Deleted")
                },
                {
                    placeState.value = PlaceState.Error("Error happened while deleting place")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun filter(text: String, ascending: Boolean) {
        publishSubjectFilter.onNext(Filter(text, ascending))
    }

    override fun getAll() {
        publishSubjectAll.onNext("")
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}