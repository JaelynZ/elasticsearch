package com.search.iface;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 10:15
 */
@RequestMapping("/hello")
public interface IHello {
    /**
     * 测试
     * @param param
     * @return
     */
    @RequestMapping(value = "/say", method = RequestMethod.POST)
    String say(@RequestBody Map param);

}
