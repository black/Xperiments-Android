package ca.nuro.nuos.mobile.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(
    private val span: Int,
    private val space: Int,
    private val edges: Boolean
):RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view!!) // item position
        val column: Int = position % span // item column
        if (edges) {
            outRect.left =
                space - column * space / span // space - column * ((1f / span) * space)
            outRect.right =
                (column + 1) * space / span // (column + 1) * ((1f / span) * space)
            if (position < span) { // top edge
                outRect.top = space
            }
            outRect.bottom = space // item bottom
        } else {
            outRect.left = column * space / span // column * ((1f / span) * space)
            outRect.right =
                space - (column + 1) * space / span // space - (column + 1) * ((1f /    span) * space)
            if (position >= span) {
                outRect.top = space // item top
            }
        }
    }

}