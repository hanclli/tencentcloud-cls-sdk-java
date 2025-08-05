package com.tencentcloudapi.cls.producer.util;

import com.tencent.polaris.api.core.ConsumerAPI;
import com.tencent.polaris.api.pojo.Instance;
import com.tencent.polaris.api.rpc.GetOneInstanceRequest;
import com.tencent.polaris.api.rpc.InstancesResponse;
import com.tencent.polaris.factory.api.DiscoveryAPIFactory;

public class PolarisInstance {

    private static  ConsumerAPI consumerAPI = DiscoveryAPIFactory.createConsumerAPI();

    /**
     * 获取Polaris实例
     */
    public static Instance getOneInstance(String namespace, String service) {
        GetOneInstanceRequest getOneInstanceRequest = new GetOneInstanceRequest();
        getOneInstanceRequest.setNamespace(namespace);
        getOneInstanceRequest.setService(service);
        InstancesResponse oneInstance = consumerAPI.getOneInstance(getOneInstanceRequest);
        Instance[] instances = oneInstance.getInstances();
        if (instances == null || instances.length == 0) {
            throw new RuntimeException("获取Polaris实例失败");
        }
        return instances[0];
    }

    /**
     * 等到不再获取polaris实例之后再退出，如果不退出就会有线程一直执行
     */
    public static void exit() {
        consumerAPI.destroy();
        consumerAPI = null;
    }
}
