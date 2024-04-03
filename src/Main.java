import org.knowm.xchart.*;
import org.knowm.xchart.style.lines.SeriesLines;

import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
    public static String trainDataFileName = "perceptron.data";
    public static String testDataFileName = "perceptron.test.data";
//    public static final String trainDataFileName = "wdbc.data";
//    public static final String testDataFileName = "wdbc.test.data";

    public static void main(String[] args)
    {
        Trainer trainer = new Trainer(new CaseLoader_File(trainDataFileName));
//        trainer.trainPerceptron(new Perceptron(), 1000, 0.97);
//        buildGraph(trainer);
        buildGraph2(trainer);
    }

    private static void buildGraph(Trainer trainer)
    {
        HashMap<Integer, Double> chartMap = new HashMap<>();
        for (int i = 1; i < 10; i++)
            chartMap.put(i, trainer.trainPerceptron(new Perceptron(), i * 100, 0.97));

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Perceptron Accuracy vs. Epochs")
                .xAxisTitle("Epochs in hundreds")
                .yAxisTitle("Accuracy").build();

        chart.getStyler().setYAxisDecimalPattern("#.##");
        chart.getStyler().setXAxisLabelRotation(45);

        chart.addSeries("Accuracy vs. Epochs", chartMap.keySet().stream().mapToDouble(Integer::doubleValue).toArray(), chartMap.values().stream().mapToDouble(Double::doubleValue).toArray());

        new SwingWrapper<>(chart).displayChart();
    }

    private static void buildGraph2(Trainer trainer)
    {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Perceptron Accuracy vs. Epochs")
                .xAxisTitle("Epochs in hundreds")
                .yAxisTitle("Accuracy")
                .build();

        chart.getStyler().setYAxisDecimalPattern("#.##");
        chart.getStyler().setXAxisLabelRotation(45);

//        Stroke[] lineStyles = {SeriesLines.SOLID, SeriesLines.SOLID, SeriesLines.SOLID, SeriesLines.SOLID, SeriesLines.SOLID};

        ArrayList<Case> testCases = new CaseLoader_File(testDataFileName).loadCases();
        for (int j = 0; j < 3; j++)
        {
            HashMap<Integer, Double> chartMap = new HashMap<>();
            Perceptron perceptron = new Perceptron();
            trainer.trainPerceptron(perceptron, 1, 0.97);
            Logger.log("");

            for (int i = 1; i < 10; i++)
            {
                Perceptron copy = new Perceptron(perceptron);
                double trainAccuracy = trainer.trainPerceptron(copy, i * 100, 0.97);
                Logger.log("trainAccuracy: " + trainAccuracy);

                double testAccuracy = copy.test(testCases);
                Logger.log("testAccuracy: " + testAccuracy + "\n");

                chartMap.put(i, trainAccuracy);
            }
            chart.addSeries("Run " + (j + 1), chartMap.keySet().stream().mapToDouble(Integer::doubleValue).toArray(),
                            chartMap.values().stream().mapToDouble(Double::doubleValue).toArray())
                    .setLineStyle(SeriesLines.SOLID);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}