package com.gkzxhn.mygithub.ui.wedgit

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.gkzxhn.mygithub.extension.dp2px

/**
 * Created by 方 on 2017/11/2.
 * @param space 传入的值，其单位视为dp
 */
class SpaceItemDecoration constructor(private val space: Float,
                                      private val length: Int): RecyclerView.ItemDecoration() {

    init {
        mLength= length
        mSpace = space.dp2px().toInt()
    }

    companion object {
        private var mLength: Int = 0
        private var mSpace: Int = 0
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val pos = parent.getChildAdapterPosition(view)

        outRect.left = 0
        outRect.top = 0
        outRect.right = 0


        if (pos != mLength - 1) {
            outRect.bottom = mSpace
        } else {
            outRect.bottom = 0
        }
    }
}