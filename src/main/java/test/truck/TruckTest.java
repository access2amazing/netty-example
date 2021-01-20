package test.truck;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xueli.wang
 * @since 2021/01/09 18:48
 */

@Slf4j
public class TruckTest {
    private static final Integer LCL_CARGO = 1;

    private static Random random = new Random();

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(10000);
        ExecutorService exec = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10000; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    test(random.nextInt(10000) < 10);
                    countDownLatch.countDown();
                }
            };

            exec.execute(runnable);
        }

        try {
            // 计数器大于0 时，await()方法会阻塞程序继续执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test(boolean remove) {
        TruckUseTypeAbility ability = new TruckUseTypeAbility();
        List<TruckUseTypeModel> models = ability.getTruckUseTypesByScene(TruckUseTypeSceneEnum.STANDARD);

        System.out.println(models.size());
        if (remove) {
            models.removeIf(
                    truckUseType -> String.valueOf(LCL_CARGO).equals(truckUseType.getOptionValue()));
        }
        System.out.println(models.size() + ":");

        for (TruckUseTypeModel model : models) {
            if (model == null) {
                log.error("具体的用车类型元数据为空，列表为：{}", models);
                continue;
            }

            for (TruckUseTypeModel model1 : models) {
                if (model1 == null) {
                    log.error("具体的用车类型元数据为空，列表为：{}", models);
                    continue;
                }
            }
        }
    }
}
