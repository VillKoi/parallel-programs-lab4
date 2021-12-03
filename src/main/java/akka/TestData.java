package akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestData {
    private String packageID;

    @JsonCreator
    public TestData(
            @JsonProperty("packageID") String packageID
    ) {
      this.packageID = packageID;
    }

    public ArrayList<Object> GetTests() {
        return new ArrayList<>();
    }

}
