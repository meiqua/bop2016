package bop;


import bop.hop.HopMethod;
import bop.hop.HopPath;
import bop.service.ImportService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

@RestController
public class mvcController {

    @Autowired
    ResettableCountDownLatch resettableCountDownLatch;
    @Autowired
    HopPath hopPath;
    @Autowired
    HopMethod hopMethod;
    @Autowired
    ImportService importService;

    @RequestMapping("/startImport")
    void startImport(){
        importService.importData();
    }


    @RequestMapping("/test")
    Set<long[]> test(@RequestParam(value="id1") long id1,@RequestParam(value="id2") long id2)  {

        try {
            hopMethod.hop(id1,id2);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            resettableCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<long[]> set = new HashSet<>(hopPath.getHops());
        hopPath.reset();
        resettableCountDownLatch.reset();
        return set ;
    }
}
