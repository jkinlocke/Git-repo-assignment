package cop2805;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineSearcher {

    private List<String> lines;

    public LineSearcher(String filePath) throws IOException {
        this.lines = Files.readAllLines(Paths.get(filePath));
    }

    public List<String> getLinesAround(int n) {
        List<String> returnLines = new ArrayList<>();
        int sub2 = n - 2;
        int sub1 = n - 1;
        int add2 = n + 2;
        int add1 = n + 1;
        //Add the preceding lines if valid
        if(sub2 >= 0) returnLines.add (lines.get(sub2));
        if(sub1 >= 0) returnLines.add (lines.get(sub1));
        //Add the requested line
        if(n >= 0 && n < lines.size()) returnLines.add (lines.get(n));
        //Add the following lines if valid
        if(add1 < lines.size()) returnLines.add (lines.get(add1));
        if(add2 < lines.size()) returnLines.add (lines.get(add2));
        return returnLines;
    }
}




