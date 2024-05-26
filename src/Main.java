import org.knowm.xchart.*;
import org.knowm.xchart.style.lines.SeriesLines;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static final String trainDataFileName = "lang.train.csv";
    public static final String testDataFileName = "lang.test.csv";

    public static void main(String[] args)
    {
        Trainer trainer = new Trainer(new CaseLoader_File(trainDataFileName));
        SingleLayerNetwork singleLayerNetwork = new SingleLayerNetwork();
        trainer.trainNetwork(singleLayerNetwork, 1000, 0.97);

        testEachPerceptron(singleLayerNetwork);
//        buildGraph(trainer);
    }

    private static void testEachPerceptron(SingleLayerNetwork singleLayerNetwork)
    {
        for(Perceptron perceptron : singleLayerNetwork.getPerceptrons())
        {
            double precision = perceptron.test(new CaseLoader_File(testDataFileName).loadCases());
            Logger.log("Perceptron " + perceptron.getValue() + "-> precision: " + precision );
        }
    }

    private static void buildGraph(Trainer trainer)
    {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Perceptron Accuracy vs. Epochs")
                .xAxisTitle("Epochs")
                .yAxisTitle("Accuracy")
                .build();

        chart.getStyler().setYAxisDecimalPattern("#.##");
        chart.getStyler().setXAxisLabelRotation(45);

//        ArrayList<Case> testCases = new CaseLoader_File(testDataFileName).loadCases();
        ArrayList<Case> testCases = new CaseLoader_File(trainDataFileName).loadCases();
        for (int j = 0; j < 3; j++)
        {
            Perceptron perceptron = new Perceptron();

            perceptron.setLen(trainer.getVectorLen());
            perceptron.setValue("English");

            ArrayList<Integer> chartEpochs = new ArrayList<>();
            ArrayList<Double> chartAccuracies = new ArrayList<>();

            int step = 25;
            for (int i = 0; i < 20 * step; i += step)
            {
                chartEpochs.add(i);

                double testAccuracy = perceptron.test(testCases);
                chartAccuracies.add(testAccuracy);

                perceptron.runEpochs(testCases, step);

            }
            chart.addSeries("Run " + (j + 1), chartEpochs, chartAccuracies)
                    .setLineStyle(SeriesLines.SOLID);
        }

        new SwingWrapper<>(chart).displayChart();
    }

    private static void testUserVector(Perceptron perceptron)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter training epochs");
        int epochs = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Input your vector");
        String vector = scanner.nextLine();

        ArrayList<Case> cases = new ArrayList<>();
        String[] split = vector.split(",");
        double[] attributes = new double[split.length];

        for (int i = 0; i < split.length; i++)
            attributes[i] = Double.parseDouble(split[i].trim());

        cases.add(new Case(attributes, ""));
        perceptron.test(cases);
    }
}