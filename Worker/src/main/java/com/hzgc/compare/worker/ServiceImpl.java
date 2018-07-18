package com.hzgc.compare.worker;

import com.hzgc.compare.worker.common.CompareParam;
import com.hzgc.compare.worker.common.Feature;
import com.hzgc.compare.rpc.client.result.AllReturn;
import com.hzgc.compare.rpc.server.annotation.RpcService;
import com.hzgc.compare.worker.common.FaceObject;
import com.hzgc.compare.worker.common.Quintuple;
import com.hzgc.compare.worker.common.SearchResult;
import com.hzgc.compare.worker.common.taskhandle.FlushTask;
import com.hzgc.compare.worker.common.taskhandle.TaskToHandleQueue;
import com.hzgc.compare.worker.compare.Comparators;
import com.hzgc.compare.worker.compare.ComparatorsImpl;
import com.hzgc.compare.worker.conf.Config;
import com.hzgc.compare.worker.memory.cache.MemoryCacheImpl;
import com.hzgc.compare.worker.persistence.HBaseClient;
import javafx.util.Pair;

import java.util.*;


public class ServiceImpl implements Service{
    private int resultDefaultCount = 10;
    private int compareSize = 500;
    private Config conf;

    public ServiceImpl(){
        this.conf = Config.getConf();
        resultDefaultCount = conf.getValue("", resultDefaultCount);
        compareSize = conf.getValue("", compareSize);
    }
//    public AllReturn<SearchResult> retrieval(List<String> arg1List, String arg2RangStart,
//                                             String arg2RangEnd, byte[] feature1, float[] feature2, int resultCount){
//        return new AllReturn<>(null);
//    }

    @Override
    public AllReturn<SearchResult> retrievalOnePerson(CompareParam param) {
        List<String> ipcIdList = param.getArg1List();
        String dateStart = param.getDateStart();
        String dateEnd = param.getDateEnd();
        byte[] feature1 = param.getFeatures().get(0).getFeature1();
        float[] feature2 = param.getFeatures().get(0).getFeature2();
        float sim = param.getSim();
        int resultCount = param.getResultCount();
        if (resultCount == 0){
            resultCount = resultDefaultCount;
        }
        SearchResult result;
        HBaseClient client = new HBaseClient();
        Comparators comparators = new ComparatorsImpl();
        // 根据条件过滤
        List<Pair<String, byte[]>> dataFilterd =  comparators.<byte[]>filter(ipcIdList, null, dateStart, dateEnd);
        if(dataFilterd.size() > compareSize){
            // 若过滤结果太大，则需要第一次对比
            List<String> firstCompared =  comparators.compareFirst(feature1, 500, dataFilterd);
            //根据对比结果从HBase读取数据
            List<FaceObject> objs =  client.readFromHBase(firstCompared);
            // 第二次对比
            result = comparators.compareSecond(feature2, sim, objs);
            //结果排序
            result.sortBySim();
            //取相似度最高的几个
            result = result.take(resultDefaultCount);
        }else {
            //若过滤结果比较小，则直接进行第二次对比
            List<FaceObject> objs = client.readFromHBase2(dataFilterd);
//            System.out.println("过滤结果" + objs.size() + " , " + objs.get(0));
            result = comparators.compareSecond(feature2, sim, objs);
            //结果排序
            result.sortBySim();
            //取相似度最高的几个
            result = result.take(resultCount);
        }
//        System.out.println("对比结果2" + result.getRecords().length + " , " + result.getRecords()[0]);
        return new AllReturn<>(result);
    }

