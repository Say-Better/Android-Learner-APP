package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.TTS

// TTSManager.kt

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TTSManager(context: Context, private val ttsListener: TTSListener) : TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isInitialized: Boolean = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.KOREA)
            isInitialized = !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
        } else {
            isInitialized = false
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            notifyTTSStarted()
            val params = HashMap<String, String>()
            params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "utteranceId"
            tts.setOnUtteranceCompletedListener(this)
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params)
        }
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }

    // 이 메서드는 말하기가 완료될 때 호출됩니다.
    override fun onUtteranceCompleted(utteranceId: String) {
        notifyTTSStopped()
    }

    private fun notifyTTSStarted() {
        ttsListener.onTTSStarted()
    }

    private fun notifyTTSStopped() {
        ttsListener.onTTSStopped()
    }
}
