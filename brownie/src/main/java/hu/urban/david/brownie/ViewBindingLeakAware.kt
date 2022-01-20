package hu.urban.david.brownie

interface ViewBindingLeakAware {
    var onViewDestroyed: (() -> Unit)?
}
