package bop.PropertyImportService;

import bop.domain.Property;
import bop.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ImportService {

    private final CsvFileReader fileReader;
    private final PropertyRepository propertyRepository;

    @Autowired
    public ImportService(CsvFileReader fileReader,PropertyRepository propertyRepository) {
        this.fileReader = fileReader;
        this.propertyRepository = propertyRepository;
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
    }

    private void persist(Property p1, Property p2) {
        propertyRepository.save(p1);
        propertyRepository.save(p2);
        propertyRepository.bind(p1,p2);
    }
}
