package hu.urban.david.brownie

sealed class UIStates {
    object Init : UIStates()
    data class Loading(val action: UIActions) : UIStates()
    data class Success(val action: UIActions) : UIStates()
    data class Error(val error: String?, val action: UIActions) : UIStates()

    /**
     * State done
     *
     * This state represents that the fragment handled other states (Init, Success, Error) to
     * prevent when rotating the device, run twice the onSuccess etc
     *
     * @constructor Create empty State done
     */
    object StateDone : UIStates()
}
