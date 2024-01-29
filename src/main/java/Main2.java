import api.Rule;
import api.RuleSet;
import boards.Board;
import boards.CellBoard;
import game.GameState;
import game.Move;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Main2 {


    public static void main(String[] args) {

        RuleSet rules = new RuleSet();

        rules.add(new Rule(board ->{
            return outerTraversals((i, j) -> "raj");
        } ));


        getvalue(board->{
            System.out.println(1+""+3);
            return  "raj";
        });
    }

    private static GameState outerTraversals(BiFunction<Integer, Integer, String> next) {
        GameState result = new GameState(false, "-");
        for (int i = 0; i < 3; i++) {

            int finalI = i;
            GameState traversal = traverse(j -> next.apply(finalI, j));
            if (traversal.isOver()) {
                result = traversal;
                break;
            }
        }
        return result;
    }

    private static GameState traverse(Function<Integer, String> traversal) {
        GameState result = new GameState(false, "-");
        boolean possibleStreak = true;
        for (int j = 0; j < 3; j++) {
            if (traversal.apply(j) == null || !traversal.apply(0).equals(traversal.apply(j))) {
                possibleStreak = false;
                break;
            }
        }
        if (possibleStreak) {
            result = new GameState(true, traversal.apply(0));
        }
        return result;
    }

    private static String getvalue (Function<Board,String> myfunction){
      // String str= myfunction.apply(1,3);
        //System.out.println(str);
        return  "rajendra";
    }


}
