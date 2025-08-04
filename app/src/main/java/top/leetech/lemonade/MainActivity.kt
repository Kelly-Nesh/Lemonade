package top.leetech.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.leetech.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Surface(modifier = Modifier) {
                    LemonadeApp()
                }
            }
        }
    }
}

/*
After they tap the lemon tree, the user sees a lemon.
They are prompted to tap the lemon to "squeeze" it to make lemonade.
They need to tap the lemon several times to squeeze it.
The number of taps required to squeeze the lemon is different each time
and is a randomly generated number between 2 to 4 (inclusive).
*/
@Composable
fun LemonadePress(modifier: Modifier = Modifier) {
    var squeezeCount by remember { mutableIntStateOf(0) }
    var page by remember { mutableIntStateOf(1) }
    val (pageImage, pageText) = when (page) {
        2 -> R.drawable.lemon_squeeze to R.string.keep_tapping
        3 -> R.drawable.lemon_drink to R.string.drink_it
        4 -> R.drawable.lemon_restart to R.string.start_again
        else -> R.drawable.lemon_tree to R.string.tap_the_lemon_tree // set the first state to default
    }
    val imageContentDescription = when (page) {
        2 -> R.string.lemon
        3 -> R.string.drink_it
        4 -> R.string.empty_glass
        else -> R.string.lemon_tree
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(pageImage),
            contentDescription = stringResource(imageContentDescription),
            modifier = Modifier
                .clip(RoundedCornerShape(25))
                .background(colorResource(R.color.base_bg))
                .padding(22.dp, 12.dp)
                .clickable {
                    when (page) {
                        1, 3 -> page++
                        2 -> {
                            if (squeezeCount == 0) {
                                squeezeCount = (2..4).random()
                            } else {
                                squeezeCount--
                                if (squeezeCount == 0) {
                                    page++
                                }
                            }
                        }

                        else -> page = 1
                    }
                }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(pageText)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        LemonadePress(modifier = Modifier)
    }
}