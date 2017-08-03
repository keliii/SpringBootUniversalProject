package com.keliii.user.controller;

import com.keliii.common.domain.NetListMsg;
import com.keliii.user.annotation.Permission;
import com.keliii.user.repository.ControllerUrlRepository;
import com.keliii.user.entity.ControllerUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by keliii on 2017/6/22.
 */
@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private ControllerUrlRepository controllerUrlRepository;

    @RequestMapping("/all")
    @Permission(isPublic = true)
    public NetListMsg<ControllerUrl> getAll(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer limit) {
//    public NetListMsg<ControllerUrl> getAll(@PathVariable("page")Integer page, @PathVariable("limit")Integer limit) {
        Pageable pageable = new PageRequest(page, limit);
        Page<ControllerUrl> list = controllerUrlRepository.findAll(pageable);
        NetListMsg<ControllerUrl> msg = new NetListMsg<>();
        msg.setResult(1);
        msg.setMsg("success");
        msg.setRows(list.getContent());
        msg.setPages(list.getTotalPages());
        msg.setElements(list.getTotalElements());
        return msg;
    }

    @RequestMapping("/all2/{page}-{limit}")
    @Permission(url = "/page/all2/*")
    public NetListMsg<ControllerUrl> getAll2(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return getAll(page, limit);
    }
}
