package test.truck;

import lombok.Data;

/**
 * 用车类型元数据模型
 *
 * @author xueli.wang
 * @since 2020/12/03 17:14
 */

@Data
public class TruckUseTypeModel {
    /**
     * 用车类型值
     */
    private String optionValue;

    /**
     * 用车类型名称
     */
    private String optionName;

    public TruckUseTypeModel() {

    }

    public TruckUseTypeModel(String optionValue, String optionName) {
        this.optionValue = optionValue;
        this.optionName = optionName;
    }
}
