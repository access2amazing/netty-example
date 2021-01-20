package test.truck;

import lombok.Data;

import java.util.List;

/**
 * @author xueli.wang
 * @since 2020/12/03 20:31
 */

@Data
public class TruckUseTypeConfig {
    /**
     * 场景
     */
    private Integer scene;

    /**
     * 场景对应的用车类型
     */
    private List<TruckUseTypeModel> truckUseTypes;
}
