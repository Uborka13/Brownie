package hu.urban.david.brownie.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.urban.david.brownie.BrownieViewModel
import hu.urban.david.brownie.UIActions
import hu.urban.david.brownie.UIModel
import hu.urban.david.brownie.UIStates
import kotlinx.coroutines.channels.Channel

abstract class BrownieAuthViewModel<UM : UIModel, UA : UIActions>(
    uiModelFactory: () -> UM,
    channelCapacity: Int = Channel.RENDEZVOUS
) : BrownieViewModel<UM, UA>(
    uiModelFactory, channelCapacity
) {

    private var _userState = MutableLiveData<UIStates>(UIStates.Init)
    val userState: LiveData<UIStates> get() = _userState
}
