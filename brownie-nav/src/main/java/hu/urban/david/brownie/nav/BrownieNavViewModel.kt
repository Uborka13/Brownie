package hu.urban.david.brownie.nav

import hu.urban.david.brownie.BrownieViewModel
import hu.urban.david.brownie.UIActions
import hu.urban.david.brownie.UIModel
import kotlinx.coroutines.channels.Channel

abstract class BrownieNavViewModel<UM : UIModel, UA : UIActions>(
    uiModelFactory: () -> UM,
    channelCapacity: Int = Channel.RENDEZVOUS
) : BrownieViewModel<UM, UA>(uiModelFactory, channelCapacity)
