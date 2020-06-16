package cn.tedu.straw.portal.domian.vo;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description: 该类在checkbox展示
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/9$ 16:58$
 * @Version: 1.0
 */
@Data
public class TeacherVO {
    private String text;
    private Integer value;

}
