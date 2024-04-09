import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CaseLoader_File implements CaseLoader
{
    private String fileName;

    CaseLoader_File(String fileName)
    {
        this.fileName = fileName;
    }

    public ArrayList<Case> loadCases(String fileName)
    {
        this.fileName = fileName;
        return loadCases();
    }

    @Override
    public ArrayList<Case> loadCases()
    {
        Logger.log("loading cases from file " + fileName);
        ArrayList<Case> cases = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] splitInput = line.split(",");

                String value = splitInput[splitInput.length - 1];
                double[] attributes = new double[splitInput.length - 1];

                for (int i = 0; i < splitInput.length - 1; i++)
                    attributes[i] = Double.parseDouble(splitInput[i].trim());

                cases.add(new Case(attributes, value));
            }

        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        return cases;
    }

    @Override
    public String getSource()
    {
        return "file " + fileName;
    }
}
