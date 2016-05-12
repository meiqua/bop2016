package bop.PropertyImportService;

import bop.domain.Property;
import bop.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ImportService {

    private final CsvFileReader fileReader;
    private final ImportRepository importRepository;

    @Autowired
    public ImportService(CsvFileReader fileReader,ImportRepository importRepository) {
        this.fileReader = fileReader;
        this.importRepository = importRepository;
    }

    public void importData() {
//        String[] values;
//        int i = 0;
//
//        while ((values = fileReader.attemptToReadNextValues()) != null) {
//            System.out.println("imported: " + i++);
//            Cart cart = new Cart(values[0]);
//            Product product = new Product(values[1]);
//            persist(cart, product);
//        }

        importFinish();
    }

    private void importFinish(){
        importRepository.importFinish();
    }

//    private void persist(Property p1, Property p2) {
//        importRepository.save(p1);
//        importRepository.save(p2);
//        importRepository.bind(p1,p2);
//    }
}
