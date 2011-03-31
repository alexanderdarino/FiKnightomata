import FiKnightomata.nfa.RFAFile;
import FiKnightomata.*;
import FiKnightomata.dfa.DFA;
import FiKnightomata.nfa.NFA;
import java.util.Scanner;

/**
 *
 * @author Alexander Darino
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        RFAFile file = new RFAFile("samples\\deadPaths.rfa");
        NFA nfaTest = file.loadNFA();
        DFA convertedTest = nfaTest.toDFA();
        convertedTest.print();

        Scanner in = new Scanner(System.in);

        System.out.println("Start State: " + convertedTest.getStartState());
        System.out.println(convertedTest.isAccepting(convertedTest.getStartState()) ? "Start State is Accepting." : "Start State is Not Accepting");
        String input = in.nextLine();
        if (input.compareTo("") == 0)
            return;
        finiteautomaton.dfa.Execution myExecution = convertedTest.execute(input);
        myExecution.printLastTraversal();
        input = in.nextLine();
        while (input.compareTo("") != 0)
        {
            myExecution = convertedTest.execute(input, myExecution);
            myExecution.printLastTraversal();
            input = in.nextLine();
        }
    }
}
