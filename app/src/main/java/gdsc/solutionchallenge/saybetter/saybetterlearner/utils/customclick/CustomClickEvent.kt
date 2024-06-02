package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick

import android.util.Log
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object CustomClickEvent : IndicationNodeFactory {
    private class DefaultDebugIndicationNode(
        private val interactionSource: InteractionSource,
    ) : Modifier.Node(), DrawModifierNode {
        override fun ContentDrawScope.draw() {
            Log.d("indication", "contentDrawScope")
            drawRect(color = Color.Gray.copy(alpha = 0f), size = size)
            this@draw.drawContent()
        }
    }

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return DefaultDebugIndicationNode(interactionSource)
    }

    override fun equals(other: Any?): Boolean = other === this

    override fun hashCode(): Int = -1
}