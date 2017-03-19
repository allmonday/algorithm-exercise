import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tangmin on 17/3/19.
 */
public class Solver {

    private boolean solved;
    private List<Board> solutionBoards = new ArrayList<>();

    public Solver(Board initial) {
        MinPQ<SolverStep> queueStep = new MinPQ<>(new SolverStepComparator());
        queueStep.insert(new SolverStep(initial, 0, null));

        MinPQ<SolverStep> queueStepTwin = new MinPQ<>(new SolverStepComparator());
        queueStepTwin.insert(new SolverStep(initial.twin(), 0, null));

        SolverStep step;
        while (!(queueStep.min().board().isGoal() || queueStepTwin.min().board().isGoal())) {
            step = queueStep.delMin();
            for (Board neighbor: step.board().neighbors()) {
                if (!isExisted(step, neighbor)) {
                    queueStep.insert(new SolverStep(neighbor, step.moves() + 1, step));
                }
            }

            SolverStep stepTwin = queueStepTwin.delMin();
            for (Board neighbor: stepTwin.board().neighbors()) {
                if (!isExisted(stepTwin, neighbor)) {
                    queueStepTwin.insert(new SolverStep(neighbor, stepTwin.moves() + 1, stepTwin));
                }
            }
        }

        step = queueStep.delMin();
        solved = step.board().isGoal();

        solutionBoards.add(step.board());
        while ((step = step.previous()) != null) {
            solutionBoards.add(0, step.board());
        }

    }

    private boolean isExisted(SolverStep step, Board board) {
        SolverStep previous = step;
        while ((previous = previous.previous()) != null) {
            if (previous.board().equals(board)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSolvable() {
        return solved;
    }
    public int moves() {
        int moves;
        if (isSolvable()) {
            moves = solutionBoards.size() - 1;
        }
        else {
            moves = -1;
        }
        return moves;
    }

    private static class SolverStep {
        private int moves;
        private Board board;
        private SolverStep previousStep;

        private SolverStep(Board board, int moves, SolverStep previousStep) {
            this.board = board;
            this.moves = moves;
            this.previousStep = previousStep;
        }

        public int moves() {
            return moves;
        }

        public int priority() {
            return board.manhattan() + moves;
        }

        public Board board() {
            return board;
        }

        public SolverStep previous() {
            return previousStep;
        }
    }
    public Iterable<Board> solution() {
        Iterable<Board> iterable;
        if (isSolvable()) {
            iterable = new Iterable<Board>() {
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        }
        else {
            iterable = null;
        }
        return iterable;
    }

    private class SolutionIterator implements Iterator<Board> {
        private int index = 0;
        public boolean hasNext() {
            return index < solutionBoards.size();
        }
        public Board next() {
            return solutionBoards.get(index++);
        }
        public void remove() {
            throw new UnsupportedOperationException("removal not supported");
        }
    }

    private static class SolverStepComparator implements Comparator<SolverStep> {
        public int compare(SolverStep s1, SolverStep s2) {
            return s1.priority() - s2.priority();
        }
    }
    public static void main(String[] args) {

    }
}
