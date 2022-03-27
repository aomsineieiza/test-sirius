package com.test.sirius.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.test.sirius.R
import java.lang.StringBuilder
import java.util.*

class CustomEditText : AppCompatEditText {
    private val DEFAULTCOLOR = Color.parseColor("#808080")
    private var mBackgroundColor: Int = 0
    private var clearIconTint: Int = 0
    private var searchIconTint: Int = 0
    private var prefixTextColor: Int = 0
    private var cPadding: Int = 0
    private var cPaddingLeft: Int = 0
    private var cPaddingTop: Int = 0
    private var cPaddingRight: Int = 0
    private var cPaddingBottom: Int = 0
    private var mCornerRadius: Float = 12.toFloat()
    private var mElevation: Float = 0.toFloat()
    private var mOriginalLeftPadding = -1f
    private var isClearIconVisible: Boolean = false
    private var isSearchIconVisible: Boolean = false
    private var isPassword = false
    private var imgCloseButton: Drawable? = null
    private var imgSearchButton: Drawable? = null
    private var drawableEnd: Drawable? = null
    private var mInputType: Int? = null

    var font: String? = null

    private var mPrefix: String? = null

    var prefix: String?
        get() = this.mPrefix
        set(prefix) {
            this.mPrefix = prefix
            calculatePrefix()
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val style = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)

