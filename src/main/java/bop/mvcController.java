package bop;


import bop.domain.AfId;
import bop.hop.HopMethod;
import bop.hop.HopPath;
import bop.repository.AfIdRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RestController
public class mvcController {

    @Autowired
    HopPath hopPath;
    @Autowired
    HopMethod hopMethod;
//    @Autowired
//    ImportService importService;

//    @RequestMapping("/startImport")
//    void startImport(){
//    //    importService.importData();
// //       importService.bindData();
//    }


    @RequestMapping("/test")
    Set<long[]> test(@RequestParam(value="id1") long id1,@RequestParam(value="id2") long id2)  {

        hopPath.reset();

        try {
            hopMethod.hop(id1,id2);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

       for(long[] a : hopPath.getHops()){
           System.out.println("\nPath: ");
           for (long b : a){
               System.out.println(b);
           }
           System.out.print("end\n");
       }

        return hopPath.getHops() ;
    }

//    @Autowired
//    AfIdRepository afIdRepository;
//    @RequestMapping("/gdb")
//    List<Long> gdb(@RequestParam(value = "id1") long id1, @RequestParam(value = "id2") long id2){
//
////        System.out.println(list.getName());
//        return afIdRepository.findTwoHop(id1,id2);
//    }
}
