package test.truck;

/**
 * 用车类型元数据使用场景枚举
 *
 * @author xueli.wang
 * @since 2020/12/01 12:08
 */

public enum TruckUseTypeSceneEnum {
    /**
     * 用车类型元数据使用场景枚举
     */
    STANDARD(1, "标准场景"),
    DIRECT_CONSIGNOR(2, "直客发货场景"),
    ;

    /**
     * 枚举编码
     */
    private Integer code;

    /**
     * 枚举名称
     */
    private String name;

    TruckUseTypeSceneEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
