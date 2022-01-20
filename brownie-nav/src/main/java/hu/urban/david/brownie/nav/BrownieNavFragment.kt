package hu.urban.david.brownie.nav

import android.os.Bundle
import androidx.navigation.NavController
import hu.urban.david.brownie.BrownieFragment
import hu.urban.david.brownie.UIActions
import hu.urban.david.brownie.UIModel

abstract class BrownieNavFragment<UM : UIModel, UA : UIActions> : BrownieFragment<UM, UA>() {

    abstract override val viewModel: BrownieNavViewModel<UM, UA>

    abstract val navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
