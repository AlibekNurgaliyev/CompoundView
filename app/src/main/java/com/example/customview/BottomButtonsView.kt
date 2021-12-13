package com.example.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customview.databinding.PartButtonsBinding

enum class BottomButtonAction {
    POSITIVE, NEGATIVE
}

typealias OnBottomButtonActionListener = (BottomButtonAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttrs, defStyleRes) {

    private val binding: PartButtonsBinding
    private var listener: OnBottomButtonActionListener? = null
    var isProgressMode: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    positiveButton.visibility = View.INVISIBLE
                    negativeButton.visibility = View.INVISIBLE
                    progress.visibility = View.VISIBLE
                } else {
                    positiveButton.visibility = View.VISIBLE
                    negativeButton.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            }
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : this(
        context,
        attrs,
        defStyleAttrs,
        R.style.MyBottomButtonsStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.bottomButtonsStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAttrs, defStyleRes)
        initListeners()
    }

    private fun initializeAttributes(
        attrs: AttributeSet?,
        defStyleAttrs: Int,
        defStyleRes: Int
    ) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttrs,
            defStyleRes
        )

        with(binding) {
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            setPositiveButtonText(positiveButtonText)

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            setNegativeButtonText(negativeButtonText)

            val positiveButtonBgColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomPositiveBackgroundColor,
                Color.BLACK
            )
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonBgColor)

            val negativeButtonBgColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomNegativeBackgroundColor,
                Color.WHITE
            )
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonBgColor)

            this@BottomButtonsView.isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)

        }

        typedArray.recycle()
    }

    fun initListeners() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }
        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnBottomButtonActionListener?) {
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "Ok"

    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()!!
        val savedState = SavedState(superState)
        savedState.positiveButtonText = binding.positiveButton.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        binding.positiveButton.text = savedState.positiveButtonText
    }

    class SavedState : BaseSavedState {
        var positiveButtonText: String? = null

        constructor(superState: Parcelable) : super(superState)
        constructor(parcel: Parcel) : super(parcel) {
            positiveButtonText = parcel.readString()

        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(positiveButtonText)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return Array(size) { null }
                }
            }
        }
    }
}