    @Override
    public AllReturn<SearchResult> retrievalSamePerson(CompareParam param) {
        List<String> ipcIdList = param.getArg1List();
        String dateStart = param.getDateStart();
        String dateEnd = param.getDateEnd();
        List<Feature> features = param.getFeatures();
        float sim = param.getSim();
        int resultCount = param.getResultCount();
        if (resultCount == 0){
            resultCount = resultDefaultCount;
        }
        List<byte[]> feature1List = new ArrayList<>();
        List<float[]> feature2List = new ArrayList<>();
        for (Feature feature : features) {
            feature1List.add(feature.getFeature1());
            feature2List.add(feature.getFeature2());
        }
        SearchResult result;
        HBaseClient client = new HBaseClient();
        Comparators comparators = new ComparatorsImpl();
        // 根据条件过滤
        List<Pair<String, byte[]>> dataFilterd =  comparators.<byte[]>filter(ipcIdList, null, dateStart, dateEnd);
        if(dataFilterd.size() > compareSize) {
            // 若过滤结果太大，则需要第一次对比
            List<String> firstCompared = comparators.compareFirstTheSamePerson(feature1List, 500, dataFilterd);
            //根据对比结果从HBase读取数据
            List<FaceObject> objs =  client.readFromHBase(firstCompared);
            // 第二次对比
            result = comparators.compareSecondTheSamePerson(feature2List, sim, objs);
            //结果排序
            result.sortBySim();
            //取相似度最高的几个
            result = result.take(resultDefaultCount);
        } else {
            //若过滤结果比较小，则直接进行第二次对比
            List<FaceObject> objs = client.readFromHBase2(dataFilterd);
            result = comparators.compareSecondTheSamePerson(feature2List, sim, objs);
            //结果排序
            result.sortBySim();
            //取相似度最高的几个
            result = result.take(resultCount);
        }
        return new AllReturn<>(result);
    }

    @Override
    public AllReturn<Map<String, SearchResult>> retrievalNotSamePerson(CompareParam param) {
        Map<String, SearchResult> result = new HashMap<>();
        List<String> ipcIdList = param.getArg1List();
        String dateStart = param.getDateStart();
        String dateEnd = param.getDateEnd();
        List<Feature> features = param.getFeatures();
        float sim = param.getSim();
        HBaseClient client = new HBaseClient();
        // 根据条件过滤
        Comparators comparators = new ComparatorsImpl();
        List<Pair<String, byte[]>> dataFilterd =  comparators.filter(ipcIdList, null, dateStart, dateEnd);
        if(dataFilterd.size() > compareSize){
            // 若过滤结果太大，则需要第一次对比
            List<String> Rowkeys = comparators.compareFirstNotSamePerson(features, 500, dataFilterd);

            //根据对比结果从HBase读取数据
            List<FaceObject> objs = client.readFromHBase(Rowkeys);

            Map<String, SearchResult> resultTemp = comparators.compareSecondNotSamePerson(features, sim, objs);
            for(Map.Entry<String, SearchResult> searchResult : resultTemp.entrySet()){
                //取相似度最高的几个
                SearchResult searchResult1 = searchResult.getValue().take(resultDefaultCount);
                result.put(searchResult.getKey(), searchResult1);
            }
            return new AllReturn<>(result);
        } else {
            //若过滤结果比较小，则直接进行第二次对比
            List<FaceObject> objs = client.readFromHBase2(dataFilterd);
            Map<String, SearchResult> resultTemp = comparators.compareSecondNotSamePerson(features, sim, objs);
            for(Map.Entry<String, SearchResult> searchResult : resultTemp.entrySet()){
                //取相似度最高的几个
                SearchResult searchResult1 = searchResult.getValue().take(resultDefaultCount);
                result.put(searchResult.getKey(), searchResult1);
            }
            return new AllReturn<>(result);
        }
    }

    @Override
    public void stopTheWorker() {
        MemoryCacheImpl memoryCache = MemoryCacheImpl.getInstance();
        List<Quintuple<String, String, String, String, byte[]>> buffer = memoryCache.getBuffer();
        memoryCache.moveToCacheRecords(buffer);
        TaskToHandleQueue.getTaskQueue().addTask(new FlushTask(buffer));
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
