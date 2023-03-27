
public class TempSourceFile{

    @TransitionWrapper({
        @Transition(newState = {"s0", "s1", "s2"}, curr = "s0", probs = {0.5, 0.4, 0.1}),
        @Transition(newState = {"s0", "s3", "s4"}, curr = "s2", probs = {0.18, 0.39, 0.43}),
    })
    @DisjunctWrapper(value = {
        @Disjunct(fact = "reach", args = {"S", "I", "T"}),
        @Disjunct(fact = "trans", args = {"S", "I", "U"}),
        @Disjunct(fact = "reach", args = {"U", "next(I)", "T"}),
        @Disjunct(fact = "reach", args = {"S", "_", "S"}),
        @Disjunct(fact = "trans", args = {"s4", "_", "s3"})},
        head = 0,
        tail = {1, 2}
    )
    public double t1;

    public static void main(String[] args){
        var tmp = new MarkovProbabilities();
        tmp.callPrologGetReachability("s0", "s1");
        System.out.println("Maybe this is needed for it to work.");
    }
}