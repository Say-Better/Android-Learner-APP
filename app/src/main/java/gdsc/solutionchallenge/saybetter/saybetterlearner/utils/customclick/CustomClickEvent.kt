package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick

import android.util.Log
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode

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