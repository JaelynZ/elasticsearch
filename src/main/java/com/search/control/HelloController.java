package com.search.control;

import com.search.iface.IHello;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 9:58
 */
@RestController
public class HelloController implements IHello {

    @Override
    public String say(@RequestBody Map param) {
        String name = param.get("name").toString();
        return "hello," + name;
    }
}
