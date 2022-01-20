package hu.urban.david.brownie.auth

sealed class UserStates {
    object Authenticated : UserStates()
    object Authorized : UserStates()
    object NeedAuthentication : UserStates()
    object NeedAuthorization : UserStates()
}
