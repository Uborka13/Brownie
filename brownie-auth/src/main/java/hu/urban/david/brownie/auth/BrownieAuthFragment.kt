package hu.urban.david.brownie.auth

import hu.urban.david.brownie.BrownieFragment
import hu.urban.david.brownie.UIActions
import hu.urban.david.brownie.UIModel

abstract class BrownieAuthFragment<UM : UIModel, UA : UIActions> : BrownieFragment<UM, UA>() {

    abstract override val viewModel: BrownieAuthViewModel<UM, UA>
}
