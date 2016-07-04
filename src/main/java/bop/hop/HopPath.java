package bop.hop;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class HopPath {
    public Set<long[]> hops = new HashSet<>();

    public boolean[] reverseFlag = new boolean[5];

    public  boolean[] getReverseFlag() {
        return reverseFlag;
    }

    public  void setReverseFlag(boolean[] reverseFlag) {
        this.reverseFlag = reverseFlag;
    }

    HopPath(){
        for (int i=0;i<5;i++){

            reverseFlag[i]=false;
            hops.clear();

        }


    }

    public  Set<long[]>  getHops() {
        return hops;
    }


    public  void reset(){
        getHops().clear();
        for (int i=0;i<5;i++){
            reverseFlag[i]=false;
        }
    }
}
