package ficus.suitcase.localcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by DamonFicus on 2018/10/30.
 * 通用查询模块：cardbin信息刷新调度
 * 对于更新频率不高的卡bin信息库，一天更新缓存一次
 * 更新缓存标识；
 *
 * @author DamonFicus
 */
@Service("cardBinCacheRefreshTask")
public class CardBinCacheRefreshTask {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String MASTER = "MASTER";
    public static final String SLAVE = "SLAVE";

    @Resource
    private CardBinCache cardBinCache;


//    @Autowired
//    private IDebitCardBinInfoDao debitCardBinInfoDao;

    public void run(){
        logger.info("卡bin调度触发,时间 "+new Date());
        cardBinCacheRefresh();
    }



    public void cardBinCacheRefresh(){

        logger.info("CardBinCacheRefreshTask-通用查询模块：cardbin信息刷新调度开始执行.....");

        String resourceName = cardBinCache.getCURRENT_RESOURCE();
        String newResourceName="";
        if (MASTER.equals(resourceName)) {
            cardBinCache.setCURRENT_RESOURCE(SLAVE);
            newResourceName=SLAVE;
        } else {
            cardBinCache.setCURRENT_RESOURCE(MASTER);
            newResourceName=MASTER;
        }
        try {
            //强制刷新缓存
            cardBinCache.getCardBinMap(true);
            logger.info("CardBinCacheRefreshTask通用查询模块：cardbin信息刷新调度执行完成,已将原缓存源【{}】切换至【{}】",resourceName,newResourceName);
        } catch (Exception e) {
            logger.error("卡bin缓存刷新调度执行失败", e);
        }
    }

}
