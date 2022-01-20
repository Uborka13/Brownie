package hu.urban.david.brownie

import android.view.LayoutInflater
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

internal fun <UA : UIActions> BrownieViewModel<*, UA>.actionHandle(handler: (UA) -> Unit) {
    viewModelScope.launch {
        channel.consumeAsFlow().collect {
            handler.invoke(it)
        }
    }
}

inline fun <reified VB : ViewBinding> BrownieFragment<*, *>.viewBinding(noinline inflater: (layoutInflater: LayoutInflater) -> VB) =
    FragmentViewBindingDelegate(this, this as ViewBindingLeakAware, inflater)
