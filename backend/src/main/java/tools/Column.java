package tools;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Column {

    String name;
    String dataType;
    String typeName;
    boolean nullable;
    boolean autoIncrement;
    int size;
    int bufferLength;


}
