package akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestInformation {
    private String packageID;
    private String jscript;
    private String functionName;
    private Object params;
    private String result;

    private String testName;
    private String expectedResult;
    private ArrayList<Integer> args;

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

    public TestInformation(TestInputData testInputData, String result) {
        this.packageID = testInputData.getPackageID();
        this.jscript = testInputData.getJscript();
        this.functionName = testInputData.getFunctionName();
        this.result = result;
    }

    public TestInformation(String packageID,String jscript, String functionName, String result) {
        this.packageID = testInputData.getPackageID();
        this.jscript = testInputData.getJscript();
        this.functionName = testInputData.getFunctionName();
        this.result = result;
    }

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

    public void setParams(Object params) {
        this.params = params;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
