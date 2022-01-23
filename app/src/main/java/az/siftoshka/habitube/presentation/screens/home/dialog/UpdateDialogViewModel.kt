package az.siftoshka.habitube.presentation.screens.home.dialog

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * [ViewModel] of the Update Dialog.
 */
@HiltViewModel
class UpdateDialogViewModel @Inject constructor() : ViewModel() {

    val colors = listOf(
        listOf(0xf3e9d2, 0x88d498, 0x1a936f, 0x114b5f),
        listOf(0x236969, 0x43a680, 0x74f6a7, 0x489cc1),
        listOf(0xf6e1b8, 0xc65f63, 0x84577c, 0x333644),
        listOf(0x403f3f, 0x427a5b, 0xb4cd93, 0xfcf5b8),
        listOf(0xe6d3a7, 0x59a985, 0x3a7563, 0x392f2f)
    )

    fun parade(colors: List<Int>): List<Party> {
        val party = Party(
            speed = 12f,
            maxSpeed = 40f,
            damping = 0.9f,
            angle = Angle.RIGHT - 45,
            spread = Spread.WIDE,
            colors = colors,
            emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(50),
            position = Position.Relative(0.0, 0.2)
        )

        return listOf(
            party,
            party.copy(
                angle = party.angle - 90,
                position = Position.Relative(1.0, 0.2)
            ),
        )
    }
}