package com.example.orderservicenacos.web;

import com.example.orderservicenacos.service.EsService;
import com.example.resultful.web.Envelop;
import com.example.resultful.web.ObjEnvelop;
import com.example.resultful.web.endpoint.EnvelopRestEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Bing on 2023/5/15.
 */
@RestController
@RequestMapping(value = "es")
public class EsController extends EnvelopRestEndpoint {

    @Autowired
    private EsService esService;

    @GetMapping("getHotelDO/{id}")
    public ObjEnvelop getHotel(@PathVariable("id") Long id){

        return ObjEnvelop.getSuccess("查询成功",esService.getHotel(id));
    }

    @PostMapping("createEsIndex")
    public Envelop createEsIndex(@RequestParam(value = "index")String index,
                                 @RequestParam(value = "source")String source){

        try {
           if (esService.createEsIndex(index,source)){
               return Envelop.getSuccess("创建成功");
           }
            return Envelop.getError("创建失败");
        } catch (Exception e) {
            e.printStackTrace();
            return failedException(e);
        }
    }

    @GetMapping("existIndex")
    public ObjEnvelop existIndex(@RequestParam(value = "index")String index){
        try {
            return ObjEnvelop.getSuccess("查询成功",esService.existIndex(index));
        } catch (Exception e) {
            e.printStackTrace();
            return failedObjEnvelopException(e);
        }
    }

    @PostMapping("deleteEsIndex")
    public Envelop deleteEsIndex(@RequestParam(value = "index")String index){

        try {
            if (esService.deleteEsIndex(index)){
                return Envelop.getSuccess("删除成功");
            }
            return Envelop.getError("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return failedException(e);
        }
    }

    @PostMapping("addEsIndexDocument")
    public Envelop addEsIndexDocument(@RequestParam(value = "index")String index,
                                      @RequestParam(value = "id")Long  id){

        try {
            if (esService.addEsIndexDocument(index,id)){
                return Envelop.getSuccess("添加成功");
            }
            return Envelop.getError("添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            return failedException(e);
        }
    }

    @PostMapping("addEsIndexDocuments")
    public Envelop addEsIndexDocuments(@RequestParam(value = "index")String index){

        try {
            if (esService.addEsIndexDocuments(index)){
                return Envelop.getSuccess("添加成功");
            }
            return Envelop.getError("添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            return failedException(e);
        }
    }

    @GetMapping("getDocuments")
    public ObjEnvelop getDocuments(@RequestParam(value = "index")String index,
                                @RequestParam(value = "id")Long  id){

        try {
            return ObjEnvelop.getSuccess("查询成功",esService.getDocuments(index,id));
        } catch (Exception e) {
            e.printStackTrace();
            return failedObjEnvelopException(e);
        }
    }

    @PostMapping("updateDocument")
    public Envelop updateDocument(@RequestParam(value = "index")String index){

        try {
            if (esService.updateDocument(index)){
                return Envelop.getSuccess("修改成功");
            }
            return Envelop.getError("修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            return failedException(e);
        }
    }

    @PostMapping("deleteDocument")
    public Envelop deleteDocument(@RequestParam(value = "index")String index){

        try {
            if (esService.deleteDocument(index)){
                return Envelop.getSuccess("删除成功");
            }
            return Envelop.getError("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return failedException(e);
        }
    }
}
