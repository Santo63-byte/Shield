package com.sbyte.shield.core.cache;
import com.sbyte.shield.core.base.annotations.OnApplicationStart;
import com.sbyte.shield.core.base.abst.CacheManager;
import com.sbyte.shield.datasource.entity.ledger.UserCredInfo;
import com.sbyte.shield.datasource.mybatis.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import static com.sbyte.shield.constants.ShieldConstants.*;

/**
 * This class is responsible for managing the caches related to Register component
 * It uses a ConcurrentHashMap to store the cache data and is thread safe (to do with Centralised cache manager later)
 * this is an inmemory cache and not suitable if you have multiple instances
 * @Author Santo
 */
@OnApplicationStart()
@Component("registerCacheStore")
public class RegisterCacheStore implements CacheManager<String, String> {

    @Autowired
    UserRepository userRepository;

    private final ConcurrentHashMap<String, Set<String>> cacheMap = new ConcurrentHashMap<>();

    public void init() {
        loadAllCaches();
    }

    @Override
    public void loadCache(String cacheName) {
        List<UserCredInfo> usersList = userRepository.findAllActiveUsers();
        cacheMap.putIfAbsent(cacheName, ConcurrentHashMap.newKeySet());

        Set<String> dataSet = cacheMap.get(cacheName);
        for (UserCredInfo user : usersList) {
            if (cacheName.equals(SHIELD_REGISTERED_USERNAMES_CACHE) && user.getUserName() != null) {
                dataSet.add(user.getUserName());
            }
            if (cacheName.equals(SHIELD_REGISTERED_EMAILS_CACHE)&&user.getUserEmail() != null) {
                dataSet.add(user.getUserEmail());
            }
        }
    }
    @Override
    public void loadAllCaches() {
        List<UserCredInfo> usersList = userRepository.findAllActiveUsers();
        cacheMap.putIfAbsent(SHIELD_REGISTERED_USERNAMES_CACHE, ConcurrentHashMap.newKeySet());
        cacheMap.putIfAbsent(SHIELD_REGISTERED_EMAILS_CACHE, ConcurrentHashMap.newKeySet());

        Set<String> usernamesSet = cacheMap.get(SHIELD_REGISTERED_USERNAMES_CACHE);
        Set<String> emailsSet = cacheMap.get(SHIELD_REGISTERED_EMAILS_CACHE);
        for (UserCredInfo user : usersList) {
            if (user.getUserName() != null) {
                usernamesSet.add(user.getUserName());
            }
            if (user.getUserEmail() != null) {
                emailsSet.add(user.getUserEmail());
            }
        }
    }

    @Override
    public void put(String key, Set<String> value) {
            cacheMap.putIfAbsent(key, ConcurrentHashMap.newKeySet());
            cacheMap.put(key, value);
    }
    @Override
    public void put(String key, String value) {
            cacheMap.putIfAbsent(key, ConcurrentHashMap.newKeySet());
            cacheMap.get(key).add(value);

    }
    @Override
    public Object get(String cacheName) {
        Set<String> cache = cacheMap.getOrDefault(cacheName, ConcurrentHashMap.newKeySet());
        // Return true if the key exists in the set, otherwise false
        return cache;
    }
    @Override
    public void evict(String cacheName, String key) {
        Set<String> cache = cacheMap.getOrDefault(cacheName, ConcurrentHashMap.newKeySet());
        cache.remove(key);
    }
    @Override
    public void clear(String cacheName) {
        cacheMap.remove(cacheName);
    }

    public Set<String> getRegisteredUsernames() {
        return cacheMap.getOrDefault(SHIELD_REGISTERED_USERNAMES_CACHE, ConcurrentHashMap.newKeySet());
    }
    public Set<String> getRegisteredEmails() {
        return cacheMap.getOrDefault(SHIELD_REGISTERED_EMAILS_CACHE, ConcurrentHashMap.newKeySet());
    }
    @Override
    public boolean isPresentInCache(String cacheName, String key) {
        return cacheMap.getOrDefault(cacheName, ConcurrentHashMap.newKeySet())
                .contains(key);
    }
    @Override
    public boolean isCacheEmpty(String cacheName) {
        return cacheMap.getOrDefault(cacheName, ConcurrentHashMap.newKeySet()).isEmpty();
    }
    @Override
    public void reloadAllCaches() {
        cacheMap.clear();
        loadAllCaches();
    }
    @Override
    public void reloadCache(String cacheName) {
        cacheMap.remove(cacheName);
        loadCache(cacheName);
    }
}
