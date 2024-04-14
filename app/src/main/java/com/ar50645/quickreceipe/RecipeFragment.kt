package com.ar50645.quickreceipe

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.abs


const val STATE = "State"

class RecipeFragment: Fragment() {
    private var initTouchY = 0
    private var index = 0


    private lateinit var view1: TextView
    var Recipesarr: Array<String> = emptyArray()

    companion object {
        fun newInstance() = RecipeFragment()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.context_menu, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val parentView = inflater.inflate(R.layout.fragment_recipe, container, false)
        view1 = parentView.findViewById(R.id.recipeTextView)
        Recipesarr = getResources().getStringArray((R.array.recipes))

        registerForContextMenu(view1)

        // Scroll by finger to see next and prev recipes
        parentView.setOnTouchListener { v, event ->
            var returnVal = true
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initTouchY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y.toInt()

                    // See if movement is at least 500 pixels
                    if (abs(y - initTouchY) >= 300) {
                        if (y > initTouchY) {
                            index--
                            getRecipe()
                        } else {
                            index++
                            getRecipe()
                        }
                        initTouchY = y
                    }
                }
                else -> returnVal = false
            }
            returnVal
        }

        return parentView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE, index)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            getRecipe()
        } else {
            index = savedInstanceState.getInt(STATE)
            getRecipe()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.next -> {
                index++
                getRecipe()
                true
            }
            R.id.prev -> {
                index--
                getRecipe()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun getRecipe() {
        if(index < 0 || index >= Recipesarr.size) {
            val dialog = WarningDialogFragment()
            dialog.show(getParentFragmentManager(), "warningDialog")

            return
        }
        view1.text = Recipesarr.get(index)

    }
}

