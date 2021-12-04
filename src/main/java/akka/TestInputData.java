package akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestInputData {
    private String packageID;
    private String jscript;
    private String functionName;
    private Object params;

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
      this.params = params;
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
}
