package hu.hm.familyapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ProfileImage(img: String) {
    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            modifier = Modifier.clip(CircleShape),
            imageModel = img,
            contentScale = ContentScale.Fit,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.primary,
                highlightColor = Color.White,
                durationMillis = 2000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            circularReveal = CircularReveal(1000),
            failure = {
                Text(text = "No Image", color = Color.Black)
            }
        )
    }
}
