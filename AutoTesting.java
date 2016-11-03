/**
 * Created by Yash on 9/16/16.
 */


/*public class AutoTesting {
    public static void main(String[] args) {
        int rows = 50;
        int columns = 200;
        double prob = 0.7;
        int countout = 0;
        int notcountout = 0;
        GenerateRandomGrid grg = new GenerateRandomGrid();


        for (int i = 0; i < 100; i++) {
            WaterFlow wf = new WaterFlow(rows, columns, grg.generateGrid(rows, columns, prob));
            wf.determineFlow();

            if (wf.isReached()) {
                countout++;
            } else {
                notcountout++;
            }


        }

        System.out.println("flows: " + countout + "   " + "Doesn't flow:  " + notcountout);

    }

}
*/