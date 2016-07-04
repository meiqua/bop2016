package bop.hop;

import bop.client.Client;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HopMethod {
        @Autowired
        Client client;

        @Autowired
        HopPath hopPath;

        public void hop(long id1,long id2) throws UnirestException {
                boolean isAu1 = isAu(id1);
                boolean isAu2 = isAu(id2);

                if((!isAu1)&&(!isAu2)){
                        // id - id
                        oneHopIdToId(id1,id2);
                        twoHopIdToId(id1,id2);
                        hop_Id_Id_Id_Id(id1,id2);
                        hop_Id_FCJA_Id_Id(id1,id2);
                        hop_Id_Id_FCJA_Id(id1,id2);
                        System.out.println("id - id");

                }else if (isAu1&&isAu2){
                        //au - au
                        twoHopAuToAu(id1, id2);
                        hop_Au_Id_Id_Au(id1, id2);
    //                    hopPath.getFlag()[2]=0;
    //                    hopPath.getFlag()[3]=0;
     //                   hopPath.getFlag()[4]=0;
                        System.out.println("au - au");

                }else if((!isAu1)&&isAu2){
                        //id - au
                        oneHopIdToAu(id1,id2);
                        twoHopIdToAu(id1,id2);
                        hop_Id_FCJA_Id_Au(id1,id2);
                        hop_Id_Au_Af_Au(id1,id2);
                        hop_Id_Id_Id_Au(id1,id2);
      //                  hopPath.getFlag()[4]=0;
                        System.out.println("id - au");
                }else {
                        //au - id
                        oneHopAuToId(id1, id2);
                        twoHopAuToId(id1, id2);
                        hop_Au_Id_FCJA_Id(id1,id2);
                        hop_Au_Af_Au_Id(id1,id2);
                          hop_Au_Id_Id_Id(id1,id2);
    //                    hopPath.getFlag()[4]=0;
                        System.out.println("au - id");

                }
        }

        boolean isAu(long id) throws UnirestException {
               System.out.println("isAu?: "+id);
               return client.isAu("Composite(AA.AuId="+id+")");

        }

        void oneHopAuToId(long au,long id){
        // id= and au=
   //             hopPath.getFlag()[0]=1;
                client.checkId("And(Id=" + id + ",Composite(AA.AuId="+au+"))","oneHopAuToId",au,0,0,id);
        }

        void oneHopIdToAu(long id,long au){
        // id= and au=
                hopPath.getReverseFlag()[0] = true;
                oneHopAuToId(au,id);

        }

        void oneHopIdToId(long id1,long id2){
        //id1= and Rid=
        //        hopPath.getFlag()[0]=1;
                client.checkId("And(Id=" + id1 + ",RId="+id2+")","oneHopIdToId",id1,0,0,id2);
        }

        void twoHopIdToId(long id1,long id2){
        //id-FCJAR-id
        //id1= get FID CID JID AuID RID
        //id2= --=
  //              hopPath.getFlag()[1]=1;
                client.queryFCJAR("Id="+id1,"twoHopIdToId",id1,0,id2);
        }

        void twoHopIdToAu(long id,long au){
        //id-id-au
        //id= get RID
        //id(RID)= au=
     //          hopPath.getReverseFlag()[1]=true;
                client.queryRId("Id=" + id, au, 0, id, "twoHopIdToAu");
        }

        void twoHopAuToId(long au,long id){
        //au-id-id
        //id= get RID
        //id(RID)= au=
     //           hopPath.getFlag()[1]=1;
                client.queryId("Composite(AA.AuId="+au+")","twoHopAuToId",au,0,id);
        }

        void twoHopAuToAu(long au1,long au2){
        //au1-[]-()-[]-au2 (in graphDataBase)
        //au1= au2=
     //           hopPath.getFlag()[0]=1;
                //System.out.println("twoHopAuToAu");
                client.queryTwoAu(au1, 0, au2, "twoHopAuToAu");
                client.queryId("AND(Composite(AA.AuId=" + au1 + "),Composite(AA.AuId=" + au2 + "))", "twoHopAuToAu"
                        , au1, 0, au2);
        }

        void hop_Id_Id_Id_Id(long id1,long id2){
        //id1= get RID1
        //id(RID1)= get RID2
        //id(RID2)= id2=
    //            hopPath.getFlag()[2]=1;
                client.queryRId("Id="+id1,id1,0,id2,"hop_Id_Id_Id_Id1");
        }

        void hop_Id_FCJA_Id_Id(long id1,long id2){
        //id1= get FCJA
        //FCJA= RID(id2)=
  //              hopPath.getFlag()[3]=1;
                client.queryFCJAR("Id=" + id2, "hop_Id_FCJA_Id_Id", id1, 0, id2);
        }

        void hop_Id_Id_FCJA_Id(long id1,long id2){
        //id2= get FCJA
        //FCJA= RID(id2)=
    //            hopPath.getFlag()[4]=1;
                client.queryRId("Id="+id1,id1,0,id2,"hop_Id_Id_FCJA_Id");
        }

        void hop_Id_FCJA_Id_Au(long id,long au){
        //id= get FCJA
        //FCJA= au=
                hopPath.getReverseFlag()[1]=true;
                hop_Au_Id_FCJA_Id(au,id);

        }

        void hop_Au_Id_FCJA_Id(long au,long id){
        //id= get FCJA
        //FCJA= au=
   //             hopPath.getFlag()[2]=1;
                client.queryFCJAR("Id="+id,"hop_Au_Id_FCJA_Id",au,0,id);
        }

        void hop_Id_Au_Af_Au(long id,long au){
        //id= get au
        //graphDataBase
     //           hopPath.getFlag()[3]=1;
                client.queryAu("Id="+id,"hop_Id_Au_Af_Au",id,au);
        }

        void hop_Au_Af_Au_Id(long au,long id){
        //id= get au
        //graphDataBase
                hopPath.getReverseFlag()[2]=true;
                hop_Id_Au_Af_Au(id,au);
        }

        void hop_Au_Id_Id_Au(long au1,long au2){
                //au= get id
                //RId= au=
  //              hopPath.getFlag()[1]=1;
                client.queryId("Composite(AA.AuId="+au1+")","hop_Au_Id_Id_Au",au1,0,au2);
        }
    void hop_Au_Id_Id_Id(long au,long id){
        client.queryId("Composite(AA.AuId="+au+")","hop_Au_Id_Id_Id",au,0,id);
    }

    void hop_Id_Id_Id_Au(long id,long au){
        client.queryRId("Id="+id,id,0,au,"hop_Id_Id_Id_Au1");
    }

}
