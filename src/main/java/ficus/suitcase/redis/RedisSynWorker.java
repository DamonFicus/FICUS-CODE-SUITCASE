package ficus.suitcase.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import java.util.concurrent.TimeUnit;

/**
 * 类说明：redis写时加锁处理类<br>
 * 详细描述：redis写时加锁处理类<br>
 * @author DamonFicus
 */
public abstract class RedisSynWorker<T> {
	private static final long SLEEP_TIME = 500L;
	private static final int LOCK_TIMES = 20;
	private static final long KEY_EXPIRETIME = 600L;
	private RedisTemplate<String, Object> redisTemplate;
	
	public RedisSynWorker(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public T execute(String key) throws Throwable {
		try {
			getLock(String.format("LOCK_%s", key));
			return doTask(key);
		} finally {
			releaseLock(String.format("LOCK_%s", key));
		}
	}

	public abstract T doTask(String key) throws Throwable;
	/**
	 * 获得锁
	 * @param key
	 * @return
	 */
	private boolean getLock(final String key) {
		if (!StringUtils.hasText(key)) {
			return false;
		}
		int pollTimes = 1;
		while(true) {
			Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					boolean result = connection.setNX(key.getBytes(), "0".getBytes());
					if (result) {
						connection.expire(key.getBytes(), KEY_EXPIRETIME);
					}
					return result;
				}
			});
			if (result) {
				return true;
			} else {
				Long ttl = redisTemplate.execute(new RedisCallback<Long>() {
					@Override
					public Long doInRedis(RedisConnection connection)
							throws DataAccessException {
						return connection.ttl(key.getBytes());
					}
				});
				if (ttl < 0) {
					redisTemplate.opsForValue().set(key, "0", KEY_EXPIRETIME, TimeUnit.SECONDS);
					return true;
				}				
				try {
					if (pollTimes >= LOCK_TIMES) break;
					pollTimes++;
					Thread.sleep(SLEEP_TIME);
				} catch (Exception e) {
				}
			}
		}
		return false;
	}
	/**
	 * 释放锁
	 * @param key
	 * @return
	 */
	private boolean releaseLock(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				try {
					connection.del(key.getBytes());
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}
	
}
