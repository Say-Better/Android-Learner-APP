package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts

interface TTSListener {
    fun onTTSStarted()
    fun onTTSStopped()
    fun updateIndex(start:Int, end:Int)
}