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
        String binaryOne = "";
        ArrayList<Case> cases = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] splitInput = line.split(",");
                if(binaryOne.isEmpty())
                    binaryOne = splitInput[splitInput.length -1];

                int valueIndex = splitInput.length - 1;
                int value = splitInput[valueIndex].equals(binaryOne) ? 1 : 0; // 1 or 0

                double[] attributes = new double[valueIndex];

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
