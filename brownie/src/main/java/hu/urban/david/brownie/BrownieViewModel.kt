package hu.urban.david.brownie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BrownieViewModel<UM : UIModel, UA : UIActions>(
    uiModelFactory: () -> UM,
    channelCapacity: Int = Channel.RENDEZVOUS
) : ViewModel() {

    var uiModel: UM = uiModelFactory()

    val channel: Channel<UA> = Channel(channelCapacity)

    private object InitEvent : UIEvent

    init {
        actionHandle { handleActions(it) }
    }

    abstract fun handleActions(action: UA)

    fun addAction(action: UA) {
        viewModelScope.launch {
            channel.send(action)
        }
    }

    private var _uiStates = MutableLiveData<UIStates>(UIStates.Init)
    val states: LiveData<UIStates> get() = _uiStates

    private var _event = MutableStateFlow<UIEvent>(InitEvent)
    val event: StateFlow<UIEvent> = _event

    protected fun setLoadingState(action: UA) {
        _uiStates.value = UIStates.Loading(action)
    }

    protected fun setError(message: String?, action: UA) {
        _uiStates.value = UIStates.Error(message, action)
    }

    protected fun setSuccessState(action: UA) {
        _uiStates.value = UIStates.Success(action)
    }

    fun setStateDone() {
        _uiStates.value = UIStates.StateDone
    }

    protected fun postEvent(event: UIEvent) {
        _event.value = event
    }
}
