package akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.*;

import java.util.ArrayList;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TestInformation {
    private String packageID;
    private String jscript;
    private String functionName;

    private String testName;
    private String expectedResult;
    private ArrayList<Integer> args;

    private String result;

    @JsonCreator
    public TestInformation(
            @JsonProperty("testName") String testName,
            @JsonProperty("expectedResult") String expectedResult,
            @JsonProperty("params") ArrayList<Integer> args
    ) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.args = args;
    }

    public String toString() {
        return  "testName: " + this.testName +
                ", " + this.validateResult() +
                ", expectedResult: " + this.expectedResult +
                ", result: " + this.result;

    };

    private String validateResult() {
        return expectedResult.equals(result) ? "OK" : "FALSE";
    }

    public TestInformation(TestInputData testInputData, String result) {
        this.packageID = testInputData.getPackageID();
        this.jscript = testInputData.getJscript();
        this.functionName = testInputData.getFunctionName();
    }

    public TestInformation(String packageID,String jscript,String functionName, TestInformation result) {
        this.packageID = packageID;
        this.jscript = jscript;
        this.functionName = functionName;
        this.testName = result.testName;
        this.expectedResult = result.expectedResult;
        this.args = result.args;
    }

    public TestInformation setResult(String result) {
        this.result = result;
        return this;
    };

    public ArrayList<Object> GetTests() {
        return new ArrayList<>();
    }

    public String getPackageID() {
        return this.packageID;
    }

    public String getJscript() {
        return jscript;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setJscript(String jscript) {
        this.jscript = jscript;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public ArrayList<Integer> getArgs() {
        return args;
    }

    public String getTestName() {
        return testName;
    }
}
