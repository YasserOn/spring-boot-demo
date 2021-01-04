package com.xkcoding.mongodb.component;

import cn.hutool.core.lang.Snowflake;
import com.xkcoding.mongodb.annotation.SnowFlakeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

	@Autowired
	private MongoTemplate mongo;
    @Autowired
    private Snowflake snowflake;


    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource(); // 获取事件最初发生的对象
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                ReflectionUtils.makeAccessible(field); // 使操作的成员可访问
                // 如果是带有@AutoIncrement注解的，成员调用getter方法返回的类型是Number类型的，返回的类型都是0的(没有赋值，默认为0)
                if (field.isAnnotationPresent(SnowFlakeId.class) && Long.class.equals(field.getType())) {
                    field.set(source, getSnowFlakeId());
                }
            });
        }
//        super.onBeforeConvert(event);
    }

//    /**
//	 * 获取下一个自增ID
//	 *
//	 * @param collName
//	 *            集合（这里用类名，就唯一性来说最好还是存放长类名）名称
//	 * @return 序列值
//	 */
//	private Long getNextId(String collName) {
//        System.out.println("生成雪花算法");
//		Query query = new Query(Criteria.where("collName").is(collName));
//		Update update = new Update();
//		update.inc("seqId", 1);
//		FindAndModifyOptions options = new FindAndModifyOptions();
//		options.upsert(true);
//		options.returnNew(true);
//        SeqInfo seq = mongo.findAndModify(query, update, options, SeqInfo.class);
//		return seq.getSeqId();
//	}

    /**
     * 获取下一个自增ID
     *
     * @return 序列值
     */
    private Long getSnowFlakeId() {

        return snowflake.nextId();
    }
}
