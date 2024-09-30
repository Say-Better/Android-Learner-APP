package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.*

class TTSManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var listener: TTSListener? = null
    private var isInitialized: Boolean = false

    fun setTTSListener(listener: TTSListener) {
        this.listener = listener
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setSpeechRate(0.8f)
            val result = tts.setLanguage(Locale.KOREA)
            isInitialized = !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            Log.d("isInitialized",result.toString())

            if (isInitialized) {
                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        notifyTTSStarted()
                    }

                    override fun onDone(utteranceId: String?) {
                        if (utteranceId?.startsWith("TTS_") == true) {
                            notifyTTSStopped()
                        }
                    }

                    override fun onError(utteranceId: String?) {
                        notifyTTSStopped()
                    }

                    override fun onRangeStart(
                        utteranceId: String?,
                        start: Int,
                        end: Int,
                        frame: Int
                    ) {
                        Log.d("start,end", start.toString() +" "+end.toString())
                        listener?.updateIndex(start, end)
                    }
                })
            }
        }
    }

    private fun notifyTTSStarted() {
        listener?.onTTSStarted()
    }

    private fun notifyTTSStopped() {
        listener?.onTTSStopped()
    }
}
