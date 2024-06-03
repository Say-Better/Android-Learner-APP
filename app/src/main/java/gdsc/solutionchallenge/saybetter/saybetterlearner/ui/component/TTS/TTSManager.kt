package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.TTS

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class TTSManager(context: Context, private val ttsListener: TTSListener) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isInitialized: Boolean = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.KOREA)
            isInitialized = !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)

            if (isInitialized) {
                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        notifyTTSStarted()
                    }

                    override fun onDone(utteranceId: String?) {
                        notifyTTSStopped()
                    }

                    override fun onError(utteranceId: String?) {
                        notifyTTSStopped()
                    }
                })
            }
        } else {
            isInitialized = false
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId")
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "utteranceId")
        }
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }

    private fun notifyTTSStarted() {
        ttsListener.onTTSStarted()
    }

    private fun notifyTTSStopped() {
        ttsListener.onTTSStopped()
    }
}
