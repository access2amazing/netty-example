package test.truck;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueli.wang
 * @since 2021/01/09 18:33
 */

@Slf4j
public class TruckUseTypeAbility {
    public List<TruckUseTypeModel> getTruckUseTypesByScene(TruckUseTypeSceneEnum scene) {
        List<TruckUseTypeConfig> configList = getTruckUseTypeConfigs();
        System.out.println(configList);

        List<TruckUseTypeConfig> configs = new ArrayList<>();
        configs.addAll(configList);
        System.out.println(configs);

        if (CollectionUtils.isEmpty(configs)) {
            // 用车类型配置为空
            log.error("用车类型配置为空，返回本地的用车类型配置，当前的配置为[{}]", getTruckUseTypeConfigs());
            return getLocalTruckUseTypes();
        }

        TruckUseTypeConfig truckUseTypeConfig = configs.stream()
                .filter(config -> scene.getCode().equals(config.getScene()))
                .findAny()
                .orElse(null);
        if (truckUseTypeConfig == null || CollectionUtils.isEmpty(truckUseTypeConfig.getTruckUseTypes())) {
            // 具体场景的用车类型配置为空
            log.error("获取场景[{}]的用车类型配置为空，返回本地的用车类型配置，当前的配置为[{}]",
                    scene.getName(), getTruckUseTypeConfigs());
            return getLocalTruckUseTypes();
        }

        for (TruckUseTypeModel truckUseType : truckUseTypeConfig.getTruckUseTypes()) {
            if (truckUseType == null) {
                // 具体的用车类型为空
                log.error("获取场景[{}]具体的用车类型为空，返回本地的用车类型配置，当前的配置为[{}]",
                        scene.getName(), getTruckUseTypeConfigs());
                return getLocalTruckUseTypes();
            }
        }

        return truckUseTypeConfig.getTruckUseTypes();
    }

    private List<TruckUseTypeConfig> getTruckUseTypeConfigs() {
        List<TruckUseTypeConfig> truckUseTypeConfigs = new ArrayList<>();

        TruckUseTypeConfig config1 = new TruckUseTypeConfig();
        config1.setScene(1);
        config1.setTruckUseTypes(getLocalTruckUseTypes());
        truckUseTypeConfigs.add(config1);

        TruckUseTypeConfig config2 = new TruckUseTypeConfig();
        config2.setScene(2);
        config2.setTruckUseTypes(getLocalTruckUseTypes());
        truckUseTypeConfigs.add(config2);

        return truckUseTypeConfigs;
    }

    private List<TruckUseTypeModel> getLocalTruckUseTypes() {
        List<TruckUseTypeModel> localTruckUseTypes = new ArrayList<>();
        localTruckUseTypes.add(new TruckUseTypeModel("0", "整车"));
        localTruckUseTypes.add(new TruckUseTypeModel("1", "零担"));

        return localTruckUseTypes;
    }
}
