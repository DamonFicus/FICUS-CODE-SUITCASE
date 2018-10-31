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
 * 更新完备用缓存后,使用备用缓存提供服务
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

    private String CURRENT_RESOURCE = MASTER;

/*  @Resource
    private IDebitCardBinInfoDao debitCardBinInfoDao;*/


    public String getCURRENT_RESOURCE() {
        return CURRENT_RESOURCE;
    }

    public void setCURRENT_RESOURCE(String CURRENT_RESOURCE) {
        this.CURRENT_RESOURCE = CURRENT_RESOURCE;
    }


    private CardBinCache(){
        //初始未指定缓存主从时，主从缓存全部加载
        try {
//            refreshCardBinMap(null,ALL);
        } catch (Exception e) {
           logger.error("初始加载主从缓存时异常",e);
        }

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
            if(MASTER.equals(CURRENT_RESOURCE)){;
                cardBinMapMaster.clear();
            }else if(SLAVE.equals(CURRENT_RESOURCE)){
                cardBinMapSlave.clear();
            }
        }

        if(MASTER.equals(CURRENT_RESOURCE)){;
            cardBinMapSource=cardBinMapMaster;
        }else if(SLAVE.equals(CURRENT_RESOURCE)){
            cardBinMapSource=cardBinMapSlave;
        }

        if(cardBinMapSource.isEmpty()||isforceFresh){
            synchronized (cardBinMapSource) {
                if(cardBinMapSource.isEmpty()||isforceFresh){
                    refreshCardBinMap(CURRENT_RESOURCE,null);
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
//            IDebitCardBinInfoDao debitCardBinInfoDao=(IDebitCardBinInfoDao) SpringContextHolder.getBean(IDebitCardBinInfoDao.class);
//            List<DebitCardBinInfo> cardBinInfos = debitCardBinInfoDao.queryDebitCardBinDetail();
            List<DebitCardBinInfo> cardBinInfos=null;//数据获取根据实际库表来；
            List<DebitCardBinInfo> infos = null;
            Map<String, List<DebitCardBinInfo>> extraCardBinMap = new HashMap<String, List<DebitCardBinInfo>>();
            if (CollectionUtils.isNotEmpty(cardBinInfos)) {
                for (DebitCardBinInfo cardBin : cardBinInfos) {
                    if (extraCardBinMap.containsKey(cardBin.getCardFlag())) {
                        extraCardBinMap.get(cardBin.getCardFlag()).add(cardBin);
                    } else {
                        infos = new ArrayList<DebitCardBinInfo>();
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
