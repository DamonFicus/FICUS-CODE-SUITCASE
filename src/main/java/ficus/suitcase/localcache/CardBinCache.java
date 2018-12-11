package ficus.suitcase.localcache;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DamonFicus
 * Created on 2018/10/30.
 * 卡bin信息的本地缓存
 * 1.用两个容器进行主从存装
 * 2.调度更新缓存,标识区分当前正提供服务的缓存，
 *   更新完备用缓存后,使用备用缓存提供服务
 */
@Component
public class CardBinCache {

    private static final String MASTER="MASTER";

    private static final String SLAVE="SLAVE";

    private static final String ALL="ALL";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static CardBinCache catche ;

    private Map<String, List<DebitCardBinInfo>> cardBinMapMaster = new HashMap<String, List<DebitCardBinInfo>>();

    private Map<String, List<DebitCardBinInfo>> cardBinMapSlave = new HashMap<String, List<DebitCardBinInfo>>();

    private String currentResource = MASTER;

    public String getCurrentResource() {
        return currentResource;
    }

    public void setCurrentResource(String currentResource) {
        this.currentResource = currentResource;
    }


    private CardBinCache(){
    }


    public static synchronized CardBinCache getInstance(){
        if(catche == null){
            catche = new CardBinCache();
        }
        return catche;
    }


    /**
     * 当前使用的缓存容器
     * @param isforceFresh  强制刷新：使用调度触发的时候强制刷新缓存数据
     * @return
     */
    public Map<String, List<DebitCardBinInfo>> getCardBinMap(Boolean isforceFresh) throws Exception {
        Map<String, List<DebitCardBinInfo>> cardBinMapSource=null;
        //如果是强制刷新则清空缓存对象,再去装载数据：
        if(isforceFresh){
            if(MASTER.equals(currentResource)){;
                cardBinMapMaster.clear();
            }else if(SLAVE.equals(currentResource)){
                cardBinMapSlave.clear();
            }
        }

        if(MASTER.equals(currentResource)){;
            cardBinMapSource=cardBinMapMaster;
        }else if(SLAVE.equals(currentResource)){
            cardBinMapSource=cardBinMapSlave;
        }

        if(cardBinMapSource.isEmpty()||isforceFresh){
            synchronized (cardBinMapSource) {
                if(cardBinMapSource.isEmpty()||isforceFresh){
                    refreshCardBinMap(currentResource,null);
                }
            }
        }
        return cardBinMapSource;
    }



    public void setCardBinMap(Map<String, List<DebitCardBinInfo>> cardBinMap, String resourceName){
        if(MASTER.equals(resourceName)){;
            this.cardBinMapMaster=cardBinMap;
        }else if(SLAVE.equals(resourceName)){
            this.cardBinMapSlave=cardBinMap;
        }
    }

    /**
     * 刷新缓存，获取DB最新数据
     * @param resouceName
     * @throws Exception
     */
    public synchronized void refreshCardBinMap(String resouceName, String refreshRange) throws Exception {

        Map<String, List<DebitCardBinInfo>> cardBinMap;
        cardBinMap=assembleCarbinInfo();
        //初始加载时,查询一遍同时更新主从
        if(ALL.equals(refreshRange)){
            setCardBinMap(cardBinMap,MASTER);
            setCardBinMap(cardBinMap,SLAVE);
        }else {
            setCardBinMap(cardBinMap,resouceName);
        }

    }


    /**
     * 数据获取后，组装成需要的数据结构：
     * 返回参数Map中的
     * key为cardFlag
     * value为卡bin记录列表
     */
    public Map<String, List<DebitCardBinInfo>> assembleCarbinInfo() throws Exception {
        logger.info("CardBinCache.refreshCardBinMap从数据库中获取卡bin数据信息 start....");
        try {
            //1.数据来源做获取：DAO查询或数据获取接口调用
            //数据获取根据实际库表来；
            List<DebitCardBinInfo> cardBinInfos=null;
            List<DebitCardBinInfo> infos = null;
            Map<String, List<DebitCardBinInfo>> extraCardBinMap = new HashMap<>(16);
            if (CollectionUtils.isNotEmpty(cardBinInfos)) {
                for (DebitCardBinInfo cardBin : cardBinInfos) {
                    if (extraCardBinMap.containsKey(cardBin.getCardFlag())) {
                        extraCardBinMap.get(cardBin.getCardFlag()).add(cardBin);
                    } else {
                        infos = new ArrayList<>();
                        infos.add(cardBin);
                        extraCardBinMap.put(cardBin.getCardFlag(), infos);
                    }
                }
            }
            logger.info("CardBinCache.refreshCardBinMap从数据库中获取卡bin数据信息 end....");
            return extraCardBinMap;
        }catch (Exception e){
            logger.error("getCardBinInfoFromDB error",e);
            throw new Exception("获取全部卡bin信息数据库异常" + e.getMessage(),e);
        }
    }

}
