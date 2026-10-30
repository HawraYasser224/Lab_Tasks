package com.example.studyplanner.ui.components

import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import android.util.Log

@Composable
fun StarRating(
    stars: Float,
    onStarsChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            Log.d("Lab07", "factory")
            RatingBar(context).apply {
                numStars = 5
                stepSize = 0.5f

                // TODO 1
                setOnRatingBarChangeListener { _, rating, fromUser ->
                    if (fromUser) {
                        onStarsChange(rating)
                    }
                }
            }
        },
        // TODO 2
        update = { view ->
            Log.d("Lab07", "update $stars")
            view.rating = stars
        },
        modifier = modifier
    )
}