package bop.hop;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class HopPath {
    public Set<long[]> hops = new HashSet<>();

    public int[] flag = new int[5];

    public int[] totalRequest = new int[5];
    public int[] request = new int[5];
    public int[] response = new int[5];

    public boolean[] reverseFlag = new boolean[5];

    public int[] tot2=new int[5];
    public int[] req2=new int[5];
    public int[] res2=new int[5];

    public synchronized int[] getTot2() {
        return tot2;
    }

    public synchronized void setTot2(int[] tot2) {
        this.tot2 = tot2;
    }

    public synchronized int[] getReq2() {
        return req2;
    }

    public synchronized void setReq2(int[] req2) {
        this.req2 = req2;
    }

    public synchronized int[] getRes2() {
        return res2;
    }

    public synchronized void setRes2(int[] res2) {
        this.res2 = res2;
    }

    public synchronized boolean[] getReverseFlag() {
        return reverseFlag;
    }

    public synchronized void setReverseFlag(boolean[] reverseFlag) {
        this.reverseFlag = reverseFlag;
    }

    public synchronized int[] getTotalRequest() {
        return totalRequest;
    }

    public synchronized int[] getRequest() {
        return request;
    }

    public synchronized void setRequest(int[] request) {
        this.request = request;
    }

    public synchronized void setTotalRequest(int[] totalRequest) {
        this.totalRequest = totalRequest;
    }

    public synchronized int[] getResponse() {
        return response;
    }

    public synchronized void setResponse(int[] response) {
        this.response = response;
    }

    HopPath(){
        for (int i=0;i<flag.length;i++){
            flag[i] = -1;
            totalRequest[i] = 0;
            response[i] = 0;
            request[i]=0;
            reverseFlag[i]=false;

            tot2[i] = 0;
            res2[i] = 0;
            req2[i] = 0;
        }


    }

    public synchronized int[] getFlag() {
        return flag;
    }

    public synchronized void setFlag(int[] flag) {
        this.flag = flag;
    }

    public synchronized Set<long[]>  getHops() {
        return hops;
    }

    public synchronized void  setHops(Set<long[]> hops) {
        this.hops = hops;
    }

    public synchronized void reset(){
        for (int i=0;i<flag.length;i++){
            flag[i] = -1;
            totalRequest[i] = 0;
            response[i] = 0;
            request[i]=0;
            reverseFlag[i]=false;

            tot2[i] = 0;
            res2[i] = 0;
            req2[i] = 0;
        }
    }
}