        imgCloseButton =
            ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_close_clear_cancel)

        imgSearchButton =
            ContextCompat.getDrawable(getContext(), R.drawable.ic_search)

        cPadding = style.getDimensionPixelSize(R.styleable.CustomEditText_android_padding, -1)

        cPaddingLeft = style.getDimensionPixelSize(
            R.styleable.CustomEditText_android_paddingLeft,
            DEFAULT_PADDING
        )

        cPaddingTop = style.getDimensionPixelSize(
            R.styleable.CustomEditText_android_paddingTop,
            DEFAULT_PADDING
        )

        cPaddingRight = style.getDimensionPixelSize(
            R.styleable.CustomEditText_android_paddingRight,
            DEFAULT_PADDING
        )

        cPaddingBottom = style.getDimensionPixelSize(
            R.styleable.CustomEditText_android_paddingBottom,
            DEFAULT_PADDING
        )

        isClearIconVisible = style.getBoolean(
            R.styleable.CustomEditText_CustomEditText_setClearIconVisible,
            false
        )

        isSearchIconVisible = style.getBoolean(
            R.styleable.CustomEditText_CustomEditText_setSearchIconVisible,
            false
        )

        mInputType = style.getInt(R.styleable.CustomEditText_CustomEditText_inputType, 0)

        val mNormalColor = style.getColor(
            R.styleable.CustomEditText_CustomEditText_setBorderColor,
            DEFAULTCOLOR
        )
        val cursorColor =
            style.getColor(R.styleable.CustomEditText_CustomEditText_setCursorColor,   context.getColor(R.color.gray_text_emphasis))

        mBackgroundColor = style.getColor(
            R.styleable.CustomEditText_CustomEditText_setBackgroundColor,
            context.getColor(R.color.gray_light)
        )

        clearIconTint = style.getColor(
            R.styleable.CustomEditText_CustomEditText_clearIconTint,
            context.getColor(R.color.black)
        )

        searchIconTint = style.getColor(
            R.styleable.CustomEditText_CustomEditText_searchIconTint,
            context.getColor(R.color.black)
        )

        mPrefix = style.getString(R.styleable.CustomEditText_CustomEditText_setPrefix)

        prefixTextColor =
            style.getColor(R.styleable.CustomEditText_CustomEditText_setPrefixTextColor,  context.getColor(R.color.gray_text_emphasis))

        mCornerRadius =
            style.getDimension(
                R.styleable.CustomEditText_CustomEditText_setCornerRadius,
                12f
            )

        mElevation = style.getFloat(
            R.styleable.CustomEditText_CustomEditText_setElevation,
            0f
        )

        elevation = mElevation

        setBackGroundOfLayout(getShapeBackground(mNormalColor))
        setCursorColor(cursorColor)

        if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD || inputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
            isPassword = true
            this.maxLines = 1
        } else if (inputType == EditorInfo.TYPE_CLASS_PHONE) {
            this.keyListener = DigitsKeyListener.getInstance("0123456789")

            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // TODO Auto-generated method stub
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // TODO Auto-generated method stub
                }

                override fun afterTextChanged(s: Editable) {
                    val mText: String = text.toString()
                    val textLength: Int = mText.length

                    if (mText.endsWith("-") || mText.endsWith(" ") || mText.endsWith(" ")) return

                    if (textLength == 4) {
                        if (!mText.contains("-")) {
                            setText(
                                StringBuilder(mText).insert(mText.length - 1, "-").toString()
                            )
                            setSelection(text!!.length)
                        }
                    } else if (textLength == 8) {
                        if (mText.contains("-")) {
                            setText(
                                StringBuilder(mText).insert(mText.length - 1, "-").toString()
                            )
                            setSelection(text!!.length)
                        }
                    }
                }
            })

        } else if (inputType == EditorInfo.TYPE_CLASS_DATETIME) {
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // TODO Auto-generated method stub
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // TODO Auto-generated method stub
                }

                override fun afterTextChanged(s: Editable) {
                    val mText: String = text.toString()
                    val textLength: Int = mText.length

                    if (mText.endsWith("/") || mText.endsWith(" ") || mText.endsWith(" ")) return

                    if (textLength == 3) {
                        if (!mText.contains("/")) {
                            setText(
                                StringBuilder(mText).insert(mText.length - 1, "/").toString()
                            )
                            setSelection(text!!.length)
                        }
                    } else if (textLength == 6) {
                        if (mText.contains("/")) {
                            setText(
                                StringBuilder(mText).insert(mText.length - 1, "/").toString()
                            )
                            setSelection(text!!.length)
                        }
                    }
                }
            })
        }

        when(mInputType) {
            1 -> {
                addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        // TODO Auto-generated method stub
                    }

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        // TODO Auto-generated method stub
                    }

                    override fun afterTextChanged(s: Editable) {
                        val mText: String = text.toString()
                        val textLength: Int = mText.length

                        if (mText.endsWith(" ") || mText.endsWith(" ") || mText.endsWith(" ")) return

                        if (textLength == 5) {
                            if (!mText.contains(" ")) {
                                setText(
                                    StringBuilder(mText).insert(mText.length - 1, " ").toString()
                                )
                                setSelection(text!!.length)
                            }
                        } else if (textLength == 10) {
                            if (mText.contains(" ")) {
                                setText(
                                    StringBuilder(mText).insert(mText.length - 1, " ").toString()
                                )
                                setSelection(text!!.length)
                            }
                        }
                        else if (textLength == 15) {
                            if (mText.contains(" ")) {
                                setText(
                                    StringBuilder(mText).insert(mText.length - 1, " ").toString()
                                )
                                setSelection(text!!.length)
                            }
                        } else if (textLength == 20) {
                            if (mText.contains(" ")) {
                                setText(
                                    StringBuilder(mText).insert(mText.length - 1, " ").toString()
                                )
                                setSelection(text!!.length)
                            }
                        }
                    }
                })
            }
            2 -> {
                addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        // TODO Auto-generated method stub
                    }

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        // TODO Auto-generated method stub
                    }

                    override fun afterTextChanged(s: Editable) {
                        val mText: String = text.toString()
                        val textLength: Int = mText.length

                        if (mText.endsWith("/") || mText.endsWith(" ") || mText.endsWith(" ")) return

                        if (textLength == 3) {
                            if (!mText.contains("/")) {
                                setText(
                                    StringBuilder(mText).insert(mText.length - 1, "/").toString()
                                )
                                setSelection(text!!.length)
                            }
                        }
                    }
                })
            }
        }

        if (!isPassword && isClearIconVisible) {
            handleClearButton()
        }

        if (isSearchIconVisible) {
            handleSearchButton()
        }

        if (mPrefix != null && mPrefix!!.isNotEmpty()) {
            calculatePrefix()
        }

        setOnTouchListener(OnTouchListener { _, event ->
            val editText = this
            if (editText.compoundDrawables[2] == null)
                return@OnTouchListener false
            if (event.action != MotionEvent.ACTION_UP)
                return@OnTouchListener false
            if (isPassword) {
                val width = if (drawableEnd == null) 0 else drawableEnd!!.intrinsicWidth
                if (event.x > editText.width - editText.paddingRight - width) {
                    event.action = MotionEvent.ACTION_CANCEL
                }
            } else if (isClearIconVisible) {
                val width = if (imgCloseButton == null) 0 else imgCloseButton!!.intrinsicWidth
                if (event.x > editText.width - editText.paddingRight - width) {
                    editText.setText("")
                    this.handleClearButton()
                }
            }
            false
        })

        setFont()
        style.recycle()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mPrefix != null)
            calculatePrefix()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        super.setOnFocusChangeListener(l)
    }

    @SuppressLint("SoonBlockedPrivateApi")
    fun setCursorColor(@ColorInt color: Int) = try {
        var c = color
        if (c == 0) c = getThemeAccentColor()
        // Get the cursor resource id
        var field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        field.isAccessible = true
        val drawableResId = field.getInt(this)

        // Get the editor
        field = TextView::class.java.getDeclaredField("mEditor")
        field.isAccessible = true
        val editor = field.get(this)

        // Get the drawable and set a color filter
        val drawable = ContextCompat.getDrawable(this.context, drawableResId)
        drawable?.setColorFilter(c, PorterDuff.Mode.SRC_IN)
        val drawables = arrayOf<Drawable>(drawable!!, drawable)

        // Set the drawables
        field = editor.javaClass.getDeclaredField("mCursorDrawable")
        field.isAccessible = true
        field.set(editor, drawables)
    } catch (ignored: Exception) {
    }

    private fun setFont() {
        if (font != null) {
            try {
                typeface = Typefaces[context, font!!]
            } catch (ignored: Exception) {
            }
        }
    }

    /**
     * This method is used to set the rectangle box on EditText
     */
    private fun setBackGroundOfLayout(shape: Drawable?) {
        background = shape
        padding(true)
    }

    private fun padding(isRound: Boolean) {
        val extraPadding: Int
        val extraPad: Int
        if (isRound) {
            extraPadding = 5
            extraPad = 0
        } else {
            extraPad = 5
            extraPadding = 0
        }
        if (cPadding != -1) {
            super.setPadding(cPadding + extraPadding, cPadding, cPadding, cPadding + extraPad)
        } else {
            super.setPadding(
                cPaddingLeft + extraPadding,
                cPaddingTop,
                cPaddingRight,
                cPaddingBottom + extraPad
            )
        }
    }


    /**
     * This method is used to draw the rectangle border view with color
     */
    @SuppressLint("WrongConstant")
    private fun getShapeBackground(@ColorInt color: Int): Drawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = mCornerRadius
        shape.setColor(mBackgroundColor)
        return shape
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mPrefix != null) {
            val prefix = mPrefix
            var myPaint: Paint? = null
            if (prefixTextColor != 0) {
                myPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
                myPaint.color = prefixTextColor
                myPaint.textAlign = Paint.Align.LEFT
                myPaint.textSize = textSize
            }
            canvas.drawText(
                prefix!!, mOriginalLeftPadding, getLineBounds(0, null).toFloat(), myPaint
                    ?: paint
            )
        }
    }


    @SuppressLint("NewApi")
    private fun handleSearchButton() {
        if (isSearchIconVisible) {
            DrawableCompat.setTint(imgSearchButton!!, searchIconTint)
            imgSearchButton!!.setBounds(0, 0, 43, 43)
            this.setCompoundDrawables(
                imgSearchButton,
                this.compoundDrawables[0],
                this.compoundDrawables[1],
                this.compoundDrawables[3]
            )
        }
    }

    @SuppressLint("NewApi")
    private fun handleClearButton() {
        if (isClearIconVisible) {
            DrawableCompat.setTint(imgCloseButton!!, clearIconTint)
            imgCloseButton!!.setBounds(0, 0, 43, 43)
            if (Objects.requireNonNull<Editable>(this.text).isEmpty()) {
                this.setCompoundDrawables(
                    this.compoundDrawables[0],
                    this.compoundDrawables[1],
                    null,
                    this.compoundDrawables[3]
                )
            } else {
                this.setCompoundDrawables(
                    this.compoundDrawables[0],
                    this.compoundDrawables[1],
                    imgCloseButton,
                    this.compoundDrawables[3]
                )
            }
        }
    }

    public override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
        try {
            if (isPassword) {
                maskPassword()
            } else if (isClearIconVisible)
                this.handleClearButton()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //hide it
    private fun maskPassword() {
        transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun getThemeAccentColor(): Int {
        val colorAttr: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.R.attr.colorAccent
        } else {
            context.resources.getIdentifier("colorAccent", "attr", context.packageName)
        }
        val outValue = TypedValue()
        context.theme.resolveAttribute(colorAttr, outValue, true)
        return outValue.data
    }

    private fun calculatePrefix() {
        if (mOriginalLeftPadding == -1f) {
            val prefix = mPrefix
            val widths = FloatArray(prefix!!.length)
            paint.getTextWidths(prefix, widths)
            var textWidth = 0f
            for (w in widths) {
                textWidth += w
            }
            mOriginalLeftPadding = compoundPaddingLeft.toFloat()
            setPadding(
                (textWidth + mOriginalLeftPadding).toInt() + 20,
                paddingRight, paddingTop,
                paddingBottom
            )
        }
    }

    fun setPrefixTextColor(prefixTextColor: Int) {
        this.prefixTextColor = prefixTextColor
        invalidate()
    }

    companion object {
        private const val DEFAULT_PADDING = 15
    }
}