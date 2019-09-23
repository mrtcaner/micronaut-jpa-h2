package micronaut.jpa.h2.util.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class CSVParserUtil {

    private CSVParserUtil(){

    }

    public static List<CsvBean> parse(InputStream is, Class clazz) throws Exception {

        CsvTransfer csvTransfer = new CsvTransfer();
        HeaderColumnNameMappingStrategy ms = new HeaderColumnNameMappingStrategy();
        ms.setType(clazz);

        try (Reader reader = new BufferedReader(new InputStreamReader(is))) {
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withMappingStrategy(ms)
                    .build();

            csvTransfer.setCsvList(cb.parse());
        }

        return csvTransfer.getCsvList();
    }


}
