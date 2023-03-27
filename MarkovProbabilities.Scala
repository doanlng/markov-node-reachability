class MarkovProbabilities{
   @native def callPrologGetReachability(state1: String, state2: String): Double
}

object MarkovProbabilities{
    def main(args: Array[String]): Unit = {
        System.loadLibrary("CallPrologFromScala")
        val mp = new MarkovProbabilities
        val x = mp.callPrologGetReachability("s0", "s1")
        println(x)
    }
}