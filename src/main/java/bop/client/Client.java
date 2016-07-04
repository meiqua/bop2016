package bop.client;

import bop.hop.HopPath;
import bop.repository.AfIdRepository;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Client {

    @Autowired
    HopPath hopPath;

    @Autowired
    AfIdRepository property;


//    public void queryFinished(){
//        if (hopPath.getFlag()[0]==0&&
//                hopPath.getFlag()[1]==0&&
//                hopPath.getFlag()[2]==0&&
//                hopPath.getFlag()[3]==0&&
//                hopPath.getFlag()[4]==0){
//
//   //         resettableCountDownLatch.countDown();
//        }
//    }
    public void queryId(String url,final String tag,final long start,final long between1,final long end) {
//        final JsonNode[] body = new JsonNode[1];
    url = "https://oxfordhk.azure-api.net/academic/v1.0/evaluate?expr="+
            url
            +"&attributes=Id&count=100000&subscription-key=f7cc29509a8443c5b3a5e56b0e38b5a6";

//        Future<HttpResponse<JsonNode>> future = Unirest.get(url)
//                .asJsonAsync(new Callback<JsonNode>() {
//
//                    public void failed(UnirestException e) {
//                        System.out.println("The request has failed");
//                    }
//
//                    public void completed(HttpResponse<JsonNode> response) {
        JsonNode  body = null;
        try {
            body = Unirest.get(url).asJson().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = body.getObject();
                        JSONArray jsonArray = jsonObject.getJSONArray("entities");
                        List<Long> IdList = new ArrayList<Long>();
                        for (int i=0;i<jsonArray.length();i++){
                            if (!jsonArray.getJSONObject(i).isNull("Id"))
                            IdList.add(jsonArray.getJSONObject(i).getLong("Id"));
                        }


//                        int request = IdList.size();

                        switch (tag) {
                            case "hop_Au_Id_FCJA_Id":
//                                hopPath.getTotalRequest()[2]+=request;
//                                hopPath.getRes2()[2]++;
                                for (Long aIdList : IdList) {
                                    addPath(1,3,true,end,aIdList,between1,start);
        //                            hopPath.getRequest()[2]++;
                                }
                                break;
                            case "hop_Au_Id_Id_Au":
                     //           hopPath.getTot2()[1] = IdList.size();
                                for (Long aIdList : IdList) {
                                    queryRId("Id="+aIdList,start,aIdList,end,"hop_Au_Id_Id_Au");
                      //              hopPath.getReq2()[1]++;
                                }
                                break;
                            case "twoHopAuToId":
                                for (Long aIdList : IdList) {
                                    checkId("AND(Id="+aIdList+",RId="+end+")","twoHopAuToId",start,aIdList,0,end);
                                }
                                break;
                            case "hop_Id_FCJA_Id_Id":
                                for (Long aIdList : IdList) {
                                    addPath(4,3,true,start,between1,aIdList,end);
                                    //                            hopPath.getRequest()[2]++;
                                }
                                break;
                            case "hop_Id_Id_FCJA_Id":
                                for (Long aIdList : IdList) {
                                    queryFCJAR("Id=" + aIdList, "hop_Id_Id_FCJA_Id",start,aIdList,end);
                                }
                                break;
                            case "twoHopAuToAu":
                                for (Long aIdList : IdList) {
                                    addPath(4,2,true,start,aIdList,between1,end);
                                    //                            hopPath.getRequest()[2]++;
                                }
                                break;
                            case "hop_Id_Au_Af_Au":
                                for (Long aIdList : IdList) {
                                    addPath(2,3,true,start,between1,aIdList,end);
                                    //                            hopPath.getRequest()[2]++;
                                }
                                break;
                            case "hop_Au_Id_Id_Id":
                                for (Long aIdList : IdList) {
                                    queryRId("Id="+aIdList,start,aIdList,end,"hop_Au_Id_Id_Id");
                                }
                                break;
                        }

//                    }
//
//                    public void cancelled() {
//                        System.out.println("The request has been cancelled");
//                    }
//
//                });
    }
    public void checkId(String url,final String tag,final long start,final long between1,final long between2,final long end){
        url = "https://oxfordhk.azure-api.net/academic/v1.0/evaluate?expr="+
                url
                +"&attributes=Id&count=100000&subscription-key=f7cc29509a8443c5b3a5e56b0e38b5a6";

//        Future<HttpResponse<JsonNode>> future = Unirest.get(url)
//                .asJsonAsync(new Callback<JsonNode>() {
//
//                    public void failed(UnirestException e) {
//                        System.out.println("The request has failed");
//                    }
//
//                    public void completed(HttpResponse<JsonNode> response) {
                        boolean containId=false;
        try {
            if (Unirest.get(url).asJson().getBody().getObject().getJSONArray("entities").length()>0)
                containId = true;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        switch (tag) {
                            case "oneHopIdToId":
                    //            hopPath.getRequest()[0]++;
                   //             hopPath.getTotalRequest()[0]++;
                                addPath(4,1,containId,start,between1,between2,end);
                                break;
                            case "twoHopIdToId":
                                addPath(4,2,containId,start,between2,between1,end);
                                break;
                            case "hop_Id_Id_Id_Id":
                                addPath(4,3,containId,start,between1,between2,end);
                                break;
//                            case "hop_Id_FCJA_Id_Id":
//                                addPath(3,3,containId,start,between1,between2,end);
//                                break;
                            case "hop_Id_Id_FCJA_Id":
                                 addPath(4,3,containId,start,between1,between2,end);
                                break;

                            case "oneHopAuToId":
                    //            hopPath.getRequest()[0]++;
                      //          hopPath.getTotalRequest()[0]++;
                                addPath(0,1,containId,start,between1,between2,end);
                                break;
                            case "twoHopAuToId":
                                addPath(4,2,containId,start,between1,between2,end);
                                break;
                            case "hop_Au_Id_Id_Au":
                                addPath(4,3,containId,start,between1,between2,end);
                                break;
//                            case "twoHopAuToAu":
//                                addPath(0,2,containId,start,between1,between2,end);
                            case "hop_Au_Id_Id_Id":
                                addPath(4,3,containId,start,between1,between2,end);
                                break;
                            case "hop_Id_Id_Id_Au":
                                addPath(4,3,containId,start,between1,between2,end);
                                break;
                        }
//                    }
//
//                    public void cancelled() {
//                        System.out.println("The request has been cancelled");
//                    }
//
//                });
    }
    public void addPath(int num,int hop,boolean containId,final long start,final long between1,final long between2,final long end){
   //     hopPath.getResponse()[num]++;
        if (containId){
            long[] path = new long[hop+1];
            if (hop==1){
                path[0]=start;path[1]=end;
                if (hopPath.getReverseFlag()[num]){
                    path[1]=start;path[0]=end;
                }
            }else if (hop==2){
                path[0]=start;path[1]=between1;path[2]=end;
                if (hopPath.getReverseFlag()[num]){
                    path[2]=start;path[1]=between1;path[0]=end;
                }
            }else if (hop==3){path[0]=start;path[1]=between1;path[2]=between2;path[3]=end;
                if (hopPath.getReverseFlag()[num]){
                    path[3]=start;path[2]=between1;path[1]=between2;path[0]=end;
                }
            }
          //  System.out.println("twoHopAuToAu: "+ path.toString());
            //System.out.println("path :" + path.length);
            hopPath.getHops().add(path);
        }
//        if (    hopPath.getRequest()[num]==hopPath.getTotalRequest()[num]&&
//                hopPath.getResponse()[num]==hopPath.getTotalRequest()[num]&&
//                hopPath.getReq2()[num]==hopPath.getTot2()[num]&&
//                hopPath.getRes2()[num]==hopPath.getTot2()[num]){
   //         System.out.println("twoHopAuToAu: ready");
   //         hopPath.getFlag()[num]=0;
   //         queryFinished();
    //    }
    }
    public void queryFCJAR(String url,final String tag,final long start,final long between1,final long end){
        url = "https://oxfordhk.azure-api.net/academic/v1.0/evaluate?expr="+
                url
                +"&attributes=F.FId,C.CId,J.JId,AA.AuId,RId&count=100000&subscription-key=f7cc29509a8443c5b3a5e56b0e38b5a6";

//        Future<HttpResponse<JsonNode>> future = Unirest.get(url)
//                .asJsonAsync(new Callback<JsonNode>() {
//
//                    public void failed(UnirestException e) {
//                        System.out.println("The request has failed");
//                    }
//
//                    public void completed(HttpResponse<JsonNode> response) {
//
                       int totalRequest=0;

        JsonNode  body = null;
        try {
            body = Unirest.get(url).asJson().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = body.getObject().getJSONArray("entities").getJSONObject(0);

                        JSONArray RidArray=new JSONArray();
                         if (!jsonObject.isNull("RId"))
                         RidArray = jsonObject.getJSONArray("RId");
                        List<Long> RidList = new ArrayList<Long>();
                        for (int i=0;i<RidArray.length();i++)
                            RidList.add(RidArray.getLong(i));

                        totalRequest+=RidList.size();


                        JSONArray AuIdArray=new JSONArray();
                              if (!jsonObject.isNull("AA"))
                         AuIdArray = jsonObject.getJSONArray("AA");
                        List<Long> AuIdList = new ArrayList<Long>();
                        List<Long> AfIdList = new ArrayList<Long>();
                        for (int i=0;i<AuIdArray.length();i++) {
                            AuIdList.add(AuIdArray.getJSONObject(i).getLong("AuId"));
//                            AfIdList.add(AuIdArray.getJSONObject(i).getLong("AfId"));
                        }
                      totalRequest += AfIdList.size()+AuIdList.size();

                           JSONArray FIdArray=new JSONArray();
                         if (!jsonObject.isNull("F"))
                         FIdArray = jsonObject.getJSONArray("F");
                        List<Long> FIdList = new ArrayList<Long>();
                        for (int i=0;i<FIdArray.length();i++)
                            FIdList.add(FIdArray.getJSONObject(i).getLong("FId"));

                       totalRequest +=FIdList.size();

                      JSONObject JIdArray=new JSONObject();
                      if (!jsonObject.isNull("J"))
                         JIdArray = jsonObject.getJSONObject("J");
                        List<Long> JIdList = new ArrayList<Long>();
                    //    for (int i=0;i<JIdArray.length();i++)
                            if (!JIdArray.isNull("JId"))
                            JIdList.add(JIdArray.getLong("JId"));

                        totalRequest += JIdList.size();

                         long CId=0;
                         if (!jsonObject.isNull("C"))
                         CId = jsonObject.getJSONObject("C").getLong("CId");


                        totalRequest += 1;


                        switch (tag) {
                            case "twoHopIdToId": {
                                queryFCJARnext(1,2,totalRequest,"twoHopIdToId",start,between1,end,RidList,AuIdList,AfIdList,FIdList,JIdList,CId);
                                break;
                            }
                            case "hop_Id_FCJA_Id_Id": {
                                List<Long> nullR = new ArrayList<Long>();
                                queryFCJARID(3, 3, totalRequest, "hop_Id_FCJA_Id_Id", start, between1, end, nullR, AuIdList, AfIdList, FIdList, JIdList, CId);
              //                  hopPath.getRes2()[3]++;
                                break;
                            }
                            case "hop_Id_Id_FCJA_Id": {
                                List<Long> nullR = new ArrayList<Long>();
                                queryFCJARnext(4,3,totalRequest,"hop_Id_Id_FCJA_Id",start,between1,end,nullR,AuIdList,AfIdList,FIdList,JIdList,CId);
           //                     hopPath.getRes2()[4]++;
                                break;
                            }
                            case "hop_Au_Id_FCJA_Id": {
          //                      hopPath.getTot2()[2] = totalRequest;
                                List<Long> nullR = new ArrayList<Long>();
                                queryFCJARID(2, 3, totalRequest, "hop_Au_Id_FCJA_Id", end, between1, start,nullR , AuIdList, AfIdList, FIdList, JIdList, CId);
                                break;
                            }
                        }

                   // }
//                    public void cancelled() {
//                        System.out.println("The request has been cancelled");
//                    }
//
//                });
    }
    public void queryFCJARID(int num,int hop,int totalRequest,String tag,long start,long between1,long end
            ,List<Long> RidList,List<Long> AuIdList,List<Long> AfIdList,List<Long> FIdList,List<Long> JIdList,long CId){

    //    int request=0;
        for (Long aRidList : RidList) {

            if (tag.equals("hop_Id_FCJA_Id_Id")){
                queryId("AND(Id=" + aRidList + ",RId=" + end + ")", tag, start, aRidList, end);;
            }else{
                queryId("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))", tag, start, aRidList, end);
            }
       //     request++;
        }
        for (Long aAuIdList : AuIdList) {
            if (tag.equals("hop_Id_FCJA_Id_Id")){
                queryId("AND(Composite(AA.AuId=" + aAuIdList + "),RId=" + end + ")", tag, start, aAuIdList, end);
            }else
            queryId("AND(Composite(AA.AuId=" + aAuIdList + "),Composite(AA.AuId=" + end + "))", tag, start, aAuIdList, end);

         //   request++;
        }
        for (Long aAfIdList : AfIdList) {
            if (tag.equals("hop_Id_FCJA_Id_Id")){
                queryId("AND(Composite(AA.AfId=" + aAfIdList + "),RId=" + end + ")", tag, start, aAfIdList, end);
            }else
            queryId("AND(Composite(AA.AfId=" + aAfIdList + "),Composite(AA.AuId=" + end + "))", tag, start, aAfIdList, end);

            //request++;
        }
        for (Long aFIdList : FIdList) {
            if (tag.equals("hop_Id_FCJA_Id_Id")){
                queryId("AND(Composite(F.FId=" + aFIdList + "),RId=" + end + ")", tag, start, aFIdList, end);
            }else
            queryId("AND(Composite(F.FId=" + aFIdList + "),Composite(AA.AuId=" + end + "))", tag, start, aFIdList, end);

        //    request++;
        }
        for (Long aJIdList : JIdList) {
            if (tag.equals("hop_Id_FCJA_Id_Id")){
                queryId("AND(Composite(J.JId=" + aJIdList + "),RId=" + end + ")", tag, start, aJIdList, end);
            }else
            queryId("AND(Composite(J.JId=" + aJIdList + "),Composite(AA.AuId=" + end + "))", tag, start, aJIdList, end);

        //    request++;
        }
        if (CId!=0){
            if (tag.equals("hop_Id_FCJA_Id_Id")){
                queryId("AND(Composite(C.CId=" + CId + "),RId=" + end + ")", tag, start, CId,  end);
            }else
            queryId("AND(Composite(C.CId=" + CId + "),Composite(AA.AuId=" + end + "))", tag, start, CId,  end);

        }

    }
    public void queryFCJARnext(int num,int hop,int totalRequest,String tag,long start,long between1,long end
            ,List<Long> RidList,List<Long> AuIdList,List<Long> AfIdList,List<Long> FIdList,List<Long> JIdList,long CId){

    //        hopPath.getTotalRequest()[num] += totalRequest;
  //      int request = 0;
        for (Long aRidList : RidList) {
          //  request++;
            checkId("AND(Id=" + end + ",RId=" + aRidList + ")", tag, start, between1, aRidList, end);
        }
        for (Long aAuIdList : AuIdList) {
       //     request++;
            checkId("AND(Id=" + end + ",Composite(AA.AuId=" + aAuIdList + "))", tag, start, between1, aAuIdList, end);
        }
//        for (Long aAfIdList : AfIdList) {
//            request++;
//            checkId("AND(Id=" + end + ",Composite(AA.AfId=" + aAfIdList + "))", tag, start, between1,  aAfIdList,end);
//        }
        for (Long aFIdList : FIdList) {
        //    request++;
            checkId("AND(Id=" + end + ",Composite(F.FId=" + aFIdList + "))", tag, start, between1, aFIdList, end);
        }
        for (Long aJIdList : JIdList) {
        //    request++;
            checkId("AND(Id=" + end + ",Composite(J.JId=" + aJIdList + "))", tag, start, between1, aJIdList, end);
        }

        if (CId!=0)
        checkId("AND(Id=" + end + ",Composite(C.CId=" + CId + "))",tag, start,between1, CId,  end);
//        request++;

   //     hopPath.getRequest()[num] += request;
    }
    public void queryRId(String url,final long start, final long between1, final long end,final String tag) {
//        final JsonNode[] body = new JsonNode[1];
        url = "https://oxfordhk.azure-api.net/academic/v1.0/evaluate?expr="+
                url
                +"&attributes=RId&count=100000&subscription-key=f7cc29509a8443c5b3a5e56b0e38b5a6";

//        Future<HttpResponse<JsonNode>> future = Unirest.get(url)
//                .asJsonAsync(new Callback<JsonNode>() {
//
//                    public void failed(UnirestException e) {
//                        System.out.println("The request has failed");
//                    }
//
//                    public void completed(HttpResponse<JsonNode> response) {
        JsonNode  body = null;
        try {
            body = Unirest.get(url).asJson().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = body.getObject();
        JSONArray RIdArray = new JSONArray();
        if (!jsonObject.getJSONArray("entities").getJSONObject(0).isNull("RId"))
         RIdArray = jsonObject.getJSONArray("entities").getJSONObject(0).getJSONArray("RId");
                        List<Long> RidList = new ArrayList<Long>();
                        for (int i=0;i<RIdArray.length();i++)
                            RidList.add(RIdArray.getLong(i));

                        switch (tag) {
                            case "hop_Id_Id_Id_Id1":
                //                hopPath.getTot2()[2] = RidList.size();
                                for (Long aRidList : RidList) {
                                    queryRId("Id=" + aRidList, start, aRidList, end, "hop_Id_Id_Id_Id");
              //                      hopPath.getReq2()[2] ++;
                                }
                                break;
                            case "hop_Id_Id_Id_Id":
                //                hopPath.getTotalRequest()[2] +=  RidList.size();
                //                hopPath.getRes2()[2]++;
                                for (Long aRidList : RidList) {
                                    checkId("AND(Id=" + aRidList + ",RId=" + end + ")", "hop_Id_Id_Id_Id", start, between1, aRidList, end);
              //                      hopPath.getRequest()[2]++;
                                }
                                break;
//                            case "hop_Id_FCJA_Id_Id":
//               //                 hopPath.getTot2()[3] = RidList.size();
//                                for (Long aRidList : RidList) {
//                                    queryFCJAR("Id=" + aRidList, "hop_Id_FCJA_Id_Id", end,aRidList, start);
//             //                       hopPath.getReq2()[3]++;
//                                }
//                                break;
                            case "hop_Id_Id_FCJA_Id":
             //                   hopPath.getTot2()[4] = RidList.size();
                                for (Long aRidList : RidList) {
                                    queryFCJAR("Id=" + aRidList, "hop_Id_Id_FCJA_Id", start,aRidList, end);
            //                        hopPath.getReq2()[4]++;
                                }
                                break;
//                            case "twoHopAuToId":
//        //                        hopPath.getTotalRequest()[1] = RidList.size();
//                                for (Long aRidList : RidList) {
//                             //       System.out.println("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))");
//                                    checkId("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))", "twoHopAuToId", start,aRidList,0, end);
//         //                           hopPath.getRequest()[1]++;
//                                }
//                                break;
                            case "twoHopIdToAu":
                                //                        hopPath.getTotalRequest()[1] = RidList.size();
                                for (Long aRidList : RidList) {
                                    //       System.out.println("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))");
                                    checkId("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))", "twoHopIdToAu", start,aRidList,0, end);
                                    //                           hopPath.getRequest()[1]++;
                                }
                                break;
                            case "hop_Au_Id_Id_Au":
       //                         hopPath.getTotalRequest()[1]+=RidList.size();
     //                           hopPath.getRes2()[1]++;
                                for (Long aRidList : RidList) {
                             //       System.out.println("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))");
                                    checkId("AND(Id=" + aRidList + ",Composite(AA.AuId=" + end + "))", "hop_Au_Id_Id_Au", start, between1,aRidList, end);
    //                                hopPath.getRequest()[1]++;
                                }
                                break;
                            case "hop_Au_Id_Id_Id":
                                for (Long aRidList : RidList) {
                                    checkId("AND(Id=" + aRidList + ",RId=" + end + ")", "hop_Au_Id_Id_Au", start, between1,aRidList, end);
                                }
                                break;
                            case "hop_Id_Id_Id_Au1":
                                for (Long aRidList : RidList) {
                                    queryRId("Id="+aRidList,start,aRidList,end,"hop_Id_Id_Id_Au");
                                }
                                break;
                            case "hop_Id_Id_Id_Au":
                                for (Long aRidList : RidList) {
                                    checkId("AND(Id=" + aRidList + ",RId=" + end + ")","hop_Id_Id_Id_Au", start, between1, aRidList,end );
                                }
                                break;
                        }
//
//                    }
//
//                    public void cancelled() {
//                        System.out.println("The request has been cancelled");
//                    }
//
//                });
    }
    public void queryTwoAu(final long start,final long between1,final long end,final String tag){
      //  List<Long> AfIdList = property.findTwoHop(start,end);
        List<Long> AfIdList = new ArrayList<>();

        //System.out.println("twoAu :"+AfIdList.size());
        switch (tag){
            case "twoHopAuToAu":
                AfIdList  =  property.findTwoHop(start,end);
      //          hopPath.getTotalRequest()[0]=AfIdList.size();
       //         System.out.println("twoHopAuToAu: "+ hopPath.getTotalRequest()[0]);
                for (Long aAfIdList : AfIdList) {
     //               hopPath.getRequest()[0]++;
                    addPath(4,2,true,start,aAfIdList,0,end);
                }
                break;
            case "hop_Id_Au_Af_Au":
               AfIdList  =  property.findTwoHop(between1,end);
   //             hopPath.getTotalRequest()[3]+=AfIdList.size();
   //             hopPath.getRes2()[3]++;
                for (Long aAfIdList : AfIdList) {
                    addPath(2,3,true,start,between1,aAfIdList,end);
   //                 hopPath.getRequest()[3]++;
                }
                break;
        }
    }
    public void queryAu(String url,final String tag,final long start,final long end){
        url = "https://oxfordhk.azure-api.net/academic/v1.0/evaluate?expr="+
                url
                +"&attributes=AA.AuId&count=100000&subscription-key=f7cc29509a8443c5b3a5e56b0e38b5a6";

//        Future<HttpResponse<JsonNode>> future = Unirest.get(url)
//                .asJsonAsync(new Callback<JsonNode>() {
//
//                    public void failed(UnirestException e) {
//                        System.out.println("The request has failed");
//                    }
//
//                    public void completed(HttpResponse<JsonNode> response) {
        JsonNode  body = null;
        try {
            body = Unirest.get(url).asJson().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = body.getObject();
        JSONArray jsonArray=new JSONArray();
        if (!jsonObject.getJSONArray("entities").getJSONObject(0).isNull("AA"))
                         jsonArray = jsonObject.getJSONArray("entities").getJSONObject(0).getJSONArray("AA");
                        List<Long> AuIdList = new ArrayList<Long>();
                        for (int i=0;i<jsonArray.length();i++)
                            AuIdList.add(jsonArray.getJSONObject(i).getLong("AuId"));

                        int request = AuIdList.size();

                        switch (tag) {
                            case "hop_Id_Au_Af_Au":
  //                              hopPath.getTot2()[3] = AuIdList.size();
                                for (Long aAuIdList : AuIdList) {
                                    queryTwoAu(start,aAuIdList,end,"hop_Id_Au_Af_Au");
                                    queryId("AND(Composite(AA.AuId=" + aAuIdList + "),Composite(AA.AuId=" + end + "))", "hop_Id_Au_Af_Au"
                                            , start,aAuIdList,end);
   //                                 hopPath.getReq2()[3]++;
                                }
                                break;
                        }
//
//                    }
//
//
//
//                    public void cancelled() {
//                        System.out.println("The request has been cancelled");
//                    }
//
//                });
    }
    public boolean isAu(String url) throws UnirestException {
        url = "https://oxfordhk.azure-api.net/academic/v1.0/evaluate?expr="+
                url
                +"&attributes=Id&count=100000&subscription-key=f7cc29509a8443c5b3a5e56b0e38b5a6";
        //System.out.println(url);
        return Unirest.get(url).asJson().getBody().getObject().getJSONArray("entities").length() > 0;
    }

}