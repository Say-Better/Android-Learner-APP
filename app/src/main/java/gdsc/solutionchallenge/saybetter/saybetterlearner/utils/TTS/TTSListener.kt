package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.TTS

interface TTSListener {
    fun onTTSStarted()
    fun onTTSStopped()
    fun updateIndex(start:Int, end:Int)
}