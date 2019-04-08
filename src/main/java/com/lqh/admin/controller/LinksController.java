package com.lqh.admin.controller;

import com.lqh.admin.dto.QueryPage;
import com.lqh.admin.dto.ResponseCode;
import com.lqh.admin.entity.Links;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@SuppressWarnings("all")
@RequestMapping("/links")
public class LinksController extends BaseController {

    @Autowired
    private LinksService linksService;

    @GetMapping(value = "/findAllCount")
    public ResponseCode findAllCount() {
        return ResponseCode.success(linksService.findAllCount());
    }

    @GetMapping(value = "/findAll")
    public ResponseCode findAll() {
        return ResponseCode.success(linksService.findAllCount());
    }

    @PostMapping(value = "/findByPage")
    public ResponseCode findByPage(QueryPage queryPage, Links links) {
        return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> linksService.findByPage(links)));
    }

    @GetMapping(value = "/findById")
    public ResponseCode findById(@RequestParam("id") Long id) {
        return ResponseCode.success(linksService.findById(id));
    }

    @PostMapping(value = "/save")
    public ResponseCode save(@RequestBody Links links) {
        try {
            linksService.save(links);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseCode update(@RequestBody Links links) {
        try {
            linksService.update(links);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping(value = "/delete")
    public ResponseCode delete(@RequestBody Long... ids) {
        try {
            linksService.delete(ids);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}