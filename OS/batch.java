import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class batch {
    public static void main(String[] args) throws IOException {

        int result1 = 0, result2 = 0;
        String line = "";

        BufferedReader reader = new BufferedReader(
                new FileReader("C:\\Users\\hesuh\\Desktop\\batch.inp")
        );
        BufferedWriter writer = new BufferedWriter(
                new FileWriter("C:\\Users\\hesuh\\Desktop\\batch.out")
        );

        int k = Integer.parseInt(reader.readLine());

        for (int i = 0; i < k; i++){
            line = reader.readLine();
            String[] split = line.split(" ");
            for (int j = 0; j < split.length - 1; j++) {
                if (j%2 == 1) {
                    result2 += Integer.parseInt(split[j]);
                    result1 += Integer.parseInt(split[j]);
                }
                else if (j%2 == 0) {
                    result2 += Integer.parseInt(split[j]);
                }
            }
        }

        writer.write(result1 + " " + result2 +"\n");

        reader.close();
        writer.close();
    }
}
