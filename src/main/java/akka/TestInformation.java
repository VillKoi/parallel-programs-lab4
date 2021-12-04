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

    @JsonCreator
    public TestInformation(
            @JsonProperty("packageId") String packageID,
            @JsonProperty("jsScript") String jscript,
            @JsonProperty("functionName") String functionName,
            @JsonProperty("tests") Object params
    ) {
        this.packageID = packageID;
        this.jscript = jscript;
        this.functionName = functionName;
        this.params = params;
    }

    public TestInformation(TestInputData testInputData, String result
    ) {
        this.packageID = testInputData.getPackageID();
        this.jscript = testInputData.getJscript();
        this.functionName = testInputData.getFunctionName();
        this.params = testInputData.getParams();
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

    public Object getParams() {
        return params;
    }

    public String getResult() {
        return result;
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
