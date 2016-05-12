package bop;


import bop.PropertyImportService.ImportService;
import bop.domain.ArticleHopPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class mvcController {

//    @Autowired
//    ImportRepository repo;

    @RequestMapping("/customPath")
    public Set<ArticleHopPath> greeting(@RequestParam(value="id1") String id1,@RequestParam(value="id2") String id2) {
        Set<ArticleHopPath> articleHopPaths = new HashSet<>();

        // main method should be written here

        return articleHopPaths;
    }

    @Autowired
    ImportService importService;
    @RequestMapping("/startImport")
    void startImport(){
        importService.importData();
    }
}
