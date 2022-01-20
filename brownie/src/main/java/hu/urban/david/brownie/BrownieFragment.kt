package hu.urban.david.brownie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BrownieFragment<UM : UIModel, UA : UIActions> : Fragment(), ViewBindingLeakAware {

    override var onViewDestroyed: (() -> Unit)? = null

    abstract val viewModel: BrownieViewModel<UM, UA>

    abstract val binding: ViewBinding

    val uiModel: UM
        get() = viewModel.uiModel

    @CallSuper
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.states.observe(this) {
            when (it) {
                is UIStates.Init -> {
                    onInit()
                }
                is UIStates.Loading -> {
                    onLoading(it.action as UA)
                }
                is UIStates.Success -> {
                    onSuccess(it.action as UA)
                    stateDone()
                }
                is UIStates.Error -> {
                    onError(it.error, it.action as UA)
                    stateDone()
                }
                UIStates.StateDone -> {
                    /* no - op */
                }
            }
        }
        lifecycleScope.launch {
            viewModel.event.collect {
                onEvent(it)
            }
        }
        initObservers()
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        modelRestore(viewModel.uiModel)
    }

    @CallSuper
    override fun onDestroyView() {
        saveModel()?.let {
            viewModel.uiModel = it
        }
        onViewDestroyed?.invoke()
        super.onDestroyView()
    }

    /**
     * Best place to init observers, to prevent double onChange call
     */
    open fun initObservers() {
        // no - op
    }

    /**
     * OnClickListeners should be placed here
     */
    open fun initUI() {
        // no - op
    }

    /**
     * It is called when the fragment initialized first time, won't be called on the next
     * onCreateView
     */
    open fun onInit() {
        // no - op
    }

    /**
     * It is called when an async (network/database) call finished and returned with 200 or data
     */
    open fun onSuccess(action: UA) {
        // no - op
    }

    /**
     * It is called when an async call started
     */
    open fun onLoading(action: UA) {
        // no - op
    }

    /**
     * It is called when an async call
     */
    open fun onError(error: String?, action: UA) {
        // no - op
    }

    /**
     * It is called when an event is posted
     */
    open fun onEvent(event: UIEvent) {
        // no - op
    }

    /**
     * It is need to be called, when Success or Error state is finished, to prevent the UI process
     * another Success Request
     */
    private fun stateDone() {
        viewModel.setStateDone()
    }

    /**
     * It is used to provide the Fragment the ability to restore from UIModel, but you have to save
     * it first
     */
    open fun modelRestore(uiModel: UM) {
        // no - op
    }

    /**
     * It is used to saved the actual UIModel from the Fragment. The Fragment responsible for save
     * the UIModel for later restore
     */
    open fun saveModel(): UM? {
        return null
    }
}
