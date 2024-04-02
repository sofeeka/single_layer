import org.knowm.xchart.*;

import java.util.HashMap;

public class Main
{
    //    public static String trainDataFileName = "perceptron.data";
//    public static String testDataFileName = "perceptron.test.data";
    public static final String trainDataFileName = "wdbc.data";
    public static final String testDataFileName = "wdbc.test.data";

    public static void main(String[] args)
    {
//        for (int i = 0; i < 10; i++)
//        {
//            Perceptron perceptron = new Perceptron();
//            perceptron.adjustWeightsFromFile(trainDataFileName, 1000);
//            System.out.println("accuracy: " + perceptron.testFromFile(testDataFileName));
//        }
        buildGraph();
    }

    private static void buildGraph()
    {
        HashMap<Integer, Double> chartMap = new HashMap<>();
        for (int i = 1; i < 10; i++)
        {
            Perceptron perceptron = new Perceptron();
            perceptron.adjustWeightsFromFile(trainDataFileName, i * 100);
            chartMap.put(i, perceptron.testFromFile(testDataFileName));
        }

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
}