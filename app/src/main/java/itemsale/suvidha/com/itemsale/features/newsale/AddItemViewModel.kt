package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
import itemsale.suvidha.com.itemsale.features.base.BaseViewModel
import itemsale.suvidha.com.itemsale.model.dao.ItemDao

class AddItemViewModel(private val itemDao: ItemDao) : BaseViewModel() {

  val viewState: MutableLiveData<ViewState> = MutableLiveData()

  data class ViewState(
    val item: Boolean
  )

  init {
    viewState.value = ViewState(false)
  }

  fun getCurrentViewState() = viewState.value!!
}