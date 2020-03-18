package cn.tedu.straw.search.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Data
public class EsTag implements Serializable {


    private static final long serialVersionUID = 7172096494450046637L;
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String tagName;


    private String createby;

    private Date createtime;


}
