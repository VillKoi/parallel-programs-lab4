package akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestInputData {
    private String packageID;
    private String jscript;
    private String functionName;
    private ArrayList<TestInformation> tests;

    @JsonCreator
    public TestInputData(
            @JsonProperty("packageId") String packageID,
            @JsonProperty("jsScript") String jscript,
            @JsonProperty("functionName") String functionName,
            @JsonProperty("tests") ArrayList<TestInformation> tests
    ) {
      this.packageID = packageID;
      this.jscript = jscript;
      this.functionName = functionName;
      this.tests = tests;
    }

    public ArrayList<TestInformation> GetTests() {
        ArrayList<TestInformation> array = new ArrayList<>();

        for (TestInformation test: tests) {
            array.add(new TestInformation(packageID, jscript, functionName, test));
        }

        return array;
    };

    public String getPackageID() {
        return this.packageID;
    }

    public String getJscript() {
        return jscript;
    }

    public String getFunctionName() {
        return functionName;
    }

    public ArrayList<TestInformation> getTests() {
        return tests;
    }
}
